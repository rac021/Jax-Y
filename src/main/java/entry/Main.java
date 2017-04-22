
package entry ;

/**
 *
 * @author ryahiaoui
 */

import java.io.File ;
import java.util.Map ;
import java.util.List ;
import java.util.Objects ;
import org.wildfly.swarm.Swarm ;
import org.wildfly.swarm.keycloak.Secured ;
import org.jboss.shrinkwrap.api.ShrinkWrap ;
import org.wildfly.swarm.jaxrs.JAXRSArchive ;
import org.wildfly.swarm.config.undertow.Server ;
import org.wildfly.swarm.undertow.UndertowFraction ;
import org.wildfly.swarm.config.undertow.BufferCache ;
import org.wildfly.swarm.config.undertow.server.Host ;
import org.wildfly.swarm.management.ManagementFraction ;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset ;
import org.wildfly.swarm.config.management.SecurityRealm ;
import org.wildfly.swarm.datasources.DatasourcesFraction ;
import org.wildfly.swarm.config.undertow.ServletContainer ;
import org.wildfly.swarm.config.undertow.server.HttpsListener ;
import org.wildfly.swarm.config.undertow.HandlerConfiguration ;
import com.rac021.jax.security.provider.configuration.Configurator ;
import org.wildfly.swarm.config.undertow.servlet_container.JSPSetting ;
import org.wildfly.swarm.management.console.ManagementConsoleFraction ;
import org.wildfly.swarm.config.management.security_realm.SslServerIdentity ;
import org.wildfly.swarm.config.undertow.servlet_container.WebsocketsSetting ;

