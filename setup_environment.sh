#!/bin/bash
###################################################################
# The purpose of this script is to download, install
# and configure all files and programs required to
# take part in developing ActivitySync (Android only).
###################################################################

CURRENT_USER=$USER

# update package lists from repositories
sudo apt-get update
sudo apt-get install git

sudo mkdir /usr/lib/jvm
cd /tmp/

# download, install and configure java7 jdk
sudo wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/7u79-b15/jdk-7u79-linux-x64.tar.gz
sudo tar -xvf jdk-7*
sudo mv ./jdk1.7* /usr/lib/jvm/jdk1.7.0
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.7.0/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.7.0/bin/javaws" 1
sudo chmod a+x /usr/bin/java
sudo chmod a+x /usr/bin/javac
sudo chmod a+x /usr/bin/javaws


# download, install and configure java8 jdk
sudo wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u102-b14/jdk-8u102-linux-x64.tar.gz
sudo tar -xvf jdk-8*
sudo mv ./jdk1.8* /usr/lib/jvm/jdk1.8.0
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.8.0/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.8.0/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.8.0/bin/javaws" 1
sudo chmod a+x /usr/bin/java
sudo chmod a+x /usr/bin/javac
sudo chmod a+x /usr/bin/javaws

echo "[IMPORTANT] Pick jdk1.8.0 option!"
sudo update-alternatives --config java
echo "[IMPORTANT] Pick jdk1.8.0 option!"
sudo update-alternatives --config javac
echo "[IMPORTANT] Pick jdk1.8.0 option!"
sudo update-alternatives --config javaws


# set environmental variables
sudo touch /etc/profile.d/oraclejdk.sh
sudo chmod a+rwx /etc/profile.d/oraclejdk.sh
sudo echo '#!/bin/bash' >> /etc/profile.d/oraclejdk.sh
sudo echo "export PATH=\$PATH:/usr/lib/jvm/jdk1.8.0/bin:/usr/lib/jvm/jdk1.8.0/bin" >> /etc/profile.d/oraclejdk.sh
sudo echo "export JAVA_HOME=/usr/lib/jvm/jdk1.8.0" >> /etc/profile.d/oraclejdk.sh
sudo echo "export JAVA7_HOME=/usr/lib/jvm/jdk1.7.0" >> /etc/profile.d/oraclejdk.sh
sudo echo "export JAVA8_HOME=/usr/lib/jvm/jdk1.8.0" >> /etc/profile.d/oraclejdk.sh
export PATH=$PATH:/usr/lib/jvm/jdk1.8.0/bin:/usr/lib/jvm/jdk1.8.0/bin
export JAVA_HOME=/usr/lib/jvm/jdk1.8.0
export JAVA7_HOME=/usr/lib/jvm/jdk1.7.0
export JAVA8_HOME=/usr/lib/jvm/jdk1.8.0

# download, install and configure android sdk
cd /tmp/
sudo mkdir -p /opt/android-sdk/

sudo wget https://dl.google.com/android/android-sdk_r24.4.1-linux.tgz
sudo tar -xvzf android-sdk_r24.4.1-linux.tgz

cd android-sdk-linux/
sudo mv * /opt/android-sdk/

sudo touch /etc/profile.d/android.sh
sudo chmod a+rwx /etc/profile.d/android.sh

sudo echo '#!/bin/bash' >> /etc/profile.d/android.sh
sudo echo "export ANDROID_HOME=/opt/android-sdk/" >> /etc/profile.d/android.sh
sudo echo "export PATH=\$PATH:/opt/android-sdk/tools" >> /etc/profile.d/android.sh
export ANDROID_HOME=/opt/android-sdk/
export PATH=$PATH:/opt/android-sdk/tools

sudo chown -R $CURRENT_USER /opt/android-sdk/

android update sdk --no-ui
sudo apt-get install libstdc++6:i386 libgcc1:i386 zlib1g:i386 libncurses5:i386
sudo chown -R $CURRENT_USER /home/$CURRENT_USER/.android/

echo "[OK] Rebooting in 3 seconds..."
sleep 3s
sudo reboot