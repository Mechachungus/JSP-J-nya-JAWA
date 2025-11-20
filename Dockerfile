# Stage 1 — Build the app using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Force a fresh build by explicitly cleaning
RUN mvn clean package -DskipTests

# Stage 2 — Run it using Tomcat
FROM tomcat:10.1-jdk17-temurin
# CRITICAL: Ensure the old app is completely gone
RUN rm -rf /usr/local/tomcat/webapps/ROOT
RUN rm -rf /usr/local/tomcat/webapps/ROOT.war

# Copy the new WAR
COPY --from=build /app/target/app.war /usr/local/tomcat/webapps/ROOT.war

# Expose port
EXPOSE 8080

# Launch
CMD ["catalina.sh", "run"]