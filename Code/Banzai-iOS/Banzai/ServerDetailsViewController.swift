//
//  ServerDetailsViewController.swift
//  Banzai
//
//  Created by Chris O'Hara on 12/17/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

class ServerDetailsViewController: UIViewController {
    
    @IBOutlet weak var lineGraph1View: UIView!
    @IBOutlet weak var lineGraph2View: UIView!
    @IBOutlet weak var lineGraph3View: UIView!
    @IBOutlet weak var lineGraph1Label: UILabel!
    @IBOutlet weak var lineGraph2Label: UILabel!
    @IBOutlet weak var lineGraph3Label: UILabel!
    
    // Constants
    let settings = NSUserDefaults.standardUserDefaults()
    // App Tier
    let app_cpu_1 = NSDictionary(objects: [Constants.MetricIDs.AppTier.CPU1, Constants.ServerIDs.APP1], forKeys: ["MetricId", "ServerId"])
    let app_cpu_2 = NSDictionary(objects: [Constants.MetricIDs.AppTier.CPU2, Constants.ServerIDs.APP2], forKeys: ["MetricId", "ServerId"])
    let app_cpu_3 = NSDictionary(objects: [Constants.MetricIDs.AppTier.CPU3, Constants.ServerIDs.APP3], forKeys: ["MetricId", "ServerId"])
    let app_mem_1 = NSDictionary(objects: [Constants.MetricIDs.AppTier.MEM1, Constants.ServerIDs.APP1], forKeys: ["MetricId", "ServerId"])
    let app_mem_2 = NSDictionary(objects: [Constants.MetricIDs.AppTier.MEM2, Constants.ServerIDs.APP2], forKeys: ["MetricId", "ServerId"])
    let app_mem_3 = NSDictionary(objects: [Constants.MetricIDs.AppTier.MEM3, Constants.ServerIDs.APP3], forKeys: ["MetricId", "ServerId"])
    let app_disk_1 = NSDictionary(objects: [Constants.MetricIDs.AppTier.DISK1, Constants.ServerIDs.APP1], forKeys: ["MetricId", "ServerId"])
    let app_disk_2 = NSDictionary(objects: [Constants.MetricIDs.AppTier.DISK2, Constants.ServerIDs.APP2], forKeys: ["MetricId", "ServerId"])
    let app_disk_3 = NSDictionary(objects: [Constants.MetricIDs.AppTier.DISK3, Constants.ServerIDs.APP3], forKeys: ["MetricId", "ServerId"])
    
    // Web Tier
    let web_cpu_1 = NSDictionary(objects: [Constants.MetricIDs.WebTier.CPU1, Constants.ServerIDs.WEB1], forKeys: ["MetricId", "ServerId"])
    let web_cpu_2 = NSDictionary(objects: [Constants.MetricIDs.WebTier.CPU2, Constants.ServerIDs.WEB2], forKeys: ["MetricId", "ServerId"])
    let web_cpu_3 = NSDictionary(objects: [Constants.MetricIDs.WebTier.CPU3, Constants.ServerIDs.WEB3], forKeys: ["MetricId", "ServerId"])
    let web_mem_1 = NSDictionary(objects: [Constants.MetricIDs.WebTier.MEM1, Constants.ServerIDs.WEB1], forKeys: ["MetricId", "ServerId"])
    let web_mem_2 = NSDictionary(objects: [Constants.MetricIDs.WebTier.MEM2, Constants.ServerIDs.WEB2], forKeys: ["MetricId", "ServerId"])
    let web_mem_3 = NSDictionary(objects: [Constants.MetricIDs.WebTier.MEM3, Constants.ServerIDs.WEB3], forKeys: ["MetricId", "ServerId"])
    let web_disk_1 = NSDictionary(objects: [Constants.MetricIDs.WebTier.DISK1, Constants.ServerIDs.WEB1], forKeys: ["MetricId", "ServerId"])
    let web_disk_2 = NSDictionary(objects: [Constants.MetricIDs.WebTier.DISK2, Constants.ServerIDs.WEB2], forKeys: ["MetricId", "ServerId"])
    let web_disk_3 = NSDictionary(objects: [Constants.MetricIDs.WebTier.DISK3, Constants.ServerIDs.WEB3], forKeys: ["MetricId", "ServerId"])
    
    // Data Tier
    let data_cpu = NSDictionary(objects: [Constants.MetricIDs.DataTier.CPU, Constants.ServerIDs.DISK], forKeys: ["MetricId", "ServerId"])
    let data_mem = NSDictionary(objects: [Constants.MetricIDs.DataTier.MEM, Constants.ServerIDs.DISK], forKeys: ["MetricId", "ServerId"])
    let data_disk = NSDictionary(objects: [Constants.MetricIDs.DataTier.DISK, Constants.ServerIDs.DISK], forKeys: ["MetricId", "ServerId"])
    
