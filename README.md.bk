# kafka &middot; spark streaming example


### Prerequisites

Java 1.8 or newer version required because lambda expression used for few cases

1. Java >= 1.8 (Oracle JDK has been tested)
2. Maven >= 3
3. Apache Spark >= 2.0.2
4. Kafka >= 0.10.1.0

### Installation

First of all, clone the git repository,

```bash
$ git clone https://github.com/badrishdavey/au-hackathon-streaming-app.git
```

after you need to use Maven for creating uber jar files,

```bash
$ mvn clean package -DskipTests
```

until that moment we had created jar files and now we'll install Kafka and MySQL,

```bash
$ wget http://www-us.apache.org/dist/kafka/0.10.1.0/kafka_2.11-0.10.1.0.tgz
$ # or wget http://www-eu.apache.org/dist/kafka/0.10.1.0/kafka_2.11-0.10.1.0.tgz
$ tar -xf kafka_2.11-0.10.1.0.tgz
$ cd kafka_2.11-0.10.1.0
$ nohup ./bin/zookeeper-server-start.sh ./config/zookeeper.properties > /tmp/kafka-zookeeper.out 2>&1 &
$ nohup ./bin/kafka-server-start.sh ./config/server.properties > /tmp/kafka-server.out 2>&1 &
```


### Usage

1 - Start the Spark streaming service and it'll process events from Kafka topic to MySQL,

```bash
$ cd kafka-spark-streaming-example
$ java -Dconfig=./config/common.conf -jar streaming/target/spark-streaming-0.1.jar
```

2 - Start the Kafka producer and it'll write events to Kafka topic,

```bash
$ java -Dconfig=./config/common.conf -jar producer/target/kafka-producer-0.1.jar
```

3 - Start the web server so you can see the dashboard

```bash
$ java -Dconfig=./config/common.conf -jar web/target/web-0.1.jar
```

4 - If everything look fine, please enter the dashboard address,

```bash
open http://localhost:8080 # default value : 8080
```

5 - Create topic

```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 4 --topic spark_sql_test_topic
```



5 - Submitting spark submit
```
/Users/shiv/Downloads/spark-2.2.0-bin-hadoop2.7/bin/spark-submit \
--master spark://Badrishs-MBP.home:7077 \
--deploy-mode client \
--conf spark.driver.extraJavaOptions="-Dconfig=/Users/shiv/development/research/au-hackathon/kafka-spark-streaming-example/config/common.conf" \
--conf spark.executor.extraJavaOptions="-Dconfig=/Users/shiv/development/research/au-hackathon/kafka-spark-streaming-example/config/common.conf" \
--class com.test.App \
streaming/target/spark-streaming-0.1.jar
```
