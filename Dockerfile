FROM ubuntu:16.04

RUN apt-get -qq -y update && \
    apt-get -qq -y install openjdk-8-jdk ant curl && \
    rm -rf /var/cache/apt /var/lib/apt/lists/*

# Your deployment script here
COPY . /
RUN /usr/share/ant/bin/ant
ENTRYPOINT java -cp dist/amazon-keyword-estimate.jar:dist/lib/*:dist/conf com.keyword.KeywordMain $*
