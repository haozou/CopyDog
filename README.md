# Copy Dog

- ### DEV Setup:

----------------

##### Java JDK 8

1. Intalling java jdk8 from <https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html> 
2. Setup JAVA_HOME in your **System Environment** to point it to the java jdk installation path and JRE_HOME to the java jre installation.

##### Maven

1. Installing Maven from <https://maven.apache.org/download.cgi>
2. Setup MAVEN_HOME in your **System Environment** to point it to your maven installation path.

##### Git
1. Installing Git Command tool

##### MySql
1. Installing MySql server and client for data persistent layer <https://dev.mysql.com/downloads/installer/>

##### IDE for development

1. We recommend using Intellij for local development IDE. You can install Intellij Community version from <https://www.jetbrains.com/idea/download/#section=windows>


----------------


- ### Setup local code base and development

1. Clone this repo with the following command:

   ```bash
   git clone https://github.com/haozou/copy_dog.git
   ```

   or if you use ssh keys to manage the repo

   ```bash
   git clone git@github.com:haozou/copy_dog.git
   ```

2. Build the code base with the following command:
   In your project base directory, checkout main branch and build the project

   ```bash
   git checkout main
   mvn clean install -DskipTests
   ```

3. Open the project with your favourite IDE. For example, in Intellij, you can directly open the project as maven project, and all the dependency will be auto resolved.

4. Checkout your own branch and make development there, then push the commit and create a pull request for your changes.

   ```bash
   git checkout -b user/<alias>/<branchName>
   ```

5. Before running the application, you need to create a database(copy_dog) in mysql db. In copy_dog/backend/restservice/src/main/resources/application.properties, make sure you properly configure the database's username and password
   ```bash
   mysql -u root 
   # in mysql terminal
   create database copy_dog
   ```
6. Run the following command to run the application and then you can go to localhost:8080 in your browser. We only support login using github account right now, you can login using github account.
   ```bash
   mvn spring-boot:run
   ```
7. You can go to http://localhost:8080/swagger-ui/#/ to play with the supported restapi.

---------------------------

- ### Build and Release
TODO: We need to define build and release here.