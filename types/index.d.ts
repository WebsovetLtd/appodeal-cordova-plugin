interface Window {
	plugins: Plugins
}

interface Plugins {
	Appodeal: Appodeal
}

type Func<Result> = (result: Result) => void

/* see src/android/CallbackHandler.java and www/index.js. */
type BaseEventName = (
	| "onInit" | "onLoaded" | "onLoadFailed" | "onShown"
	| "onShowFailed" | "onClick" | "onExpired"
)
type RewardedEventName = BaseEventName | "onFinished" | "onClosed"
type InterstitialEventName = BaseEventName | "onClosed"
type BannerEventName = BaseEventName
type EventName = RewardedEventName | InterstitialEventName | BannerEventName
type EventContainer<Name extends EventName> = { event: Name }
type EventFunc<Name extends EventName> = (
	(container: EventContainer<Name>) => void
)

/* see www/index.js. */
type AdType = 3 | 4 | 8 | 16 | 128 | 256 | 1024 | 2048
type LogLevel = "none" | "debug" | "verbose"

type RewardParams = {
	amount: number
	currency: string
}

declare class Appodeal {
	pluginVersion: string

	INTERSTITIAL: 3
	BANNER: 4
	BANNER_BOTTOM: 8
	BANNER_TOP: 16
	BANNER_LEFT: 1024
	BANNER_RIGHT: 2048
	REWARDED_VIDEO: 128
	MREC: 256

	BANNER_X_SMART: 0
	BANNER_X_CENTER: 1
	BANNER_X_LEFT: 2
	BANNER_X_RIGHT: 3

	LogLevel: { NONE: 0; DEBUG: 1; VERBOSE: 2 }

	Gender: { OTHER: 0; MALE: 1; FEMALE: 2 }

	EVENT_INIT: "onInit"
	EVENT_LOADED: "onLoaded"
	EVENT_LOAD_FAILED: "onLoadFailed"
	EVENT_CLICKED: "onClick"
	EVENT_SHOWN: "onShown"
	EVENT_CLOSED: "onClosed"
	EVENT_FINISHED: "onFinished"
	EVENT_EXPIRED: "onExpired"
	EVENT_SHOW_FAILED: "onShowFailed"

	initialize: (appKey: string, adTypes: number, callback?: Func<void>) => Promise<void>
	show: (adType: AdType, placement?: string, callback?: Func<void>) => Promise<void>
	isLoaded: (adType: AdType, callback?: Func<boolean>) => Promise<boolean>
	cache: (adType: AdType, callback?: Func<void>) => Promise<void>
	hide: (adType: AdType, callback?: Func<void>) => Promise<void>
	destroy: (adType: AdType, callback?: Func<void>) => Promise<void>
	setAutoCache: (adTypes: number, autoCache: boolean, callback?: Func<void>) => Promise<void>
	isPrecache: (adType: AdType, callback?: Func<boolean>) => Promise<boolean>
	setBannerAnimation: (value: boolean, callback?: Func<void>) => Promise<void>
	setSmartBanners: (value: boolean, callback?: Func<void>) => Promise<void>
	set728x90Banners: (value: boolean, callback?: Func<void>) => Promise<void>
	setTesting: (testing: boolean, callback?: Func<void>) => Promise<void>
	setLogLevel: (loglevel: LogLevel, callback?: Func<void>) => Promise<void>
	setChildDirectedTreatment: (value: boolean, callback?: Func<void>) => Promise<void>
	disableNetwork: (network: string, adType: AdType, callback?: Func<void>) => Promise<void>
	muteVideosIfCallsMuted: (value: boolean, callback?: Func<void>) => Promise<void>
	showTestScreen: (callback?: Func<void>) => Promise<void>
	getVersion: (callback?: Func<string>) => Promise<string>
	isInitialized: (adTypes: number, callback?: Func<boolean>) => Promise<boolean>
	canShow: (adType: AdType, callback?: Func<boolean>) => Promise<boolean>
	getRewardParameters: (placement: string, callback?: Func<RewardParams>) => Promise<RewardParams>
	setExtraData: (name: string, value: number, callback?: Func<void>) => Promise<void>
	getPredictedEcpm: (adType: AdType, callback?: Func<number>) => Promise<number>
	setUserId: (userId: string, callback?: Func<void>) => Promise<void>
	setRewardedVideoCallbacks: (eventCallback: EventFunc<RewardedEventName>, callback?: Func<void>) => Promise<void>
	setInterstitialCallbacks: (eventCallback: EventFunc<InterstitialEventName>, callback?: Func<void>) => Promise<void>
	setBannerCallbacks: (eventCallback: EventFunc<BannerEventName>, callback?: Func<void>) => Promise<void>
}
