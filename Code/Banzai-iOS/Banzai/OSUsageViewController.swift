//
//  OSUsageViewController.swift
//  Banzai
//
//  Created by CSSE Department on 1/7/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

import UIKit

class OSUsageViewController: UIViewController {
    
    @IBOutlet weak var lineGraphView: UIView!
    @IBOutlet weak var pieGraphView: UIView!
    @IBOutlet weak var menuButton: UIBarButtonItem!
    @IBOutlet weak var refreshButton: UIBarButtonItem!
    
    // Constants
    let settings = NSUserDefaults.standardUserDefaults()
    let LEGEND_WIDTH : CGFloat = 100
    // For server calls
    let wVista_1 = NSDictionary(objects: [Constants.OSIDs.WindowsVista_1], forKeys: ["MetricId"])
    let wVista_2 = NSDictionary(objects: [Constants.OSIDs.WindowsVista_2], forKeys: ["MetricId"])
    let wVista_3 = NSDictionary(objects: [Constants.OSIDs.WindowsVista_3], forKeys: ["MetricId"])
    let w7_1 = NSDictionary(objects: [Constants.OSIDs.Windows7_1], forKeys: ["MetricId"])
    let w7_2 = NSDictionary(objects: [Constants.OSIDs.Windows7_2], forKeys: ["MetricId"])
    let w7_3 = NSDictionary(objects: [Constants.OSIDs.Windows7_3], forKeys: ["MetricId"])
    let w8_1 = NSDictionary(objects: [Constants.OSIDs.Windows8_1], forKeys: ["MetricId"])
    let w8_2 = NSDictionary(objects: [Constants.OSIDs.Windows8_2], forKeys: ["MetricId"])
    let w8_3 = NSDictionary(objects: [Constants.OSIDs.Windows8_3], forKeys: ["MetricId"])
    let w8One_1 = NSDictionary(objects: [Constants.OSIDs.Windows8One_1], forKeys: ["MetricId"])
    let w8One_2 = NSDictionary(objects: [Constants.OSIDs.Windows8One_2], forKeys: ["MetricId"])
    let w8One_3 = NSDictionary(objects: [Constants.OSIDs.Windows8One_3], forKeys: ["MetricId"])
    let mac_1 = NSDictionary(objects: [Constants.OSIDs.Mac_1], forKeys: ["MetricId"])
    let mac_2 = NSDictionary(objects: [Constants.OSIDs.Mac_2], forKeys: ["MetricId"])
    let mac_3 = NSDictionary(objects: [Constants.OSIDs.Mac_3], forKeys: ["MetricId"])
    let iOS_1 = NSDictionary(objects: [Constants.OSIDs.iOS_1], forKeys: ["MetricId"])
    let iOS_2 = NSDictionary(objects: [Constants.OSIDs.iOS_2], forKeys: ["MetricId"])
    let iOS_3 = NSDictionary(objects: [Constants.OSIDs.iOS_3], forKeys: ["MetricId"])
    let android_1 = NSDictionary(objects: [Constants.OSIDs.Android_1], forKeys: ["MetricId"])
    let android_2 = NSDictionary(objects: [Constants.OSIDs.Android_2], forKeys: ["MetricId"])
    let android_3 = NSDictionary(objects: [Constants.OSIDs.Android_3], forKeys: ["MetricId"])
    let linux_1 = NSDictionary(objects: [Constants.OSIDs.Linux_1], forKeys: ["MetricId"])
    let linux_2 = NSDictionary(objects: [Constants.OSIDs.Linux_2], forKeys: ["MetricId"])
    let linux_3 = NSDictionary(objects: [Constants.OSIDs.Linux_3], forKeys: ["MetricId"])
    
