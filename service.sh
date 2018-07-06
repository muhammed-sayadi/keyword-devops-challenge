#!/usr/bin/env bash

cd $(dirname $0)

dev_build() {
  ant
}

dev_run() {
  java -cp dist/amazon-keyword-estimate.jar:dist/lib/*:dist/conf com.keyword.KeywordMain $*
}

docker_build() {
  # Your implementation here
  docker build -t amazon-keyword-estimate-img .
}

docker_run() {
  # Your implementation here
  docker run --name amazon-keyword-estimate-img -p 8080:8080 amazon-keyword-estimate
}

usage() {
  cat <<EOF
Usage:
  $0 <command> <args>
Local machine commands:
  dev_build        : builds and packages the app
  dev_run          : starts the app
Docker commands:
  docker_build     : packages the app into a docker image
  docker_run       : runs the app using a docker image
EOF
}

action=$1
action=${action:-"usage"}
action=${action/help/usage}
shift
if type -t $action >/dev/null; then
  echo "Invoking: $action"
  $action $*
else
  echo "Unknown action: $action"
  usage
  exit 1
fi
