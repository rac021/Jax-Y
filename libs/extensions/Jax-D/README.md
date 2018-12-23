# Jax-D  [![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/MIT) 

| Branch    | build status  |
|-----------|---------------|
| [master](https://github.com/rac021/Jax-D/tree/master)  |[![Build Status](https://travis-ci.org/ontop/ontop.svg?branch=master)](https://travis-ci.org/rac021/Jax-D)|



 **Jax-D** is Generic Jax-Rs web Service base on the implementation of the project  **[G-Jax-Api]( https://github.com/rac021/G-Jax-Api)**

------------------------------------------------------

**Linked Projects :** 

- **Api :**
   * [https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Api]( https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Api)

- **Default Service Discovery Implementation :**
   * [https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Service-Discovery]( https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Service-Discovery) 

- **Default Security Provider Implementation :**
   * [https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Security-Provider]( https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Security-Provider) 
   
- **GUI :**
   * [https://github.com/rac021/Jax-Y/tree/master/libs/extensions/G-Jax-Client]( https://github.com/rac021/Jax-Y/tree/master/libs/extensions/G-Jax-Client) 

**Requirements :**

-    `JAVA 8`
    
-    `MAVEN 3.3.9 + `
   
-    `Postgres | mySql `

-----------------------------------------------------

## installation

```xml
  mvn clean package 
```  
------------------------------------------------------

## Demo 

```xml
  
  chmod +x demo/db-script/db-planes.sh
  
  ./demo/db-script/db-planes.sh 
  
  java -jar target/jax-d-swarm.jar
  
```  
