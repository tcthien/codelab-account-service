FROM java:8-jre
MAINTAINER Thien Tran <thientran1986@gmail.com>


ADD ./target/codelab-account-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/codelab-account-service.jar"]

EXPOSE 20082
