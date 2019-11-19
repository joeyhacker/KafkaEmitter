package cn.xxsapp.kafka.bean.schema;

public class IntegerField extends DataField {

    public IntegerField() {
        super("integer");
    }

    private String range;

    private int start;

    private int end;

    private int step;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }


    @Override
    public String toString() {
        return "IntegerField{" +
                "range='" + range + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", step=" + step +
                '}';
    }
}
