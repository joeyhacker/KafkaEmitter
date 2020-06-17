package cn.xxsapp.kafka.generator.string;

import cn.xxsapp.kafka.generator.Generator;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class StringWordsGenerator extends Generator<String> {

    String[] array;

    Random random = new Random();

    AtomicInteger index = new AtomicInteger(0);

    boolean isRan;

    public StringWordsGenerator(String[] array, boolean isRan) {
        this.array = array;
        this.isRan = isRan;
    }

    @Override
    public String getNext() {
        if (array != null && array.length > 0) {
            if (isRan) {
                return array[random.nextInt(array.length)];
            } else {
                int idx = index.getAndUpdate(n -> {
                    if (array.length - 1 <= n) {
                        return 0;
                    } else {
                        return n + 1;
                    }
                });
                return array[idx];
            }
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
    }
}