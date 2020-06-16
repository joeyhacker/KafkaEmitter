package cn.xxsapp.kafka.generator.integer;

import cn.hutool.core.util.RandomUtil;
import cn.xxsapp.kafka.generator.Generator;

public class IntegerRandomGenerator extends Generator<Integer> {

    int start;

    int end;

    public IntegerRandomGenerator(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer getNext() {
        return RandomUtil.randomInt(start, end);
    }
}