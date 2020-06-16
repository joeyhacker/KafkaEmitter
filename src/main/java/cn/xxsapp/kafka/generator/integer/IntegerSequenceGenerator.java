package cn.xxsapp.kafka.generator.integer;

import cn.xxsapp.kafka.generator.Generator;

import java.util.concurrent.atomic.AtomicInteger;

public class IntegerSequenceGenerator extends Generator<Integer> {

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