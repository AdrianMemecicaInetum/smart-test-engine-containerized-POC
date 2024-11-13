#!/bin/bash

#Start Android
echo "Arrancando Android en ventana nueva."
ruta_script_Android="/Users/inetum/Documents/Android-Front-Automated-testing/Test-Automation/Plugins/Mobile/startAndroid.sh"
osascript -e "tell application \"Terminal\" to do script \"$ruta_script_Android\""

sleep 20
emulator -list-avds
adb devices

#Start Appium
echo "Arrancando Appium en ventana nueva."
ruta_script_Appium="/Users/inetum/Documents/Android-Front-Automated-testing/Test-Automation/Plugins/Mobile/startAppium.sh"
osascript -e "tell application \"Terminal\" to do script \"$ruta_script_Appium\""

emulator -list-avds
adb devices