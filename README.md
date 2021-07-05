# Only car sales   
[![Build Status](https://travis-ci.com/amasterenko/job4j_todolist.svg?branch=master)](https://travis-ci.com/amasterenko/job4j_todolist)  

Сar sales web service. Allows  users to create and search for cars sales ads.  
Stores ads in the DB, and the ads image files on the file system of the server.  

### Technologies:  
- Hibernate (PostgreSQL)
- JAVA servlets/JSTL    
- Bootstrap  
- JS/jQuery  
- Slf4j  
- Travis CI  

### Features  

- User authentication  
- Creating ads      
- Sold status management      
- Filtering ads by parameters

### User interface  
 
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

### DB Schema  

![ScreenShot](images/cars_dbschema.png)  

### Usage  

Before deploying:
1. Set the "imagesPath" parameter in ./src/main/webapp/WEB-INF/web.xml file.  
   It's a directory where the ads photo will be stored.
2. Set your DB's parameters in ./src/resources/hibernate.cfg.xml.
3. Run scheme.sql on your DB.

Deploy the WAR file to Tomcat:
1. Build the project.
2. Copy the war file to the Tomcat/webapps directory.  
