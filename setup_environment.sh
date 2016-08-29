#!/bin/bash
###################################################################
# The purpose of this script is to download, install
# and configure all files and programs required to
# take part in developing ActivitySync (Android only).
###################################################################

RED='\033[0;31m'
GREEN='\033[0;32m'
NOCOLOR='\033[0m'

# update package lists from repositories
sudo apt-get update

# install git
git --version 2>&1 >/dev/null
GIT_IS_AVAILABLE=$?
if [ $GIT_IS_AVAILABLE -eq 0 ]; then
    echo "Skipping git installation (already exists)."
else
    sudo apt-get install git
fi

# keep all work inside /tmp directory
cd /tmp/

# create directory for java
sudo mkdir /usr/lib/jvm

# download, install and configure java7 jdk
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/7u79-b15/jdk-7u79-linux-x64.tar.gz
echo "Unpacking JDK7..."
tar -xzf jdk-7*
sudo mv ./jdk1.7* /usr/lib/jvm/jdk1.7.0_79
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0_79/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.7.0_79/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.7.0_79/bin/javaws" 1
sudo chmod a+x /usr/bin/java
sudo chmod a+x /usr/bin/javac
sudo chmod a+x /usr/bin/javaws

# download, install and configure java8 jdk
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u102-b14/jdk-8u102-linux-x64.tar.gz
echo "Unpacking JDK8..."
tar -xzf jdk-8*
sudo mv ./jdk1.8* /usr/lib/jvm/jdk1.8.0_102
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.8.0_102/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.8.0_102/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.8.0_102/bin/javaws" 1
sudo chmod a+x /usr/bin/java
sudo chmod a+x /usr/bin/javac
sudo chmod a+x /usr/bin/javaws

printf "${RED}[IMPORTANT]${NOCOLOR} Pick JDK8 option!\n"
sudo update-alternatives --config java
printf "${RED}[IMPORTANT]${NOCOLOR} Pick JDK8 option!\n"
sudo update-alternatives --config javac
printf "${RED}[IMPORTANT]${NOCOLOR} Pick JDK8 option!\n"
sudo update-alternatives --config javaws

# export jdk environmental variables
export PATH=$PATH:/usr/lib/jvm/jdk1.8.0_102/bin
export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_102
export JAVA7_HOME=/usr/lib/jvm/jdk1.7.0_79
export JAVA8_HOME=/usr/lib/jvm/jdk1.8.0_102

# download, install and configure android sdk
mkdir -p $HOME/android-sdk/
wget https://dl.google.com/android/android-sdk_r24.4.1-linux.tgz
echo "Unpacking Android SDK..."
tar -xzf android-sdk_r24.4.1-linux.tgz
cd android-sdk-linux/
mv * $HOME/android-sdk/

# export sdk environmental variables
export ANDROID_HOME=$HOME/android-sdk/
export PATH=$PATH:$HOME/android-sdk/tools

# set needed environmental variables permanent for current user
touch $HOME/android-sdk/setup-activityteam-development.sh
chmod a+rwx $HOME/android-sdk/setup-activityteam-development.sh
echo '#!/bin/bash' >> $HOME/android-sdk/setup-activityteam-development.sh
echo "export PATH=\$PATH:/usr/lib/jvm/jdk1.8.0_102/bin" >> $HOME/android-sdk/setup-activityteam-development.sh
echo "export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_102" >> $HOME/android-sdk/setup-activityteam-development.sh
echo "export JAVA7_HOME=/usr/lib/jvm/jdk1.7.0_79" >> $HOME/android-sdk/setup-activityteam-development.sh
echo "export JAVA8_HOME=/usr/lib/jvm/jdk1.8.0_102" >> $HOME/android-sdk/setup-activityteam-development.sh
echo "export ANDROID_HOME=${HOME}/android-sdk/" >> $HOME/android-sdk/setup-activityteam-development.sh
echo "export PATH=\$PATH:${HOME}/android-sdk/tools" >> $HOME/android-sdk/setup-activityteam-development.sh
echo "source ${HOME}/android-sdk/setup-activityteam-development.sh" >> $HOME/.bashrc

# download and update essential sdk files
android update sdk --no-ui

# libraries needed by aapt and adb
sudo apt-get install libstdc++6:i386 libgcc1:i386 zlib1g:i386 libncurses5:i386

# kill current user session in order to run setup-activityteam-development.sh
printf "${GREEN}[OK]${NOCOLOR} Log out in 3 seconds.\n"
sleep 3s
pkill -u $USER