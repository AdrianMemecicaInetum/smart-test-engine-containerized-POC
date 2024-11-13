#!/bin/bash

#chsh -s /bin/zsh
#Stop Android
export ANDROID_SDK=$HOME/Library/Android/ANDROID_SDK
export PATH=$ANDROID_SDK/emulator:$ANDROID_SDK/tools:$PATH

#Stop Appium
echo "Parando Appium"
pid=$(lsof -i tcp:4723 -t)
kill $pid

sleep 5

echo "Parando Android"
adb -s emulator-5554 emu kill
