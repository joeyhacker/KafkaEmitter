package cn.xxsapp.kafka;

import cn.hutool.core.convert.Convert;
import cn.xxsapp.kafka.bean.ProducerConfigBean;
import cn.xxsapp.kafka.bean.schema.DateField;
import cn.xxsapp.kafka.bean.schema.IntegerField;
import cn.xxsapp.kafka.bean.schema.StringField;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfParser {

    private Map<String, Object> props;

    public ConfParser(Map props) {
        this.props = props;
    }

    private <T> T getObject(Map map, String key) {
        return (T) map.get(key);
    }

    public String getBroker() {
        List list = getObject(props, "broker");
        if (list == null)
            throw new RuntimeException("broker can not be null");
        return StringUtils.join(list, ",");
    }

    public Map<String, Object> getConfig(String id) {
        Map map;
        if (StringUtils.isBlank(id)) {
            map = getObject(props, "producer");
        } else {
            map = getObject(getObject(props, "producer"), id);
        }
        return map;
    }

    public List getSchema(String id) {
        Map map;
        if (StringUtils.isBlank(id)) {
            map = getObject(props, "schema");
        } else {
            map = getObject(getObject(props, "schema"), id);
        }
        List<Map> fields = (List) map.get("fields");
        List ret = new ArrayList();
        for (Map m : fields) {
            String type = (String) m.get("type");
            switch (type) {
                case "int":
                case "integer":
                    ret.add(Convert.convert(IntegerField.class, m));
                    break;
                case "str":
                case "string":
                    ret.add(Convert.convert(StringField.class, m));
                    break;
                case "date":
                    ret.add(Convert.convert(DateField.class, m));
                    break;
                default:
                    throw new RuntimeException("non support type " + type);
            }
        }
        return ret;
    }
}
