interface Window {
  plugins: Plugins
}

interface Plugins {
  Appodeal: Appodeal
}

type successFunction = { result: any }
type errorFunction = { result: any }
type eventCallback = { event: string }

declare class Appodeal {
  initialize: (appKey: any, adTypes: any, callback: successFunction) => Promise<successFunction> | void
  manageConsent: (appKey: any, adTypes: any, hasConsent: any, callback: successFunction) => Promise<successFunction> | void
  show: (adType: any, placement: string, callback: successFunction) => Promise<successFunction> | void
  isLoaded: (adType: any, callback: successFunction) => Promise<successFunction> | void
  cache: (adType: any, callback: successFunction) => Promise<successFunction> | void
  hide: (adType: any, callback: successFunction) => Promise<successFunction> | void
  destroy: (adType: any, callback: successFunction) => Promise<successFunction> | void
  onResume: (adType: any, callback: successFunction) => Promise<successFunction> | void
  setAutoCache: (adTypes: any, autoCache: any, callback: successFunction) => Promise<successFunction> | void
  isPrecache: (adType: any, callback: successFunction) => Promise<successFunction> | void
  setBannerAnimation: (value: any, callback: successFunction) => Promise<successFunction> | void
  setSmartBanners: (value: any, callback: successFunction) => Promise<successFunction> | void
  set728x90Banners: (value: any, callback: successFunction) => Promise<successFunction> | void
  setBannerOverLap: (value: any, callback: successFunction) => Promise<successFunction> | void
  refreshWebViewForBanner: (value: any, callback: successFunction) => Promise<successFunction> | void
  setTesting: (testing: boolean, callback: successFunction) => Promise<successFunction> | void
  setLogLevel: (loglevel: any, callback: successFunction) => Promise<successFunction> | void
  setChildDirectedTreatment: (value: any, callback: successFunction) => Promise<successFunction> | void
  disableNetwork: (network: any, adType: any, callback: successFunction) => Promise<successFunction> | void
  disableLocationPermissionCheck: (callback: successFunction) => Promise<successFunction> | void
  disableWriteExternalStoragePermissionCheck: (callback: successFunction) => Promise<successFunction> | void
  muteVideosIfCallsMuted: (value: any, callback: successFunction) => Promise<successFunction> | void
  showTestScreen: (value: any, callback: successFunction) => Promise<successFunction> | void
  getVersion: (callback: successFunction) => Promise<successFunction> | void
  isInitialized: (adTypes: any, callback: successFunction) => Promise<successFunction> | void
  canShow: (adType: any, callback: successFunction) => Promise<successFunction> | void
  getRewardParameters: (callback: successFunction) => Promise<successFunction> | void
  setExtraData: (name: any, value: any, callback: successFunction) => Promise<successFunction> | void
  getPredictedEcpm: (adType: any, callback: successFunction) => Promise<successFunction> | void
  setUserId: (userid: number, callback: successFunction) => Promise<successFunction> | void
  trackInAppPurchase: (amount: any, currency: any, callback: successFunction) => Promise<successFunction> | void
  hasStatusBarPlugin: (value: any, callback: successFunction) => Promise<successFunction> | void
  setInterstitialCallbacks: (callbacks: any, callback: eventCallback) => void
  setRewardedVideoCallbacks: (callbacks: any, callback: eventCallback) => void
  setBannerCallbacks: (callbacks: any, callback: eventCallback) => void
}