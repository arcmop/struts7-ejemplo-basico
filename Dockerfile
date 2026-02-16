FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY .mvn/ .mvn/
COPY pom.xml .

COPY src/ src/

RUN chmod +x .mvn/wrapper/maven-wrapper.jar 2>/dev/null; \
    ./mvnw -B clean package -DskipTests || \
    (apt-get update && apt-get install -y maven && mvn -B clean package -DskipTests)

FROM eclipse-temurin:21-jre

LABEL maintainer="tienda-app"
LABEL description="Struts 2 v7.1.1 Tienda App on Tomcat 11 JDK 21"

ENV CATALINA_HOME=/opt/tomcat
ENV PATH="${CATALINA_HOME}/bin:${PATH}"
ENV TOMCAT_VERSION=11.0.18

RUN apt-get update && \
    apt-get install -y --no-install-recommends wget unzip && \
    wget -q "https://dlcdn.apache.org/tomcat/tomcat-11/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.zip" \
         -O /tmp/tomcat.zip && \
    unzip -q /tmp/tomcat.zip -d /opt && \
    mv /opt/apache-tomcat-${TOMCAT_VERSION} ${CATALINA_HOME} && \
    rm -f /tmp/tomcat.zip && \
    apt-get purge -y wget unzip && \
    apt-get autoremove -y && \
    rm -rf /var/lib/apt/lists/* && \
    rm -rf ${CATALINA_HOME}/webapps/ROOT \
           ${CATALINA_HOME}/webapps/docs \
           ${CATALINA_HOME}/webapps/examples && \
    chmod +x ${CATALINA_HOME}/bin/*.sh && \
    sed -i '/RemoteAddrValve/d; /RemoteCIDRValve/d' ${CATALINA_HOME}/webapps/manager/META-INF/context.xml ${CATALINA_HOME}/webapps/host-manager/META-INF/context.xml

RUN groupadd -r tomcat && \
    useradd -r -g tomcat -d ${CATALINA_HOME} -s /sbin/nologin tomcat && \
    chown -R tomcat:tomcat ${CATALINA_HOME}

COPY --from=builder --chown=tomcat:tomcat /app/target/tienda.war ${CATALINA_HOME}/webapps/tienda.war

RUN printf '#!/bin/sh\n\
export CATALINA_OPTS="$CATALINA_OPTS \
  -Dapp.database.host=$APP_DATABASE_HOST \
  -Dapp.database.port=$APP_DATABASE_PORT \
  -Dapp.database.name=$APP_DATABASE_NAME \
  -Dapp.database.user=$APP_DATABASE_USER \
  -Dapp.database.password=$APP_DATABASE_PASSWORD"\n\
exec catalina.sh run\n' > ${CATALINA_HOME}/bin/entrypoint.sh && \
    chmod +x ${CATALINA_HOME}/bin/entrypoint.sh && \
    chown tomcat:tomcat ${CATALINA_HOME}/bin/entrypoint.sh

USER tomcat

EXPOSE 8080

CMD ["entrypoint.sh"]