public class Main {
   
    
    private static String  driverModule = null ;
    
      
    public static void main(String[] args) throws Exception {
             
       // System.setProperty("swarm.debug.port" ,"11555") ;

        String tmpDirProperty = "java.io.tmpdir"                     ;
 
        String tmpDir         = System.getProperty( tmpDirProperty ) ;
 
        System.out.println( "\n ** VFS = " + tmpDir )                ;

        System.setProperty("java.net.preferIPv4Stack" , "true")      ;
        
        if( System.getProperty("serviceConf") == null ) {
            
            File conf = new File("serviceConf.yaml") ;
            if( ! conf.exists() ) {
                System.out.println(" **** Error " )                                                                              ;
                System.out.println(" - No Service Configuration Provided ! " )                                                   ;
                System.out.println(" - Restart Service providing a configuration file using : -DserviceConf=serviceConf.yaml " ) ;
                System.out.println(" - Where [ serviceConf.yaml ] is the path of the config file " )                             ;
                System.out.println(" - It's used fot Configuring Database Connection  " )                                        ;
                System.out.println("    " )                                                                                      ;
                System.exit(0)   ;
            }
            else {
                System.setProperty("serviceConf" , "serviceConf.yaml") ;
            }
        }
        
        System.out.println( " ** Provided Configuration = " + System.getProperty("serviceConf" ) ) ;
        
        Configurator cfg = new Configurator() ;
        
        /** Set KeyCloak Properties if SSO is Enable **/
        
        if( cfg.getKeycloakFile() != null ) {
            System.setProperty("swarm.keycloak.json.path" , cfg.getKeycloakFile()) ;
        }
        
        String driverClassName  = ((String) cfg.getConfiguration().get("driverClassName")).replaceAll(" +", " ").trim() ;
        String userName         = ((String) cfg.getConfiguration().get("userName"))       .replaceAll(" +", " ").trim() ;
        String password         = ((String) cfg.getConfiguration().get("password"))       .replaceAll(" +", " ").trim() ;
        String connectionUrl    = ((String) cfg.getConfiguration().get("connectionUrl"))  .replaceAll(" +", " ").trim() ;
           
        Swarm  swarm            = new Swarm() ;
      
        swarm.fraction( getDataSource ( driverClassName, connectionUrl, userName, password) ) ;

        ManagementFraction securityRealm = ManagementFraction.createDefaultFraction()
                                                             .httpInterfaceManagementInterface((iface) -> {
                                               iface.allowedOrigin("http://localhost:8080") ;
                                               iface.securityRealm("ManagementRealm")       ;
                                           }).securityRealm("ManagementRealm" , (realm) ->  {
                                                    realm.inMemoryAuthentication (
                                                        (authn) -> {
                                                           authn.add("rya", "rac021", true) ;
                                                    }) ;
                                                    realm.inMemoryAuthorization(
                                                        (authz) -> {
                                                            authz.add("rya", "admin") ;
                                                    }) ;
                                              }) ;

        /*
          Enable HTTPS 
          Ex of Generating a Certificat using JDK : 
         -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000 
        */
        
        if( System.getProperty("transport") != null && 
            System.getProperty("transport").equalsIgnoreCase("HTTPS")) {
             
            System.out.println(" **** Enable HTTP **** " ) ;
             
            securityRealm.securityRealm ( new SecurityRealm("SSLRealm")
                         .sslServerIdentity ( new SslServerIdentity<>()
                            .keystorePath("/opt/jdk/jdk1.8.0_111/jre/bin/my-release-key.keystore")
                            .keystorePassword("yahiaoui021")
                            .alias("alias_name")
                            .keyPassword("yahiaoui021")
                         )
            ) ;
           
            swarm.fraction(new UndertowFraction()
                 .server(new Server("default-server")
                 .httpsListener(new HttpsListener("default")
                 .securityRealm("SSLRealm")
                 .socketBinding("https"))
                 .host(new Host("default-host")))
                 .bufferCache(new BufferCache("default"))
                 .servletContainer(new ServletContainer("default")
                 .websocketsSetting(new WebsocketsSetting())
                 .jspSetting(new JSPSetting()))
                 .handlerConfiguration(new HandlerConfiguration())) ; 

               /*
               swarm.fraction(UndertowFraction.createDefaultFraction()
                    .server("ssl-server", server -> server.httpsListener( new HttpsListener("https")
                      .securityRealm("SSLRealm")
                      .socketBinding("https")
                    ))
               ) ;
               */
        }

        swarm.fraction( securityRealm ) ;
        swarm.fraction(new ManagementConsoleFraction().contextRoot("/console")) ;
        
        swarm.start() ;

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "jax-y.war") ;

        
       if( cfg.getAuthenticationType().equalsIgnoreCase("SSO"))  {
           
           Map  authentication = cfg.getAuthenticationInfos()    ;
           
            ((Map) authentication.get("secured")).forEach( ( _sName, _methods )  -> {
                
               ((Map) _methods).forEach( ( _method, _roles ) -> {
                     
                   String method      = (String) _method        ;
                   List<String> roles = (List<String>) _roles   ;
                   
                   roles.forEach(  role -> {
                         
                     deployment.as(Secured.class)
                               .protect( "/rest/resources/" + _sName )
                               .withMethod( method.toUpperCase().replaceAll(" +", " ").trim() )
                               .withRole( role.replaceAll(" +", " " ).trim() ) ;
                   }) ; 
              }) ;
           }) ;
       }

       /* Swagger Fraction -- Not implemented yet 
       
       deployment.as(SwaggerArchive.class)
                 .setResourcePackages("org.inra.swarm").setTitle("My Application API")
                 .setLicenseUrl("http://myapplication.com/license.txt")
                 .setLicense("Use at will").setContextRoot("/tacos")
                 .setDescription("This is a description of my API")
                 .setHost("api.myapplication.com").setContact("help@myapplication.com")
                 .setPrettyPrint(true).setSchemes( "http", "https")
                 .setTermsOfServiceUrl("http://myapplication.com/tos.txt")
                 .setVersion("1.0") ;
       */

