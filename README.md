# Jax-Y  <img src="https://cloud.githubusercontent.com/assets/7684497/25315596/e191fb00-2857-11e7-99bf-8e233b4eb795.jpg" width="50"> [![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/MIT) 


| Branch    | build status  |
|-----------|---------------|
| [master](https://github.com/rac021/Jax-Y/tree/master)  |[![Build Status](https://travis-ci.org/ontop/ontop.svg?branch=master)](https://travis-ci.org/rac021/Jax-Y)|



 **Jax-Y** is Generic Jax-Rs web Service based over yaml configuration implementing the project  **[G-Jax-Api]( https://github.com/rac021/G-Jax-Api)**

**SourceForge Download Link** : **[https://sourceforge.net/projects/jax-y/?source=typ_redirect]( https://sourceforge.net/projects/jax-y/?source=typ_redirect)** 


------------------------------------------------------

**Linked Projects :** 

-    [https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Api]( https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Api) ( Api )

-    [https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Service-Discovery]( https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Service-Discovery) ( Default Service Discovery Implementation )

-    [https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Security-Provider]( https://github.com/rac021/Jax-Y/tree/master/libs/dependencies/G-Jax-Security-Provider) ( Default Security Provider Implementation )
   
-    [https://github.com/rac021/G-Jax-Client]( https://github.com/rac021/G-Jax-Client) ( GUI )

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
  
  cp target/jax-y-swarm.jar demo/
  
  cd demo/
  
  chmod +x db-script/db-planes.sh
  
  ./db-script/db-planes.sh 
  
  java -jar jax-y-swarm.jar
  
```  


  [Talk_2017_PasSageEnSeine]( https://github.com/rac021/Jax-Y/blob/master/demo_sourceForge/Talk_PasSageEnSeine/Jax-Y.pdf
) ( PDF ) 


============================

# Jaxy 2.0 ( Features ) :

#### Fault tolerance integration : Circuit Breaker, Fallback, Bulkhead, metrics, health check :

#### Deploy ( or not ) web-ui ( with ou without authentication ) :

![01_login](https://user-images.githubusercontent.com/7684497/48570327-706af980-e904-11e8-8fe4-a0204eb3b950.png)

#### List available services ( with quick description ) :

![02_service_list](https://user-images.githubusercontent.com/7684497/48570376-8b3d6e00-e904-11e8-8f68-36dd6dd560a2.png)

#### List the detail of the selected service ( with automatic generation of the client + decryptor based on openssl ) :

![03_datail_service](https://user-images.githubusercontent.com/7684497/48570409-9abcb700-e904-11e8-9e63-a54150d2b5b0.png)

#### Checker ( for testing Authorizations ) :

![04_](https://user-images.githubusercontent.com/7684497/48570430-a314f200-e904-11e8-9072-03286ec9ee05.png)

#### List Jaxy global configuration  :

![05_global_conf](https://user-images.githubusercontent.com/7684497/48570449-ac9e5a00-e904-11e8-857a-a2a5edba8fbf.png)

#### List Jaxy global informations ( metrics , health check .. ) :

![06_infos](https://user-images.githubusercontent.com/7684497/48570457-b2943b00-e904-11e8-8c7a-0cdd6fe274a3.png)

#### Jaxy Ui Client ( custom Authentication ) :

![07_jaxy_client_01](https://user-images.githubusercontent.com/7684497/48570468-b88a1c00-e904-11e8-822c-560d5ee38fa3.png)

#### Jaxy Ui Client ( Keycloak Authentication ) :

![08_jaxy_client_02](https://user-images.githubusercontent.com/7684497/48570481-bf189380-e904-11e8-8760-2b0b926a7ed1.png)

#### Automated monitoring generation : global view ( for grafana ) :

![09_monitoring_01](https://user-images.githubusercontent.com/7684497/48570493-c5a70b00-e904-11e8-9854-72451452c9ca.png)

#### Automated monitoring generation : service_counter ( for grafana ) :

![10_monitoring_counter_services](https://user-images.githubusercontent.com/7684497/48570510-cc358280-e904-11e8-9d30-bc0ebe2a0639.png)

#### Automated monitoring generation : service_timer ( for grafana ) :

![11_monitoring_timer](https://user-images.githubusercontent.com/7684497/48570525-d2c3fa00-e904-11e8-8a87-3d665ba3ca6c.png)

#### Secured access to the wildfly swarm management :

![12_wildfly_management](https://user-images.githubusercontent.com/7684497/48570543-de172580-e904-11e8-9a2f-0991e92779e5.png)

#### Automated let's Encrypt certificate generation :

![13_letsencrypt](https://user-images.githubusercontent.com/7684497/48572188-00ab3d80-e909-11e8-802f-a046fa53ab2e.png)



