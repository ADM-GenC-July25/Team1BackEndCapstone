#!/bin/bash

# Stop the Spring Boot application
echo "Stopping ByteBazaar application..."

# Find and kill the Java process running ByteBazaar
PID=$(ps -ef | grep "ByteBazaar" | grep -v grep | awk '{print $2}')

if [ -n "$PID" ]; then
    echo "Killing process $PID"
    kill -9 $PID
    echo "Application stopped successfully"
else
    echo "No running ByteBazaar application found"
fi

# Wait a moment for the process to fully stop
sleep 5
