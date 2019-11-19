package cn.xxsapp.kafka.bean.schema;

import java.util.Date;

public class DateField extends DataField {

    public DateField() {
        super("date");
    }

    private String format;

    private Date start;

    private int step;

    private String stepUnit;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getStepUnit() {
        return stepUnit;
    }

    public void setStepUnit(String stepUnit) {
        this.stepUnit = stepUnit;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "DateField{" +
                "format='" + format + '\'' +
                ", start='" + start + '\'' +
                ", step=" + step +
                ", stepUnit='" + stepUnit + '\'' +
                '}';
    }
}
