#import "RewardedVideoDelegate.h"

@implementation RewardedVideoDelegate


/**
 Method called when rewarded video loads
 @param precache If precache is YES it means that precached ad loaded
 */
- (void)rewardedVideoDidLoadAdIsPrecache:(BOOL)precache {
    [self sendEventResult:CallbackHandlerEvent_onLoaded withValues:@{@"isPrecache": @(precache)}];
}

/**
 Method called if rewarded video mediation failed
 */
- (void)rewardedVideoDidFailToLoadAd {
    [self sendEventResult:CallbackHandlerEvent_onLoadFailed];
}

/**
 Method called if loaded ad creative expired by timeout
 */
- (void)rewardedVideoDidExpired {
    [self sendEventResult:CallbackHandlerEvent_onExpired];
}

/**
 Method called if interstitial mediation was successful, but ready ad network can't show ad or
 ad presentation was too frequent according to your placement settings
 @param error Error object that indicates error reason
 */
- (void)rewardedVideoDidFailToPresentWithError:(nonnull NSError *)error {
    [self sendEventResult:CallbackHandlerEvent_onShowFailed]; // todo: add error
}
/**
 Method called after rewarded video starts displaying
 */
- (void)rewardedVideoDidPresent {
    [self sendEventResult:CallbackHandlerEvent_onShown];
}

/**
 Method called before rewarded video leaves screen
 @param wasFullyWatched boolean flag indicated that user watch video fully
 */
- (void)rewardedVideoWillDismissAndWasFullyWatched:(BOOL)wasFullyWatched {
    [self sendEventResult:CallbackHandlerEvent_onClosed withValues:@{@"finished": @(wasFullyWatched)}];
}

/**
 Method called after completion of video
 @warning After calling this method, rewarded video can stay on screen and show postbanner
 *
 @param rewardAmount Amount of app currency turned on in Appodeal Dashboard
 @param rewardName Name of app currency set on in Appodeal Dashboard
 */
- (void)rewardedVideoDidFinish:(float)rewardAmount name:(nullable NSString *)rewardName {
    [self sendEventResult:CallbackHandlerEvent_onFinished withValues:@{
        @"currency": rewardName == nil ? @"" : rewardName,
        @"amount": @(rewardAmount)
    }];
}

/**
 Method called when user taps on rewarded video
 */
- (void)rewardedVideoDidClick {
    [self sendEventResult:CallbackHandlerEvent_onClick];
}


@end