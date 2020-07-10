## How to use backend code

#### Follow jupiter project 
- select new workspace
- create tomcat server
- tomcat configuration
- add and remove: configured with `delivery`
- new maven project
> Group Id: flagcampproject
> Artifact Id: delivery
> Version: 0.0.0.1-SNAPSHOT

#### dependencies required in this project
```
<properties>
   <javaVersion>1.8</javaVersion>
   <maven.compiler.source>1.8</maven.compiler.source>
   <maven.compiler.target>1.8</maven.compiler.target>
</properties>

<dependencies>
   <dependency>
       <groupId>junit</groupId>
       <artifactId>junit</artifactId>
       <version>4.11</version>
       <scope>test</scope>
   </dependency>
   <dependency>
       <groupId>org.apache.tomcat</groupId>
       <artifactId>tomcat-catalina</artifactId>
       <version>9.0.30</version>
   </dependency>
   <dependency>
       <groupId>org.json</groupId>
       <artifactId>json</artifactId>
       <version>20190722</version>
   </dependency>
   <dependency>
       <groupId>org.apache.httpcomponents</groupId>
       <artifactId>httpclient</artifactId>
       <version>4.5.10</version>
   </dependency>
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>8.0.18</version>
   </dependency>
   <dependency>
       <groupId>com.google.maps</groupId>
       <artifactId>google-maps-services</artifactId>
       <version>0.14.0</version>
   </dependency>
   <dependency>
       <groupId>org.slf4j</groupId>
       <artifactId>slf4j-simple</artifactId>
       <version>1.7.25</version>
   </dependency>
   <dependency>
       <groupId>com.smartystreets.api</groupId>
	   <artifactId>smartystreets-java-sdk</artifactId>
	   <version>3.5.2</version>
   </dependency>
</dependencies>
```

#### Servlet
- rpc
  - /register
  - /login
  - /recommendation






















