package cn.xxsapp.kafka.bean.schema;

public class Field {

    public Field(String type) {
        this.type = type;
    }

    public Field(String type, String mode) {
        this.type = type;
        this.mode = mode;
    }

    private String type;

    private String name;

    private String mode;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
