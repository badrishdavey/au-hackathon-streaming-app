There are two ways of listening to the data in Kafka 
1) [Using the Kafka console echo consumer](https://github.com/badrishdavey/au-hackathon-streaming-app#using-the-kafka-console-echo-consumer)
2) [Using this Spark project](https://github.com/badrishdavey/au-hackathon-streaming-app#compiling-the-code)

# Using the Kafka console echo consumer

## Install Kafka

### Step 1

Download Kafka v0.11.0.1 for Scala 2.11
(One example mirror [here](http://apache.claz.org/kafka/0.11.0.1/kafka_2.11-0.11.0.1.tgz))

### Step 2

Move to desired location
```
cd ~/Downloads
mkdir -p /usr/local/Cellar/kafka/0.11.0.1
cp 
```

### Step 3

Unzip the archive
```
tar -zxvf kafka_2.11-0.11.0.1.tgz
```

### Step 4

Set environment variable KAFKA_HOME to the installation path
```
export KAFKA_HOME=/usr/local/Cellar/kafka/0.11.0.1
```

### Step 5

Run the shell script
```
$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server kafkastreaming.capitalonehackathon.com:9092 --topic au_hackathon
```

# Using this project

## Compiling the code

### Step 1

Clone this repository into local
```
git clone https://github.com/badrishdavey/au-hackathon-streaming-app.git
```

Cd into the directory
```
cd au-hackathon-streaming-app
```

Compile the code into jar
```
mvn clean package
```

### Step 2

Upload the Spark jar to your team directory in S3
![AWS S3 jar upload image](https://github.com/badrishdavey/au-hackathon-streaming-app/raw/master/AWS_S3_Upload.png "AWS S3 jar upload")

## Deploying onto AWS EMR

### Step 1

Create the EMR cluster

Name the cluster after your team

Select Spark as the Software configuration

Select the Hackathon pem file for the EC2 key pair
![AWS EMR create cluster image](https://github.com/badrishdavey/au-hackathon-streaming-app/raw/master/AWS_EMR_Create_Cluster.png "AWS EMR Create Cluster")

### Step 2

Wait for the EMR cluster to initialize
![AWS EMR cluster initialized image](https://github.com/badrishdavey/au-hackathon-streaming-app/raw/master/AWS_EMR_Cluster_Initialized.png "AWS EMR Cluster Initialized")

### Step 3

Download the Spark jar from S3 onto the EMR master
```
ssh -i ~/AU_Hackathon.pem hadoop@ec2-54-159-186-164.compute-1.amazonaws.com
aws s3 cp s3://auhackathon/omar/au-hackathon-streaming-0.1.jar .
```

### Step 4

Navigate to the Steps tab for the EMR cluster
Add step
```
Step type: Spark application
Deploy mode: Client
Spark-submit options: --master yarn --class com.test.App
Application location: /home/hadoop/au-hackathon-streaming-0.1.jar
Arguments: kafkastreaming.capitalonehackathon.com:9092 au_hackathon 5
Action on failure: Continue
```
![AWS EMR Add Step image](https://github.com/badrishdavey/au-hackathon-streaming-app/raw/master/AWS_EMR_Add_Step.png "AWS EMR Add Step")

### Step 5

Wait for the Spark job to start, approximately 5 minutes

Click on View logs

Click on stdout

![AWS EMR Success image](https://github.com/badrishdavey/au-hackathon-streaming-app/raw/master/AWS_EMR_Success.png "AWS EMR Success")