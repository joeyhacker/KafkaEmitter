package cn.xxsapp.kafka.generator.string;

import cn.xxsapp.kafka.generator.Generator;

import java.util.UUID;

public class StringUUIDGenerator extends Generator<String> {

    @Override
    public String getNext() {
        return UUID.randomUUID().toString();
    }
}