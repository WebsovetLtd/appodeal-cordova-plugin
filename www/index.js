var Appodeal = exports;

var cordova = require('cordova');
var exec = require('cordova/exec');

var SERVICE = 'Appodeal';

Appodeal.INTERSTITIAL = 3;     // 000000000011
Appodeal.BANNER = 4;           // 000000000100
Appodeal.BANNER_BOTTOM = 8;    // 000000001000
Appodeal.BANNER_TOP = 16;      // 000000010000
Appodeal.BANNER_LEFT = 1024;   // 010000000000
Appodeal.BANNER_RIGHT = 2048;  // 100000000000
Appodeal.REWARDED_VIDEO = 128; // 000010000000
Appodeal.MREC = 256;           // 000100000000

Appodeal.BANNER_X_SMART = 0;
Appodeal.BANNER_X_CENTER = 1;
Appodeal.BANNER_X_LEFT = 2;
Appodeal.BANNER_X_RIGHT = 3;

Appodeal.LogLevel = {
  NONE: 0,
  DEBUG: 1,
  VERBOSE: 2
};

Appodeal.Gender = {
  OTHER: 0,
  MALE: 1,
  FEMALE: 2
};

Appodeal.pluginVersion = '3.2.0';

Appodeal.initialize = function (appKey, adTypes, callback) {
  exec(callback, null, SERVICE, "initialize", [appKey, adTypes]);
};

Appodeal.show = function (adType, placement, callback) {
  exec(callback, null, SERVICE, "show", [adType, placement]);
};

Appodeal.isLoaded = function (adType, callback) {
  exec(callback, null, SERVICE, "isLoaded", [adType]);
};

Appodeal.cache = function (adType) {
  exec(null, null, SERVICE, "cache", [adType]);
};

Appodeal.hide = function (adType) {
  exec(null, null, SERVICE, "hide", [adType]);
};

Appodeal.destroy = function (adType) {
  exec(null, null, SERVICE, "destroy", [adType]);
}

Appodeal.onResume = function (adType) {
  exec(null, null, SERVICE, "onResume", [adType]);
}

Appodeal.setAutoCache = function (adTypes, autoCache) {
  exec(null, null, SERVICE, "setAutoCache", [adTypes, autoCache]);
};

Appodeal.isPrecache = function (adType, callback) {
  exec(callback, null, SERVICE, "isPrecache", [adType]);
};

Appodeal.setBannerAnimation = function (value) {
  exec(null, null, SERVICE, "setBannerAnimation", [value]);
};

Appodeal.setSmartBanners = function (value) {
  exec(null, null, SERVICE, "setSmartBanners", [value]);
};

Appodeal.set728x90Banners = function (value) {
  exec(null, null, SERVICE, "set728x90Banners", [value]);
};

Appodeal.setBannerOverLap = function (value) {
  exec(null, null, SERVICE, "setBannerOverLap", [value]);
};

Appodeal.refreshWebViewForBanner = function () {
  exec(null, null, SERVICE, "refreshWebViewForBanner", []);
};

Appodeal.setTesting = function (testing) {
  exec(null, null, SERVICE, "setTesting", [testing]);
};

Appodeal.setLogLevel = function (loglevel) {
  exec(null, null, SERVICE, "setLogLevel", [loglevel]);
};

Appodeal.setChildDirectedTreatment = function (value) {
  exec(null, null, SERVICE, "setChildDirectedTreatment", [value]);
};

Appodeal.setTriggerOnLoadedOnPrecache = function (set) {
  exec(null, null, SERVICE, "setOnLoadedTriggerBoth", [set]);
};

Appodeal.disableNetwork = function (network, adType) {
  adType = Number(adType) || 0;
  exec(null, null, SERVICE, "disableNetwork", [network, adType]);
};

Appodeal.disableLocationPermissionCheck = function () {
  exec(null, null, SERVICE, "disableLocationPermissionCheck", []);
};

Appodeal.disableWriteExternalStoragePermissionCheck = function () {
  exec(null, null, SERVICE, "disableWriteExternalStoragePermissionCheck", []);
};

Appodeal.muteVideosIfCallsMuted = function (value) {
  exec(null, null, SERVICE, "muteVideosIfCallsMuted", [value]);
};

Appodeal.showTestScreen = function () {
  exec(null, null, SERVICE, "showTestScreen", []);
};

Appodeal.getVersion = function (callback) {
  exec(callback, null, SERVICE, "getVersion", []);
};

Appodeal.getPluginVersion = function () {
  return Appodeal.pluginVersion;
};

Appodeal.isInitialized = function (adTypes, callback) {
  exec(callback, null, SERVICE, "isInitialized", [adTypes]);
};

Appodeal.canShow = function (adType, placement, callback) {
  exec(callback, null, SERVICE, "canShow", [adType, placement]);
};

Appodeal.getRewardParameters = function (placement, callback) {
  exec(callback, null, SERVICE, "getRewardParameters", [placement]);
};

Appodeal.setExtraData = function (name, value) {
  exec(null, null, SERVICE, "setExtraData", [name, value]);
};

Appodeal.getPredictedEcpm = function (adType, callback) {
  exec(callback, null, SERVICE, "getPredictedEcpm", [adType]);
};

Appodeal.setUserId = function (userid) {
  exec(null, null, SERVICE, "setUserId", [userid]);
};

Appodeal.trackInAppPurchase = function (amount, currency) {
  exec(null, null, SERVICE, "trackInAppPurchase", [amount, currency]);
};

Appodeal.setInterstitialCallbacks = function (callback) {
  exec(callback, null, SERVICE, "setInterstitialCallbacks", [])
};

Appodeal.setNonSkippableVideoCallbacks = function (callback) {
  exec(callback, null, SERVICE, "setNonSkippableVideoCallbacks", []);
};

Appodeal.setRewardedVideoCallbacks = function (callback) {
  exec(callback, null, SERVICE, "setRewardedVideoCallbacks", []);
};

Appodeal.setBannerCallbacks = function (callback) {
  exec(callback, null, SERVICE, "setBannerCallbacks", []);
};
