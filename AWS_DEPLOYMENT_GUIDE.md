# AWS Elastic Beanstalk Deployment Guide for ByteBazaar (Web Console)

## Prerequisites
- Java 17 and Maven installed
- AWS account with web console access
- Web browser

## Deployment Steps

### Step 1: Build Your Application

Build the JAR file using Maven:
```bash
mvn clean package -DskipTests
```

This creates: `target/ByteBazaar-0.0.1-SNAPSHOT.jar`

### Step 2: Access AWS Elastic Beanstalk Console

1. Go to the [AWS Console](https://console.aws.amazon.com/)
2. Sign in to your AWS account
3. Search for "Elastic Beanstalk" in the services menu
4. Click on "Elastic Beanstalk"

### Step 3: Create New Application

1. Click **"Create application"**
2. Fill in the application details:
   - **Application name**: `ByteBazaar`
   - **Application tags** (optional): Add any tags you want

### Step 4: Configure Environment

1. **Environment tier**: Select **"Web server environment"**
2. **Environment information**:
   - **Environment name**: `ByteBazaar-prod` (or your choice)
   - **Domain**: Choose an available domain name
3. **Platform**:
   - **Platform type**: Java
   - **Platform branch**: Java 17 running on 64bit Amazon Linux 2
   - **Platform version**: Use the recommended version
4. **Application code**:
   - Select **"Upload your code"**
   - **Source code origin**: Local file
   - Click **"Choose file"** and select: `target/ByteBazaar-0.0.1-SNAPSHOT.jar`
   - **Version label**: `v1.0` (or your choice)

### Step 5: Configure Service Access (Important)

1. **Service role**: 
   - If you have an existing role, select it
   - If not, select **"Create and use new service role"**
2. **EC2 key pair**: Skip (not needed for basic deployment)
3. **EC2 instance profile**:
   - Select **"Create and use new instance profile"** 
   - Or use existing `aws-elasticbeanstalk-ec2-role` if available

### Step 6: Set Up Networking (Optional)

- **VPC**: Use default VPC (recommended for beginners)
- **Instance subnets**: Select available subnets
- **Database**: Skip (you're using external RDS)

### Step 7: Configure Instance Traffic and Scaling

1. **Instance types**: `t3.micro` (free tier eligible)
2. **Load balancer type**: Application Load Balancer
3. **Processes**: Keep defaults

### Step 8: Configure Updates, Monitoring, and Logging

1. **Rolling updates**: Keep defaults
2. **Health monitoring**: Enhanced health reporting
3. **Managed platform updates**: Enable with your preferred maintenance window

### Step 9: Environment Properties (Critical)

Add these environment variables in the **Environment properties** section:

| Name | Value |
|------|-------|
| `SPRING_PROFILES_ACTIVE` | `production` |
| `SERVER_PORT` | `5000` |
| `DATABASE_URL` | `jdbc:mysql://mysql-training.cd4qsem4uf1x.us-west-2.rds.amazonaws.com:3306/team1capstonedb` |
| `DATABASE_USERNAME` | `admin` |
| `DATABASE_PASSWORD` | `Be.Cognizant2025!` |
| `JWT_SECRET` | `bXlTZWNyZXRLZXlGb3JKV1RBdXRoZW50aWNhdGlvblNlY3VyaXR5S2V5MTIzNDU2Nzg5MA==` |
| `JWT_EXPIRATION` | `86400000` |

### Step 10: Review and Submit

1. Review all your settings
2. Click **"Submit"**
3. Wait for environment creation (5-10 minutes)

## Alternative: Upload via Web Console (Existing Application)

If you already have an application and want to deploy a new version:

1. Go to your Elastic Beanstalk application
2. Click **"Upload and deploy"**
3. Choose your JAR file: `target/ByteBazaar-0.0.1-SNAPSHOT.jar`
4. Enter a version label
5. Click **"Deploy"**

## Configuration Files

Your project includes pre-configured files for Elastic Beanstalk:

### `.ebextensions/01-java-settings.config`
- JVM settings (512MB max heap)
- Environment variables for database and JWT
- Instance type configuration (t3.micro)

### `.ebextensions/02-healthcheck.config`
- Health check endpoint configuration
- Uses Spring Boot Actuator `/actuator/health`

### `application-production.properties`
- Production database settings
- Logging configuration
- Security settings

## Environment Variables

The following environment variables are automatically set:
- `SPRING_PROFILES_ACTIVE=production`
- `DATABASE_URL` - MySQL connection string
- `DATABASE_USERNAME` - Database username
- `DATABASE_PASSWORD` - Database password
- `JWT_SECRET` - JWT signing secret
- `SERVER_PORT=5000` - Required by Elastic Beanstalk

## Monitoring and Management

### Using AWS Web Console

1. **Environment Dashboard**:
   - Go to your Elastic Beanstalk environment
   - View health status, recent events, and monitoring graphs

2. **Application Logs**:
   - In your environment dashboard
   - Go to **Logs** → **Request Logs** → **Last 100 Lines** or **Full Logs**

3. **Configuration Changes**:
   - Go to **Configuration** in your environment
   - Modify settings like instance types, environment variables, etc.

4. **Application Versions**:
   - Go to **Application versions** to manage different versions
   - Deploy previous versions if needed

### Application URLs
- **Main Application**: Your EB environment URL (shown in dashboard)
- **API Documentation**: `[Your-URL]/swagger-ui/index.html`
- **Health Check**: `[Your-URL]/actuator/health`

## Web Console Management Tasks

### Deploy New Version
1. Build new JAR: `mvn clean package -DskipTests`
2. Go to environment dashboard
3. Click **"Upload and deploy"**
4. Select new JAR file
5. Enter version label
6. Click **"Deploy"**

### View Application Logs
1. Go to environment dashboard
2. Click **"Logs"** in left sidebar
3. Click **"Request Logs"** → **"Last 100 Lines"** or **"Full Logs"**

### Monitor Application Health
1. Environment dashboard shows health status
2. **Monitoring** tab shows graphs for:
   - Environment health
   - Root filesystem usage
   - CPU utilization
   - Network in/out
   - Request count and latency

### Update Environment Variables
1. Go to **Configuration** → **Software**
2. Edit **Environment properties**
3. Add/modify variables
4. Click **"Apply"** (this will restart your application)

### Scale Your Application
1. Go to **Configuration** → **Capacity**
2. Modify:
   - Instance type
   - Auto scaling settings
   - Load balancer settings
3. Click **"Apply"**

### Terminate Environment (Cleanup)
1. Go to environment dashboard
2. Click **"Actions"** → **"Terminate environment"**
3. Confirm termination
4. This stops all resources and charges

## Cost Optimization

- **Instance Type**: Starts with t3.micro (free tier eligible)
- **Auto Scaling**: Configured for load-based scaling
- **Load Balancer**: Application Load Balancer included

## Troubleshooting

### Common Issues

1. **Build Fails Locally**
   ```bash
   mvn clean package -DskipTests
   ```
   Check for compilation errors and fix them before uploading.

2. **Environment Creation Fails**
   - Check that you have proper IAM permissions
   - Ensure service roles are created correctly
   - Try using default VPC and subnets

3. **Application Won't Start**
   - Check environment logs in AWS console
   - Verify environment variables are set correctly
   - Ensure JAR file is executable Spring Boot jar

4. **Health Check Fails**
   - Verify Spring Boot Actuator is included in dependencies
   - Check that `/actuator/health` endpoint responds
   - Ensure `SERVER_PORT=5000` is set (EB requirement)

5. **Database Connection Issues**
   - Verify RDS security groups allow connections from EB environment
   - Check database URL, username, and password in environment variables
   - Ensure RDS instance is running

6. **502 Bad Gateway or Timeout**
   - Application might be starting on wrong port (must be 5000 for EB)
   - Check application logs for startup errors
   - Verify JVM memory settings aren't too high for instance type

### Checking Logs via Web Console
1. Go to your environment dashboard
2. Click **"Logs"** in the left sidebar  
3. Click **"Request Logs"** → **"Full Logs"**
4. Download and examine:
   - `/var/log/eb-engine.log` - Elastic Beanstalk deployment logs
   - `/var/log/web.stdout.log` - Application stdout logs
   - `/var/log/eb-hooks.log` - Hook execution logs

### Getting Help
- Check logs first (most issues are in the logs)
- Search AWS documentation for specific error messages
- Use AWS Support if you have a support plan

## Security Best Practices

1. **Database Security**: 
   - RDS is already configured with security groups
   - Use IAM database authentication in production

2. **Application Security**:
   - HTTPS is automatically configured by EB
   - JWT tokens for authentication
   - Spring Security configuration included

3. **Environment Variables**:
   - Sensitive data is stored as environment variables
   - Consider using AWS Systems Manager Parameter Store for enhanced security

## Next Steps

1. **Custom Domain**: Configure Route 53 for custom domain
2. **SSL Certificate**: Use AWS Certificate Manager
3. **CI/CD**: Set up AWS CodePipeline for automated deployments
4. **Monitoring**: Configure CloudWatch alarms
5. **Backup**: Set up RDS automated backups

## URGENT: Fix for Current AWS Errors

### Error: "No default VPC for this user" & Security Group Issues

If you're seeing these errors in your AWS events:
- `No default VPC for this user`
- `AWSEBSecurityGroup does not exist`
- `Stack named 'awseb-e-wa2lvkykcv-stack' aborted operation`

**Solution: Create VPC and Configure Networking**

#### Option 1: Quick Fix - Use Existing VPC (Recommended)

1. **Go to VPC Console**:
   - In AWS Console, search for "VPC"
   - Click on "VPC" service

2. **Check Available VPCs**:
   - Look for any existing VPCs in your region
   - Note the VPC ID (e.g., vpc-xxxxxxxxx)
   - Note associated subnets

3. **Update Elastic Beanstalk Configuration**:
   - Go back to your Elastic Beanstalk environment
   - Go to **Configuration** → **Network**
   - Select an existing VPC from the dropdown
   - Select at least 2 subnets in different availability zones
   - Save configuration

#### Option 2: Create Default VPC

1. **In VPC Console**:
   - Click **"Actions"** → **"Create Default VPC"**
   - This creates a default VPC with subnets in all availability zones
   - Wait for creation to complete

2. **Retry Elastic Beanstalk Deployment**:
   - The default VPC will now be available
   - Elastic Beanstalk will automatically use it

#### Option 3: Terminate and Recreate Environment

If the environment is stuck in a failed state:

1. **Terminate Current Environment**:
   - Go to your EB environment
   - **Actions** → **"Terminate Environment"**
   - Confirm termination

2. **Wait for Cleanup** (5-10 minutes)

3. **Recreate Environment**:
   - Follow the deployment guide again
   - Ensure VPC is properly selected during creation

## 🚨 CRITICAL: IAM Permission Errors Fix

### Latest Error: "You do not have permission to perform the 'ec2:AssociateAddress' action"

This is an **IAM permissions problem**. Here's how to fix it:

#### Immediate Solution: Use Single Instance (No Load Balancer)

**STOP** - Terminate your current environment and recreate with these **simplified settings**:

1. **Terminate Failed Environment First**:
   - Go to Elastic Beanstalk Console
   - Actions → "Terminate Environment" 
   - Wait for complete cleanup (10 minutes)

2. **Create New Environment with SIMPLE settings**:
   
   **Critical Configuration Changes:**
   - ✅ **Environment tier**: Web server environment
   - ✅ **Environment type**: **Single instance** (NOT Load balanced)
   - ✅ **Platform**: Java 17 running on 64bit Amazon Linux 2
   - ✅ **Instance type**: t3.micro
   - ✅ **Skip networking configuration** (use defaults)
   - ✅ **Service role**: Use existing or create new
   - ✅ **Instance profile**: aws-elasticbeanstalk-ec2-role

3. **Why Single Instance Works**:
   - No load balancer = No Elastic IP needed
   - No VPC complexity 
   - Fewer IAM permissions required
   - Still fully functional for development/testing

#### Alternative: Fix IAM Permissions (Advanced)

If you need Load Balanced environment, ask your AWS administrator to add these permissions:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "ec2:AssociateAddress",
                "ec2:DisassociateAddress",
                "ec2:AllocateAddress",
                "ec2:ReleaseAddress",
                "ec2:DescribeAddresses"
            ],
            "Resource": "*"
        }
    ]
}
```

#### RECOMMENDED: Quick Single Instance Setup

**Environment Configuration:**
- Environment name: `ByteBazaar-single`
- Environment type: **Single instance**
- Platform: Java 17
- Instance type: t3.micro
- Application file: Your JAR file

**Environment Variables (Same as before):**
```
SPRING_PROFILES_ACTIVE=production
SERVER_PORT=5000
DATABASE_URL=jdbc:mysql://mysql-training.cd4qsem4uf1x.us-west-2.rds.amazonaws.com:3306/team1capstonedb
DATABASE_USERNAME=admin
DATABASE_PASSWORD=Be.Cognizant2025!
JWT_SECRET=bXlTZWNyZXRLZXlGb3JKV1RBdXRoZW50aWNhdGlvblNlY3VyaXR5S2V5MTIzNDU2Nzg5MA==
JWT_EXPIRATION=86400000
```

This will work without complex networking or IAM issues!
