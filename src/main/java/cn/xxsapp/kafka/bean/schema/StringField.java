package cn.xxsapp.kafka.bean.schema;

import java.util.Arrays;

public class StringField extends DataField {

    public StringField() {
        super("string");
    }

    private int length;

    private String[] words;

    private String filePath;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "StringField{" +
                "length=" + length +
                ", words=" + Arrays.toString(words) +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