    // Variables
    var lineChart1 : PNLineChart!
    var lineChart2 : PNLineChart!
    var lineChart3 : PNLineChart!
    var data1 = PNLineChartData(), data2 = PNLineChartData(), data3 = PNLineChartData()
    var vals1 = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], vals2 = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], vals3 = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
    var lineGraphDates : [String] = []
    var timeFrame = 604800 // 604800 = 7 days
    var dataToFetch : [NSDictionary] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let backItem = UIBarButtonItem(title: "Back", style: UIBarButtonItemStyle.Plain, target: self, action: "goBack")
        navigationItem.leftBarButtonItem = backItem
        
        // LINE GRAPH
        data1.dataTitle = "Server 1"
        data1.color = Constants.UIColors.purple
        data1.inflexionPointStyle = PNLineChartPointStyle.Circle
        data1.itemCount = 7
        
        data2.dataTitle = "Server 2"
        data2.color = Constants.UIColors.indigo
        data2.inflexionPointStyle = PNLineChartPointStyle.Circle
        data2.itemCount = 7
        
        data3.dataTitle = "Server 3"
        data3.color = Constants.UIColors.teal
        data3.inflexionPointStyle = PNLineChartPointStyle.Circle
        data3.itemCount = 7
        
        var server = settings.valueForKey("server") as! NSString
        if settings.integerForKey("RowSelected") == 0 {
            if server == "cpu" {
                dataToFetch = [app_cpu_1, app_cpu_2, app_cpu_3]
                self.title = "App CPU Servers"
            } else if server == "mem" {
                dataToFetch = [app_mem_1, app_mem_2, app_mem_3]
                self.title = "App MEM Servers"
            } else {
                dataToFetch = [app_disk_1, app_disk_2, app_disk_3]
                self.title = "App DISK Servers"
            }
            getServerData()
        } else if settings.integerForKey("RowSelected") == 1 {
            if server == "cpu" {
                dataToFetch = [web_cpu_1, web_cpu_2, web_cpu_3]
                self.title = "Web CPU Servers"
            } else if server == "mem" {
                dataToFetch = [web_mem_1, web_mem_2, web_mem_3]
                self.title = "Web MEM Servers"
            } else {
                dataToFetch = [web_disk_1, web_disk_2, web_disk_3]
                self.title = "Web DISK Servers"
            }
            getServerData()
        } else if settings.integerForKey("RowSelected") == 2 {
            lineGraph2Label.hidden = true
            lineGraph3Label.hidden = true
            if server == "cpu" {
                dataToFetch = [data_cpu]
                self.title = "Data CPU Server"
            } else if server == "mem" {
                dataToFetch = [data_mem]
                self.title = "Data MEM Server"
            } else {
                dataToFetch = [data_disk]
                self.title = "Data Disk Server"
            }
            getServerData()
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func refreshData(sender: AnyObject) {
        println("Refresh the Server Details page")
    }
    
    func goBack() {
        let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main",bundle: nil)
        let destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("DashboardViewController") as! UIViewController
        self.sideMenuController()?.setContentViewControllerWithoutMenu(destViewController)
    }
    
    func updateGraphs() {
        
        var lineRect1 = CGRect(x: lineGraph1View.bounds.minX, y: lineGraph1View.bounds.minY + 10, width: lineGraph1View.bounds.width, height: lineGraph1View.bounds.height - 10)
        self.lineChart1 = PNLineChart(frame: lineRect1) as PNLineChart
        lineChart1.backgroundColor = UIColor.clearColor()
        lineChart1.showCoordinateAxis = true
        lineChart1.yValueMax = getMaxValue(vals1)
        lineChart1.yValueMin = getMinValue(vals1)

        for (var i = 0; i < lineGraphDates.count; i++) {
            if i % 2 != 0 {
                lineGraphDates[i] = ""
            }
        }
        
        lineChart1.setXLabels(lineGraphDates, withWidth: ((lineGraph1View.bounds.width - 80) / CGFloat(lineGraphDates.count)))
        data1.getData = ({ (index: UInt) -> PNLineChartDataItem in
            var yValue : CGFloat = CGFloat(self.vals1[Int(index) as Int])
            var item = PNLineChartDataItem(y: yValue)
            return item
        })
        
        lineChart1.chartData = [data1]
        lineChart1.strokeChart()
        lineGraph1View.addSubview(lineChart1)
        
        if settings.integerForKey("RowSelected") == 0 || settings.integerForKey("RowSelected") == 1 {
            var lineRect2 = CGRect(x: lineGraph2View.bounds.minX, y: lineGraph2View.bounds.minY + 10, width: lineGraph2View.bounds.width, height: lineGraph2View.bounds.height - 10)
            var lineRect3 = CGRect(x: lineGraph3View.bounds.minX, y: lineGraph3View.bounds.minY + 10, width: lineGraph3View.bounds.width, height: lineGraph3View.bounds.height - 10)
            self.lineChart2 = PNLineChart(frame: lineRect2) as PNLineChart
            self.lineChart3 = PNLineChart(frame: lineRect3) as PNLineChart
            lineChart2.backgroundColor = UIColor.clearColor()
            lineChart2.showCoordinateAxis = true
            lineChart2.yValueMax = getMaxValue(vals2)
            lineChart2.yValueMin = getMinValue(vals2)
            lineChart3.backgroundColor = UIColor.clearColor()
            lineChart3.showCoordinateAxis = true
            lineChart3.yValueMax = getMaxValue(vals3)
            lineChart3.yValueMin = getMinValue(vals3)
            lineChart2.setXLabels(lineGraphDates, withWidth: ((lineGraph2View.bounds.width - 80) / CGFloat(lineGraphDates.count)))
            lineChart3.setXLabels(lineGraphDates, withWidth: ((lineGraph2View.bounds.width - 80) / CGFloat(lineGraphDates.count)))
            
            data2.getData = ({ (index: UInt) -> PNLineChartDataItem in
                var yValue : CGFloat = CGFloat(self.vals2[Int(index) as Int])
                var item = PNLineChartDataItem(y: yValue)
                return item
            })
            data3.getData = ({ (index: UInt) -> PNLineChartDataItem in
                var yValue : CGFloat = CGFloat(self.vals3[Int(index) as Int])
                var item = PNLineChartDataItem(y: yValue)
                return item
            })
            
            lineChart2.chartData = [data2]
            lineChart2.strokeChart()
            lineChart3.chartData = [data3]
            lineChart3.strokeChart()
            
            lineGraph2View.addSubview(lineChart2)
            lineGraph3View.addSubview(lineChart3)
        }
    }
    
    func getServerData() {
        self.lineGraphDates = []
        JHProgressHUD.sharedHUD.showInView(self.view, withHeader: "Fetching Latest Data", andFooter: "")
        
        var error : NSError? = nil
        let url = NSURL(string: "http://pan-banzai.cloudapp.net/banzai/api/data/historicaldata")
        var request = NSMutableURLRequest(URL: url!)
        request.HTTPMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        var credentialString = "Basic " + ((settings.valueForKey("credentials") as! NSString) as String)
        request.addValue(credentialString, forHTTPHeaderField: "Authorization")
        
        UIApplication.sharedApplication().networkActivityIndicatorVisible = true
        
        var outerDictionary = NSDictionary(objects: ["Day", timeFrame, dataToFetch], forKeys: ["GroupBy", "TimeFrame", "WidgetMetrics"])
        var jsonData = NSJSONSerialization.dataWithJSONObject(outerDictionary, options: NSJSONWritingOptions.PrettyPrinted, error: &error)
        request.HTTPBody = jsonData!
        
        NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue()) { (response, data, error) -> Void in
            var statusCode = (response as! NSHTTPURLResponse).statusCode
            if error != nil {
                println("Uh oh... there was an error getting the server detail data from the server.")
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
                if metricID == Constants.MetricIDs.AppTier.CPU1 || metricID == Constants.MetricIDs.AppTier.MEM1 || metricID == Constants.MetricIDs.AppTier.DISK1 || metricID == Constants.MetricIDs.WebTier.CPU1 || metricID == Constants.MetricIDs.WebTier.MEM1 || metricID == Constants.MetricIDs.WebTier.DISK1 || metricID == Constants.MetricIDs.DataTier.CPU || metricID == Constants.MetricIDs.DataTier.MEM || metricID == Constants.MetricIDs.DataTier.DISK {
                    vals1[index] = value
                } else if metricID == Constants.MetricIDs.AppTier.CPU2 || metricID == Constants.MetricIDs.AppTier.MEM2 || metricID == Constants.MetricIDs.AppTier.DISK2 || metricID == Constants.MetricIDs.WebTier.CPU2 || metricID == Constants.MetricIDs.WebTier.MEM2 || metricID == Constants.MetricIDs.WebTier.DISK2 {
                    vals2[index] = value
                } else if metricID == Constants.MetricIDs.AppTier.CPU3 || metricID == Constants.MetricIDs.AppTier.MEM3 || metricID == Constants.MetricIDs.AppTier.DISK3 || metricID == Constants.MetricIDs.WebTier.CPU3 || metricID == Constants.MetricIDs.WebTier.MEM3 || metricID == Constants.MetricIDs.WebTier.DISK3 {
                    vals3[index] = value
                } else {
                    println("There was an issue getting the data in the server detail page.")
                }
            }
        }
    }
    
    func getIndexOfDate(date : String) -> Int {
        var counter = 0
        for d in lineGraphDates {
            if d == date { return counter }
            counter++
        }
        
        return -1
    }
    
    func getMaxValue(vals : [Double]) -> CGFloat {
        var max = 0.0
        for val in vals {
            if val > max {
                max = val
            }
        }
        
        if max <= 90.0 {
            max += 10.0
        }
        
        return CGFloat(max)
    }
    
    func getMinValue(vals : [Double]) -> CGFloat {
        var min = 100.0
        for val in vals {
            if val < min {
                min = val
            }
        }
        
        if min >= 10.0 {
            min -= 10.0
        }
        
        return CGFloat(min)
    }

}
