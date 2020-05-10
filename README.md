# proximity project

This project uses an embedded tomcat server, in order to keep installation steps to a minimum.

# Installation instructions
1. JDK 8 or later is required to run the project. JAVA_HOME variable should point to jdk installation.
2. First, checkout the code with the command **git clone https://github.com/Raphael-/proximity.git**
3. Go to directory that contains the maven wrapper by executing cd proximity/proximity

- Windows
  1. mvnw.cmd clean install tomee:run
- Linux
  1. chmod +x mvnw
  2. ./mvnw clean install tomee:run
  
To stop the server run (according to your platform) mvn tomee:stop

# WSDL File location
You can find the wsdl file in http://localhost:8080/proximity-1.0.0/ProximitySoapService?wsdl

# WSDL Sample Requests
Sample requests for the SOAP service are located in sample_requests folder
 
