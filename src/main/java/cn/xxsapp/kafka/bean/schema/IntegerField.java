package cn.xxsapp.kafka.bean.schema;

public class IntegerField extends Field {

    public static final String MODE_SEQ = "seq";
    public static final String MODE_RAN = "ran";

    public IntegerField() {
        super("integer");
    }

    public IntegerField(int start, int step) {
        super("integer", MODE_SEQ);
        this.start = start;
        this.step = step;
    }

    private int start;

    private int end;

    private int step;

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
                "start=" + start +
                ", end=" + end +
                ", step=" + step +
                '}';
    }
}
