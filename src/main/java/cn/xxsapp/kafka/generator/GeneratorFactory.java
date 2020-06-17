package cn.xxsapp.kafka.generator;

import cn.xxsapp.kafka.bean.schema.Field;
import cn.xxsapp.kafka.bean.schema.DateField;
import cn.xxsapp.kafka.bean.schema.IntegerField;
import cn.xxsapp.kafka.bean.schema.StringField;
import cn.xxsapp.kafka.generator.date.DateGenerator;
import cn.xxsapp.kafka.generator.integer.IntegerRandomGenerator;
import cn.xxsapp.kafka.generator.integer.IntegerSequenceGenerator;
import cn.xxsapp.kafka.generator.string.StringRandomGenerator;
import cn.xxsapp.kafka.generator.string.StringUUIDGenerator;
import cn.xxsapp.kafka.generator.string.StringWordsGenerator;


public class GeneratorFactory {

    public static Generator getGenerator(Field field) {
        String type = field.getType();
        String mode = field.getMode();
        switch (type) {
            case "integer":
                IntegerField iField = (IntegerField) field;
                if (IntegerField.MODE_SEQ.equalsIgnoreCase(mode)) {
                    return new IntegerSequenceGenerator(iField.getStart(), iField.getEnd(), iField.getStep());
                }
                if (IntegerField.MODE_RAN.equalsIgnoreCase(mode)) {
                    return new IntegerRandomGenerator(iField.getStart(), iField.getEnd());
                }
                break;
            case "string":
                StringField sField = (StringField) field;
                if (StringField.MODE_RAN.equalsIgnoreCase(mode)) {
                    return new StringRandomGenerator(sField.getLength());
                }
                if (StringField.MODE_WORDS.equalsIgnoreCase(mode)) {
                    return new StringWordsGenerator(sField.getWords(), sField.isRan());
                }
                if (StringField.MODE_UUID.equalsIgnoreCase(mode)) {
                    return new StringUUIDGenerator();
                }
                break;
            case "date":
                DateField dField = (DateField) field;
                return new DateGenerator(dField.getStart(), dField.getFormat(), dField.getStep(), dField.getStepUnit());
            default:
                throw new RuntimeException("non support field type " + type);
        }
        throw new RuntimeException("non support mode " + mode + " for " + type + " generator");
    }
}







