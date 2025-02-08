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
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "initialize", [appKey, adTypes]);

  if (!callback) return promise;
};

Appodeal.show = function (adType, placement, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  if (!placement) placement = null;
  exec(resolve, reject, SERVICE, "show", [adType, placement]);

  if (!callback) return promise;
};

Appodeal.isLoaded = function (adType, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "isLoaded", [adType]);

  if (!callback) return promise;
};

Appodeal.cache = function (adType, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "cache", [adType]);

  if (!callback) return promise;
};

Appodeal.hide = function (adType, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "hide", [adType]);

  if (!callback) return promise;
};

Appodeal.destroy = function (adType, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "destroy", [adType]);

  if (!callback) return promise;
}

Appodeal.onResume = function (adType, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "onResume", [adType]);

  if (!callback) return promise;
}

Appodeal.setAutoCache = function (adTypes, autoCache, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "setAutoCache", [adTypes, autoCache]);

  if (!callback) return promise;
};

Appodeal.isPrecache = function (adType, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "isPrecache", [adType]);

  if (!callback) return promise;
};

Appodeal.setBannerAnimation = function (value, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "setBannerAnimation", [value]);

  if (!callback) return promise;
};

Appodeal.setSmartBanners = function (value, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "setSmartBanners", [value]);

  if (!callback) return promise;
};

Appodeal.set728x90Banners = function (value, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "set728x90Banners", [value]);

  if (!callback) return promise;
};

Appodeal.setBannerOverLap = function (value, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "setBannerOverLap", [value]);

  if (!callback) return promise;
};

Appodeal.refreshWebViewForBanner = function (callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "refreshWebViewForBanner", []);

  if (!callback) return promise;
};

Appodeal.setTesting = function (testing, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "setTesting", [testing]);

  if (!callback) return promise;
};

Appodeal.setLogLevel = function (loglevel, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "setLogLevel", [loglevel]);

  if (!callback) return promise;
};

Appodeal.setChildDirectedTreatment = function (value, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "setChildDirectedTreatment", [value]);

  if (!callback) return promise;
};

Appodeal.setTriggerOnLoadedOnPrecache = function (set, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "setOnLoadedTriggerBoth", [set]);

  if (!callback) return promise;
};

Appodeal.disableNetwork = function (network, adType, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  adType = Number(adType) || 0;
  exec(resolve, reject, SERVICE, "disableNetwork", [network, adType]);

  if (!callback) return promise;
};

Appodeal.disableLocationPermissionCheck = function (callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "disableLocationPermissionCheck", []);

  if (!callback) return promise;
};

Appodeal.disableWriteExternalStoragePermissionCheck = function (callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "disableWriteExternalStoragePermissionCheck", []);

  if (!callback) return promise;
};

Appodeal.muteVideosIfCallsMuted = function (value, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "muteVideosIfCallsMuted", [value]);

  if (!callback) return promise;
};

Appodeal.showTestScreen = function (callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "showTestScreen", []);

  if (!callback) return promise;
};

Appodeal.getVersion = function (callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "getVersion", []);

  if (!callback) return promise;
};

Appodeal.getPluginVersion = function () {
  return Appodeal.pluginVersion;
};

Appodeal.isInitialized = function (adTypes, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "isInitialized", [adTypes]);

  if (!callback) return promise;
};

Appodeal.canShow = function (adType, placement, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  if (!placement) placement = null;
  exec(resolve, reject, SERVICE, "canShow", [adType, placement]);

  if (!callback) return promise;
};

Appodeal.getRewardParameters = function (placement, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  if (!placement) placement = null;
  exec(resolve, reject, SERVICE, "getRewardParameters", [placement]);

  if (!callback) return promise;
};

Appodeal.setExtraData = function (name, value, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  let type = typeof value;
  let method;
  if (type === "number")
    method = Number.isInteger(value) ? "setCustomIntegerRule" : "setCustomDoubleRule";
  else if (type === "boolean")
    method = "setCustomBooleanRule";
  else {
    method = "setCustomStringRule";
    value = `${value}`;
  }

  exec(resolve, reject, SERVICE, method, [name, value]);

  if (!callback) return promise;
}

Appodeal.getPredictedEcpm = function (adType, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "getPredictedEcpm", [adType]);

  if (!callback) return promise;
};

Appodeal.setUserId = function (userid, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "setUserId", [userid]);

  if (!callback) return promise;
};

Appodeal.trackInAppPurchase = function (amount, currency, callback) {
  let { promise, resolve, reject } = Promise.withResolvers();
  resolve = callback || resolve;

  exec(resolve, reject, SERVICE, "trackInAppPurchase", [amount, currency]);

  if (!callback) return promise;
};

Appodeal.setInterstitialCallbacks = function (callback) {
  exec(callback, null, SERVICE, "setInterstitialCallbacks", [])
};

Appodeal.setRewardedVideoCallbacks = function (callback) {
  exec(callback, null, SERVICE, "setRewardedVideoCallbacks", []);
};

Appodeal.setBannerCallbacks = function (callback) {
  exec(callback, null, SERVICE, "setBannerCallbacks", []);
};
