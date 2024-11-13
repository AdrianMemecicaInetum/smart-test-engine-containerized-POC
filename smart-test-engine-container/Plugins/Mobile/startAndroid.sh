#!/bin/bash

export ANDROID_SDK=$HOME/Library/Android/sdk
export PATH=$ANDROID_SDK/emulator:$ANDROID_SDK/tools:$PATH

emulator @Nexus_6_API_28 -snapshot "Android limpio"