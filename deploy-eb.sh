#!/bin/bash

# Build and deploy to AWS Elastic Beanstalk

echo "Building application..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi

echo "Deploying to Elastic Beanstalk..."
eb deploy

if [ $? -eq 0 ]; then
    echo "Deployment successful!"
    eb open
else
    echo "Deployment failed!"
    exit 1
fi
