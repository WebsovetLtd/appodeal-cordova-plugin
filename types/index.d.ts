interface Window {
  plugins: Plugins
}

interface Plugins {
  Appodeal: Appodeal
}

type successFunction = { result: any }
type errorFunction = { result: any }

declare class Appodeal {
  initialize: (appKey: any, adTypes: any, callback: successFunction) => void
  manageConsent: (appKey: any, adTypes: any, hasConsent: any, callback: successFunction) => void
  show: (adType: any, placement: string, callback: successFunction) => void
  showBannerView: (xAxis: any, yAxis: any, placement: string) => void
  isLoaded: (adType: any, callback: successFunction) => void
  cache: (adType: any) => void
  hide: (adType: any) => void
  destroy: (adType: any) => void
  onResume: (adType: any) => void
  setAutoCache: (adTypes: any, autoCache: any) => void
  isPrecache: (adType: any, callback: successFunction) => void
  setBannerAnimation: (value: any) => void
  setSmartBanners: (value: any) => void
  set728x90Banners: (value: any) => void
  setBannerOverLap: (value: any) => void
  refreshWebViewForBanner: (value: any) => void
  setTesting: (testing: boolean) => void
  setLogLevel: (loglevel: any) => void
  setChildDirectedTreatment: (value: any) => void
  setTriggerOnLoadedOnPrecache: (set: any) => void
  disableNetwork: (network: any, adType: any) => void
  disableNetworkType: (network: any, adType: any) => void
  disableLocationPermissionCheck: () => void
  disableWriteExternalStoragePermissionCheck: () => void
  muteVideosIfCallsMuted: (value: any) => void
  showTestScreen: (value: any) => void
  getVersion: (callback: successFunction) => void
  getPluginVersion: () => string
  isInitialized: (adTypes: any, callback: successFunction) => void
  canShow: (adType: any, callback: successFunction) => void
  canShowWithPlacement: (adType: any, placement: string, callback: successFunction) => void
  getRewardParameters: (callback: successFunction) => void
  getRewardParametersForPlacement: (placement: string, callback: successFunction) => void
  setExtraData: (name: any, value: any) => void
  getPredictedEcpm: (adType: any, callback: successFunction) => void
  setAge: (age: number) => void
  setGender: (gender: number) => void
  setUserId: (userid: number) => void
  trackInAppPurchase: (amount: any, currency: any) => void
  hasStatusBarPlugin: (value: any) => void
  setInterstitialCallbacks: (callback: successFunction) => void
  setNonSkippableVideoCallbacks: (callbacks: any) => void
  setRewardedVideoCallbacks: (callbacks: any) => void
  setBannerCallbacks: (callbacks: any) => void
  setNativeCallbacks: (callback: successFunction) => void
  getNativeAds: (callback: successFunction) => void
  setNativeAdPosition: (x: any, y: any, w: any, h: any, tabH: any, callback: successFunction) => void
  hideNativeAd: (callback: successFunction) => void
  revealHiddenNativeAd: (callback: successFunction) => void
}