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
    
    var dataObject: AnyObject?
    @IBOutlet weak var dashboardTable: UITableView!
    var delegate: DashboardViewControllerDelegate?
    let settings = NSUserDefaults.standardUserDefaults()
    
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
    
//    override func viewWillAppear(animated: Bool) {
//        super.viewWillAppear(animated)
//        self.updateData()
//    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: false)
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        
    }
    
    func defaultSettings() {
        settings.setBool(true, forKey: "changed")
        settings.setDouble(0.2, forKey: "cpuLower")
        settings.setDouble(0.8, forKey: "cpuUpper")
        settings.setDouble(0.2, forKey: "memLower")
        settings.setDouble(0.8, forKey: "memUpper")
        settings.setDouble(0.2, forKey: "diskLower")
        settings.setDouble(0.8, forKey: "diskUpper")
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
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        println("Selected row #\(indexPath.row)")
        var selectedCell : DashboardTableCell = tableView.cellForRowAtIndexPath(indexPath)! as! DashboardTableCell
        
        tableView.beginUpdates()
        selectedCell.updateDoughnuts()
        selectedCell.circleArea.hidden = false
        selectedCell.accessoryType = .None
        selectedCell.selectionStyle = UITableViewCellSelectionStyle.None
        selectedCell.contentView.autoresizingMask = UIViewAutoresizing.FlexibleWidth | UIViewAutoresizing.FlexibleLeftMargin | UIViewAutoresizing.FlexibleRightMargin
        tableView.endUpdates()
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

    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    @IBAction func refreshData(sender: AnyObject) {
        println("Refresh the dashboard screen")
    }
}