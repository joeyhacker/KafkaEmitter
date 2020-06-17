package cn.xxsapp.kafka;

import cn.xxsapp.kafka.bean.schema.Field;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class MessageSender {

    private List<KafkaProducer> producers = new ArrayList<>();

    private int limit;

    private int interval;

    private String topic;

    private String separator = ",";

    private List<Field> schema;

    private MessageSender() {
    }

    public void addProducer(KafkaProducer producer) {
        this.producers.add(producer);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public void start(CountDownLatch latch) {
        final RecordFactory rf = RecordFactory.getInstance(schema);
        Flux.generate(() -> new AtomicLong(0),
                (AtomicLong counter, SynchronousSink<ProducerRecord<Long, String>> sink) -> {
                    if (counter.get() == limit) {
                        sink.complete();
                    } else {
                        sink.next(rf.getRecord(topic, counter.getAndIncrement(), separator));
                    }
                    return counter;
                }).delayElements(Duration.ofMillis(interval)).map(rec -> {
            long k = rec.key();
            int n = Long.valueOf(k % producers.size()).intValue();
            producers.get(n).send(rec);
            return k;
        }).publishOn(Schedulers.parallel()).doOnComplete(() -> {
            for (KafkaProducer kp : producers) {
                try {
                    kp.flush();
                    kp.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).subscribe(ret -> {
            latch.countDown();
        });
    }

    static class Builder {
        private Map props;
        private MessageSender sender;

        private Builder() {
            sender = new MessageSender();
        }

        public Builder setProps(Map props) {
            this.props = props;
            return this;
        }

        public Builder setTopic(String topic) {
            sender.topic = topic;
            return this;
        }

        public Builder setLimit(int limit) {
            sender.limit = limit;
            return this;
        }

        public Builder setInterval(int interval) {
            sender.interval = interval;
            return this;
        }

        public Builder setSchema(List<Field> schema) {
            sender.schema = schema;
            return this;
        }

        public MessageSender build(int num) {
            for (int i = 0; i < num; i++) {
                KafkaProducer kp = new KafkaProducer(props, new LongSerializer(), new StringSerializer());
                sender.addProducer(kp);
            }
            return sender;
        }
    }

    public static void main(String[] args) {
//        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG
//        Map props = new HashMap();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//
//        List<Field> schema = new ArrayList<>();
//        schema.add(new StringField(6));
//        schema.add(new IntegerField(0, 5));
//
//        MessageSender sender =
//                MessageSender.getBuilder()
//                        .setTopic("test4")
//                        .setLimit(100)
//                        .setInterval(100)
//                        .setProps(props)
//                        .setSchema(schema)
//                        .build(2);
//
//        new Thread(sender).start();
//
//        try {
//            Thread.sleep(1000 * 60);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
