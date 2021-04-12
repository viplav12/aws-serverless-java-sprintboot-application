# AWS Serverless springboot API
This project consists of a Java Spring boot application utilizing the power of AWS SAM(Serverless Application Model), that accepts input as String and find ("Google", "Oracle", "Microsoft", "Amazon", "Deloitte") and add (© with the strings)for certain words and outputs the result deployed on AWS.


#### High level Design
![alt text](images/high-level-architecture.png)

### Exposed API
##### 1) https://base_url/Stage/input/replace : This is a post call
Body (Json): 
```
{
    "userInput": "We really like the new security features of Amazon Web Service"
}
```

Sample request from Postman:
![alt text](images/postman-sample-post-request.png)

Example input: 	“We really like the new security features of Amazon Web Service”. 
Expected output: “We really like the new security features of Amazon Web Service©”.

##### 2) https://base_url/Stage/healthcheck.html: This is a get call
Expected output: Health check: OK

![alt text](images/sample-get-request.png)

### Serverless Application Model(SAM)
    I have choosen [Serverless](https://aws.amazon.com/serverless/sam/) model for the deployment of the service because of following reasons:
    - Since its a small web service and doesn't require a lot of AWS services.
    - This is efficient from Cost perspective since the resources for lambda is only pay what you use(No idle capacity).
    - Integrity security model for code
    - No overhead of maintaining the servers: reduced operational cost
    - Flexible scaling
    - Write less infra-structure-as-code: Using SAM reduces number of lines of code for infra drastically and do most of it in background. 
    For Example:serverless-cloudformation-app.yaml has only 27 lines of code and the exapnded version of same template has 83 lines of code.  
    - This results in less time for implementation which results in fast and cost effective deliverables.


#### AWS Lambda Function:
    - AWS Lambda is a serverless compute service that lets you run code without provisioning or managing servers, creating workload-aware cluster scaling logic, maintaining event integrations, or managing runtimes.
      With Lambda, you can run code for virtually any type of application or backend service - all with zero administration.  
    - Using Lambda reduce the cost of infra dramatically. As Lambda is event driven. Pay for the compute time you consume, so we never pay for over-provisioned infrastructure.
    - AWS Lambda automatically scales application by running code in response to each event.  
    - Can optimize the code execution time by choosing the right memory size for your function 
    - Serve dynamic content of the application Like: Data which changes according to the  Business logic

#### Serverless stack
Serverless Stack includes following objective
![alt text](images/aws-designer-stack.png)
    - Creates one API Gateway using RestApi with two endpoints:
        - /<project-context-part>
        - /healthcheck
    - LambdaRole containing the policies to use API Gateway
    - UserInput Lambda function.
    - Healthcheck Lambda Function.

### Codebuild
AWS [CodeBuild](https://docs.aws.amazon.com/codebuild/latest/userguide/welcome.html) is a fully managed continuous integration service that compiles source code, runs tests, and produces software packages that are ready to deploy. With CodeBuild, you don’t need to provision, manage, and scale your own build servers. The agent is automatically provisioned as container by AWS.

#### CodeBuild stack
This stack includes the following objectives:

![alt text](images/codebuild-stack.png)

    - Creates a S3 bucket which will be used to contain the CloudFormation package.
    - buildspec.yml stage in the build part performing activities.
      - Maven clean package: This creates the Java package as jar.
      - AWS CodeBuild role to carry CodeBuild operation,
      - AWS CloudFormation package: This will package the CloudFormation stack expanding the Serverless along with source code as jar and push to S3 bucket created by CodeBuild stack.
      - AWS CloudFormation deploy: This will deploy the stack from an output of package to deploy the Serverless infra.


## How to deploy and use the project

### Pre-requisite
- AWS account
- CloudFormation role with the policy attached named file `cf-role-policy.json`

### Steps to follow

Step-1: Share the associate git username (who want to check/see my code repository) with me via mail viplavsingh1209@gmail.com or on my mobile number 0630350408 to give the access.

Step-2: Deploy cloudformation for serverless application i.e codebuild-cloudformation.yaml in regions eu-central-1
        - Use all the default values 
        - Use "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" in GitHubToken (to create Personal Access token https://github.com/settings/tokens)
        
Step-3: To trigger codebuild and deploy serverless-cloudformation-template.yaml: 
         - Commit any small change in the code or
         - Manually trigger code build

Step-4: Copy the uri from cloudformation output
        Test API-1: https://copied-uri/Stage/healthcheck.html (get call)
        Test API-2: https://copied-uri/Stage/input/replace (post call)
                    Body of post call (json): 
                    ```
                    {
                        "userInput": "We really like the new security features of Amazon Web Service"
                    }
                    ```
                    
Code Repository:          
```
https://github.com/viplav12/aws-sam-simple-spriboot-app.git
```
To run the cloudformation from AWS CLI
```
aws cloudformation create-stack --stack-name codebuild-serverless --template-url codebuild-cloudformation.yaml
```

### Disaster Recovery
Consideration: If we have registered domain and using AWS Route-53 

This refers to the restore of application in case of any regional failure and depends on Recovery Time Objective - time within which the application needs to be up (RTO) and Recovery Point Object- point from which the backup needs to be restored(RPO).As per the Recovery Time Objective(RTO) to restore the webservice within 30 mins. There are the two scenario which can be considered :

#### Active-Passive Scenario:
Deploy infrastructure in two regions but keep one region inactive with respect to usage.
![alt text](images/active-passive-scenario.png)

#### Active-Active Scenario

![alt text](images/active-active-scenario.png)

## TODOs
- Register DNS Zone and configure Route53 with CNAME to two API in different region based on latency to cater active-active scenario,draft template is with the code `todo-route53-tmpl.yaml`
 









