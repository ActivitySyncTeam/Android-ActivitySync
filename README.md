# Android-ActivitySync
App for organizing sport enents

## Preparing developer's environment
To prepare developer's environment follow quick guide below. Stay responsive during script execution and be patient - it takes time.

1. Make sure you have clear [Ubuntu](http://www.ubuntu.com/download/alternative-downloads) installation - Ubuntu 14.04.5 LTS (Trusty Tahr) 64-bit

2. Download and run script by executing following commands (do not run script with super user context!)
```
wget https://github.com/ActivitySyncTeam/Android-ActivitySync/blob/master/setup_environment.sh
sh setup_environment.sh
```

3. After it has done it's work you can clone repository by executing the following command
```
git clone https://github.com/ActivitySyncTeam/Android-ActivitySync
```

4. To build project type in root project directory
```
./gradlew assemble
```

5. After successful build you can find ActivitySync Android package (*.apk) in 

> $ROOT_PROJECT_DIRECTORY/app/build/outputs/apk/

