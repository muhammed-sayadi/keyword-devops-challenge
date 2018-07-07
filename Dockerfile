FROM java:8-jre-alpine
ADD .circleci/amazon-keyword-estimate.jar app.jar
RUN apk update && apk add bash
RUN bash -c 'touch /app.jar'
ENTRYPOINT java -cp dist/amazon-keyword-estimate.jar:dist/lib/*:dist/conf com.keyword.KeywordMain $*
