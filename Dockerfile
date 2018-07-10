FROM java:8-jre-alpine
ADD dist/amazon-keyword-estimate.jar app.jar
ADD dist/lib lib
ADD dist/conf conf
RUN apk update && apk add bash
RUN bash -c 'touch /app.jar'
ENTRYPOINT java -cp app.jar:lib/*conf com.keyword.KeywordMain $*