       /** Persistence xml **/
       deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml" ,
        Main.class.getClassLoader()) , "classes/META-INF/persistence.xml") ;
       
       /** beans xml **/
       deployment.addAsWebInfResource(new ClassLoaderAsset("WEB-INF/beans.xml" ,
        Main.class.getClassLoader()) , "beans.xml") ;
       
       /** moxy preperites **/
       deployment.addAsWebInfResource(new ClassLoaderAsset("com/rac021/jax/api/streamers/jaxb.properties" , 
                                        Main.class.getClassLoader()) ,
                                       "classes/com/rac021/jax/api/streamers/jaxb.properties") ;
       
       /** Add Templates resources Dto Resource Service Templates **/ 
       deployment.addAsWebInfResource(new ClassLoaderAsset("templates/Dto"      ,
                  Main.class.getClassLoader()), "classes/templates/Dto")      ;
       deployment.addAsWebInfResource(new ClassLoaderAsset("templates/Resource" ,
                  Main.class.getClassLoader()), "classes/templates/Resource") ;
       deployment.addAsWebInfResource(new ClassLoaderAsset("templates/Service"  ,
                  Main.class.getClassLoader()), "classes/templates/Service")  ;
       
       /** Packages **/
       deployment.addPackage("com.rac021.jaxy.cors")                    ;
       deployment.addPackage("com.rac021.jaxy.unzipper")                ;
       deployment.addPackage("com.rac021.jaxy.override.configuration")  ;
       deployment.addPackage("com.rac021.jaxy.ghosts.services.manager") ;
       
       deployment.addModule(driverModule)  ;
          
       /** Deployment **/
       deployment.addAllDependencies(true) ;
       swarm.deploy( deployment)           ;

    }
    
    private static DatasourcesFraction getDataSource( String driverClassName ,
                                                      String connectionUrl   ,
                                                      String userName        , 
                                                      String password )      {
         
       Objects.requireNonNull(driverClassName) ;
         
       if( driverClassName.toLowerCase().contains("mysql")) {
             
          driverModule = "com.mysql" ;
        
          return datasourceWithMysql( driverClassName , 
                                      connectionUrl   , 
                                      userName        , 
                                      password      ) ;
       }
         
       driverModule = "org.postgresql" ;
         
       return datasourceWithPostgresql( driverClassName , 
                                        connectionUrl   , 
                                        userName        , 
                                        password      ) ;
    }
     
     
    private static DatasourcesFraction datasourceWithMysql( String driverClassName ,
                                                            String connectionUrl   ,
                                                            String userName        , 
                                                            String password)       {
         
       return new DatasourcesFraction().jdbcDriver("com.mysql", (d) -> {
                    d.driverClassName(driverClassName);
                    d.xaDatasourceClass("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
                    d.driverModuleName("com.mysql");
                })
                .dataSource("MyPU", (ds) -> {
                    ds.driverName("com.mysql")                      ;
                    ds.connectionUrl(connectionUrl)                 ;
                    ds.userName(userName)                           ;
                    ds.password(password)                           ;
                    ds.jndiName("java:jboss/datasources/Scheduler") ;
                })                                                  ;
    }
     
    private static DatasourcesFraction datasourceWithPostgresql( String driverClassName ,
                                                                 String connectionUrl   ,
                                                                 String userName        , 
                                                                 String password)       {
       
       return  new DatasourcesFraction().jdbcDriver("org.postgresql" ,
                (d) -> {
                        d.driverClassName( driverClassName)                     ;
                        d.xaDatasourceClass("org.postgresql.xa.PGXADataSource") ;
                        d.driverModuleName("org.postgresql")                    ; 
                 }).dataSource("MyPU", (ds) -> {
                        ds.driverName(driverClassName.replace(".Driver", ""))   ;
                        ds.connectionUrl(connectionUrl)                         ;
                        ds.userName(userName)                                   ;
                        ds.password(password)                                   ;
                        ds.jndiName("java:jboss/datasources/Scheduler")         ;
                    })                                                          ;
    }

}
