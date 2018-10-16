FROM gradle:jdk10 as builder

COPY --chown=gradle:gradle build.gradle /home/gradle/

WORKDIR /home/gradle

# to get dependencies
RUN gradle build || return 0 

COPY --chown=gradle:gradle src/ src/

RUN gradle bootRepackage
RUN ls /home/gradle/build/libs/


FROM openjdk:10-jre-slim as target
COPY --from=builder /home/gradle/build/libs/gradle.jar /app/demo.jar
CMD java -jar /app/demo.jar
