## Migrate Linux application containers from x86 to IBM LinuxONE on Red Hat OpenShift 4.4 using Multi-Architecture

## Summary

This code pattern describes a step-by-step approach based on best practices for migrating an existing x86 application container to LinuxONE on Red Hat OpenShift 4.4 using multi-architecture. We focus on deploying OpenShift Container using multi-architecture and provides a validated process for deploying a production ready OpenShift Container Platform Cluster on LinuxONE.

## Why IBM LinuxONE for Red Hat OpenShift?

IBM LinuxONE offers flexible compute with horizontal scalability on a single system that can grow to thousands of Linux guests and millions of containers. As well as vertical scalability that can non-disruptively grow databases without requiring any partitioning or sharding while maintaining response times and throughput.
In addition, IBM LinuxONE provides confidential computing capabilities, including FIPS 140-2 Level 4 certification and isolation that meets Common Criteria EAL5+. LinuxONE hosts secure key technology with built-in tamper sensing that will automatically zero encryption keys when a tamper is detected.

RedHat OpenShift is built on Kubernetes with portability in mind. 4.x version supports multiple architectures, including IBM LinuxONE, IBM Z, and IBM Power. On top of OpenShift, IBM is making Cloud Paks available with multi-architectural support. Both stacks can be deployed on premises as an untethered private cloud. Red Hat OpenShift acts as a layer of abstraction for the choice of different cloud vendors and infrastructures.

## Migration Architecture Flow
![alt text](docs/images/tat-banking-migration-arch.png "TAT Banking Architecture")

# Pre-requisites


