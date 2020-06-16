package cn.xxsapp.kafka;

import cn.xxsapp.kafka.bean.schema.Field;
import cn.xxsapp.kafka.generator.Generator;
import cn.xxsapp.kafka.generator.GeneratorFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import reactor.kafka.sender.SenderRecord;

import java.util.ArrayList;
import java.util.List;

public class RecordFactory {

    private List<Generator> generators = new ArrayList<>();

    private RecordFactory(List<Field> schema) {
        for (Field field : schema) {
            Generator generator = GeneratorFactory.getGenerator(field);
            generators.add(generator);
        }
    }

    public static RecordFactory getInstance(List<Field> schema) {
        return new RecordFactory(schema);
    }

    public ProducerRecord<Long, String> getRecord(String topic, Long key, String sep) {
        Object[] rec = new Object[generators.size()];
        for (int i = 0; i < generators.size(); i++) {
            rec[i] = generators.get(i).getNext();
        }
        String str = StringUtils.join(rec, sep);
        return new ProducerRecord<>(topic, key, str);
    }
}
