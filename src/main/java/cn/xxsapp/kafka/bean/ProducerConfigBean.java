package cn.xxsapp.kafka.bean;

public class ProducerConfigBean {

    private String acks;
    private int batchSize;

    public ProducerConfigBean(String acks) {
        this.acks = acks;
    }

    public ProducerConfigBean(String acks, int batchSize) {
        this.acks = acks;
        this.batchSize = batchSize;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public String toString() {
        return "ProducerConfig{" +
                "acks=" + acks +
                ", batchSize=" + batchSize +
                '}';
    }
}
