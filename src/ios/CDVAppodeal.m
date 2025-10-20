#import "CDVAppodeal.h"
#import <UIKit/UIKit.h>
#import <objc/runtime.h>
#import <AppTrackingTransparency/AppTrackingTransparency.h>
#import "InterstitialDelegate.h"
#import "RewardedVideoDelegate.h"
#import "BannerDelegate.h"
#import "StackConsentManager/StackConsentManager-Swift.h"
#import "CDVInvokedUrlCommand+Helpers.h"
#import <Appodeal/APDRewardProtocol.h>


static NSString *AppodealApiKey;
static InterstitialDelegate *interstitialDelegate;
static RewardedVideoDelegate *rewardedVideoDelegate;
static BannerDelegate *bannerDelegate;

static NSString* initCallbackId;

@implementation CDVAppodeal

// interface implementation
- (void) initialize:(CDVInvokedUrlCommand*)command
{

    AppodealApiKey = [command stringAtIndex: 0];
    AppodealAdType types = [self getAdTypeFromInt:[command intAtIndex:1 withDefault:0]];

    initCallbackId = command.callbackId;
    [Appodeal setInitializationDelegate:self];
    [Appodeal initializeWithApiKey:AppodealApiKey types:types];

}

- (void)appodealSDKDidInitialize {
    APDConsentStatus status = APDConsentManager.shared.status;

    [self sendPluginResOk:initCallbackId];
}

- (void)show:(CDVInvokedUrlCommand*)command {
    AppodealShowStyle style = [self getShowStyleFromInt:[command intAtIndex:0 withDefault:0]];
    NSString* placement = [command stringAtIndex:1 withDefault:@""];

    UIViewController* rootViewController = [UIApplication sharedApplication].keyWindow.rootViewController;

    BOOL result = YES;
    if (placement.length == 0) {
        result = [Appodeal showAd:style rootViewController:rootViewController];
    } else {
        result = [Appodeal showAd:style forPlacement:placement rootViewController:rootViewController];
    }

    [self sendPluginResOk:command.callbackId resultAsBool:result];
}

- (void)hide:(CDVInvokedUrlCommand*)command {
    [Appodeal hideBanner];
    [self sendPluginResOk:command.callbackId];
}

- (void) cache:(CDVInvokedUrlCommand*)command {
    AppodealAdType types = [self getAdTypeFromInt:[command intAtIndex:0 withDefault:0]];
    [Appodeal cacheAd:types];
    
    [self sendPluginResOk:command.callbackId resultAsBool:YES];
}

- (void)getRewardParameters:(CDVInvokedUrlCommand*)command {
    NSString* placement = [command stringAtIndex:1 withDefault:@""];
    id<APDReward> reward = [Appodeal rewardForPlacement:placement];

    NSString* currency = [reward currencyName];

    [self sendPluginResOk:command.callbackId resultAsDict:@{
        @"currency": currency == nil ? @"" : currency,
        @"amount": @([reward amount])
    }];
}

-(void) getPredictedEcpm:(CDVInvokedUrlCommand*)command {
    AppodealAdType types = [self getAdTypeFromInt:[command intAtIndex:0 withDefault:0]];
    NSString* placement = [command stringAtIndex:1 withDefault:@""];

    double result = [Appodeal predictedEcpmForAdType:types placement:placement]; 
    [self sendPluginResOk:command.callbackId resultAsDouble:result];
}

- (void) trackInAppPurchase:(CDVInvokedUrlCommand*)command {
    NSNumber * amount = [command argumentAtIndex:0 withDefault:@0 andClass:[NSNumber class]];;
    NSNumber * currency = [command stringAtIndex:1 withDefault:@""];
    [Appodeal trackInAppPurchase:amount currency:currency];

    [self sendPluginResOk:command.callbackId];
}

- (void) isLoaded:(CDVInvokedUrlCommand*)command {
    AppodealShowStyle style = [self getShowStyleFromInt:[command intAtIndex:0 withDefault:0]];

    [self sendPluginResOk:command.callbackId resultAsBool:[Appodeal isReadyForShowWithStyle:style]];
}

- (void) setInterstitialCallbacks:(CDVInvokedUrlCommand*)command {
    interstitialDelegate = [[InterstitialDelegate alloc] initWithPlugin:self andCallbackId:command.callbackId];
    [Appodeal setInterstitialDelegate:interstitialDelegate];
}

- (void) setBannerCallbacks:(CDVInvokedUrlCommand*)command {
    bannerDelegate = [[BannerDelegate alloc] initWithPlugin:self andCallbackId:command.callbackId];
    [Appodeal setBannerDelegate:bannerDelegate];
}

- (void) setRewardedVideoCallbacks:(CDVInvokedUrlCommand*)command {
    rewardedVideoDelegate = [[RewardedVideoDelegate alloc] initWithPlugin:self andCallbackId:command.callbackId];
    [Appodeal setRewardedVideoDelegate:rewardedVideoDelegate];
}

