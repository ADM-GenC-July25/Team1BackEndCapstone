# AWS Error Recovery Script
# Use this when you encounter VPC or Security Group errors

Write-Host "=== AWS Elastic Beanstalk Error Recovery ===" -ForegroundColor Red

Write-Host "`nCommon errors you might be seeing:" -ForegroundColor Yellow
Write-Host "- No default VPC for this user" -ForegroundColor White
Write-Host "- AWSEBSecurityGroup does not exist" -ForegroundColor White
Write-Host "- You do not have permission to perform the 'ec2:AssociateAddress' action" -ForegroundColor White
Write-Host "- EC2 instances failed to communicate with AWS Elastic Beanstalk" -ForegroundColor White
Write-Host "- Stack creation failed" -ForegroundColor White

Write-Host "`n=== SOLUTION STEPS ===" -ForegroundColor Cyan

Write-Host "`n1. CHECK YOUR AWS REGION" -ForegroundColor Green
Write-Host "   - Make sure you're in the correct AWS region (e.g., us-west-2)" -ForegroundColor White
Write-Host "   - Your database is in us-west-2, so use that region" -ForegroundColor White

Write-Host "`n2. CREATE DEFAULT VPC (if needed)" -ForegroundColor Green
Write-Host "   - Go to AWS Console → VPC service" -ForegroundColor White
Write-Host "   - Click 'Actions' → 'Create Default VPC'" -ForegroundColor White
Write-Host "   - Wait for creation to complete" -ForegroundColor White

Write-Host "`n3. TERMINATE FAILED ENVIRONMENT" -ForegroundColor Green
Write-Host "   - Go to Elastic Beanstalk console" -ForegroundColor White
Write-Host "   - Find your failed environment" -ForegroundColor White
Write-Host "   - Actions → Terminate Environment" -ForegroundColor White
Write-Host "   - Wait 5-10 minutes for cleanup" -ForegroundColor White

Write-Host "`n4. RECREATE ENVIRONMENT" -ForegroundColor Green
Write-Host "   - Create new environment" -ForegroundColor White
Write-Host "   - Select default VPC when prompted" -ForegroundColor White
Write-Host "   - Choose subnets in different availability zones" -ForegroundColor White

Write-Host "`n5. ALTERNATIVE: Use Single Instance" -ForegroundColor Green
Write-Host "   - Instead of Load Balanced environment" -ForegroundColor White
Write-Host "   - Choose 'Single Instance' environment type" -ForegroundColor White
Write-Host "   - This requires less networking setup" -ForegroundColor White

Write-Host "`n=== STEP-BY-STEP RECOVERY ===" -ForegroundColor Cyan

$region = Read-Host "`nWhat AWS region are you using? (e.g., us-west-2)"
Write-Host "Setting region to: $region" -ForegroundColor Green

Write-Host "`nNext steps:" -ForegroundColor Yellow
Write-Host "1. Open AWS Console: https://console.aws.amazon.com/" -ForegroundColor White
Write-Host "2. Ensure you're in region: $region" -ForegroundColor White
Write-Host "3. Go to VPC service and check if default VPC exists" -ForegroundColor White
Write-Host "4. If no default VPC, create one" -ForegroundColor White
Write-Host "5. Go to Elastic Beanstalk and terminate failed environment" -ForegroundColor White
Write-Host "6. Create new environment with proper VPC selection" -ForegroundColor White

Write-Host "`n=== SIMPLIFIED ENVIRONMENT SETTINGS ===" -ForegroundColor Cyan
Write-Host "To avoid networking issues, use these settings:" -ForegroundColor Yellow
Write-Host "- Environment tier: Web server environment" -ForegroundColor White
Write-Host "- Environment type: Single instance (not Load balanced)" -ForegroundColor White
Write-Host "- Platform: Java 17" -ForegroundColor White
Write-Host "- Instance type: t3.micro" -ForegroundColor White
Write-Host "- VPC: Default VPC" -ForegroundColor White

Write-Host "`n=== CRITICAL ENVIRONMENT VARIABLES ===" -ForegroundColor Cyan
Write-Host "Don't forget to set these:" -ForegroundColor Yellow
Write-Host "SERVER_PORT=5000" -ForegroundColor White
Write-Host "SPRING_PROFILES_ACTIVE=production" -ForegroundColor White
Write-Host "DATABASE_URL=jdbc:mysql://mysql-training.cd4qsem4uf1x.us-west-2.rds.amazonaws.com:3306/team1capstonedb" -ForegroundColor White

Write-Host "`nIf you continue having issues, try creating a Single Instance environment instead of Load Balanced." -ForegroundColor Green