1. Red Hat OpenShift Cluster 4.4 or Higher.
1. Download and install [Maven version 3.6.3 or Higher](https://maven.apache.org/ "maven")
1. Download and install [Git](https://git-scm.com/ "git")

## Getting a test OpenShift cluster

If you do not already have an OpenShift cluster, please visit the [IBM LinuxONE Community Cloud](https://developer.ibm.com/components/ibm-linuxone) to register for a free trial.  The following instructions are written for someone with some experience using Red Hat OpenShift.  If you find that you'd like more introductory details, please make use of these [Fast Start Guides](https://www.ibm.com/community/z/linuxone-cc/faststart/).

## Steps

1. [Clone the TAT-Banking Repository Locally](#1-clone-the-repository-locally).
2. [Build TAT-Banking custom docker image for LinuxONE](#2-install-docker-community-edition).
3. [Install Manifest tool](#3-install-manifest-tool).
4. [Create Multi-architecture Images for TAT-Banking](#4-create-multi-architecture-images).
5. [Pre-build Container Image for TAT-Banking](#5-pre-build-comtainer-image).
6. [Deploy TAT-Banking Application Using Multi-Architecture Images](#6-deploy-tat-banking-application-using-multi-architecture-images).
7. [Test TAT-Banking Application](#7-test-tat-banking-application).


### 1. Clone the tat-banking repository locally

Clone down this `TAT-Banking` repository into your LinuxONE environment.

**Note:** Please make sure you login to your LinuxONE environment to complete these steps below. If you don't want to pull the TAT-Banking Application code from Github and build the docker images for LinuxONE then you can skip `Step 1` through `Step 4`.
 
```
$ git clone https://github.com/IBM/TAT-Banking.git
```

### 2. Build TAT-Banking custom docker image for LinuxONE

The custom Dockerfile for each service uses a Universal Base OS Image which supports amd64, s390x, and ppc64e architectures.

Follow the steps outlined below to clone the repo, build docker images, and tag and push the images to dockerhub: 


       1. Change to the directory where you clone the repo 
          
           $ cd TAT-Banking/
          
              
      2. Execute this maven command below to build the tat-banking application binaries 
          
           $ mvn clean package
                          
      3. If the build is successful, then copy the binaries as showing below
         
           $ cp customer-service/target/customer-service-1.0-SNAPSHOT.jar TAT-Banking/docs/build/customer
           $ cp account-service/target/account-service-1.0-SNAPSHOT.jar TAT-Banking/docs/build/account
           $ cp transaction-service/target/transaction-service-1.0-SNAPSHOT.jar TAT-Banking/docs/build/transaction
           $ cp portal/target/portal-1.0-SNAPSHOT.war TAT-Banking/docs/build/portal
         
      4. Change to the build directory as shown below 
         
         $ cd docs/build/customer
         
      5. Execute docker build command below to build the customer images 
         
         $ docker build -t customer-ubi:v0.1 .
         
      6. Execute docker tag command to tag the images that you just created 
         
         $ docker tag customer-ubi:v0.1 <your-docker-account>/customer-ubi-s390x:v0.1
         
      7. Execute docker push command to push the image into dockerhub 
         
         $ docker push <your-docker-account>/customer-s390x:v0.5  
         
      8. Repeat steps 1-8 above to build the docker images for account-service, transaction-service, and portal. For rabbit service, repeat steps 4-7.    
       
### 3. Install Manifest tool

For TAT Banking we use a manifest tool to create multi-architecture images, the manifest-tool is a command line utility that implements a portion of the client side of the Docker registry v2.2 API for interacting with manifest objects in a registry conforming to that specification. The manifest tool can be installed on Linux on x86, Linux on IBM Z, or on LinuxONE.

To install the manifest tool, please follow the installation steps [here](http://containerz.blogspot.com/2016/07/multi-arch-registry.html).

### 4. Create Multi-architecture Images for TAT-Banking
 
Multi-architecture images allow you to put several images behind a common identifier in a registry.  With this approach a consumer of the image won't have to think about pulling the right binary flavor of the image, because it's done right automatically for him.

When a container is started from a multi-arch image, OpenShift selects and uses the image that matches the architecture of the OpenShift host.

We have already created variant images for our TAT Banking application that will serve two different architectures (x86 and s390x). The first thing we will need to do is to enable the Manifest tool and assemble the images into a manifest list. To download the manifest deployment files, click [here](https://github.com/IBM/TAT-Banking/tree/master/docs/manifest-list).

Follow the steps outline below to create Multi-Architecture image lists: 

    1. Login to the server where you install the manifest-tool and download the manifest configuration files to a temp directory.  Execute the following commands to create a Multi-Architecture image for each service.
 
       a. Create manifest list for customer-service
          $ manifest push from-spec customer-v0.1.yml
      @@@@@@@@@@@@@@
          Then use this command to inspect the manifest list
          $ manifest inspect tonyfieit/customer:v0.1    
            
       b. Create manifest list for account-service
          $ manifest push from-spec account-v0.1.yml
      
          Then use this command to inspect the manifest list
          $ manifest inspect tonyfieit/account:v0.1    
      
       c.  Create manifest list for transaction-service
           $ manifest push from-spec transaction-v0.1.yml
      
           Then use this command to inspect the manifest list
           $ manifest inspect tonyfieit/transaction:v0.1  
      
       d.  Create manifest list for portal
           $ manifest push from-spec portal-v0.1.yml
      
           Then use this command to inspect the manifest list
           $ manifest inspect tonyfieit/portal:v0.1  
      
       e.  Create manifest list for rabbit-service
           $ manifest push from-spec rabbit-v0.5.yml
      
           Then use this command to inspect the manifest list
           $ manifest inspect tonyfieit/rabbit:v0.5  
  
### 5. Pre-build Container Image for TAT-Banking

The TAT Banking application has the following images built and available for both x86 and s390x on dockerhub. If you don't want to deal with the tat-banking applicationn code and don't want to build container images then you can use the multi-arch images listed from the screen shot below to deploy the application on LinuxONE. To deploy the tat-banking application, please go to `Step 6`.

If you completed `Step 1` through `Step 4` then continue to `Step 6`.

![alt text](docs/images/multi-arch-images.png "multi-arch")

### 6. Deploy TAT Banking Application Using Multi-Architecture Images

Now that we've created the multi-architecture images for TAT Banking, let us use them to deploy the application into OpenShift.  The following [artifacts](https://github.com/IBM/TAT-Banking/tree/master/docs/deployment) will be needed to complete the deployment of the TAT-Banking application On Red Hat OpenShift. If you completed `Step 1` through `Step 4` then you will need to modify the following yaml files by adding the manifest list image that you created on `Step4`:
 
 * Customer.yaml
 * Account.yaml
 * Transaction.yaml
 * Portal.yaml
 * Rabbit.yaml
 * Deploy.sh

Follow the steps outlined below to deploy the TAT Banking application on Red Hat OpenShift:

       1. Login to your Linux environment and execute the command below to login to OpenShift 
          
           $ oc login –token=<koken> --server=https://api.<clusterid>.<domain>:6443
          
       2. Clone the tat-banking repos (if repo is already cloned then no need to clone it again, proceed to step 3) 
         
           $ git clone https://github.com/IBM/TAT-Banking.git
          
       3. Change to the directory where you clone the repo
          
           $ cd TAT-Banking/docs/deployment
                          
       4. Run the Deploy.sh script
         
           $ ./deploy.sh
                   
       5. Use oc project command to change to the project namespace
         
         $ oc project tat-banking-demo
         
       6. Run oc get command to list all the pods running under the project
         
         $ oc get pods
         
       7. If everything goes well, then you should see the Status for each pod listed as Running
  ![alt text](docs/images/runningpods.png "pods")

### 7. Test TAT-Banking Application

After a successful deployment, let's test out our TAT-Banking Application. To test the application, we will use the following test cases:

* Test Case 1: Create a customer using swagger API
* Test Case 2: Create an account for the Customer using the customer Social Security Number
* Test Case 3: Deposit amount to the account
* Test Case 4: Withdraw amount from the account
* Test Case 5: List All transactions on the account


###### 1. `Test Case 1` - Create a customer using swagger API
 
We implemented a Swagger API for each microservice in TAT-Banking, so we will use Swagger to make an API call to the Customer container service to create a customer profile.

Follow the steps outlined below to create a customer:

a. Login to `OpenShift web Console` and click `tat-banking-demo > networking > route > Customer > [URL]`.  Your browser should launch with the swagger-ui.html file from the URL you just clicked. The URL should have the same format as this example url listed below.

 ![alt text](docs/images/url-img01.png "01")
 
  ![alt text](docs/images/swagger-img01.png "01")

b. Click createCustomer and type firstname, lastname, and title, and click execute link to submit the request.

 ![alt text](docs/images/swagger-img02.png "02")
 
c. If the request is successful, then the customer is created and a ss# is returned as a response back to the client.
 
 ![alt text](docs/images/swagger-img03.png "03")

c. Now the customer is created, and we will use the customer social security number to create the account.

###### 2. `Test Case 2` - Create an account for the Customer using the customer Social Security Number

Follow the steps outlined below to create a customer account:

a. Login to `OpenShift web Console` and click `tat-banking-demo > networking > route > portal r > [URL]` and once the browser launches, you should see a page like this:

![alt text](docs/images/url-img02.png "02")
 
![alt text](docs/images/ui-img01.png "01")
 
b. Enter the customer SS# 564-86-88659 and click submit.  If the request is successful then you should see a page like:

![alt text](docs/images/ui-img02.png "02")

c. Click Add account to create the account

![alt text](docs/images/ui-img03.png "03")

d. Now an account has been created for the customer. Let’s make a deposit.

###### 3. `Test Case 3` - Deposit amount to the account

Follow the steps outlined below to make a deposit to account:

a. Click on `account number`, enter the `amount` you want to deposit and select `Deposit` and click `submit`.

![alt text](docs/images/ui-img04.png "04")

b. Now a deposit has been made to the account and you should see a page as showing:

![alt text](docs/images/ui-img05.png "05")

###### 4. `Test Case 4` - Withdraw amount from the account

Follow the steps outlined below to make a withdraw:

a. Enter the `amount` you want to withdraw and select `Withdraw`.

![alt text](docs/images/ui-img06.png "06")

b. Now the withdraw has been made:

![alt text](docs/images/ui-img07.png "07")

###### 5. `Test Case 5` - List All transactions on the account

Follow the steps outline below to list all transactions from the account:

a. To list the transaction, select `list transaction` and click `submit`.

![alt text](docs/images/ui-img08.png "08")

b. The transaction list should look like the screen shot below. We can see the date that the deposit and withdraw were made.

![alt text](docs/images/ui-img09.png "09")
 
## License

This code pattern is licensed under the Apache License, Version 2. Separate third-party code objects invoked within this code pattern are licensed by their respective providers pursuant to their own separate licenses. Contributions are subject to the [Developer Certificate of Origin, Version 1.1](https://developercertificate.org/) and the [Apache License, Version 2](https://www.apache.org/licenses/LICENSE-2.0.txt).

[Apache License FAQ](https://www.apache.org/foundation/license-faq.html#WhatDoesItMEAN)
