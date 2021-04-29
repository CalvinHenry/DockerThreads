FROM openjdk:13

RUN yum update -y
RUN yum install -y python3

COPY . /Server
EXPOSE 8080

WORKDIR /Server

RUN javac Server/*.java
RUN python3 --version

CMD ["python3", "Server/pythonStarter.py"]
#CMD ["java", "-classpath", "Server", "Main"]