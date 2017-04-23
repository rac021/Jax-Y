# JAX-Y-Demo <img src="https://cloud.githubusercontent.com/assets/7684497/25315596/e191fb00-2857-11e7-99bf-8e233b4eb795.jpg" width="50">


 **Source Download** :  https://sourceforge.net/projects/jax-y/?source=typ_redirect
 
 
 **Demo :** 
 
  
 I )   Download : 
    
       1 ) download jax-y-demo.zip from the link above
   
       2 ) unzip jax-y-demo.zip
  
 II )  Install demo Database :
 
        1 )  chmod +x demo/db-script/db-planes.sh 
 
        2 )  ./demo/db-script/db-planes.sh 
       
       
 III )  Run The executable Jar which deploy an endpoint at the URL : http://localhost:8080/rest/resources/
 
        1 )  java -jar jax-y-swarm.jar
       
 
 IV ) Run the GUI Client :
        
        1 )  java -jar GUI/jaxy-ui.jar
       
        
 V ) Tests ( Public serivce ) :
 
        1 ) Invoke the serviceDiscovery : 
            http://localhost:8080/rest/resources/infoServices
         
        2 ) Invoke the service planes : 
            http://localhost:8080/rest/resources/planes ( XML / JSON )

        3 ) Filter on total_pssengers > 300  :  total_passengers=_>_300
         
        4 ) Sort Only model                  :  model
         
        5 ) Sort Only model + distance_km    :  model - distance_km
       

 VI  ) Add new Secured Service ( customSignOn authentication ) :
 
        1 ) Stop the server
         
        2 ) Uncomment vip_planes service
         
        3 ) Uncomment customSignOn authentication in the serviceConf.yaml file
                  
        4 ) restart the server 
         
        5 ) Invoke the serviceDiscovery : 
            http://localhost:8080/rest/resources/infoServices
         
        7 ) Invoke the service vip_planes : 
            http://localhost:8080/rest/resources/vip_planes 
            ( XML / JSON / XML-ENCRYPTED / JSON-ENCRYPTED )
         
        8 ) Test authentication by changing login / password / timeStamp. t
            Test timeOut // test SQL type inference capacity

        9 ) Decrypt data locally 
         
       10 ) Filter on total_pssengers > 300   
                      total_passengers=_>_300 
                      total_passengers=_>_300&model='Airbus A340-500'
                      total_passengers=_>_300&model=_not_'Airbus A340-500'
         
       11 ) Sort Only model                  :  model
         
       12 ) Sort Only model + distance_km    :  model - distance_km
       
       13 ) Change Tags using "AS" in SQL Queries
         

 VII ) Test SSO authentication with KeyCloack ( should works with HTTPS ) :

        1 ) Start KeyCloack SERVER ( 127.0.0.1:8180 )
 
        2 ) Stop the jaxy server
         
        3 ) Comment customSignOn authentication in the serviceConf.yaml file
        
        4 ) Uncomment SSO authentication in the serviceConf.yaml file
        
        5 ) restart the server 
         
        6 ) Go to the SSO panel 
         
        7 ) Invoke the serviceDiscovery : 
            http://localhost:8080/rest/resources/infoServices
         
        8 ) Invoke the service vip_planes : 
            http://localhost:8080/rest/resources/vip_planes 
            ( XML / JSON / XML-ENCRYPTED / JSON-ENCRYPTED )
         
        9 ) Test authentication by changing login / password / clientID / secretID
         
       10 ) Filter on total_pssengers > 300  :  total_passengers=_>_300
         
       11 ) Sort Only model                  :  model
         
       12 ) Sort Only model + distance_km    :  model - distance_km
         
       13 ) Check logs in KeyCloack server for the user admin
         
   
 VIII ) Generate Shell-script for automation 
 
        1 ) Generate script 
         
        2 ) test Script 
       
       
 **Upcoming Features :**
 
       * Swagger-Angular-Client intergration 
       
       * Runtime Algo Choice for Encryption ( AES - DES ... )

       * Global Configuration Supports ( Thread pool size, nb of threads by service, data queue size ... 
         authentication server, HTTPS ...  )
              
       * GUI Client Https supports  
       
       
