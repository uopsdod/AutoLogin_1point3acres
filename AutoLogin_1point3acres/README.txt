
### Resources overall: 

Project_Git_Url

Lambda
	Role: AWSLambdaBasicExecutionRole
		Policy: AWSLambdaBasicExecutionRole
		Policy: [aws_policy_ssm.txt]

CloudWatch_Schedule_Rule

CloudWatch_Log 

Parameter_Store



### Bisiness Flow: 
CloudWatch Rule -> Lambda(Parameter Store + CloudWatch log) 

