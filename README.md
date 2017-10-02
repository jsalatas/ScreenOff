# Screen Off

## What is it?
**Screen Off** can be used to turn off and lock the screen of any Android 6.0 (Marshmallow) or 7.0 (Nougat) device, in a way that it can be unlocked without violating the security policy that requires a PIN to unlock.

That means that you can use your fingerprint to unlock your device, as you would normally do if the screen was turned of by pressing the hardware power key.

**Screen Off** can also be assigned to be the Assist App of your device and thus it can be activated by long-pressing the Home key.

## How it Works?

Behind the scenes **Screen Off** temporarily lowers the Screen timeout setting to zero and then waits for some time which can be modified in Settings until the operating system triggers the timeout and the screen is turn off. During that wait time the screen is dimmed out and protected against any accidental touch.

## Permissions Needed
### Write Settings

**Screen Off** needs to be able to change system settings and it will ask you to explicitly allow this the first time it runs
Write Secure Settings

Furthermore, if you have enabled the _Developer Options_ and the _Stay awake_ option, Screen Off can still lock the screen even while charging. In order to do so, it needs to be able to change secure system settings. You need to explicitly allow this by connecting your phone to a PC having Android SDK installed, opening a command prompt and typing the following command:

    adb shell pm grant gr.ictpro.jsalatas.screenoff android.permission.WRITE_SECURE_SETTINGS

## Install
**Screen Off** is available free of charge in [Google's Play Store](https://play.google.com/store/apps/details?id=gr.ictpro.jsalatas.screenoff).

_Copyright &copy; 2017: John Salatas. All Rights Reserved._
 