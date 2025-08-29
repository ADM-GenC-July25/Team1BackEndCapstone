#!/bin/bash

# Install Java 17 if not already installed
echo "Installing Java 17..."

# Update package manager
yum update -y

# Install Amazon Corretto 17
yum install -y java-17-amazon-corretto-devel

# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto' >> /etc/environment

# Create application directory
mkdir -p /opt/bytebazaar/logs
chown -R ec2-user:ec2-user /opt/bytebazaar

echo "Java installation and setup completed"
