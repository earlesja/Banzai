//
//  BrowserUsageViewController.swift
//  Banzai
//
//  Created by CSSE Department on 1/7/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

import UIKit

class BrowserUsageViewController: UIViewController, CPTPlotDataSource, CPTPieChartDataSource {
    
    @IBOutlet weak var graphView: CPTGraphHostingView!
    var browserPercentages = [NSInteger]()
    var browserNames = ["Opera", "Firefox", "Chrome", "Safari", "IE 8", "IE 9", "IE 10", "IE 11"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.browserPercentages = [2, 6, 8, 9, 12, 15, 18, 30]
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
        pie.pieRadius = (self.view.frame.width - 130) / 2
        pie.centerAnchor = CGPointMake(0.35, 0.5)
        graph.addPlot(pie)
        
        var legend = CPTLegend.legendWithGraph(graph) as CPTLegend
        legend.numberOfColumns = 1
        graph.legend = legend
        var legendPadding = -(self.view.bounds.size.width / 8) + pie.pieRadius / 4
        graph.legendAnchor = CPTRectAnchorRight
        graph.legendDisplacement = CGPointMake(legendPadding, 0.0)
        
        self.graphView.hostedGraph = graph
    }
    
    func legendTitleForPieChart(pieChart: CPTPieChart!, recordIndex idx: UInt) -> String! {
        return browserNames[Int(idx)]
    }
    
    func numberOfRecordsForPlot(plot: CPTPlot!) -> UInt {
        return UInt(browserPercentages.count)
    }
    
    func numberForPlot(plot: CPTPlot!, field fieldEnum: UInt, recordIndex idx: UInt) -> NSNumber! {
        return browserPercentages[Int(idx)]
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