package cn.xxsapp.kafka;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Boot {

    public static void main(String[] args) throws Throwable {

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("c", "config", true, "config file in YAML format.");
        options.addOption("t", "topic", true, "the kafka topic to send.");
        options.addOption("l", "limit", true, "how much data needs to be sent.");
        options.addOption("i", "interval", true, "the interval between two massages.");
        options.addOption("P", "producerId", true, "the producer id.");
        options.addOption("S", "schemaId", true, "the schema id.");
        options.addOption("h", "help", true, "show some help if need.");

        CommandLine cLine = parser.parse(options, args);

        if (!cLine.hasOption("c")) {
            System.out.println("config path can't be null.");
            return;
        }
        if (!cLine.hasOption("t")) {
            System.out.println("topic can't be null.");
            return;
        }

        String topic = cLine.getOptionValue("t");
        String path = cLine.getOptionValue("c");

        String _limit = cLine.getOptionValue("l");
        String _interval = cLine.getOptionValue("i");

        String producerId = cLine.getOptionValue("p");
        String schemaId = cLine.getOptionValue("s");

        if (!FileUtil.exist(path)) {
            System.out.println("config file is not exist.");
            return;
        }

        String str = FileUtil.readString(path, Charset.defaultCharset());
        Yaml yaml = new Yaml();
        Map conf = yaml.load(str);

        int limit = 100;
        if (StringUtils.isNotBlank(_limit)) {
            limit = Integer.valueOf(_limit);
        }

        int interval = 0;
        if (StringUtils.isNotBlank(_interval)) {
            interval = Integer.valueOf(_interval);
        }

        KafkaProducer producer = new KafkaProducer(conf, topic, limit, interval, producerId, schemaId);
        CountDownLatch latch = new CountDownLatch(limit);
        producer.sendMessages(latch);

        latch.await();
        Thread.sleep(1000);
        producer.close();
    }

}
