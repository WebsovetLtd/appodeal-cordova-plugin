#import <Cordova/CDV.h>
#import <Appodeal/Appodeal.h>

typedef NS_ENUM(NSInteger, CallbackHandlerEventName) {
    CallbackHandlerEvent_onInit,
    CallbackHandlerEvent_onLoaded,
    CallbackHandlerEvent_onLoadFailed,
    CallbackHandlerEvent_onClick,
    CallbackHandlerEvent_onShown,
    CallbackHandlerEvent_onClosed,
    CallbackHandlerEvent_onFinished,
    CallbackHandlerEvent_onExpired,
    CallbackHandlerEvent_onShowFailed
};

@interface CallbackHandler : NSObject

@property (nonatomic, strong, readonly) CDVPlugin* plugin;
@property (nonatomic, strong, readonly) NSString* callbackId;

- (instancetype)initWithPlugin:(CDVPlugin *)plugin andCallbackId:(NSString *)callbackId;

- (void) sendEventResult:(CallbackHandlerEventName)event;
- (void) sendEventResult:(CallbackHandlerEventName)event withValues:(NSDictionary*)vals;

@end

