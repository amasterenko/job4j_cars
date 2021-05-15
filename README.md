# Only car sales  
[![Build Status](https://travis-ci.com/amasterenko/job4j_todolist.svg?branch=master)](https://travis-ci.com/amasterenko/job4j_todolist)  
____ 

This project is created as an example, demonstrating the principles of next technologies:  
- Hibernate  
- PostgreSQL  
- JAVA servlets  
- Bootstrap  
- JS/JQuery  

### Features:
____  
- User's authentication  
- Creating ads  
- Managing ad's sold status  
- Filtering ads 

____ 
### Usage:  
Before deploying:  
1. Set the "imagesPath" parameter in web.xml file.
That's the directory where the ads' files will be stored.  
2. Set your DB's parameters in hibernate.cfg.xml  

Deploy the WAR file to Tomcat:  
1. Build the project
2. Copy the war file to the Tomcat/webapps directory  

### Preview:
____  
<p align="center"> Main page:</p>  

![ScreenShot](images/main.png)  

<div align="center"> Filters:</div> 

![ScreenShot](images/main_filter.png)   

<p align="center"> User menu:</p> 

![ScreenShot](images/user_menu.png)  

<p align="center"> User ads:</p> 

![ScreenShot](images/user_cars.png)  

<p align="center"> New ad's page:</p> 

![ScreenShot](images/sell_car.png)  

<p align="center"> Sign uppage:</p> 

![ScreenShot](images/sign_up.png) 

<p align="center"> Sign in page:</p> 

![ScreenShot](images/sign_in.png)  