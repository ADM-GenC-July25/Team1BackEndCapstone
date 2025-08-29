# Simple Elastic Beanstalk Deployment Script (PowerShell)

Write-Host "=== ByteBazaar Elastic Beanstalk Deployment ===" -ForegroundColor Cyan

# Build the application
Write-Host "Building Spring Boot application..." -ForegroundColor Green
mvn clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed!" -ForegroundColor Red
    exit 1
}

Write-Host "Build successful!" -ForegroundColor Green

# Deploy to Elastic Beanstalk
Write-Host "Deploying to Elastic Beanstalk..." -ForegroundColor Green
eb deploy

if ($LASTEXITCODE -eq 0) {
    Write-Host "Deployment successful!" -ForegroundColor Green
    Write-Host "Opening application..." -ForegroundColor Green
    eb open
} else {
    Write-Host "Deployment failed! Check logs with 'eb logs'" -ForegroundColor Red
    exit 1
}
