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
sudo wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/7u79-b15/jdk-7u79-linux-x64.tar.gz
sudo tar -xvf jdk-7*
sudo mv ./jdk1.7* /usr/lib/jvm/jdk1.7.0_79
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0_79/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.7.0_79/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.7.0_79/bin/javaws" 1
sudo chmod a+x /usr/bin/java
sudo chmod a+x /usr/bin/javac
sudo chmod a+x /usr/bin/javaws


# download, install and configure java8 jdk
sudo wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u102-b14/jdk-8u102-linux-x64.tar.gz
sudo tar -xvf jdk-8*
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


# set environmental variables
sudo touch /etc/profile.d/oraclejdk.sh
sudo chmod a+rwx /etc/profile.d/oraclejdk.sh
sudo echo '#!/bin/bash' >> /etc/profile.d/oraclejdk.sh
sudo echo "export PATH=\$PATH:/usr/lib/jvm/jdk1.8.0_102/bin" >> /etc/profile.d/oraclejdk.sh
sudo echo "export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_102" >> /etc/profile.d/oraclejdk.sh
sudo echo "export JAVA7_HOME=/usr/lib/jvm/jdk1.7.0_79" >> /etc/profile.d/oraclejdk.sh
sudo echo "export JAVA8_HOME=/usr/lib/jvm/jdk1.8.0_102" >> /etc/profile.d/oraclejdk.sh
export PATH=$PATH:/usr/lib/jvm/jdk1.8.0_102/bin
export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_102
export JAVA7_HOME=/usr/lib/jvm/jdk1.7.0_79
export JAVA8_HOME=/usr/lib/jvm/jdk1.8.0_102

# download, install and configure android sdk
mkdir -p /home/$USER/android-sdk/

sudo wget https://dl.google.com/android/android-sdk_r24.4.1-linux.tgz
sudo tar -xvzf android-sdk_r24.4.1-linux.tgz

cd android-sdk-linux/
sudo mv * /home/$USER/android-sdk/

sudo touch /etc/profile.d/android.sh
sudo chmod a+rwx /etc/profile.d/android.sh

sudo echo '#!/bin/bash' >> /etc/profile.d/android.sh
sudo echo "export ANDROID_HOME=/home/${USER}/android-sdk/" >> /etc/profile.d/android.sh
sudo echo "export PATH=\$PATH:/home/${USER}/android-sdk/tools" >> /etc/profile.d/android.sh
export ANDROID_HOME=/home/$USER/android-sdk/
export PATH=$PATH:/home/$USER/android-sdk/tools

sudo chown -R $USER /home/$USER/android-sdk/

android update sdk --no-ui
sudo apt-get install libstdc++6:i386 libgcc1:i386 zlib1g:i386 libncurses5:i386

printf "${GREEN}[OK]${NOCOLOR} Log out in 3 seconds\n"
sleep 3
pkill -u $USER