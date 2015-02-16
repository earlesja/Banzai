//
//  DashboardTableCell.swift
//  Banzai
//
//  Created by Jonathan Earles on 1/21/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

import UIKit

class DashboardTableCell: UITableViewCell {

    @IBOutlet weak var serverName: UILabel!
    @IBOutlet weak var circleArea: UIView!
    @IBOutlet weak var cpuArea: UIView!
    @IBOutlet weak var memArea: UIView!
    @IBOutlet weak var diskArea: UIView!
    @IBOutlet weak var cpuLabel: UILabel!
    @IBOutlet weak var memLabel: UILabel!
    @IBOutlet weak var diskLabel: UILabel!
    
    var cpuDoughnut = PNCircleChart()
    var memDoughnut = PNCircleChart()
    var diskDoughnut = PNCircleChart()
    
    var cpuRect = CGRectZero
    var memRect = CGRectZero
    var diskRect = CGRectZero
    
    var cpuLower = 0.0
    var cpuUpper = 0.0
    var memLower = 0.0
    var memUpper = 0.0
    var diskLower = 0.0
    var diskUpper = 0.0
    
    let settings = NSUserDefaults.standardUserDefaults()
    
    override func awakeFromNib() {
        super.awakeFromNib()
        initValues()
        initDoughnuts()
        circleArea.hidden = true;
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func initValues(){
        cpuLower = settings.doubleForKey("cpuLower") * 100
        cpuUpper = settings.doubleForKey("cpuUpper") * 100
        memLower = settings.doubleForKey("memLower") * 100
        memUpper = settings.doubleForKey("memUpper") * 100
        diskLower = settings.doubleForKey("diskLower") * 100
        diskUpper = settings.doubleForKey("diskUpper") * 100
        
        self.bringSubviewToFront(cpuLabel)
        self.bringSubviewToFront(memLabel)
        self.bringSubviewToFront(diskLabel)
    }
    
    func initDoughnuts(){
        var spacing : CGFloat = 20.0
        var posAdjust : CGFloat = 10.0
        var widthh = (cpuArea.bounds.width-spacing)
        var height = (cpuArea.bounds.height-spacing)
        
        cpuRect = CGRect(x: cpuArea.bounds.minX + posAdjust, y: cpuArea.bounds.minY + posAdjust, width: widthh, height: height)
        memRect = CGRect(x: memArea.bounds.minX + posAdjust, y: memArea.bounds.minY + posAdjust, width: widthh, height: height)
        diskRect = CGRect(x: diskArea.bounds.minX + posAdjust, y: diskArea.bounds.minY + posAdjust, width:widthh, height: height)
        
        cpuDoughnut = PNCircleChart(frame: cpuRect, total: 100, current: 40, clockwise: false)
        memDoughnut = PNCircleChart(frame: memRect, total: 100, current: 60, clockwise: false)
        diskDoughnut = PNCircleChart(frame: diskRect, total: 100, current: 96, clockwise: false)
        
        cpuDoughnut.lineWidth = 15
        memDoughnut.lineWidth = 15
        diskDoughnut.lineWidth = 15
        
        updateColors()
    }
    
    func clearDoughnuts(){
        cpuDoughnut.removeFromSuperview()
        memDoughnut.removeFromSuperview()
        diskDoughnut.removeFromSuperview()
    }
    
    func updateDoughnuts() {
        clearDoughnuts()
        updateColors()
        
        cpuArea.addSubview(cpuDoughnut)
        memArea.addSubview(memDoughnut)
        diskArea.addSubview(diskDoughnut)
        cpuDoughnut.strokeChart()
        memDoughnut.strokeChart()
        diskDoughnut.strokeChart()
    }
    
    func updateColors() {
        if (cpuDoughnut.current as Double) > cpuUpper {
            cpuDoughnut.strokeColor = UIColor.redColor()
        }
        else if (cpuDoughnut.current as Double) < cpuLower {
            cpuDoughnut.strokeColor = UIColor.greenColor()
        }
        else {
            cpuDoughnut.strokeColor = UIColor.orangeColor()
        }
        
        if (memDoughnut.current as Double) > memUpper {
            memDoughnut.strokeColor = UIColor.redColor()
        }
        else if (memDoughnut.current as Double) < memLower {
            memDoughnut.strokeColor = UIColor.greenColor()
        }
        else {
            memDoughnut.strokeColor = UIColor.orangeColor()
        }
        
        if (diskDoughnut.current as Double) > diskUpper {
            diskDoughnut.strokeColor = UIColor.redColor()
        }
        else if (diskDoughnut.current as Double) < diskLower {
            diskDoughnut.strokeColor = UIColor.greenColor()
        }
        else {
            diskDoughnut.strokeColor = UIColor.orangeColor()
        }
    }

}
