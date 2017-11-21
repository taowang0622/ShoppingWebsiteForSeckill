FROM tomcat:7-jre8
# tomcat-users.xml sets up user accounts for the Tomcat manager GUI
ADD tomcat-users.xml $CATALINA_HOME/conf/
# deploying the artifact to Tomcat
ADD webapps/seckill.war /usr/local/tomcat/webapps/
#Remove the system defined ROOT folder to avoid the URL prefixed with "/SnakeOnWeb"
#Points to note:
#1. RUN <command>==>command is run in a shell which is by default "/bin/sh -c" in linux!
#2. The RUN instruction will execute any commands in a new layer on top of the current image and commit the results.
#    The resulting committed image will be used for the next step in the Dockerfile.
#    Layering RUN instructions and generating commits conforms to the core concepts
#    of Docker where commits are cheap and containers can be created from any point in an image’s history, much like source control.
#    No wonder there are intermediate containners created!!
RUN rm -r /usr/local/tomcat/webapps/ROOT/
#Rename the war file to ROOT to take "/" url!!!
RUN mv /usr/local/tomcat/webapps/seckill.war /usr/local/tomcat/webapps/ROOT.war