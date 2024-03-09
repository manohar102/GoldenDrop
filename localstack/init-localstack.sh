#!/bin/bash

# Create S3 buckets using the AWS CLI
aws --endpoint-url=http://localhost:4566 s3api create-bucket --bucket techbuddy.goldendrop

# Add CORS configuration for results bucket
awslocal s3api put-bucket-cors --bucket techbuddy.goldendrop --cors-configuration '{
  "CORSRules": [
    {
      "AllowedHeaders": ["*"],
      "AllowedMethods": ["GET", "POST", "PUT"],
      "AllowedOrigins": ["http://dev.alwaystech.com"],
      "ExposeHeaders": ["*"]
    }
  ]
}'