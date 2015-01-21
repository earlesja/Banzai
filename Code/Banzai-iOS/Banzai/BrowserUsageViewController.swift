//
//  BrowserUsageViewController.swift
//  Banzai
//
//  Created by CSSE Department on 1/7/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

import UIKit

class BrowserUsageViewController: UIViewController, CPTPlotDataSource {
    
    @IBOutlet weak var graphView: CPTGraphHostingView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        // Create sample graph
        var graph = CPTXYGraph(frame: CGRectZero)
        
        graph.paddingLeft = 0
        graph.paddingTop = 0
        graph.paddingRight = 0
        graph.paddingBottom = 0
        var axes = graph.axisSet as CPTXYAxisSet
        var lineStyle = CPTMutableLineStyle()
        lineStyle.lineWidth = 0
        axes.xAxis.axisLineStyle = lineStyle
        axes.yAxis.axisLineStyle = lineStyle
        var pie = CPTPieChart()
        pie.dataSource = self
        pie.pieRadius = 80
        graph.addPlot(pie)
        
        self.graphView.hostedGraph = graph
    }
    
    func numberOfRecordsForPlot(plot: CPTPlot!) -> UInt {
        return 4
    }
    
    func numberForPlot(plot: CPTPlot!, field fieldEnum: UInt, recordIndex idx: UInt) -> NSNumber! {
        return idx+1
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    @IBAction func refreshData(sender: AnyObject) {
        println("Referesh the Browser Usage page")
    }
}