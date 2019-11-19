package cn.xxsapp.kafka.generator;

import cn.hutool.core.util.RandomUtil;
import cn.xxsapp.kafka.bean.schema.DataField;
import cn.xxsapp.kafka.bean.schema.DateField;
import cn.xxsapp.kafka.bean.schema.IntegerField;
import cn.xxsapp.kafka.bean.schema.StringField;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratorFactory {

    public static Generator getGenerator(DataField field) {
        String type = field.getType();
        String mode = field.getMode();
        switch (type) {
            case "integer":
                IntegerField iField = (IntegerField) field;
                if ("sequence".equalsIgnoreCase(mode)) {
                    return new IntegerSequenceGenerator(iField.getStart(), iField.getEnd(), iField.getStep());
                }
                if ("random".equalsIgnoreCase(mode)) {
                    return new IntegerRandomGenerator(iField.getStart(), iField.getEnd());
                }
                break;
            case "string":
                StringField sField = (StringField) field;
                if ("random".equalsIgnoreCase(mode)) {
                    return new StringRandomGenerator(sField.getLength());
                }
                if ("words".equalsIgnoreCase(mode)) {
                    return new StringWordsGenerator(sField.getWords());
                }
                if ("uuid".equalsIgnoreCase(mode)) {
                    return new StringUUIDGenerator();
                }
                break;
            case "date":
                DateField dField = (DateField) field;
                return new DateGenerator(dField.getStart(), dField.getFormat(), dField.getStep(), dField.getStepUnit());
            default:
                throw new RuntimeException("non support field type " + type);
        }
        throw new RuntimeException("non support mode " + mode + " for " + type + " generator");
    }
}

class DateGenerator extends Generator<String> {

    LocalDateTime baseTime;
    DateTimeFormatter formatter;
    TemporalUnit stepUnit;
    int step;

    LocalDateTime cursor;

    public DateGenerator(Date start, String format, int step, String stepUnit) {
        if (start == null) {
            start = new Date();
        }
        Instant instant = start.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime ldt = instant.atZone(zoneId).toLocalDateTime();
        this.baseTime = ldt;
        this.cursor = ldt;
        this.formatter = DateTimeFormatter.ofPattern(format);
        if (StringUtils.isBlank(stepUnit)) {
            stepUnit = "seconds";
        }
        if (step == 0) {
            step = 1;
        }
        this.stepUnit = ChronoUnit.valueOf(stepUnit.toUpperCase());
        this.step = step;
    }

    @Override
    public String getNext() {
        cursor = cursor.plus(step, stepUnit);
        return cursor.format(formatter);
    }
}

class StringUUIDGenerator extends Generator<String> {

    @Override
    public String getNext() {
        return UUID.randomUUID().toString();
    }
}

class StringWordsGenerator extends Generator<String> {

    String[] array;

    Random random;

    public StringWordsGenerator(String[] array) {
        this.array = array;
        this.random = new Random();
    }

    @Override
    public String getNext() {
        return array[random.nextInt(array.length)];
    }
}

class StringRandomGenerator extends Generator<String> {

    int length;

    public StringRandomGenerator(int length) {
        this.length = length;
    }

    @Override
    public String getNext() {
        return RandomStringUtils.random(length, true, false);
    }
}

class IntegerRandomGenerator extends Generator<Integer> {

    int start;

    int end;

    public IntegerRandomGenerator(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer getNext() {
        return RandomUtil.randomInt(start, end);
    }
}

class IntegerSequenceGenerator extends Generator<Integer> {

    int end;

    int step;

    AtomicInteger cursor;

    public IntegerSequenceGenerator(int start, int end, int step) {
        cursor = new AtomicInteger(start);
        if (step == 0) {
            step = 1;
        }
        this.step = step;
        this.end = end;
    }

    @Override
    public Integer getNext() {
        return cursor.getAndAdd(step);
    }
}
