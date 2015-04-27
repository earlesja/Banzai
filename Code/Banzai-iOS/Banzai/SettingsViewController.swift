//
//  SettingsViewController.swift
//  Banzai
//
//  Created by Chris O'Hara on 12/17/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

extension Double {
    func format(f: String) -> String {
        return NSString(format: "%\(f)f", self) as String
    }
}

class SettingsViewController: UIViewController {
    let cpuSlider = RangeSlider(frame: CGRectZero)
    let memSlider = RangeSlider(frame: CGRectZero)
    let diskSlider = RangeSlider(frame: CGRectZero)
    
    let settings = NSUserDefaults.standardUserDefaults()
    
    var valueFormat = "01."
    
    @IBOutlet weak var sliderArea: UIView!
    
    var cpuLow = UILabel(frame: CGRectMake(0, 0, 200, 21))
    var memLow = UILabel(frame: CGRectMake(0, 0, 200, 21))
    var diskLow = UILabel(frame: CGRectMake(0, 0, 200, 21))
    var cpuHigh = UILabel(frame: CGRectMake(0, 0, 200, 21))
    var memHigh = UILabel(frame: CGRectMake(0, 0, 200, 21))
    var diskHigh = UILabel(frame: CGRectMake(0, 0, 200, 21))
    
    var cpuLabel = UILabel(frame: CGRectMake(0, 0, 200, 21))
    var memLabel = UILabel(frame: CGRectMake(0, 0, 200, 21))
    var diskLabel = UILabel(frame: CGRectMake(0, 0, 200, 21))
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        sliderArea.addSubview(cpuSlider)
        sliderArea.addSubview(memSlider)
        sliderArea.addSubview(diskSlider)
        
        
        cpuSlider.addTarget(self, action: "rangeSliderValueChanged:", forControlEvents: .ValueChanged)
        memSlider.addTarget(self, action: "rangeSliderValueChanged:", forControlEvents: .ValueChanged)
        diskSlider.addTarget(self, action: "rangeSliderValueChanged:", forControlEvents: .ValueChanged)
        
        cpuSlider.addSubview(cpuLow)
        cpuSlider.addSubview(cpuHigh)
        cpuSlider.addSubview(cpuLabel)
        memSlider.addSubview(memLow)
        memSlider.addSubview(memHigh)
        memSlider.addSubview(memLabel)
        diskSlider.addSubview(diskLow)
        diskSlider.addSubview(diskHigh)
        diskSlider.addSubview(diskLabel)
        
        loadValues()
    }
    
    func loadValues() {
        cpuSlider.lowerValue = settings.doubleForKey("cpuLower")
        cpuSlider.upperValue = settings.doubleForKey("cpuUpper")
        memSlider.lowerValue = settings.doubleForKey("memLower")
        memSlider.upperValue = settings.doubleForKey("memUpper")
        diskSlider.lowerValue = settings.doubleForKey("diskLower")
        diskSlider.upperValue = settings.doubleForKey("diskUpper")
    }
    
    func defaultSettings() {
        settings.setDouble(20, forKey: "cpuLower")
        settings.setDouble(80, forKey: "cpuUpper")
        settings.setDouble(20, forKey: "memLower")
        settings.setDouble(80, forKey: "memUpper")
        settings.setDouble(20, forKey: "diskLower")
        settings.setDouble(80, forKey: "diskUpper")
    }
    
    override func viewDidLayoutSubviews() {
        let margin: CGFloat = 12.0
        let sliderWidth = view.bounds.width - 2.0 * margin
        let labelYPos: CGFloat = -30.0
        let labelFontSize: CGFloat = 18.0
        
        
        cpuSlider.frame = CGRect(x: margin, y: margin + topLayoutGuide.length + 40,
            width: sliderWidth, height: 20.0)
        memSlider.frame = CGRect(x: margin, y: margin + topLayoutGuide.length + 120,
            width: sliderWidth, height: 20.0)
        diskSlider.frame = CGRect(x: margin, y: margin + topLayoutGuide.length + 200,
            width: sliderWidth, height: 20.0)
        
        cpuLabel.sizeToFit()
        memLabel.sizeToFit()
        diskLabel.sizeToFit()
        
        cpuLabel.font = UIFont.boldSystemFontOfSize(labelFontSize);
        cpuLabel.center.y = labelYPos
        cpuLabel.text = "CPU Utilization Thresholds"
        memLabel.font = UIFont.boldSystemFontOfSize(labelFontSize);
        memLabel.center.y = labelYPos
        memLabel.text = "Memory Utilization Thresholds"
        diskLabel.font = UIFont.boldSystemFontOfSize(labelFontSize);
        diskLabel.center.y = labelYPos
        diskLabel.text = "Disk Utilization Thresholds"
        
        self.updateLabels()
    }
    
    func updateLabels(){
        let margin = -10.0
        
        cpuLow.text = "\((cpuSlider.lowerValue * 100.0).format(valueFormat))%"
        cpuLow.center = CGPoint(x: cpuSlider.positionForValue(cpuSlider.lowerValue) + 90, y: margin)
        cpuHigh.text = "\((cpuSlider.upperValue * 100.0).format(valueFormat))%"
        cpuHigh.center = CGPoint(x: cpuSlider.positionForValue(cpuSlider.upperValue) + 90, y: margin)
        
        memLow.text = "\((memSlider.lowerValue * 100.0).format(valueFormat))%"
        memLow.center = CGPoint(x: memSlider.positionForValue(memSlider.lowerValue) + 90, y: margin)
        memHigh.text = "\((memSlider.upperValue * 100.0).format(valueFormat))%"
        memHigh.center = CGPoint(x: memSlider.positionForValue(memSlider.upperValue) + 90, y: margin)
        
        diskLow.text = "\((diskSlider.lowerValue * 100.0).format(valueFormat))%"
        diskLow.center = CGPoint(x: diskSlider.positionForValue(diskSlider.lowerValue) + 90, y: margin)
        diskHigh.text = "\((diskSlider.upperValue * 100.0).format(valueFormat))%"
        diskHigh.center = CGPoint(x: diskSlider.positionForValue(diskSlider.upperValue) + 90, y: margin)
    }
    
    func rangeSliderValueChanged(cpuSlider: RangeSlider) {
        updateLabels()
    }
    
    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    @IBAction func saveSettings(sender: AnyObject) {
        settings.setBool(true, forKey: "changed")
        settings.setDouble(cpuSlider.lowerValue, forKey: "cpuLower")
        settings.setDouble(cpuSlider.upperValue, forKey: "cpuUpper")
        settings.setDouble(memSlider.lowerValue, forKey: "memLower")
        settings.setDouble(memSlider.upperValue, forKey: "memUpper")
        settings.setDouble(diskSlider.lowerValue, forKey: "diskLower")
        settings.setDouble(diskSlider.upperValue, forKey: "diskUpper")
    }
}
