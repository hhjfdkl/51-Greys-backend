# The following section contributed by Gabe, said "Here are some commands for getting tomcat on a VM from scratch. This doesn't include steps to configure remote access. So I would leverage Dustin's snippet"

# Update Packages
sudo apt update

# Instal JDK 11
sudo apt install openjdk-11-jdk

# Verify Version Number
java -version

# Update bash by appending JAVA_HOME variable
echo 'export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64' >> ~/.bashrc

# Reload bash
source ~/.bashrc

# Grab latest version of tomcat 9
# Date: July 23, 2024
# Location: https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.91/bin/apache-tomcat-9.0.91.tar.gz
wget https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.91/bin/apache-tomcat-9.0.91.tar.gz

# Create a sailpoint directory in HOME directory
mkdir ~/sailpoint

# Export and copy tomcat to sailpoint directoy
sudo tar xf apache-tomcat-9.0.91.tar.gz -C ~/sailpoint

# Set access owner for tomcat directory
# UPDATE "current_user"
sudo chown -R current_user:current_user ~/sailpoint/apache-tomcat-9.0.91

# Set shell scripts to executables
sudo chmod +x ~/sailpoint/apache-tomcat-9.0.91/bin/*.sh

# Execute startup.sh
~/sailpoint/apache-tomcat-9.0.91/bin/startup.sh

