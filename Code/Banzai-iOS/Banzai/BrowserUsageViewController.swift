//
//  BrowserUsageViewController.swift
//  Banzai
//
//  Created by CSSE Department on 1/7/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

import UIKit

class BrowserUsageViewController: UIViewController {
    
    @IBOutlet weak var pieGraphView: UIView!
    @IBOutlet weak var lineGraphView: UIView!
    let settings = NSUserDefaults.standardUserDefaults()
    let LEGEND_WIDTH : CGFloat = 100
    var browserNames = ["Firefox", "Chrome", "Safari", "IE 8", "IE 9", "IE 10", "IE 11"]
    var browserPercentages = ["Firefox":0.0, "Chrome":0.0, "Safari":0.0, "IE8":0.0, "IE9":0.0, "IE10":0.0, "IE11":0.0]
    var timeFrame = 604800 // 604800 = 7 days
    var lineGraphDates : [String] = []
    var pieChart : PNPieChart!
    var lineChart : PNLineChart!
    var ie8Data = PNLineChartData(), ie9Data = PNLineChartData(), ie10Data = PNLineChartData(), ie11Data = PNLineChartData()
    var firefoxData = PNLineChartData(), chromeData = PNLineChartData(), safariData = PNLineChartData()
    
    var ie8Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], ie9Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], ie10Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], ie11Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
    var firefoxVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], chromeVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], safariVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
    
    @IBOutlet weak var menuButton: UIBarButtonItem!
    @IBOutlet weak var refreshButton: UIBarButtonItem!
    
    // For server calls
    let ie8_1 = NSDictionary(objects: [Constants.BrowserIDs.IE8_1], forKeys: ["MetricId"])
    let ie8_2 = NSDictionary(objects: [Constants.BrowserIDs.IE8_2], forKeys: ["MetricId"])
    let ie8_3 = NSDictionary(objects: [Constants.BrowserIDs.IE8_3], forKeys: ["MetricId"])
    let ie9_1 = NSDictionary(objects: [Constants.BrowserIDs.IE9_1], forKeys: ["MetricId"])
    let ie9_2 = NSDictionary(objects: [Constants.BrowserIDs.IE9_2], forKeys: ["MetricId"])
    let ie9_3 = NSDictionary(objects: [Constants.BrowserIDs.IE9_3], forKeys: ["MetricId"])
    let ie10_1 = NSDictionary(objects: [Constants.BrowserIDs.IE10_1], forKeys: ["MetricId"])
    let ie10_2 = NSDictionary(objects: [Constants.BrowserIDs.IE10_2], forKeys: ["MetricId"])
    let ie10_3 = NSDictionary(objects: [Constants.BrowserIDs.IE10_3], forKeys: ["MetricId"])
    let ie11_1 = NSDictionary(objects: [Constants.BrowserIDs.IE11_1], forKeys: ["MetricId"])
    let ie11_2 = NSDictionary(objects: [Constants.BrowserIDs.IE11_2], forKeys: ["MetricId"])
    let ie11_3 = NSDictionary(objects: [Constants.BrowserIDs.IE11_3], forKeys: ["MetricId"])
    let firefox_1 = NSDictionary(objects: [Constants.BrowserIDs.Firefox_1], forKeys: ["MetricId"])
    let firefox_2 = NSDictionary(objects: [Constants.BrowserIDs.Firefox_2], forKeys: ["MetricId"])
    let firefox_3 = NSDictionary(objects: [Constants.BrowserIDs.Firefox_3], forKeys: ["MetricId"])
    let chrome_1 = NSDictionary(objects: [Constants.BrowserIDs.Chrome_1], forKeys: ["MetricId"])
    let chrome_2 = NSDictionary(objects: [Constants.BrowserIDs.Chrome_2], forKeys: ["MetricId"])
    let chrome_3 = NSDictionary(objects: [Constants.BrowserIDs.Chrome_3], forKeys: ["MetricId"])
    let safari_1 = NSDictionary(objects: [Constants.BrowserIDs.Safari_1], forKeys: ["MetricId"])
    let safari_2 = NSDictionary(objects: [Constants.BrowserIDs.Safari_2], forKeys: ["MetricId"])
    let safari_3 = NSDictionary(objects: [Constants.BrowserIDs.Safari_3], forKeys: ["MetricId"])
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // LINE GRAPH
        ie8Data.dataTitle = "IE 8"
        ie8Data.color = Constants.UIColors.purple
        ie8Data.inflexionPointStyle = PNLineChartPointStyle.Circle
        ie8Data.itemCount = 7
        
        ie9Data.dataTitle = "IE 9"
        ie9Data.color = Constants.UIColors.deepPurple
        ie9Data.inflexionPointStyle = PNLineChartPointStyle.Circle
        ie9Data.itemCount = 7
        
        ie10Data.dataTitle = "IE 10"
        ie10Data.color = Constants.UIColors.indigo
        ie10Data.inflexionPointStyle = PNLineChartPointStyle.Circle
        ie10Data.itemCount = 7
        
        ie11Data.dataTitle = "IE 11"
        ie11Data.color = Constants.UIColors.blue
        ie11Data.inflexionPointStyle = PNLineChartPointStyle.Circle
        ie11Data.itemCount = 7
        
        firefoxData.dataTitle = "Firefox"
        firefoxData.color = Constants.UIColors.teal
        firefoxData.inflexionPointStyle = PNLineChartPointStyle.Circle
        firefoxData.itemCount = 7
        
        chromeData.dataTitle = "Chrome"
        chromeData.color = Constants.UIColors.green
        chromeData.inflexionPointStyle = PNLineChartPointStyle.Circle
        chromeData.itemCount = 7
        
        safariData.dataTitle = "Safari"
        safariData.color = Constants.UIColors.gray
        safariData.inflexionPointStyle = PNLineChartPointStyle.Circle
        safariData.itemCount = 7
        
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        if settings.boolForKey("FetchedBrowserData") {
            getStoredData()
            updateGraphs()
        } else {
            JHProgressHUD.sharedHUD.showInView(self.view, withHeader: "Fetching Data", andFooter: "")
            disableButtons()
            getServerData()
        }

    }
    
    func getStoredData() {
        self.browserPercentages = settings.objectForKey("browserPercentages") as! [String : Double]
        self.lineGraphDates = settings.objectForKey("browserDates") as! [String]
        self.ie8Vals = settings.objectForKey("ie8Values") as! [Double]
        self.ie9Vals = settings.objectForKey("ie9Values") as! [Double]
        self.ie10Vals = settings.objectForKey("ie10Values") as! [Double]
        self.ie11Vals = settings.objectForKey("ie11Values") as! [Double]
        self.firefoxVals = settings.objectForKey("firefoxValues") as! [Double]
        self.chromeVals = settings.objectForKey("chromeValues") as! [Double]
        self.safariVals = settings.objectForKey("safariValues") as! [Double]
    }
    
    func updateGraphs() {
        var keys = ["IE8", "IE9", "IE10", "IE11", "Firefox", "Chrome", "Safari"]
        var percentages : [Double] = []
        for key in keys {
            percentages.append(browserPercentages[key]!)
        }
        
        var items : [PNPieChartDataItem] = []
        items.append(PNPieChartDataItem(value: CGFloat(percentages[0]), color: Constants.UIColors.purple, description: NSString(format: "IE 8 (%.0f%%)", percentages[0]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[1]), color: Constants.UIColors.deepPurple, description: NSString(format: "IE 9 (%.0f%%)", percentages[1]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[2]), color: Constants.UIColors.indigo, description: NSString(format: "IE 10 (%.0f%%)", percentages[2]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[3]), color: Constants.UIColors.blue, description: NSString(format: "IE 11 (%.0f%%)", percentages[3]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[4]), color: Constants.UIColors.teal, description: NSString(format: "Firefox (%.0f%%)", percentages[4]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[5]), color: Constants.UIColors.green, description: NSString(format: "Chrome (%.0f%%)", percentages[5]) as String))
        items.append(PNPieChartDataItem(value: CGFloat(percentages[6]), color: Constants.UIColors.gray, description: NSString(format: "Safari (%.0f%%)", percentages[6]) as String))
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
        
        var lineRect = CGRect(x: lineGraphView.bounds.minX, y: lineGraphView.bounds.minY, width: lineGraphView.bounds.width, height: lineGraphView.bounds.height)
        self.lineChart = PNLineChart(frame: lineRect) as PNLineChart
        lineChart.backgroundColor = UIColor.clearColor()
        lineChart.showCoordinateAxis = true
        //lineChart.yFixedValueMax = 100
        //lineChart.yFixedValueMin = 5
        lineChart.yValueMax = 100
        lineChart.yValueMin = 0
        
        // LINE GRAPH
        for (var i = 0; i < lineGraphDates.count; i++) {
            if i % 2 != 0 {
                lineGraphDates[i] = ""
            }
        }

        lineChart.setXLabels(lineGraphDates, withWidth: ((lineGraphView.bounds.width - 80) / CGFloat(lineGraphDates.count)))
        ie8Data.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.ie8Vals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        ie9Data.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.ie9Vals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        ie10Data.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.ie10Vals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        ie11Data.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.ie11Vals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        firefoxData.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.firefoxVals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        chromeData.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.chromeVals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        safariData.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.safariVals[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        
        lineChart.chartData = [ie8Data, ie9Data, ie10Data, ie11Data, firefoxData, chromeData, safariData]
        lineChart.strokeChart()
        
        pieGraphView.addSubview(pieChart)
        lineGraphView.addSubview(lineChart)
    }
    
    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    @IBAction func refreshData(sender: AnyObject) {
        println("Referesh the Browser Usage page")
        disableButtons()
        JHProgressHUD.sharedHUD.showInView(self.view, withHeader: "Fetching Data", andFooter: "")
        getServerData()
    }
    
    func getServerData() {
        self.browserPercentages = ["Firefox":0.0, "Chrome":0.0, "Safari":0.0, "IE8":0.0, "IE9":0.0, "IE10":0.0, "IE11":0.0]
        ie8Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        ie9Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        ie10Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        ie11Vals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        firefoxVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        chromeVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        safariVals = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        self.lineGraphDates = []

        var error : NSError? = nil
        let url = NSURL(string: "http://pan-banzai.cloudapp.net/banzai/api/data/historicaldata")
        var request = NSMutableURLRequest(URL: url!)
        request.HTTPMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        var credentialString = "Basic " + ((settings.valueForKey("credentials") as! NSString) as String)
        request.addValue(credentialString, forHTTPHeaderField: "Authorization")
        
        UIApplication.sharedApplication().networkActivityIndicatorVisible = true
        
        var outerDictionary = NSDictionary(objects: ["Day", timeFrame, [ie8_1, ie8_2, ie8_3, ie9_1, ie9_2, ie9_3, ie10_1, ie10_2, ie10_3, ie11_1, ie11_2, ie11_3, firefox_1, firefox_2, firefox_3, chrome_1, chrome_2, chrome_3, safari_1, safari_2, safari_3]], forKeys: ["GroupBy", "TimeFrame", "WidgetMetrics"])
        var jsonData = NSJSONSerialization.dataWithJSONObject(outerDictionary, options: NSJSONWritingOptions.PrettyPrinted, error: &error)
        request.HTTPBody = jsonData!

        NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue()) { (response, data, error) -> Void in
            var statusCode = (response as! NSHTTPURLResponse).statusCode
            println(statusCode)
            
            if error != nil {
                println("Uh oh... there was an error getting the browser data from the server.")
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
            self.enableBUttons()
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
        println(self.lineGraphDates)
        
        for json in data as! Array<AnyObject>{
            metricID = (json["MetricId"] as AnyObject? as? Int) ?? -1 // to get rid of null
            date = (json["DateCapturedUtc"]  as AnyObject? as? String) ?? ""
            value = (json["Value"] as AnyObject? as? Double) ?? -1.0
            array = date.componentsSeparatedByString("T")
            array = array[0].componentsSeparatedByString("-")
            date = "\(array[1])-\(array[2])"
            if date == self.lineGraphDates.last! {
                if metricID == Constants.BrowserIDs.IE8_1 || metricID == Constants.BrowserIDs.IE8_2 || metricID == Constants.BrowserIDs.IE8_3 {
                    self.browserPercentages.updateValue(self.browserPercentages["IE8"]! + value/3, forKey: "IE8")
                } else if metricID == Constants.BrowserIDs.IE9_1 || metricID == Constants.BrowserIDs.IE9_2 || metricID == Constants.BrowserIDs.IE9_3 {
                    self.browserPercentages.updateValue(self.browserPercentages["IE9"]! + value/3, forKey: "IE9")
                } else if metricID == Constants.BrowserIDs.IE10_1 || metricID == Constants.BrowserIDs.IE10_2 || metricID == Constants.BrowserIDs.IE10_3 {
                    self.browserPercentages.updateValue(self.browserPercentages["IE10"]! + value/3, forKey: "IE10")
                } else if metricID == Constants.BrowserIDs.IE11_1 || metricID == Constants.BrowserIDs.IE11_2 || metricID == Constants.BrowserIDs.IE11_3 {
                    self.browserPercentages.updateValue(self.browserPercentages["IE11"]! + value/3, forKey: "IE11")
                } else if metricID == Constants.BrowserIDs.Firefox_1 || metricID == Constants.BrowserIDs.Firefox_2 || metricID == Constants.BrowserIDs.Firefox_3 {
                    self.browserPercentages.updateValue(self.browserPercentages["Firefox"]! + value/3, forKey: "Firefox")
                } else if metricID == Constants.BrowserIDs.Chrome_1 || metricID == Constants.BrowserIDs.Chrome_2 || metricID == Constants.BrowserIDs.Chrome_3 {
                    self.browserPercentages.updateValue(self.browserPercentages["Chrome"]! + value/3, forKey: "Chrome")
                } else if metricID == Constants.BrowserIDs.Safari_1 || metricID == Constants.BrowserIDs.Safari_2 || metricID == Constants.BrowserIDs.Safari_3 {
                    self.browserPercentages.updateValue(self.browserPercentages["Safari"]! + value/3, forKey: "Safari")
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
                if metricID == Constants.BrowserIDs.IE8_1 || metricID == Constants.BrowserIDs.IE8_2 || metricID == Constants.BrowserIDs.IE8_3 {
                    ie8Vals[index] += value/3
                } else if metricID == Constants.BrowserIDs.IE9_1 || metricID == Constants.BrowserIDs.IE9_2 || metricID == Constants.BrowserIDs.IE9_3 {
                    ie9Vals[index] += value/3
                } else if metricID == Constants.BrowserIDs.IE10_1 || metricID == Constants.BrowserIDs.IE10_2 || metricID == Constants.BrowserIDs.IE10_3 {
                    ie10Vals[index] += value/3
                } else if metricID == Constants.BrowserIDs.IE11_1 || metricID == Constants.BrowserIDs.IE11_2 || metricID == Constants.BrowserIDs.IE11_3 {
                    ie11Vals[index] += value/3
                } else if metricID == Constants.BrowserIDs.Firefox_1 || metricID == Constants.BrowserIDs.Firefox_2 || metricID == Constants.BrowserIDs.Firefox_3 {
                    firefoxVals[index] += value/3
                } else if metricID == Constants.BrowserIDs.Chrome_1 || metricID == Constants.BrowserIDs.Chrome_2 || metricID == Constants.BrowserIDs.Chrome_3 {
                    chromeVals[index] += value/3
                } else if metricID == Constants.BrowserIDs.Safari_1 || metricID == Constants.BrowserIDs.Safari_2 || metricID == Constants.BrowserIDs.Safari_3 {
                    safariVals[index] += value/3
                } else {
                    println("Something went wrong in getting the line graph data.")
                }
            }
        }
        
        // Store the data
        settings.setBool(true, forKey: "FetchedBrowserData")
        settings.setObject(self.browserPercentages, forKey: "browserPercentages")
        settings.setObject(self.lineGraphDates, forKey: "browserDates")
        settings.setObject(self.ie8Vals, forKey: "ie8Values")
        settings.setObject(self.ie9Vals, forKey: "ie9Values")
        settings.setObject(self.ie10Vals, forKey: "ie10Values")
        settings.setObject(self.ie11Vals, forKey: "ie11Values")
        settings.setObject(self.firefoxVals, forKey: "firefoxValues")
        settings.setObject(self.chromeVals, forKey: "chromeValues")
        settings.setObject(self.safariVals, forKey: "safariValues")
        
    }
    
    func getIndexOfDate(date : String) -> Int {
        var counter = 0
        for d in lineGraphDates {
            if d == date { return counter }
            counter++
        }
        
        return -1
    }
    
    func disableButtons() {
        menuButton.enabled = false
        refreshButton.enabled = false
    }
    
    func enableBUttons() {
        menuButton.enabled = true
        refreshButton.enabled = true
    }
}