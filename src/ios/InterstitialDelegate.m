#import "InterstitialDelegate.h"

@implementation InterstitialDelegate

/**
 Method called when precached or usual interstitial view loads
 @warning If you want show only expensive ads, ignore this callback call with precache equal to YES
 *
 @param precache If precache is YES it means that precached ad loaded
 */
- (void)interstitialDidLoadAdIsPrecache:(BOOL)precache {
    [self sendEventResult:CallbackHandlerEvent_onLoaded withValues:@{@"isPrecache": @(precache)}];
}

/**
 Method called if interstitial mediation failed
 */
- (void)interstitialDidFailToLoadAd {
    [self sendEventResult:CallbackHandlerEvent_onLoadFailed];
}

/**
 Method called if loaded interstital ad expired by timeout
 */
- (void)interstitialDidExpired {
    [self sendEventResult:CallbackHandlerEvent_onExpired];
}

/**
 Method called if interstitial mediation was successful, but ready ad network can't show ad or
 ad presentation was too frequent according to your placement settings
 */
- (void)interstitialDidFailToPresent {
    [self sendEventResult:CallbackHandlerEvent_onShowFailed];
}

/**
 Method called when interstitial displays on screen
 */
- (void)interstitialWillPresent {
    [self sendEventResult:CallbackHandlerEvent_onShown];
}

/**
 Method called after interstitial leaves the screen
 */
- (void)interstitialDidDismiss {
    [self sendEventResult:CallbackHandlerEvent_onClosed];
}

/**
 Method called when user taps on interstitial
 */
- (void)interstitialDidClick {
    [self sendEventResult:CallbackHandlerEvent_onClick];
}


@end