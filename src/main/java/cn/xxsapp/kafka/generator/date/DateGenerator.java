package cn.xxsapp.kafka.generator.date;

import cn.xxsapp.kafka.generator.Generator;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class DateGenerator extends Generator<String> {

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
