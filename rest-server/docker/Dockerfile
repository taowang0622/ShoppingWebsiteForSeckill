FROM tomcat:7-jre8
# tomcat-users.xml sets up user accounts for the Tomcat manager GUI
ADD tomcat-users.xml $CATALINA_HOME/conf/
# deploying the artifact to Tomcat
ADD seckill.war /usr/local/tomcat/webapps/