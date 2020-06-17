### Kafka数据生成器
用来快速模拟消息数据, 支持描述式schema， 支持多数据类型

#### 使用方法:

1. 打包。 mvn clean package, 在target中生成可执行jar文件: kafka-emitter-jar-with-dependencies.jar
2. 执行。 java -jar target/kafka-emitter-jar-with-dependencies.jar  -c src/main/resources/conf.yaml

#### 命令行参数:
 * -c: 配置文件路径 
 * -t: 指定kafka的topic
 * -l: 发送消息数量, 不指定则无限发
 * -i: 发送每条消息的时间间隔, 不指定则间隔1ms
 * -P: 指定producer配置, 不指定则使用producer节点下的配置
 * -S: 指定schema配置, 不指定则使用schema节点下的配置
 * -T: 发送消息的线程数, 不指定使用单线程

#### 配置文件:
示例
```yaml
producer:
  bootstrap.servers:
    - "localhost:9092"
  acks: "-1"

schema:
  fields:
    -
      name: id
      type: integer
      mode: seq
    -
      name: name
      type: string
      mode: ran
      length: 6
    -
      name: createTime
      type: date
      format: yyyy-MM-dd HH:mm:ss
      start: 2019-01-01 00:00:00
```
支持的数据类型: 整数-integer, 字符串string, date-日期时间

| 类型 | 模式 | 参数 |
| - | - | - |
| 1 | 2 | 3 | 


#### 示例:
 * java -jar target/kafka-emitter-jar-with-dependencies.jar  -c src/main/resources/conf.yaml -t test1 -i 500 -l 5
 * ![avatar](/snapshot/WX20200617-161104.png)   
 * ![avatar](/snapshot/WX20200617-160707.png) 