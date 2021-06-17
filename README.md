# Only car sales  
[![Build Status](https://travis-ci.com/amasterenko/job4j_todolist.svg?branch=master)](https://travis-ci.com/amasterenko/job4j_todolist)  
____ 
Сar sales web market. It allows you to create and find advertising for cars sales.   

This project demonstrates the principles of the following technologies:  
- Hibernate (PostgreSQL)
- JAVA servlets/JSTL    
- Bootstrap  
- JS/JQuery  
- Slf4j  
- Travis CI

### Features:
____  
- User's authentication  
- Creating ads  
- Sold status management      
- Filtering ads by parameters   

### Usage:  
____  
Before deploying:  
1. Set the "imagesPath" parameter in ./src/main/webapp/WEB-INF/web.xml file.  
It's a directory where the ads photo will be stored.  
2. Set your DB's parameters in ./src/resources/hibernate.cfg.xml.  
3. Run scheme.sql on your DB.   

Deploy the WAR file to Tomcat:  
1. Build the project.  
2. Copy the war file to the Tomcat/webapps directory.  

### Preview:
____  
<p align="center"> Main page:</p>  

![ScreenShot](images/main.png)  

<div align="center"> Filters:</div> 

![ScreenShot](images/main_filter.png)   

<p align="center"> User menu:</p> 

![ScreenShot](images/user_menu.png)  

<p align="center"> Ads of current user:</p> 

![ScreenShot](images/user_cars.png)  

<p align="center"> Page of new ad:</p> 

![ScreenShot](images/sell_car.png)  

<p align="center"> Sign up page:</p> 

![ScreenShot](images/sign_up.png) 

<p align="center"> Sign in page:</p> 

![ScreenShot](images/sign_in.png)  