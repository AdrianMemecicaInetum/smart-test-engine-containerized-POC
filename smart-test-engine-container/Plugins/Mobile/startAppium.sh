#!/bin/bash

# Autor: Ricardo Ruiz Anaya

export MAVEN_HOME=/usr/local/Cellar/maven/3.6.3_1
export PATH=$MAVEN_HOME/bin:$PATH
export ANDROID_HOME=/Users/inetum/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/tools/bin
export PATH=$PATH:$ANDROID_HOME/emulator
node --max-old-space-size=8192 $(which appium) --port 4723