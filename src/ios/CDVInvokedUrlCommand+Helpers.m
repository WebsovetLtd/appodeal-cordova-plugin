#import "CDVInvokedUrlCommand+Helpers.h"

@implementation CDVInvokedUrlCommand (Helpers)

- (NSInteger)intAtIndex:(NSUInteger)index {
    return [[self argumentAtIndex:index] integerValue];
}

- (NSInteger)intAtIndex:(NSUInteger)index withDefault:(NSInteger)defaultVal {
    return [[self argumentAtIndex:index withDefault:@(defaultVal)] integerValue];
}

- (BOOL)boolAtIndex:(NSUInteger)index {
    return [[self argumentAtIndex:index] boolValue];
}

- (BOOL)boolAtIndex:(NSUInteger)index withDefault:(BOOL)defaultVal {
    return [[self argumentAtIndex:index withDefault:@(defaultVal)] boolValue];
}

- (NSString*)stringAtIndex:(NSUInteger)index withDefault:(NSString*)defaultVal {
    id val = [self argumentAtIndex:index withDefault:defaultVal];
    return [val isKindOfClass:[NSString class]] ? val : [val description];
}

- (NSString*)stringAtIndex:(NSUInteger)index {
    id val = [self argumentAtIndex:index];
    return [val isKindOfClass:[NSString class]] ? val : [val description];
}

@end

