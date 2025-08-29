#!/bin/bash

# Start the Spring Boot application
echo "Starting ByteBazaar application..."

# Navigate to the application directory
cd /opt/bytebazaar

# Set JAVA_HOME if not already set
export JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto

# Start the application in the background
nohup $JAVA_HOME/bin/java -jar ByteBazaar-0.0.1-SNAPSHOT.jar \
    --server.port=8080 \
    --spring.profiles.active=prod \
    > /opt/bytebazaar/logs/application.log 2>&1 &

# Get the process ID
PID=$!
echo "ByteBazaar application started with PID: $PID"

# Wait a moment and check if the process is still running
sleep 10
if ps -p $PID > /dev/null; then
    echo "Application started successfully"
    exit 0
else
    echo "Failed to start application"
    exit 1
fi
