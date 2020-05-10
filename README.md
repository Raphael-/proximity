# proximity project

This project uses an embedded tomcat server, in order to keep installation steps to a minimum.

# Installation instructions
1. JDK 8 or later is required to run the project. JAVA_HOME variable should point to jdk installation.
2. First, checkout the code with the command **git clone https://github.com/Raphael-/proximity.git**
3. Go to proximity directory (root folder of the checked out project)

- Windows
  1. mvnw.cmd clean install tomee:run
- Linux
  1. chmod +x mvnw
  2. mvnw clean install tomee:run
 
 To stop the server run (according to your platform) mvn tomee:stop
