package cn.xxsapp.kafka.bean.schema;

import java.util.Arrays;

public class StringField extends Field {

    public static final String MODE_WORDS = "words";
    public static final String MODE_RAN = "ran";
    public static final String MODE_UUID = "uuid";

    private int length;

    private String[] words;

    public StringField() {
        super("string");
    }

    public StringField(int length) {
        super("string", MODE_RAN);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "StringField{" +
                "length=" + length +
                ", words=" + Arrays.toString(words) +
                '}';
    }
}
