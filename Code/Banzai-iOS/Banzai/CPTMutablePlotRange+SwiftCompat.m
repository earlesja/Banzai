//
//  CPTMutablePlotRange+SwiftCompat.m
//  Banzai
//
//  Created by CSSE Department on 2/11/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

#import "CPTMutablePlotRange+SwiftCompat.h"

@implementation CPTMutablePlotRange (SwiftCompat)

- (void)setLengthFloat:(float)lengthFloat
{
    NSNumber *number = [NSNumber numberWithFloat:lengthFloat];
    [self setLength:[number decimalValue]];
}

@end
