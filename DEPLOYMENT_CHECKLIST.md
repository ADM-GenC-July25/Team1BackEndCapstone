# AWS Elastic Beanstalk Deployment Checklist (Web Console)

## Pre-Deployment ✅

- [ ] Built JAR file: Run `.\build-for-aws.ps1`
- [ ] JAR file exists: `target\ByteBazaar-0.0.1-SNAPSHOT.jar`
- [ ] AWS account access to console
- [ ] Database connection details ready

## AWS Console Steps ✅

### 1. Access Elastic Beanstalk
- [ ] Go to https://console.aws.amazon.com/
- [ ] Search for "Elastic Beanstalk"
- [ ] Click on Elastic Beanstalk service

### 2. Create Application
- [ ] Click "Create application"
- [ ] Application name: `ByteBazaar`
- [ ] Add tags (optional)

### 3. Environment Configuration
- [ ] Environment tier: Web server environment
- [ ] Environment name: `ByteBazaar-prod`
- [ ] Platform: Java
- [ ] Platform branch: Java 17 running on 64bit Amazon Linux 2
- [ ] Upload JAR file: `target\ByteBazaar-0.0.1-SNAPSHOT.jar`

### 4. Service Access
- [ ] Service role: Create new or use existing
- [ ] EC2 instance profile: Create new or use `aws-elasticbeanstalk-ec2-role`

### 5. Environment Properties (CRITICAL)
Copy these exactly:

```
SPRING_PROFILES_ACTIVE = production
SERVER_PORT = 5000
DATABASE_URL = jdbc:mysql://mysql-training.cd4qsem4uf1x.us-west-2.rds.amazonaws.com:3306/team1capstonedb
DATABASE_USERNAME = admin
DATABASE_PASSWORD = Be.Cognizant2025!
JWT_SECRET = bXlTZWNyZXRLZXlGb3JKV1RBdXRoZW50aWNhdGlvblNlY3VyaXR5S2V5MTIzNDU2Nzg5MA==
JWT_EXPIRATION = 86400000
```

### 6. Instance Settings
- [ ] Instance type: t3.micro
- [ ] Load balancer: Application Load Balancer

### 7. Review and Launch
- [ ] Review all settings
- [ ] Click "Submit"
- [ ] Wait 5-10 minutes for environment creation

## Post-Deployment ✅

- [ ] Environment shows "Ok" health status
- [ ] Application URL opens successfully
- [ ] Test API endpoint: `[URL]/actuator/health`
- [ ] Test Swagger docs: `[URL]/swagger-ui/index.html`
- [ ] Test authentication endpoints

## For Updates ✅

- [ ] Build new JAR: `.\build-for-aws.ps1`
- [ ] Go to environment dashboard
- [ ] Click "Upload and deploy"
- [ ] Select new JAR file
- [ ] Enter version label
- [ ] Click "Deploy"

## Monitoring ✅

- [ ] Check environment health in dashboard
- [ ] Review application logs if issues occur
- [ ] Monitor CPU and memory usage

## Cleanup (When Done) ✅

- [ ] Go to environment dashboard
- [ ] Actions → Terminate environment
- [ ] Confirm termination to stop charges

## ERROR RECOVERY ⚠️

If you see errors like "No default VPC" or "Security Group doesn't exist":

### Quick Fix Steps:
- [ ] Check you're in the correct AWS region (us-west-2 recommended)
- [ ] Go to VPC service in AWS Console
- [ ] Click "Actions" → "Create Default VPC" (if none exists)
- [ ] Go back to Elastic Beanstalk
- [ ] Terminate the failed environment: Actions → Terminate Environment
- [ ] Wait 5-10 minutes for cleanup
- [ ] Create new environment with these simplified settings:

### Simplified Environment Settings (Avoids VPC Issues):
- [ ] Environment tier: **Web server environment**
- [ ] Environment type: **Single instance** (not Load balanced)
- [ ] Platform: Java 17 running on 64bit Amazon Linux 2
- [ ] Instance type: t3.micro
- [ ] VPC: Default VPC (should appear after creating it)
- [ ] No load balancer needed for single instance

### Alternative Recovery:
- [ ] Run the error recovery script: `.\aws-error-recovery.ps1`

---

**Important Notes:**
- SERVER_PORT=5000 is required by Elastic Beanstalk
- Environment variables are case-sensitive
- Use Application Load Balancer for better performance
- t3.micro is free tier eligible for first 750 hours/month
