package com.appodeal.plugin;

public enum Action {

    initialize,
    isInitialized,

    show,
    isLoaded,
    cache,
    hide,
    destroy,
    setAutoCache,
    isPrecache,

    setBannerAnimation,
    setSmartBanners,
    set728x90Banners,

    setTesting,
    setLogLevel,
    setChildDirectedTreatment,
    disableNetwork,
    setTriggerOnLoadedOnPrecache,
    muteVideosIfCallsMuted,
    showTestScreen,
    getVersion,

    canShow,
    canShowWithPlacement,
    setCustomIntegerRule,
    setCustomBooleanRule,
    setCustomDoubleRule,
    setCustomStringRule,
    getRewardParameters,
    getPredictedEcpm,

    setUserId,
    setInterstitialCallbacks,
    setRewardedVideoCallbacks,
    setBannerCallbacks;

    public static Action getAction(String actionName) {
        try {
            return Action.valueOf(actionName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
