FROM maven:3.8.5-openjdk-11
EXPOSE 8080

ENV deploy  /var/local/app
RUN mkdir -p $deploy
ADD ./pom.xml $deploy/pom.xml
ADD ./openid-connect-client/pom.xml $deploy/openid-connect-client/pom.xml
ADD ./openid-connect-common/pom.xml $deploy/openid-connect-common/pom.xml
ADD ./openid-connect-server/pom.xml $deploy/openid-connect-server/pom.xml
ADD ./openid-connect-server-webapp/pom.xml $deploy/openid-connect-server-webapp/pom.xml
ADD ./uma-server/pom.xml $deploy/uma-server/pom.xml
ADD ./uma-server-webapp/pom.xml $deploy/uma-server-webapp/pom.xml

WORKDIR $deploy

RUN mvn clean install --fail-never