- (void) setTesting:(CDVInvokedUrlCommand*)command {
    [Appodeal setTestingEnabled:[command boolAtIndex:0 withDefault:NO]];
    [self sendPluginResOk:command.callbackId];
}

- (void) setLogLevel:(CDVInvokedUrlCommand*)command {

    NSString* level = [command stringAtIndex:0 withDefault:@""];

    if ([level isEqualToString:@"debug"]) {
        [Appodeal setLogLevel:APDLogLevelDebug];
    } else if([level isEqualToString:@"verbose"]) {
        [Appodeal setLogLevel:APDLogLevelVerbose];
    } else {
        [Appodeal setLogLevel:APDLogLevelOff];
    }

    [self sendPluginResOk:command.callbackId];
}

- (void) setUserId:(CDVInvokedUrlCommand*)command {
    [Appodeal setUserId:[command stringAtIndex:0 withDefault:@""]];

    [self sendPluginResOk:command.callbackId];
}

- (void) setCustomIntegerRule:(CDVInvokedUrlCommand*)command {
    [self setCustomStringRule:command];
}
- (void) setCustomDoubleRule:(CDVInvokedUrlCommand*)command {
    [self setCustomStringRule:command];
}
- (void) setCustomBooleanRule:(CDVInvokedUrlCommand*)command {
    [self setCustomStringRule:command];
}
- (void) setCustomStringRule:(CDVInvokedUrlCommand*)command {
    NSString* key = [command stringAtIndex:0 withDefault:@""];
    NSString* value = [command stringAtIndex:1 withDefault:@""];
    [Appodeal setExtrasValue:value forKey:key];

    [self sendPluginResOk:command.callbackId];
}

- (void) setBannerAnimation:(CDVInvokedUrlCommand*)command {
    [Appodeal setBannerAnimationEnabled:[command boolAtIndex:0 withDefault:NO]];

    [self sendPluginResOk:command.callbackId];
}

- (void) setSmartBanners:(CDVInvokedUrlCommand*)command {
    [Appodeal setSmartBannersEnabled:[command boolAtIndex:0 withDefault:NO]];

    [self sendPluginResOk:command.callbackId];
}

- (void) set728x90Banners:(CDVInvokedUrlCommand*)command {
    // no such method
    [self sendPluginResOk:command.callbackId];
}

- (void) setChildDirectedTreatment:(CDVInvokedUrlCommand*)command {
    [Appodeal setChildDirectedTreatment:[command boolAtIndex:0 withDefault:NO]];

    [self sendPluginResOk:command.callbackId];
}

- (void) muteVideosIfCallsMuted:(CDVInvokedUrlCommand*)command {
    // no such method
    [self sendPluginResOk:command.callbackId];
}

- (void) setAutoCache:(CDVInvokedUrlCommand*)command {
    AppodealAdType types = [self getAdTypeFromInt:[command intAtIndex:0 withDefault:0]];
    BOOL enabled = [command boolAtIndex:1 withDefault:NO];

    [Appodeal setAutocache:enabled types:types];
    [self sendPluginResOk:command.callbackId];
}

- (void) isPrecache:(CDVInvokedUrlCommand*)command {
    AppodealAdType types = [self getAdTypeFromInt:[command intAtIndex:0 withDefault:0]];

    [self sendPluginResOk:command.callbackId resultAsBool:[Appodeal isPrecacheAd:types]];
}

- (AppodealShowStyle)getShowStyleFromInt:(NSUInteger)rawTypes {

    NSUInteger types = 0;

    if (rawTypes & 3)    // 000000000011
        types = types | AppodealShowStyleInterstitial;

    if (rawTypes & 8)    // 000000001000
            types = types | AppodealShowStyleBannerBottom;

    if (rawTypes & 16)   // 000000010000
            types = types | AppodealShowStyleBannerTop;

    if (rawTypes & 1024) // 010000000000
            types = types | AppodealShowStyleBannerLeft;

    if (rawTypes & 2048) // 100000000000
            types = types | AppodealShowStyleBannerRight;

    if (rawTypes & 128)  // 000010000000
            types = types | AppodealShowStyleRewardedVideo;

    return types;
}

- (AppodealAdType)getAdTypeFromInt:(NSUInteger)rawTypes {

    NSUInteger types = 0;

    if (rawTypes & 3)   // 000000000011
        types = types | AppodealAdTypeInterstitial;

    if (rawTypes & 4)   // 000000000100
        types = types | AppodealAdTypeBanner;

    if (rawTypes & 128) // 000010000000
        types = types | AppodealAdTypeRewardedVideo;

    return types;
}

- (void)sendPluginResOk:(NSString*)callbackId
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void)sendPluginResOk:(NSString*)callbackId resultAsString:(NSString*)result
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void)sendPluginResOk:(NSString*)callbackId resultAsBool:(bool)result
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void)sendPluginResOk:(NSString*)callbackId resultAsInt:(int)result
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void)sendPluginResOk:(NSString*)callbackId resultAsDouble:(double)result
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDouble:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void)sendPluginResOk:(NSString*)callbackId resultAsDict:(NSDictionary*)result
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

@end

