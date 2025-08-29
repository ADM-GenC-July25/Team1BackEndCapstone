# Build Script for Web Console Deployment
# Run this script to prepare your JAR file for upload

Write-Host "=== ByteBazaar Build for AWS Deployment ===" -ForegroundColor Cyan

# Build the application
Write-Host "Building Spring Boot application..." -ForegroundColor Green
mvn clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed! Please check your code and try again." -ForegroundColor Red
    exit 1
}

Write-Host "Build successful!" -ForegroundColor Green

# Check if JAR file exists
$jarFile = "target\ByteBazaar-0.0.1-SNAPSHOT.jar"
if (Test-Path $jarFile) {
    $jarSize = (Get-Item $jarFile).Length / 1MB
    Write-Host "JAR file created: $jarFile" -ForegroundColor Green
    Write-Host "File size: $([math]::Round($jarSize, 2)) MB" -ForegroundColor Yellow
    
    Write-Host "`n=== Next Steps ===" -ForegroundColor Cyan
    Write-Host "1. Go to AWS Console: https://console.aws.amazon.com/" -ForegroundColor White
    Write-Host "2. Navigate to Elastic Beanstalk service" -ForegroundColor White
    Write-Host "3. Create new application or upload to existing environment" -ForegroundColor White
    Write-Host "4. Upload this file: $jarFile" -ForegroundColor Yellow
    Write-Host "5. Set environment variables as described in AWS_DEPLOYMENT_GUIDE.md" -ForegroundColor White
    
    Write-Host "`n=== Important Environment Variables ===" -ForegroundColor Cyan
    Write-Host "SPRING_PROFILES_ACTIVE=production" -ForegroundColor White
    Write-Host "SERVER_PORT=5000" -ForegroundColor White
    Write-Host "DATABASE_URL=jdbc:mysql://mysql-training.cd4qsem4uf1x.us-west-2.rds.amazonaws.com:3306/team1capstonedb" -ForegroundColor White
    Write-Host "DATABASE_USERNAME=admin" -ForegroundColor White
    Write-Host "DATABASE_PASSWORD=Be.Cognizant2025!" -ForegroundColor White
    Write-Host "JWT_SECRET=bXlTZWNyZXRLZXlGb3JKV1RBdXRoZW50aWNhdGlvblNlY3VyaXR5S2V5MTIzNDU2Nzg5MA==" -ForegroundColor White
    Write-Host "JWT_EXPIRATION=86400000" -ForegroundColor White
    
} else {
    Write-Host "JAR file not found! Build may have failed." -ForegroundColor Red
    exit 1
}

Write-Host "`n=== Ready for deployment! ===" -ForegroundColor Green
