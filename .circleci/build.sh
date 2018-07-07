#@IgnoreInspection BashAddShebang

TAG=$CIRCLE_BUILD_NUM
APP_NAME=amazon-keyword-estimate
DOCKER_IMAGE_NAME=$APP_NAME
ant -buildfile /home/circleci/repo/
cp /home/circleci/repo/dist/$APP_NAME.jar .
docker build -t $DOCKER_IMAGE_NAME /home/circleci/repo/
curl "https://s3.amazonaws.com/aws-cli/awscli-bundle.zip" -o "awscli-bundle.zip"
unzip awscli-bundle.zip
./awscli-bundle/install -b ~/bin/aws
docker_login=$(~/bin/aws ecr get-login --no-include-email)
ECR_REGISTRY=`echo $docker_login | sed 's|.*https://||'`
eval "$docker_login"
docker images
docker tag $DOCKER_IMAGE_NAME:latest $ECR_REGISTRY/$DOCKER_IMAGE_NAME:latest
docker tag $DOCKER_IMAGE_NAME:latest $ECR_REGISTRY/$DOCKER_IMAGE_NAME:$TAG
docker push $ECR_REGISTRY/$DOCKER_IMAGE_NAME:latest
docker push $ECR_REGISTRY/$DOCKER_IMAGE_NAME:$TAG
