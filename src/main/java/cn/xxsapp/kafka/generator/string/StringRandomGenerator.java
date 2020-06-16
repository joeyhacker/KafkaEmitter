package cn.xxsapp.kafka.generator.string;

import cn.xxsapp.kafka.generator.Generator;
import org.apache.commons.lang3.RandomStringUtils;

public class StringRandomGenerator extends Generator<String> {

    int length;

    public StringRandomGenerator(int length) {
        this.length = length;
    }

    @Override
    public String getNext() {
        return RandomStringUtils.random(length, true, false);
    }
}