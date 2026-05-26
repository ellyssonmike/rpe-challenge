#!/bin/bash

# pix-payment-status
awslocal sqs create-queue --queue-name pix-payment-status-dlq
awslocal sqs create-queue --queue-name pix-payment-status \
  --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:pix-payment-status-dlq\",\"maxReceiveCount\":\"5\"}"}'

# pending-pix-payment
awslocal sqs create-queue --queue-name pending-pix-payment-dlq.fifo --attributes FifoQueue=true,ContentBasedDeduplication=true
awslocal sqs create-queue --queue-name pending-pix-payment.fifo \
  --attributes '{"FifoQueue": "true", "ContentBasedDeduplication": "true", "RedrivePolicy": "{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:pending-pix-payment-dlq.fifo\",\"maxReceiveCount\":\"5\"}"}'