    // Variables
    var lineGraphDates : [String] = []
    var macData = PNLineChartData(), iOSData = PNLineChartData(), androidData = PNLineChartData(), linuxData = PNLineChartData()
    var macVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], iOSVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], androidVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], linuxVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
    var osNames = ["W Vista", "W 7", "W 8", "W 8.1", "Mac", "iOS", "Linux", "Android"]
    var osPercentages = ["WVista":0.0, "W7":0.0, "W8":0.0, "W8One":0.0, "Mac":0.0, "iOS":0.0, "Android":0.0, "Linux":0.0]
    var pieChart : PNPieChart!, lineChart : PNLineChart!
    var timeFrame = 604800 // 604800 = 7 days
    var wVistaData = PNLineChartData(), w7Data = PNLineChartData(), w8Data = PNLineChartData(), w8OneData = PNLineChartData()
    var wVistaVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], w7Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], w8Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], w8OneVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.

        // LINE GRAPH
        
        wVistaData.dataTitle = "W Vista"
        wVistaData.color = Constants.UIColors.purple
        wVistaData.inflexionPointStyle = PNLineChartPointStyle.Circle
        wVistaData.itemCount = 7
        
        w7Data.dataTitle = "W 7"
        w7Data.color = Constants.UIColors.deepPurple
        w7Data.inflexionPointStyle = PNLineChartPointStyle.Circle
        w7Data.itemCount = 7
        
        w8Data.dataTitle = "W 8"
        w8Data.color = Constants.UIColors.indigo
        w8Data.inflexionPointStyle = PNLineChartPointStyle.Circle
        w8Data.itemCount = 7
        
        w8OneData.dataTitle = "W 8.1"
        w8OneData.color = Constants.UIColors.blue
        w8OneData.inflexionPointStyle = PNLineChartPointStyle.Circle
        w8OneData.itemCount = 7
        
        macData.dataTitle = "Mac"
        macData.color = Constants.UIColors.teal
        macData.inflexionPointStyle = PNLineChartPointStyle.Circle
        macData.itemCount = 7
        
        iOSData.dataTitle = "iOS"
        iOSData.color = Constants.UIColors.green
        iOSData.inflexionPointStyle = PNLineChartPointStyle.Circle
        iOSData.itemCount = 7
        
        androidData.dataTitle = "Android"
        androidData.color = Constants.UIColors.gray
        androidData.inflexionPointStyle = PNLineChartPointStyle.Circle
        androidData.itemCount = 7
        
        linuxData.dataTitle = "Linux"
        linuxData.color = Constants.UIColors.brown
        linuxData.inflexionPointStyle = PNLineChartPointStyle.Circle
        linuxData.itemCount = 7
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        if settings.boolForKey("FetchedOSData") {
            getStoredData()
            updateGraphs()
        } else {
            JHProgressHUD.sharedHUD.showInView(self.view, withHeader: "Fetching Data", andFooter: "")
            disableButtons()
            getServerData()
        }
    }
    
    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    @IBAction func refreshData(sender: AnyObject) {
        println("Referesh the OS Usage page")
        JHProgressHUD.sharedHUD.showInView(self.view, withHeader: "Fetching Data", andFooter: "")
        disableButtons()
        getServerData()
    }
    
    func disableButtons() {
        menuButton.enabled = false
        refreshButton.enabled = false
    }
    
    func enableButtons() {
        menuButton.enabled = true
        refreshButton.enabled = true
    }
    
    func getStoredData() {
        self.osPercentages = settings.objectForKey("osPercentages") as! [String : Double]
        self.lineGraphDates = settings.objectForKey("osDates") as! [String]
        self.wVistaVals = settings.objectForKey("wVistaValues") as! [Double]
        self.w7Vals = settings.objectForKey("w7Values") as! [Double]
        self.w8Vals = settings.objectForKey("w8Values") as! [Double]
        self.w8OneVals = settings.objectForKey("w8OneValues") as! [Double]
        self.macVals = settings.objectForKey("macValues") as! [Double]
        self.iOSVals = settings.objectForKey("iOSValues") as! [Double]
        self.androidVals = settings.objectForKey("androidValues") as! [Double]
        self.linuxVals = settings.objectForKey("linuxValues") as! [Double]
    }
    
    func updateGraphs() {
        var keys = ["WVista", "W7", "W8", "W8One", "Mac", "iOS", "Android", "Linux"]
        var percentages : [Double] = []
        for key in keys {
            percentages.append(osPercentages[key]!)
            println("\(key) %: \(percentages.last!)")
        }
        
        var items : [PNPieChartDataItem] = []
        items.append(PNPieChartDataItem(value: CGFloat(percentages[0]), color: Constants.UIColors.purple, description: NSString(format: "W Vista (%.0f%%)", percentages[0]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[1]), color: Constants.UIColors.deepPurple, description: NSString(format: "W 7 (%.0f%%)", percentages[1]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[2]), color: Constants.UIColors.indigo, description: NSString(format: "W 8 (%.0f%%)", percentages[2]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[3]), color: Constants.UIColors.blue, description: NSString(format: "W 8.1 (%.0f%%)", percentages[3]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[4]), color: Constants.UIColors.teal, description: NSString(format: "Mac (%.0f%%)", percentages[4]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[5]), color: Constants.UIColors.green, description: NSString(format: "iOS (%.0f%%)", percentages[5]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[6]), color: Constants.UIColors.gray, description: NSString(format: "Android (%.0f%%)", percentages[6]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[7]), color: Constants.UIColors.brown, description: NSString(format: "Linux (%.0f%%)", percentages[7]) as String))
        var graphSquareDimension : CGFloat
        var graphStartX : CGFloat
        var graphStartY : CGFloat
        if pieGraphView.bounds.height < pieGraphView.bounds.width - LEGEND_WIDTH {
            graphSquareDimension = pieGraphView.bounds.height
            graphStartX = pieGraphView.bounds.minX + ((pieGraphView.bounds.width - LEGEND_WIDTH - pieGraphView.bounds.height) / 2)
            graphStartY = pieGraphView.bounds.minY
        } else {
            graphSquareDimension = pieGraphView.bounds.width - LEGEND_WIDTH
            graphStartX = pieGraphView.bounds.minX
            graphStartY = pieGraphView.bounds.minY + ((pieGraphView.bounds.height - (pieGraphView.bounds.width - LEGEND_WIDTH)) / 2)
        }
        
        self.pieChart = PNPieChart(frame: CGRectMake(graphStartX, graphStartY, graphSquareDimension, graphSquareDimension), items: items)
        pieChart.descriptionTextFont = UIFont(name: "Avenir-Medium", size: 13.0)
        pieChart.showOnlyValues = true
        pieChart.strokeChart()
        
        var legend = pieChart.getLegendWithMaxWidth(LEGEND_WIDTH) as UIView
        legend.frame = CGRectMake(pieGraphView.bounds.width - LEGEND_WIDTH, pieGraphView.bounds.minY + pieGraphView.bounds.height / 4, LEGEND_WIDTH, pieGraphView.bounds.height/2)
        pieGraphView.addSubview(legend)
        
        
        // LINE GRAPH
        self.lineChart = PNLineChart(frame: CGRect(x: lineGraphView.bounds.minX, y: lineGraphView.bounds.minY, width: lineGraphView.bounds.width, height: lineGraphView.bounds.height))
        lineChart.backgroundColor = UIColor.clearColor()
        lineChart.showCoordinateAxis = true
        
        //lineChart.yFixedValueMax = 100
        //lineChart.yFixedValueMin = 5
        lineChart.yValueMax = 100
        lineChart.yValueMin = 0
        
        
        for (var i = 0; i < lineGraphDates.count; i++) {
            if i % 2 != 0 {
                lineGraphDates[i] = ""
            }
        }

        lineChart.setXLabels(lineGraphDates, withWidth: ((lineGraphView.bounds.width - 80) / CGFloat(lineGraphDates.count)))
        wVistaData.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.wVistaVals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        w7Data.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.w7Vals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        w8Data.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.w8Vals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        w8OneData.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.w8OneVals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        macData.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.macVals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        iOSData.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.iOSVals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        androidData.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.androidVals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        linuxData.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.linuxVals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        
        lineChart.chartData = [wVistaData, w7Data, w8Data, w8OneData, macData, iOSData, androidData, linuxData]
        lineChart.strokeChart()
        
        pieGraphView.addSubview(pieChart)
        lineGraphView.addSubview(lineChart)
    }
    
    func getServerData() {
        self.osPercentages = ["WVista":0.0, "W7":0.0, "W8":0.0, "W8One":0.0, "Mac":0.0, "iOS":0.0, "Android":0.0, "Linux":0.0]
        wVistaVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        w7Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        w8Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        w8OneVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        macVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        iOSVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        androidVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        linuxVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        self.lineGraphDates = []
        
        var error : NSError? = nil
        let url = NSURL(string: "http://pan-banzai.cloudapp.net/banzai/api/data/historicaldata")
        var request = NSMutableURLRequest(URL: url!)
        request.HTTPMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        var credentialString = "Basic " + ((settings.valueForKey("credentials") as! NSString) as String)
        request.addValue(credentialString, forHTTPHeaderField: "Authorization")
        
        UIApplication.sharedApplication().networkActivityIndicatorVisible = true
        
        var outerDictionary = NSDictionary(objects: ["Day", timeFrame, [wVista_1, wVista_2, wVista_3, w7_1, w7_2, w7_3, w8_1, w8_2, w8_3, w8One_1, w8One_2, w8One_3, mac_1, mac_2, mac_3, iOS_1, iOS_2, iOS_3, android_1, android_2, android_3, linux_1, linux_2, linux_3]], forKeys: ["GroupBy", "TimeFrame", "WidgetMetrics"])
        var jsonData = NSJSONSerialization.dataWithJSONObject(outerDictionary, options: NSJSONWritingOptions.PrettyPrinted, error: &error)
        request.HTTPBody = jsonData!
        
        NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue()) { (response, data, error) -> Void in
            if error != nil {
                println("Uh oh... there was an error getting the os data from the server.")
                var statusCode = (response as! NSHTTPURLResponse).statusCode
                let alertController = UIAlertController(title: "", message: "", preferredStyle: UIAlertControllerStyle.Alert)
                alertController.title = "Server error. Status code: \(statusCode)"
                let okAction = UIAlertAction(title: "OK", style: UIAlertActionStyle.Default) { (action) -> Void in
                }
                alertController.addAction(okAction)
                self.presentViewController(alertController, animated: true, completion: nil)
            } else {
                var responseText = NSString(data: data, encoding: NSASCIIStringEncoding)
                
                var data: NSData = responseText!.dataUsingEncoding(NSUTF8StringEncoding)!
                var error: NSError?
                
                // convert NSData to 'AnyObject'
                let anyObj: AnyObject? = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions(0), error: &error)
                
                if  anyObj is Array<AnyObject> {
                    self.parseData(anyObj!)
                }
                self.updateGraphs()
            }
            UIApplication.sharedApplication().networkActivityIndicatorVisible = false
            JHProgressHUD.sharedHUD.hide()
            self.enableButtons()
        }
    }
    
    func parseData(data : AnyObject) {
        var metricID = 0
        var date = ""
        var value = 0.0
        var array = []
        
        // First, get the dates
        for json in data as! Array<AnyObject> {
            date = (json["DateCapturedUtc"] as AnyObject? as? String) ?? ""
            array = date.componentsSeparatedByString("T")
            array = array[0].componentsSeparatedByString("-")
            date = "\(array[1])-\(array[2])"
            if !contains(self.lineGraphDates, date) {
                self.lineGraphDates.append(date)
            }
        }
        
        // Sort the dates
        self.lineGraphDates = sorted(self.lineGraphDates, {(d1: String, d2: String) -> Bool in
            if d1.componentsSeparatedByString("-")[0] == d2.componentsSeparatedByString("-")[0] {
                return d1.componentsSeparatedByString("-")[1].toInt() < d2.componentsSeparatedByString("-")[1].toInt()
            } else {
                return d1.componentsSeparatedByString("-")[0].toInt() < d2.componentsSeparatedByString("-")[0].toInt()
            }
        })
        
        var numDates = lineGraphDates.count
        while(numDates > 7) {
            lineGraphDates.removeAtIndex(0)
            numDates = lineGraphDates.count
        }
        
        for json in data as! Array<AnyObject>{
            metricID = (json["MetricId"] as AnyObject? as? Int) ?? -1 // to get rid of null
            date = (json["DateCapturedUtc"]  as AnyObject? as? String) ?? ""
            value = (json["Value"] as AnyObject? as? Double) ?? -1.0
            array = date.componentsSeparatedByString("T")
            array = array[0].componentsSeparatedByString("-")
            date = "\(array[1])-\(array[2])"
            if date == self.lineGraphDates.last! {
                if metricID == Constants.OSIDs.WindowsVista_1 || metricID == Constants.OSIDs.WindowsVista_2 || metricID == Constants.OSIDs.WindowsVista_3 {
                    self.osPercentages.updateValue(self.osPercentages["WVista"]! + value/3, forKey: "WVista")
                } else if metricID == Constants.OSIDs.Windows7_1 || metricID == Constants.OSIDs.Windows7_2 || metricID == Constants.OSIDs.Windows7_3 {
                    self.osPercentages.updateValue(self.osPercentages["W7"]! + value/3, forKey: "W7")
                } else if metricID == Constants.OSIDs.Windows8_1 || metricID == Constants.OSIDs.Windows8_2 || metricID == Constants.OSIDs.Windows8_3 {
                    self.osPercentages.updateValue(self.osPercentages["W8"]! + value/3, forKey: "W8")
                } else if metricID == Constants.OSIDs.Windows8One_1 || metricID == Constants.OSIDs.Windows8One_2 || metricID == Constants.OSIDs.Windows8One_3 {
                    self.osPercentages.updateValue(self.osPercentages["W8One"]! + value/3, forKey: "W8One")
                } else if metricID == Constants.OSIDs.Mac_1 || metricID == Constants.OSIDs.Mac_2 || metricID == Constants.OSIDs.Mac_3 {
                    self.osPercentages.updateValue(self.osPercentages["Mac"]! + value/3, forKey: "Mac")
                } else if metricID == Constants.OSIDs.iOS_1 || metricID == Constants.OSIDs.iOS_2 || metricID == Constants.OSIDs.iOS_3 {
                    self.osPercentages.updateValue(self.osPercentages["iOS"]! + value/3, forKey: "iOS")
                } else if metricID == Constants.OSIDs.Android_1 || metricID == Constants.OSIDs.Android_2 || metricID == Constants.OSIDs.Android_3 {
                    self.osPercentages.updateValue(self.osPercentages["Android"]! + value/3, forKey: "Android")
                } else if metricID == Constants.OSIDs.Linux_1 || metricID == Constants.OSIDs.Linux_2 || metricID == Constants.OSIDs.Linux_3 {
                    self.osPercentages.updateValue(self.osPercentages["Linux"]! + value/3, forKey: "Linux")
                }
            }
        }
        
        // Parse data for line graph
        var index = 0
        for json in data as! Array<AnyObject>{
            metricID = (json["MetricId"] as AnyObject? as? Int) ?? -1 // to get rid of null
            date = (json["DateCapturedUtc"]  as AnyObject? as? String) ?? ""
            value = (json["Value"] as AnyObject? as? Double) ?? -1.0
            array = date.componentsSeparatedByString("T")
            array = array[0].componentsSeparatedByString("-")
            date = "\(array[1])-\(array[2])"
            index = getIndexOfDate(date)
            if index != -1 {
                if metricID == Constants.OSIDs.WindowsVista_1 || metricID == Constants.OSIDs.WindowsVista_2 || metricID == Constants.OSIDs.WindowsVista_3 {
                    wVistaVals[index] += value/3
                } else if metricID == Constants.OSIDs.Windows7_1 || metricID == Constants.OSIDs.Windows7_2 || metricID == Constants.OSIDs.Windows7_3 {
                    w7Vals[index] += value/3
                } else if metricID == Constants.OSIDs.Windows8_1 || metricID == Constants.OSIDs.Windows8_2 || metricID == Constants.OSIDs.Windows8_3 {
                    w8Vals[index] += value/3
                } else if metricID == Constants.OSIDs.Windows8One_1 || metricID == Constants.OSIDs.Windows8One_2 || metricID == Constants.OSIDs.Windows8One_3 {
                    w8OneVals[index] += value/3
                } else if metricID == Constants.OSIDs.Mac_1 || metricID == Constants.OSIDs.Mac_2 || metricID == Constants.OSIDs.Mac_3 {
                    macVals[index] += value/3
                } else if metricID == Constants.OSIDs.iOS_1 || metricID == Constants.OSIDs.iOS_2 || metricID == Constants.OSIDs.iOS_3 {
                    iOSVals[index] += value/3
                } else if metricID == Constants.OSIDs.Android_1 || metricID == Constants.OSIDs.Android_2 || metricID == Constants.OSIDs.Android_3 {
                    androidVals[index] += value/3
                } else if metricID == Constants.OSIDs.Linux_1 || metricID == Constants.OSIDs.Linux_2 || metricID == Constants.OSIDs.Linux_3 {
                    linuxVals[index] += value/3
                } else {
                    println("Something went wrong in getting the line graph data.")
                }
            }
        }
        
        // Store the data
        settings.setBool(true, forKey: "FetchedOSData")
        settings.setObject(self.osPercentages, forKey: "osPercentages")
        settings.setObject(self.lineGraphDates, forKey: "osDates")
        settings.setObject(self.wVistaVals, forKey: "wVistaValues")
        settings.setObject(self.w7Vals, forKey: "w7Values")
        settings.setObject(self.w8Vals, forKey: "w8Values")
        settings.setObject(self.w8OneVals, forKey: "w8OneValues")
        settings.setObject(self.macVals, forKey: "macValues")
        settings.setObject(self.iOSVals, forKey: "iOSValues")
        settings.setObject(self.androidVals, forKey: "androidValues")
        settings.setObject(self.linuxVals, forKey: "linuxValues")
    }
    
    func getIndexOfDate(date : String) -> Int {
        var counter = 0
        for d in lineGraphDates {
            if d == date { return counter }
            counter++
        }
        
        return -1
    }


}