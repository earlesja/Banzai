//
//  dashboardController.swift
//  Banzai
//
//  Created by Jonathan Earles on 12/2/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

@objc
protocol DashboardViewControllerDelegate {
    optional func toggleSideMenu()
    optional func collapseSideMenu()
}

class DashboardViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var dashboardTable: UITableView!
    
    // Constants
    let settings = NSUserDefaults.standardUserDefaults()
    let timeFrame = 14500
    
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
    var dataObject: AnyObject?
    var delegate: DashboardViewControllerDelegate?
    var currentlyUpdating : Bool = false
    var selectedRow : Int = -1
    var timer = NSTimer()
    var disabled : Bool = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if !settings.boolForKey("changed") {
            let alertController = UIAlertController(title: "Default Settings.", message: "Your settings have been configured to the default values.", preferredStyle: .Alert)
            let defaultAction = UIAlertAction(title: "Return", style: .Default) { action -> Void in
                self.defaultSettings()
            }
            alertController.addAction(defaultAction)
            presentViewController(alertController, animated: true, completion: nil)
        }
        
        dashboardTable.separatorStyle = UITableViewCellSeparatorStyle.SingleLine
        self.dashboardTable.tableFooterView = UIView()
        // wrap the centerViewController in a navigation controller, so we can push views to it
        // and display bar button items in the navigation bar
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: false)
    }
    override func viewDidDisappear(animated: Bool) {
        super.viewDidDisappear(animated)
        timer.invalidate()
    }
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        if settings.boolForKey("FetchedDashboardData"){
            updateValues()
            timer = NSTimer.scheduledTimerWithTimeInterval(15.0, target: self, selector: Selector("updateValues"), userInfo: nil, repeats: true)
        } else {
            // Update the values every 15 seconds.
            settings.setBool(true, forKey: "FetchedDashboardData")
            disabled = true
            JHProgressHUD.sharedHUD.showInView(self.view, withHeader: "Fetching Latest Data", andFooter: "")
            updateValues()
            timer = NSTimer.scheduledTimerWithTimeInterval(15.0, target: self, selector: Selector("updateValues"), userInfo: nil, repeats: true)
        }
    }
    
    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    func updateValues() {
        if !currentlyUpdating {
            currentlyUpdating = true
            var error : NSError? = nil
            let url = NSURL(string: "http://pan-banzai.cloudapp.net/banzai/api/data/historicaldata")
            var request = NSMutableURLRequest(URL: url!)
            request.HTTPMethod = "POST"
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            var credentialString = "Basic " + ((settings.valueForKey("credentials") as! NSString) as String)
            request.addValue(credentialString, forHTTPHeaderField: "Authorization")
            
            UIApplication.sharedApplication().networkActivityIndicatorVisible = true
            
            var outerDictionary = NSDictionary(objects: ["Second", timeFrame, [app_cpu_1, app_cpu_2, app_cpu_3, app_mem_1, app_mem_2, app_mem_3, app_disk_1, app_disk_2, app_disk_3, web_cpu_1, web_cpu_2, web_cpu_3, web_mem_1, web_mem_2, web_mem_3, web_disk_1, web_disk_2, web_disk_3, data_cpu, data_mem, data_disk]], forKeys: ["GroupBy", "TimeFrame", "WidgetMetrics"])
            var jsonData = NSJSONSerialization.dataWithJSONObject(outerDictionary, options: NSJSONWritingOptions.PrettyPrinted, error: &error)
            request.HTTPBody = jsonData!
            
            NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue()) { (response, data, error) -> Void in
                if error != nil {
                    println("Uh oh... there was an error getting the browser data from the server.")
                    var statusCode = (response as! NSHTTPURLResponse).statusCode
                    let alertController = UIAlertController(title: "", message: "", preferredStyle: UIAlertControllerStyle.Alert)
                    alertController.title = "Server error. Status code \(statusCode)"
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

                }
                self.disabled = false
                self.currentlyUpdating = false
                JHProgressHUD.sharedHUD.hide()
                UIApplication.sharedApplication().networkActivityIndicatorVisible = false
            }
        }
    }
    
    func parseData(data : AnyObject) {
        // temp values
        var t1_cpu = 0.0
        var t1_mem = 0.0
        var t1_disk = 0.0
        var t2_cpu = 0.0
        var t2_mem = 0.0
        var t2_disk = 0.0
        var t3_cpu = 0.0
        var t3_mem = 0.0
        var t3_disk = 0.0
        
        var dataList = data as! Array<AnyObject>
        var endRemove = dataList.count - 21 // 21 is the number of metrics we fetched.
        if endRemove > 0 {
            dataList.removeRange(0..<endRemove)
            
            println("Number elements: \(dataList.count)")
            
            var metricID = 0
            var value = 0.0
            var array = []
            for json in dataList {
                metricID = (json["MetricId"] as AnyObject? as? Int) ?? -1
                value = (json["Value"] as AnyObject? as? Double) ?? -1.0
                
                if metricID == Constants.MetricIDs.AppTier.CPU1 || metricID == Constants.MetricIDs.AppTier.CPU2 || metricID == Constants.MetricIDs.AppTier.CPU3 {
                    t1_cpu += value/3
                } else if metricID == Constants.MetricIDs.AppTier.MEM1 || metricID == Constants.MetricIDs.AppTier.MEM2 || metricID == Constants.MetricIDs.AppTier.MEM3 {
                    t1_mem += value/3
                } else if metricID == Constants.MetricIDs.AppTier.DISK1 || metricID == Constants.MetricIDs.AppTier.DISK2 || metricID == Constants.MetricIDs.AppTier.DISK3 {
                    t1_disk += value/3
                } else if metricID == Constants.MetricIDs.WebTier.CPU1 || metricID == Constants.MetricIDs.WebTier.CPU2 || metricID == Constants.MetricIDs.WebTier.CPU3 {
                    t2_cpu += value/3
                } else if metricID == Constants.MetricIDs.WebTier.MEM1 || metricID == Constants.MetricIDs.WebTier.MEM2 || metricID == Constants.MetricIDs.WebTier.MEM3 {
                    t2_mem += value/3
                } else if metricID == Constants.MetricIDs.WebTier.DISK1 || metricID == Constants.MetricIDs.WebTier.DISK2 || metricID == Constants.MetricIDs.WebTier.DISK3 {
                    t2_disk += value/3
                } else if metricID == Constants.MetricIDs.DataTier.CPU {
                    t3_cpu += value
                } else if metricID == Constants.MetricIDs.DataTier.MEM {
                    t3_mem += value
                } else if metricID == Constants.MetricIDs.DataTier.DISK {
                    t3_disk += value
                }
            }
            
            settings.setInteger(Int(t1_cpu), forKey: "tier1_cpu")
            settings.setInteger(Int(t1_mem), forKey: "tier1_mem")
            settings.setInteger(Int(t1_disk), forKey: "tier1_disk")
            settings.setInteger(Int(t2_cpu), forKey: "tier2_cpu")
            settings.setInteger(Int(t2_mem), forKey: "tier2_mem")
            settings.setInteger(Int(t2_disk), forKey: "tier2_disk")
            settings.setInteger(Int(t3_cpu), forKey: "tier3_cpu")
            settings.setInteger(Int(t3_mem), forKey: "tier3_mem")
            settings.setInteger(Int(t3_disk), forKey: "tier3_disk")
            
            if selectedRow == 0 {
                (self.dashboardTable.cellForRowAtIndexPath(NSIndexPath(forRow: selectedRow, inSection: 0)) as! DashboardTableCell).updateDoughnuts(settings.integerForKey("tier1_cpu"), memVal: settings.integerForKey("tier1_mem"), diskVal: settings.integerForKey("tier1_disk"))
            } else if selectedRow == 1 {
                (self.dashboardTable.cellForRowAtIndexPath(NSIndexPath(forRow: selectedRow, inSection: 0)) as! DashboardTableCell).updateDoughnuts(settings.integerForKey("tier2_cpu"), memVal: settings.integerForKey("tier2_mem"), diskVal: settings.integerForKey("tier2_disk"))
            } else if selectedRow == 2 {
                (self.dashboardTable.cellForRowAtIndexPath(NSIndexPath(forRow: selectedRow, inSection: 0)) as! DashboardTableCell).updateDoughnuts(settings.integerForKey("tier3_cpu"), memVal: settings.integerForKey("tier3_mem"), diskVal: settings.integerForKey("tier3_disk"))
            }
        }
        
        println("Finished updating data")
    }
    
    func defaultSettings() {
        settings.setBool(true, forKey: "changed")
        settings.setDouble(0.2, forKey: "cpuLower")
        settings.setDouble(0.8, forKey: "cpuUpper")
        settings.setDouble(0.2, forKey: "memLower")
        settings.setDouble(0.8, forKey: "memUpper")
        settings.setDouble(0.2, forKey: "diskLower")
        settings.setDouble(0.8, forKey: "diskUpper")
        settings.setInteger(0, forKey: "RowSelected")
        settings.setInteger(60*60*24, forKey: "TimeFrame")
        settings.setValue("Day", forKey: "TimeFrameString")
        settings.setValue("Hour", forKey: "GroupBy")
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return 3;
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(
            "DashboardCell", forIndexPath: indexPath)
                as! DashboardTableCell
        
        switch(indexPath.row) {
        case 0:
            cell.serverName.text = "App Tier"
        case 1:
            cell.serverName.text = "Web Tier"
        case 2:
            cell.serverName.text = "Data Tier"
        default:
            cell.serverName.text = "An Error Occurred"
        }
        return cell
    }
    
    func tableView(tableView: UITableView, willSelectRowAtIndexPath indexPath: NSIndexPath) -> NSIndexPath? {
        if disabled {
            return nil
        } else {
            return indexPath
        }
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        println("Selected row #\(indexPath.row)")
        settings.setInteger(indexPath.row, forKey: "RowSelected")
        self.selectedRow = indexPath.row
        var selectedCell : DashboardTableCell = tableView.cellForRowAtIndexPath(indexPath)! as! DashboardTableCell
        
        var cpuTap = UITapGestureRecognizer(target: self, action: "navigateToCPUServerDetails")
        var memTap = UITapGestureRecognizer(target: self, action: "navigateToMEMServerDetails")
        var diskTap = UITapGestureRecognizer(target: self, action: "navigateToDISKServerDetails")
        cpuTap.numberOfTapsRequired = 1
        memTap.numberOfTapsRequired = 1
        diskTap.numberOfTapsRequired = 1
        
        tableView.beginUpdates()
        
        switch(indexPath.row) {
        case 0:
            selectedCell.updateDoughnuts(settings.integerForKey("tier1_cpu"), memVal: settings.integerForKey("tier1_mem"), diskVal: settings.integerForKey("tier1_disk"))
        case 1:
            selectedCell.updateDoughnuts(settings.integerForKey("tier2_cpu"), memVal: settings.integerForKey("tier2_mem"), diskVal: settings.integerForKey("tier2_disk"))
        case 2:
            selectedCell.updateDoughnuts(settings.integerForKey("tier3_cpu"), memVal: settings.integerForKey("tier3_mem"), diskVal: settings.integerForKey("tier3_disk"))
        default:
            selectedCell.serverName.text = "An Error Occurred"
        }
        
        selectedCell.cpuArea.addGestureRecognizer(cpuTap)
        selectedCell.memArea.addGestureRecognizer(memTap)
        selectedCell.diskArea.addGestureRecognizer(diskTap)
        
        selectedCell.circleArea.hidden = false
        selectedCell.accessoryType = .None
        selectedCell.selectionStyle = UITableViewCellSelectionStyle.None
        selectedCell.contentView.autoresizingMask = UIViewAutoresizing.FlexibleWidth | UIViewAutoresizing.FlexibleLeftMargin | UIViewAutoresizing.FlexibleRightMargin
        tableView.endUpdates()
    }
    
    func navigateToCPUServerDetails() {
        settings.setObject("cpu", forKey: "server")
        let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main",bundle: nil)
        let destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("ServerDetailsViewController") as! UIViewController
        self.sideMenuController()?.setContentViewControllerWithoutMenu(destViewController)
    }
    
    func navigateToMEMServerDetails() {
        settings.setObject("mem", forKey: "server")
        let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main",bundle: nil)
        let destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("ServerDetailsViewController") as! UIViewController
        self.sideMenuController()?.setContentViewControllerWithoutMenu(destViewController)
    }
    
    func navigateToDISKServerDetails() {
        settings.setObject("disk", forKey: "server")
        let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main",bundle: nil)
        let destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("ServerDetailsViewController") as! UIViewController
        self.sideMenuController()?.setContentViewControllerWithoutMenu(destViewController)
    }
    
    func tableView(tableView: UITableView, didDeselectRowAtIndexPath indexPath: NSIndexPath) {
        var selectedCell : DashboardTableCell = tableView.cellForRowAtIndexPath(indexPath)! as! DashboardTableCell
        selectedCell.clearDoughnuts()
        selectedCell.accessoryType = .DisclosureIndicator
        selectedCell.circleArea.hidden = true;
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        
        if indexPath == tableView.indexPathForSelectedRow() {
            return 250.0
        }
        return 37.0
    }
}