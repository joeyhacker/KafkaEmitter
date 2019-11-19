package cn.xxsapp.kafka.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Generator<T> implements Serializable {

    public abstract T getNext();

    public List<T> getList(int count){
        List<T> ret = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ret.add(getNext());
        }
        return ret;
    }
}
