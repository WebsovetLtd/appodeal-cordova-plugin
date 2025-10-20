#import "BannerDelegate.h"

@implementation BannerDelegate

/**
 Method called when precached or usual banner view loads
 *
 @param precache If precache is YES it means that precached ad loaded
 */
- (void)bannerDidLoadAdIsPrecache:(BOOL)precache {
    [self sendEventResult:CallbackHandlerEvent_onLoaded withValues:@{@"isPrecache": @(precache)}];
}

/**
 Method called if banner mediation failed
 */
- (void)bannerDidFailToLoadAd {
    [self sendEventResult:CallbackHandlerEvent_onLoadFailed];
}

/**
 Method called when banner view did fail to load
 */
- (void)bannerDidExpired {
    [self sendEventResult:CallbackHandlerEvent_onExpired];
}

/**
 Method called when user taps on banner
 */
- (void)bannerDidClick {
    [self sendEventResult:CallbackHandlerEvent_onClick];
}

/**
 Method called when banner shows or refreshes
 */
- (void)bannerDidShow {
    [self sendEventResult:CallbackHandlerEvent_onShown];
}

/**
 Method called when banner did fail to present
 *
 @param error Error occured while presenting
 */
- (void)bannerDidFailToPresentWithError:(nonnull NSError *)error {
    [self sendEventResult:CallbackHandlerEvent_onShowFailed]; // todo: add error
}


@end