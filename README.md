<p align="center"><img src="fastlane/metadata/android/en-US/images/icon.svg" width="150"></p>
<h1 align="center"><b>CheckIn24</b></h1>
<h4 align="center">Offline, Material You, check in client for 24 Hour Fitness gym memberships</h4>
<p align="center">
    <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-a503fc?logo=kotlin&logoColor=white&style=for-the-badge">
    <img alt="Jetpack Compose" src="https://img.shields.io/static/v1?style=for-the-badge&message=Jetpack+Compose&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label=">
    <img alt="API" src="https://img.shields.io/badge/Api%2028+-50f270?logo=android&logoColor=black&style=for-the-badge"><br>
    <img alt="GitHub Release" src="https://img.shields.io/github/v/release/sergcam/CheckIn24?color=a1168e&include_prereleases&style=for-the-badge&labelColor=700f63">
    <img alt="GitHub License" src="https://img.shields.io/github/license/sergcam/CheckIn24?style=for-the-badge&labelColor=A6572C">
    <img alt="GitHub Downloads (all assets, all releases)" src="https://img.shields.io/github/downloads/sergcam/CheckIn24/total?link=https%3A%2F%2Fgithub.com%2Fsergcam%2FCheckIn24%2Freleases&style=for-the-badge&labelColor=97790E">
</p><br>
<p align="center"><a href="https://apps.obtainium.imranr.dev/redirect?r=obtainium://app/%7B%22id%22%3A%22dev.secam.checkin24%22%2C%22url%22%3A%22https%3A%2F%2Fgithub.com%2Fsergcam%2FCheckIn24%22%2C%22author%22%3A%22sergcam%22%2C%22name%22%3A%22CheckIn24%22%2C%22preferredApkIndex%22%3A0%2C%22additionalSettings%22%3A%22%7B%5C%22includePrereleases%5C%22%3Atrue%2C%5C%22fallbackToOlderReleases%5C%22%3Atrue%2C%5C%22filterReleaseTitlesByRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22filterReleaseNotesByRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22verifyLatestTag%5C%22%3Afalse%2C%5C%22sortMethodChoice%5C%22%3A%5C%22date%5C%22%2C%5C%22useLatestAssetDateAsReleaseDate%5C%22%3Afalse%2C%5C%22releaseTitleAsVersion%5C%22%3Afalse%2C%5C%22trackOnly%5C%22%3Afalse%2C%5C%22versionExtractionRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22matchGroupToUse%5C%22%3A%5C%22%5C%22%2C%5C%22versionDetection%5C%22%3Atrue%2C%5C%22releaseDateAsVersion%5C%22%3Afalse%2C%5C%22useVersionCodeAsOSVersion%5C%22%3Afalse%2C%5C%22apkFilterRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22invertAPKFilter%5C%22%3Afalse%2C%5C%22autoApkFilterByArch%5C%22%3Atrue%2C%5C%22appName%5C%22%3A%5C%22%5C%22%2C%5C%22appAuthor%5C%22%3A%5C%22%5C%22%2C%5C%22shizukuPretendToBeGooglePlay%5C%22%3Afalse%2C%5C%22allowInsecure%5C%22%3Afalse%2C%5C%22exemptFromBackgroundUpdates%5C%22%3Afalse%2C%5C%22skipUpdateNotifications%5C%22%3Afalse%2C%5C%22about%5C%22%3A%5C%22%5C%22%2C%5C%22refreshBeforeDownload%5C%22%3Afalse%7D%22%2C%22overrideSource%22%3Anull%7D
"><img src="meta/badges/obtain.svg" width="200"></a></p>

## About

Checkin24 is a lightweight, offline alternative to the official 24go app for checking into your 24 Hour Fitness gym built with Jetpack Compose. This app can NOT get you into the gym for free. An active 24 hour fitness membership is still required.

## Features 
- Fully offline
- All user data is stored locally
- Small app size
- Material You theming

## Screenshots
<p align="center">
    <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/1.png" width=400>
    <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/2.png" width=400>
    <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/3.png" width=400>
    <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/4.png" width=400>

</p>

## How it works

The data written in the check in qr code on the official 24go app is a JSON object in the following format: 

```json
{
  "SR":"24GO",
  "MB":"MBR00000000",
  "DT":1752738571206,
  "TP":"P",
  "OS":"Android",
  "AP":"1.78.1",
  "DI":"00000000-0000-0000-0000-000000000000"
}
```
- `SR` app name. default value: `"24GO"`
- `MB` user member number preceded by `"MBR"`
- `DT` unix time stamp in millis
- `TP` honestly don't know what this means but default value is `"P"`
- `OS` device operating system. in this case `"Android"`
- `AP` 24go app version. `"1.78.1"` at the time of 1.0.0 release. will update value as necessary to maintain functionality
- `DI` device advertising id. for a device with no advertising id this value is `"00000000-0000-0000-0000-000000000000"` which is also the value this app uses

CheckIn24 uses a user provided member number stored locally on the device to generate a valid QR code which can be used to check in to the gym

## Installation

Head over to the [Releases](https://github.com/sergcam/CheckIn24/releases) section and download the latest APK. Might add to F-Droid in the future

## Disclaimer 

This application comes with no guarantee of functionality or adherence to 24 Hour Fitness policy. It works for me. It might work for you. Use at your own discretion.

This app is not intended to facilitate membership sharing. Pretty sure photos are required now anyway

