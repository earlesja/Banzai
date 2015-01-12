import UIKit
import QuartzCore

class RangeSliderTrackLayer: CALayer {
    weak var rangeSlider: RangeSlider?
    
    override func drawInContext(ctx: CGContext!) {
        if let slider = rangeSlider {

            let cornerRadius = bounds.height * slider.curvaceousness / 2.0
            let path = UIBezierPath(roundedRect: bounds, cornerRadius: cornerRadius)
            CGContextAddPath(ctx, path.CGPath)
            
            let redArea = CGColorCreate(CGColorSpaceCreateDeviceRGB(), [1.0, 0.0, 0.0, 1.0])
            let orangeArea = CGColorCreate(CGColorSpaceCreateDeviceRGB(), [1.0, 0.64, 0.0, 1.0])
            let greenArea = CGColorCreate(CGColorSpaceCreateDeviceRGB(), [0.0, 1.0, 0.0, 1.0])
            
            let minValuePosition = CGFloat(slider.positionForValue(0))
            let lowerValuePosition = CGFloat(slider.positionForValue(slider.lowerValue))
            let upperValuePosition = CGFloat(slider.positionForValue(slider.upperValue))
            let maxValuePosition = CGFloat(slider.positionForValue(1.0))
            
            CGContextSetFillColorWithColor(ctx, redArea)
            let rectRed = CGRect(x: minValuePosition, y: 0.0, width: lowerValuePosition - minValuePosition, height: bounds.height)
            CGContextFillRect(ctx, rectRed)

            CGContextSetFillColorWithColor(ctx, orangeArea)
            let rectOrange = CGRect(x: lowerValuePosition, y: 0.0, width: upperValuePosition - lowerValuePosition, height: bounds.height)
            CGContextFillRect(ctx, rectOrange)
            
            CGContextSetFillColorWithColor(ctx, greenArea)
            let rectGreen = CGRect(x: upperValuePosition, y: 0.0, width: maxValuePosition - upperValuePosition, height: bounds.height)
            CGContextFillRect(ctx, rectGreen)
            
        }
    }
}