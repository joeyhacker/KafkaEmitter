package cn.xxsapp.kafka;

import cn.xxsapp.kafka.bean.ProducerConfigBean;
import cn.xxsapp.kafka.bean.schema.DataField;
import cn.xxsapp.kafka.generator.Generator;
import cn.xxsapp.kafka.generator.GeneratorFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaProducer {

    static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaSender<String, String> sender;

    private String topic;

    private int limit;

    private int interval;

    private List<Generator> generators = new ArrayList<>();

    public KafkaProducer(Map config, String topic, int limit, int interval, String producerId, String schemaId) {
        this.topic = topic;
        this.limit = limit;
        this.interval = interval;

        ConfParser confParser = new ConfParser(config);
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, confParser.getBroker());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        ProducerConfigBean producerConfig = confParser.getProducerConfig(producerId);
        props.put(ProducerConfig.ACKS_CONFIG, producerConfig.getAcks());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, producerConfig.getBatchSize());

        List<DataField> schemaList = confParser.getSchema(schemaId);
        for (DataField field : schemaList) {
            Generator generator = GeneratorFactory.getGenerator(field);
            generators.add(generator);
        }

        SenderOptions<String, String> options = SenderOptions.create(props);
        sender = KafkaSender.create(options);
    }

    private ProducerRecord getRecord() {
        Object[] arr = new Object[generators.size()];
        for (int i = 0; i < generators.size(); i++) {
            arr[i] = generators.get(i).getNext();
        }
        String str = StringUtils.join(arr, ",");
        return new ProducerRecord<>(topic, str);
    }

    public void sendMessages(CountDownLatch latch) {
        //build source
        Flux source = Flux.generate(() -> new AtomicInteger(0), (counter, sink) -> {
            if (counter.get() == limit) {
                sink.complete();
            } else {
                sink.next(SenderRecord.create(getRecord(), counter.get()));
                counter.incrementAndGet();
                if (interval != 0) {
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return counter;
        });
        // send messages
        sender.send(source).doOnError(e -> {
            logger.error("send error !", e);
        }).subscribe(ret -> {
            if (latch.getCount() % 100 == 0) {
                System.out.println(latch.getCount() + " message will be send, info: " + ret);
            }
            latch.countDown();
        });
    }

    public void close() {
        sender.close();
    }

    public static void main(String[] args) throws Exception {
//        int count = 200;
//        CountDownLatch latch = new CountDownLatch(count);
//        KafkaProducer producer = new KafkaProducer();
//        producer.sendMessages(TOPIC, count, latch);
//        latch.await(10, TimeUnit.SECONDS);
//        producer.close();
    }
}
