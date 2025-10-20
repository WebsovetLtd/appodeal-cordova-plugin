#import <Cordova/CDV.h>

@interface CDVInvokedUrlCommand (Helpers)
- (NSInteger)intAtIndex:(NSUInteger)index;
- (NSInteger)intAtIndex:(NSUInteger)index withDefault:(NSInteger)defaultVal;

- (BOOL)boolAtIndex:(NSUInteger)index;
- (BOOL)boolAtIndex:(NSUInteger)index withDefault:(BOOL)defaultVal;

- (NSString*)stringAtIndex:(NSUInteger)index;
- (NSString*)stringAtIndex:(NSUInteger)index withDefault:(NSString*)defaultVal;
@end