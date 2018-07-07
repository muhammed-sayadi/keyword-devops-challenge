#@IgnoreInspection BashAddShebang

sudo apt-get -y -qq update
sudo apt-get install python-pip python-dev build-essential
sudo pip install awsebcli --upgrade
eb deploy --timeout 15
