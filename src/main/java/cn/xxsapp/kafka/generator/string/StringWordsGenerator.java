package cn.xxsapp.kafka.generator.string;

import cn.xxsapp.kafka.generator.Generator;

import java.util.Random;

public class StringWordsGenerator extends Generator<String> {

    String[] array;

    Random random;

    public StringWordsGenerator(String[] array) {
        this.array = array;
        this.random = new Random();
    }

    @Override
    public String getNext() {
        return array[random.nextInt(array.length)];
    }
}