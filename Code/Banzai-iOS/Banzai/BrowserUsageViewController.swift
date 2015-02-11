//
//  BrowserUsageViewController.swift
//  Banzai
//
//  Created by CSSE Department on 1/7/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

import UIKit

class BrowserUsageViewController: UIViewController, CPTPlotDataSource, CPTPieChartDataSource {
    
    @IBOutlet weak var lineGraphView: UIView!
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
        
        var lineRect = CGRect(x: lineGraphView.bounds.minX, y: lineGraphView.bounds.minY, width: lineGraphView.bounds.width, height: lineGraphView.bounds.height - 20)
        var lineChart = PNLineChart(frame: lineRect) as PNLineChart
        lineChart.showCoordinateAxis = true
        var space = lineChart.bounds.width / 7
        lineChart.setXLabels(["SEP 1", "SEP 2", "SEP 3", "SEP 4", "SEP 5"], withWidth: space)
        var dataArray = [60.1, 160.1, 126.4, 262.2, 186.2] as [CGFloat]
        var data = PNLineChartData()
        //data.color = PNFreshGreen
        data.itemCount = UInt(lineChart.xLabels.count)
        data.getData = { (index) -> PNLineChartDataItem in
            var yValue = dataArray[Int(index)]
            return PNLineChartDataItem(y: yValue)
        }
        lineChart.chartData = [data]
        lineChart.strokeChart()
        lineGraphView.addSubview(lineChart)
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