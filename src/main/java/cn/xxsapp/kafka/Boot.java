package cn.xxsapp.kafka;

import cn.hutool.core.io.FileUtil;
import cn.xxsapp.kafka.bean.schema.Field;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Boot {

    public static void main(String[] args) throws Throwable {

        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("c", "config", true, "config file in YAML format.");
        options.addOption("t", "topic", true, "the kafka topic to send.");
        options.addOption("l", "limit", true, "how much data needs to be sent.");
        options.addOption("i", "interval", true, "the interval between two massages.");
        options.addOption("P", "producer", true, "the producer id.");
        options.addOption("S", "schema", true, "the schema id.");
        options.addOption("T", "thread", true, "thread count for sending message.");
        options.addOption("h", "help", true, "show some help if need.");

        CommandLine cLine = parser.parse(options, args);

        if (!cLine.hasOption("c")) {
            System.out.println("config path can't be empty.");
            return;
        }
        if (!cLine.hasOption("t")) {
            System.out.println("topic can't be empty.");
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

        int limit = Integer.MAX_VALUE;
        if (StringUtils.isNotBlank(_limit)) {
            limit = Integer.valueOf(_limit);
        }

        int interval = 1;
        if (StringUtils.isNotBlank(_interval)) {
            interval = Integer.valueOf(_interval);
        }

        ConfParser confParser = new ConfParser(conf);
        Map<String, Object> props = confParser.getConfig(producerId);
        if (props == null || props.size() == 0) {
            System.out.println("producer's prop can't be found.");
            return;
        }
        List<Field> schema = confParser.getSchema(schemaId);
        if (schema == null || schema.size() == 0) {
            System.out.println("schema can't be found.");
            return;
        }

        MessageSender sender =
                MessageSender.getBuilder().setInterval(interval).setLimit(limit).setProps(props).setSchema(schema).setTopic(topic).build(1);

        CountDownLatch latch = new CountDownLatch(limit);
        sender.start(latch);

        latch.await();

        System.out.println("Send Done.");
    }

}
