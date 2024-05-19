FROM ubuntu:22.04

# Install dependencies
RUN apt-get update && \
    apt-get install -y curl unzip wget gnupg2 software-properties-common

# Install Java
RUN apt install firefox -y

RUN add-apt-repository ppa:openjdk-r/ppa && \
    apt-get update && \
    apt-get install -y openjdk-8-jdk

# Install Gradle
ENV GRADLE_VERSION=7.4
RUN wget -q https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip && \
    unzip gradle-$GRADLE_VERSION-bin.zip && \
    rm gradle-$GRADLE_VERSION-bin.zip && \
    mv gradle-$GRADLE_VERSION /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle

# install geckodriver
RUN apt-get update && \
    apt-get install -y wget tar firefox && \
    wget https://github.com/mozilla/geckodriver/releases/download/v0.32.0/geckodriver-v0.32.0-linux64.tar.gz && \
    tar -xvzf geckodriver-v0.32.0-linux64.tar.gz && \
    chmod +x geckodriver && \
    mv geckodriver /usr/local/bin/ && \
    rm geckodriver-v0.32.0-linux64.tar.gz && \
    apt-get clean


# Set environment variables
# Using apple silicon
# ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-arm64
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd4
ENV GRADLE_HOME=/opt/gradle

# Add Gradle and Java to PATH
ENV PATH=$PATH:$GRADLE_HOME/bin:$JAVA_HOME/bin

RUN apt-get update && apt-get install -y sudo

RUN username="selenium" && \
    addgroup -gid 1000 $username && \
    mkdir -p "/home/$username" && \
    cp -a /root/. "/home/$username" && \
    adduser --uid 1000 --home "/home/$username" --gid 1000 --quiet --disabled-password --gecos "Mr. $username User,,,"  $username && \
    usermod -p "Q4oQmhJG0ctkM" $username && \
    sudo usermod -a -G sudo $username && \
    chown -R "$username.$username" "/home/$username"

ENV TZ=Europe/Budapest
ENV DEBIAN_FRONTEND=noninteractive
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /home/selenium/
ENV HOME=/home/selenium/

USER selenium

CMD ["/bin/bash"]
