# AWS Elastic Beanstalk Deployment Quick Start Script for ByteBazaar (No Docker)
# Run this script in PowerShell as Administrator

Write-Host "=== ByteBazaar AWS Elastic Beanstalk Deployment ===" -ForegroundColor Cyan

# Check if AWS CLI is installed
Write-Host "Checking AWS CLI..." -ForegroundColor Yellow
if (!(Get-Command aws -ErrorAction SilentlyContinue)) {
    Write-Host "AWS CLI not found. Please install it first:" -ForegroundColor Red
    Write-Host "https://aws.amazon.com/cli/" -ForegroundColor Blue
    exit 1
}

# Check AWS credentials
Write-Host "Checking AWS credentials..." -ForegroundColor Yellow
try {
    $awsIdentity = aws sts get-caller-identity --output json | ConvertFrom-Json
    Write-Host "AWS Account: $($awsIdentity.Account)" -ForegroundColor Green
    Write-Host "AWS User: $($awsIdentity.Arn)" -ForegroundColor Green
} catch {
    Write-Host "AWS credentials not configured. Please run 'aws configure'" -ForegroundColor Red
    exit 1
}

# Check if EB CLI is installed
Write-Host "Checking EB CLI..." -ForegroundColor Yellow
if (!(Get-Command eb -ErrorAction SilentlyContinue)) {
    Write-Host "Installing EB CLI..." -ForegroundColor Green
    pip install awsebcli
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Failed to install EB CLI. Please install manually:" -ForegroundColor Red
        Write-Host "pip install awsebcli" -ForegroundColor Blue
        exit 1
    }
}

# Build the application
Write-Host "Building the Spring Boot application..." -ForegroundColor Green
mvn clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed! Please check your code and try again." -ForegroundColor Red
    exit 1
}

Write-Host "Build successful! JAR file created." -ForegroundColor Green

# Check if EB is already initialized
if (!(Test-Path ".elasticbeanstalk")) {
    Write-Host "Initializing Elastic Beanstalk..." -ForegroundColor Green
    Write-Host "Please configure the following when prompted:" -ForegroundColor Yellow
    Write-Host "1. Select your AWS region (recommended: us-west-2)" -ForegroundColor White
    Write-Host "2. Choose 'Java' as the platform" -ForegroundColor White
    Write-Host "3. Select 'Java 17 running on 64bit Amazon Linux 2'" -ForegroundColor White
    Write-Host "4. Do NOT set up SSH (unless you need it)" -ForegroundColor White
    
    eb init --platform java-17
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "EB initialization failed!" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "Elastic Beanstalk already initialized." -ForegroundColor Green
}

# Check if environment already exists
Write-Host "Checking for existing environments..." -ForegroundColor Yellow
$environments = eb list --output json | ConvertFrom-Json

if ($environments.Count -eq 0) {
    Write-Host "No environments found. Creating new environment..." -ForegroundColor Green
    eb create bytebazaar-prod --instance-type t3.micro --platform-version "Java 17 running on 64bit Amazon Linux 2/3.2.17"
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Environment creation failed!" -ForegroundColor Red
        exit 1
    }
    
    Write-Host "Environment created successfully!" -ForegroundColor Green
} else {
    Write-Host "Found existing environments:" -ForegroundColor Green
    $environments | ForEach-Object { Write-Host "  - $_" -ForegroundColor White }
}

# Deploy the application
Write-Host "Deploying application to Elastic Beanstalk..." -ForegroundColor Green
eb deploy

if ($LASTEXITCODE -eq 0) {
    Write-Host "Deployment successful!" -ForegroundColor Green
    
    # Get the application URL
    $status = eb status --verbose
    Write-Host "Application Status:" -ForegroundColor Yellow
    Write-Host $status -ForegroundColor White
    
    Write-Host "`nOpening application in browser..." -ForegroundColor Green
    eb open
    
    Write-Host "`n=== Deployment Complete! ===" -ForegroundColor Green
    Write-Host "Your Spring Boot application is now running on AWS!" -ForegroundColor Cyan
    Write-Host "`nUseful commands:" -ForegroundColor Yellow
    Write-Host "  eb status      - Check environment status" -ForegroundColor White
    Write-Host "  eb logs        - View application logs" -ForegroundColor White
    Write-Host "  eb open        - Open application in browser" -ForegroundColor White
    Write-Host "  eb deploy      - Deploy updates" -ForegroundColor White
    Write-Host "  eb terminate   - Terminate the environment" -ForegroundColor White
    Write-Host "`nAPI Documentation available at: [Your-URL]/swagger-ui/index.html" -ForegroundColor Cyan
} else {
    Write-Host "Deployment failed!" -ForegroundColor Red
    Write-Host "Check the logs with: eb logs" -ForegroundColor Yellow
    exit 1
}

Write-Host "`n=== Script completed ===" -ForegroundColor Cyan
