//
//  OSUsageViewController.swift
//  Banzai
//
//  Created by CSSE Department on 1/7/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

import UIKit

class OSUsageViewController: UIViewController, CPTPlotDataSource, CPTPieChartDataSource {
    
    @IBOutlet weak var lineGraphView: UIView!
//    @IBOutlet weak var lineGraphView: CPTGraphHostingView!
    @IBOutlet weak var graphView: CPTGraphHostingView!
    var osPercentages = [NSInteger]()
    var osNames = ["W XP", "W Vista", "W 7", "W 8", "W Mobile", "Mac OS", "iOS", "Linux", "Android"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.osPercentages = [2, 4, 6, 8, 9, 12, 15, 18, 26]
        
        addPieChart()
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
        
        //addLineGraph()
    }
    
    func addPieChart() {
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
    
    func addLineGraph() {
        var graph = CPTXYGraph(frame: CGRectZero)
        
        // CONFIGURE GRAPH
        var title = "OS Breakdown"
        graph.title = title
        var titleStyle = CPTMutableTextStyle()
        titleStyle.color = CPTColor.blackColor()
        titleStyle.fontName = "Helvetica-Bold"
        titleStyle.fontSize = 16.0
        graph.borderWidth = 2.0
        graph.titleTextStyle = titleStyle
        graph.titlePlotAreaFrameAnchor = CPTRectAnchorTop
        graph.titleDisplacement = CGPointMake(0.0, 10.0)
        graph.plotAreaFrame.paddingLeft = 30.0
        graph.plotAreaFrame.paddingBottom = 30.0
        var plotSpace = graph.defaultPlotSpace as CPTXYPlotSpace

        
//        xRange.setLengthFloat(10)
//        yRange.setLengthFloat(10)
        
        // CONFIGURE PLOTS
        var win7Plot = CPTScatterPlot()
        win7Plot.dataSource = self
        var win7Color = CPTColor.redColor()
        graph.addPlot(win7Plot, toPlotSpace: plotSpace)
        plotSpace.scaleToFitPlots([win7Plot])
        var xRange = plotSpace.xRange.mutableCopy() as CPTMutablePlotRange
        var yRange = plotSpace.yRange.mutableCopy() as CPTMutablePlotRange
        plotSpace.xRange = xRange
        plotSpace.yRange = yRange
        var win7LineStyle = win7Plot.dataLineStyle.mutableCopy() as CPTMutableLineStyle
        win7LineStyle.lineWidth = 2.5
        win7LineStyle.lineColor = win7Color
        win7Plot.dataLineStyle = win7LineStyle
        var win7SymbolLineStyle = CPTMutableLineStyle.lineStyle() as CPTMutableLineStyle
        win7SymbolLineStyle.lineColor = win7Color
        var win7Symbol = CPTPlotSymbol.ellipsePlotSymbol() as CPTPlotSymbol
        win7Symbol.fill = CPTFill(color: CPTColor.redColor())
        win7Symbol.lineStyle = win7SymbolLineStyle
        win7Symbol.size = CGSizeMake(6.0, 6.0)
        win7Plot.plotSymbol = win7Symbol
        
        
        // Styles
        var axisTitleStyle = CPTMutableTextStyle.textStyle() as CPTMutableTextStyle
        axisTitleStyle.color = CPTColor.blackColor()
        axisTitleStyle.fontName = "Helvetica-Bold"
        axisTitleStyle.fontSize = 10.0
        var axisLineStyle = CPTMutableLineStyle.lineStyle() as CPTMutableLineStyle
        axisLineStyle.lineWidth = 2.0
        axisLineStyle.lineColor = CPTColor.blackColor()
        var axisTextStyle = CPTMutableTextStyle()
        axisTextStyle.color = CPTColor.blackColor()
        axisTextStyle.fontName = "Helvetica-Bold"
        axisTextStyle.fontSize = 8.0
//        var tickLineStyle = CPTMutableLineStyle()
//        tickLineStyle.lineColor = CPTColor.blackColor()
//        tickLineStyle.lineWidth = 2.0
//        var gridLineStyle = CPTMutableLineStyle()
//        gridLineStyle.lineColor = CPTColor.blackColor()
//        gridLineStyle.lineWidth = 1.0

        // X AXIS
        var axisSet = graph.axisSet as CPTXYAxisSet
        var x = axisSet.xAxis
        x.title = "Day of Month"
        x.titleTextStyle = axisTitleStyle
        x.titleOffset = 15.0
        x.axisLineStyle = axisLineStyle
        x.labelingPolicy = CPTAxisLabelingPolicyNone
        x.labelTextStyle = axisTextStyle
        x.majorTickLineStyle = axisLineStyle
        x.majorTickLength = 4.0
        x.tickDirection = CPTSignNegative
        var dateCount = 9
        var xLabels = NSMutableSet(capacity: dateCount)
        var xLocations = NSMutableSet(capacity: dateCount)
        for(var i = 1; i < 10; i++) {
            var label = CPTAxisLabel(text: "\(i)", textStyle: x.labelTextStyle)
            var location = i
            label.offset = x.majorTickLength
            xLabels.addObject(label)
            xLocations.addObject(location)
        }
        x.axisLabels = xLabels
        x.majorTickLocations = xLocations
        println(x.axisLabels.count)
        // Y AXIS
        var y = axisSet.yAxis
        y.title = "Usage"
        y.titleTextStyle = axisTitleStyle
        y.titleOffset = -30.0
        y.axisLineStyle = axisLineStyle
        //y.majorGridLineStyle = gridLineStyle
        y.labelingPolicy = CPTAxisLabelingPolicyNone
        y.labelTextStyle = axisTextStyle
        y.labelOffset = 16.0
        y.majorTickLineStyle = axisLineStyle
        y.majorTickLength = 4.0
        y.minorTickLength = 2.0
        y.tickDirection = CPTSignPositive
        var majorIncrement = 20
        var minorIncrement = 10
        var yMax = 100
        var yLabels = NSMutableSet()
        var yMajorLocations = NSMutableSet()
        var yMinorLocations = NSMutableSet()
        for (var i = minorIncrement; i <= yMax; i += minorIncrement) {
            var mod = i % majorIncrement
            if mod == 0 {
                var label = CPTAxisLabel(text: String(i), textStyle: y.labelTextStyle)
                label.offset = -y.majorTickLength - y.labelOffset
                yLabels.addObject(label)
                yMajorLocations.addObject(i)
            } else {
                yMinorLocations.addObject(i)
            }
        }
        y.axisLabels = yLabels
        y.majorTickLocations = yMajorLocations
        y.minorTickLocations = yMinorLocations
        //self.lineGraphView.backgroundColor = UIColor.greenColor()
        //self.lineGraphView.hostedGraph = graph
    }
    
    func legendTitleForPieChart(pieChart: CPTPieChart!, recordIndex idx: UInt) -> String! {
        return osNames[Int(idx)]
    }
    
    func numberOfRecordsForPlot(plot: CPTPlot!) -> UInt {
        return UInt(osPercentages.count)
    }

    func numberForPlot(plot: CPTPlot!, field fieldEnum: UInt, recordIndex idx: UInt) -> NSNumber! {
        return osPercentages[Int(idx)]
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    @IBAction func refreshData(sender: AnyObject) {
        println("Referesh the OS Usage page")
    }
}