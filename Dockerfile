FROM openjdk:17-slim

# Java 환경 변수 설정
ENV JAVA_HOME /usr/lib/jvm/openjdk-17
ENV PATH $PATH:$JAVA_HOME/bin

WORKDIR /app

# 현재 디렉토리의 모든 파일을 컨테이너의 /app 디렉토리에 복사
COPY . /app

EXPOSE 5005
# 애플리케이션이 사용할 포트
EXPOSE 8443

# 컨테이너 시작 시 실행할 명령어
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "./build/libs/oauth2-0.0.1-SNAPSHOT.jar"]