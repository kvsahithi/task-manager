set -e

# Check if Tomcat is running and stop it
if ps -ef | grep -q 'apache-tomcat'; then
    echo "Shutting down Tomcat"
    ../"apache-tomcat-10.1.34"/bin/shutdown.sh
fi

# Remove old .war and directory if they exist
echo "Removing old .war and directory"
if [ -f ../"apache-tomcat-10.1.34"/webapps/TodoAppBackend.war ]; then
    rm ../"apache-tomcat-10.1.34"/webapps/TodoAppBackend.war
fi
if [ -d ../"apache-tomcat-10.1.34"/webapps/TodoAppBackend ]; then
    rm -rf ../"apache-tomcat-10.1.34"/webapps/TodoAppBackend
fi

# Build the Maven project
echo "Changing to backend directory and running Maven build"
   mvn clean package

# Copy the new .war file to the Tomcat webapps directory
echo "Copying new .war file to Tomcat"
cp ./target/TodoAppBackend.war ../"apache-tomcat-10.1.34"/webapps

# Start Tomcat
echo "Starting Tomcat"
 ../"apache-tomcat-10.1.34"/bin/startup.sh
