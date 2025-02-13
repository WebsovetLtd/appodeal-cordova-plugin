# Appodeal Cordova Plugin

This is an unofficial Appodeal Cordova plugin, created to support Appodeal SDK with Apache Cordova / Ionic apps.<br>

## Implemented

| Ad type        | Android | iOS |
|----------------|---------|-----|
| Banners        | +       | -   |
| Interstitial   | +       | -   |
| Rewarded Video | +       | -   |
| MREC           | -       | -   |
| Native         | -       | -   |

## How to use:

```bash
cordova plugin add "repourl" --variable GOOGLE_MOBILE_ADS_SDK_APP_ID="ca-app-pub-xxxx~yyyy"
```

## initialize:

```javascript
let Appodeal = window['plugins'].Appodeal;
Appodeal.setLogLevel(Appodeal.LogLevel.DEBUG);
Appodeal.muteVideosIfCallsMuted(true);

Appodeal.initialize(
  "Appodeal App Key",
  Appodeal.BANNER | Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO,
  callback
);
```

## set callbacks:

```javascript
Appodeal.setBannerCallbacks(function (container) {
  console.log('banner callback event triggered:', container.event);
});
Appodeal.setInterstitialCallbacks(function (container) {
  console.log('interstitial callback event triggered:', container.event);
});
Appodeal.setRewardedVideoCallbacks(function (container) {
  console.log('rewarded video callback event triggered:', container.event);
  if (container.event === Appodeal.EVENT_CLOSED) {
    // reward is identified here for iOS
    console.log('rewarded video closed, was fully watched?', container.wasFullyWatched);
  } else if (container.event === Appodeal.EVENT_FINISHED) {
    // reward is identified here for android
    console.log('rewarded video finished and fully watched.');
  }
});
```

## show the ads:

```javascript
Appodeal.isInitialized(Appodeal.BANNER_TOP, function (status) {
  console.log('initialization status for BANNER_TOP:', status);
});
Appodeal.canShow(Appodeal.BANNER_TOP, function (canShow) {
  console.log('can show BANNER_TOP?', canShow);
});
Appodeal.show(Appodeal.BANNER_TOP, function (result) {
  console.log('BANNER_TOP ad shown', result);
});

Appodeal.show(Appodeal.INTERSTITIAL, function (result) {
  console.log('INTERSTITIAL ad shown', result);
});

Appodeal.show(Appodeal.REWARDED_VIDEO, function (result) {
  console.log('REWARDED_VIDEO ad shown', result);
});
```

## appodeal SDK versions:

iOS: ~~3.0.2 (must use XCode 14+, with Rosetta when on an Apple silicon processor)~~ \
android: 3.4.1.0