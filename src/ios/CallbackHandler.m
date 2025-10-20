#import "CallbackHandler.h"

@implementation CallbackHandler

- (instancetype)initWithPlugin:(CDVPlugin *)plugin andCallbackId:(NSString *)callbackId {
    self = [super init]; // вызов "super" конструктора (init)
    if (self) {
        _plugin = plugin;
        _callbackId = callbackId;
    }

    [self sendEventResult:CallbackHandlerEvent_onInit];

    return self;
}

- (void) sendEventResult:(CallbackHandlerEventName)event {
    [self sendEventResult:event withValues:@{}];
}

- (void) sendEventResult:(CallbackHandlerEventName)event withValues:(NSDictionary*)vals {

    NSMutableDictionary *values = [vals mutableCopy];
    values[@"event"] = [self eventName:event];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:values];
    [pluginResult setKeepCallbackAsBool:YES];

    [self.plugin.commandDelegate sendPluginResult:pluginResult callbackId:self.callbackId];
}

- (NSString*) eventName:(CallbackHandlerEventName)event {
    switch (event) {
        default: 
            return @"";
            break;

        case CallbackHandlerEvent_onInit:
            return @"onInit";
            break;

        case CallbackHandlerEvent_onLoaded:
            return @"onLoaded";
            break;

        case CallbackHandlerEvent_onLoadFailed:
            return @"onLoadFailed";
            break;

        case CallbackHandlerEvent_onClick:
            return @"onClick";
            break;

        case CallbackHandlerEvent_onShown:
            return @"onShown";
            break;

        case CallbackHandlerEvent_onClosed:
            return @"onClosed";
            break;

        case CallbackHandlerEvent_onFinished:
            return @"onFinished";
            break;

        case CallbackHandlerEvent_onExpired:
            return @"onExpired";
            break;

        case CallbackHandlerEvent_onShowFailed:
            return @"onShowFailed";
            break;
    }
}

@end