# Marketplace
Marketplace is a web-application that allows potential customers to search, order, and track delivery of groceries. Also, this application has a bunch of administrative and management features, each available to specific system accounts.

## Table of contents
* [System roles](#system-roles)
* [Features](#features)
* [Technologies](#technologies)
* [ER diagram](#er-diagram)
* [Setup](#setup)
* [Screenshots](#screenshots)

## System roles
| User role       | Description   
| --------------- |-------------
| Admin           | A person who is responsible for account management.<br />Admin can serve as product manager.
| Product manager | A person who is responsible for product configuration.       
| Courier         | A person who is responsible for delivery.  
| User            | A customer of the system.<br />‘User’ = anonymous or authorized user<br />‘Authorized user’ = only authorized user.<br />In all cases where ‘User’ is mentioned, it described common functionality. All special functions for authorized user are always written with ‘authorized user’. 
 
![alt text](https://i.ibb.co/nRWmCLP/Picture-1.png)
 
## Features
List of general features available to all users
* Interface of the system is designed to be used in Google Chrome, Mozilla Firefox, Opera web browsers.
* Adaptive and responsive design
* View product catalog
* View product details
* Login
* Create account

List of features available to all accounts
* View own profile and edit it
* Change password
* Logout
 
#### Admin
* View list of couriers and managers
* Add courier or product manager
* View and edit courier\`s or product manager\`s profile
* Change courier\`s or product manager\`s status

#### Product manager
* Create product
* Edit product
* Deactivate product

#### Courier
* View list of assigned deliveries
* View delivery details
* Change delivery status

#### Unauthorized user
* Add product to shopping cart
* Change the quantity of a product or delete it from cart
* Add product to comparison
* Go to checkout and choose delivery date and time, and fulfil delivery details
* View order summary and confirm it
* Register or login

#### Authorized user
* Add product to shopping cart
* Change the quantity of a product or delete it from cart
* Add product to comparison
* Go to checkout and choose delivery date and time, and fulfil delivery details
* View order summary and confirm it
* View the list of incoming orders and order history
* View order details

## Technologies
__Backend__
* Java 8
* Spring Boot 2
* JWT
* PostgreSQL
* AWS S3

__Frontend__
* Angular 2
* Bootstrap

__Deployment__
* Travis CI
* Heroku
* Maven

## ER diagram
![alt text](https://i.ibb.co/hfYrTzt/ER-v3.png)

## Setup
To run this project, add enviromental variables and build it using Apache Maven version 3.3.9 or higher:

```
$ cd Marketplace
$ export DB_USERNAME="replace this"
$ export DB_PASSWORD="replace this"
$ export PROFILE="replace this"
$ export BASE_URL_SERVER="replace this"
$ export DEFAULT_ADMIN_MAIL="replace this"
$ export DEFAULT_ADMIN_PASSWORD="replace this"
$ export DEFAULT_USER_MAIL="replace this"
$ export DEFAULT_USER_PASSWORD="replace this"
$ export EMAIL_PASSWORD="replace this"
$ export EMAIL_PORT="replace this"
$ export EMAIL_USERNAME="replace this"
$ export BASE_URL_CLIENT="replace this"
$ export AWS_BUCKET_NAME="replace this"
$ export AWS_BUCKET_REGION="replace this"
$ export AWS_ACCESS_KEY="replace this"
$ export AWS_SECRET_KEY="replace this"
$ export GOOGLE_RECAPTCHA_SECRET="replace this"
$ export DEFAULT_COURIER_MAIL="replace this"
$ export DEFAULT_COURIER_PASSWORD="replace this"
$ export DEFAULT_MANAGER_MAIL="replace this"
$ export DEFAULT_MANAGER_PASSWORD="replace this"
$ mvn package
$ java -jar "target/marketplace-server-0.0.1-SNAPSHOT.jar"
```

## Screenshots
![alt text](https://i.ibb.co/JyKxQV3/screencapture-localhost-4200-home-2021-07-07-17-18-42.png)
![alt text](https://i.ibb.co/23t7G6V/screencapture-localhost-4200-products-2021-07-07-17-21-39.png)
![alt text](https://i.ibb.co/CvJSvNx/screencapture-localhost-4200-cart-2021-07-07-17-22-49.png)
![alt text](https://i.ibb.co/0qBT3Dt/screencapture-localhost-4200-checkout-2021-07-07-17-24-41.png)
![alt text](https://i.ibb.co/7J5Gmnf/screencapture-localhost-4200-accounts-profile-2021-07-07-17-25-59.png)
![alt text](https://i.ibb.co/Fmhj9m5/screencapture-localhost-4200-accounts-register-2021-07-07-17-26-15.png)
![alt text](https://i.ibb.co/N7c9wfD/screencapture-localhost-4200-sysaccounts-couriers-2021-07-07-17-39-11.png)
