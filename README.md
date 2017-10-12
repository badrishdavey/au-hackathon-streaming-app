# Deploying onto AWS EMR

It is recommended that the Spark job takes its configuration through the command line

Upload the Spark jar to S3
![AWS S3 jar upload image](https://github.com/badrishdavey/au-hackathon-streaming-app/raw/master/AWS_S3_Upload.png "AWS S3 jar upload")

Create the EMR cluster
Name the cluster after your team
Select Spark as the Software configuration
Select the Hackathon pem file for the EC2 key pair
![AWS EMR create cluster image](https://github.com/badrishdavey/au-hackathon-streaming-app/raw/master/AWS_EMR_Create_Cluster.png "AWS EMR Create Cluster")

Wait for the EMR cluster to initialize
![AWS EMR cluster initialized image](https://github.com/badrishdavey/au-hackathon-streaming-app/raw/master/AWS_EMR_Cluster_Initialized.png "AWS EMR Cluster Initialized")

Download the Spark jar from S3 onto the EMR master
```
ssh -i <Hackathon pem file> hadoop@<EMR address>
aws s3 cp s3://auhackathon/omar/au-hackathon-streaming-0.1.jar .
```

Navigate to the Steps tab for the EMR cluster
Add step
```
Step type: Spark application
Deploy mode: Client
Spark-submit options: --master yarn --class <your driver class>
Application location: <absolute path to your jarfile in the EMR master>
Arguments: <your command line arguments>
Action on failure: Continue
```
![AWS EMR Add Step image](https://github.com/badrishdavey/au-hackathon-streaming-app/raw/master/AWS_EMR_Add_Step.png "AWS EMR Add Step")