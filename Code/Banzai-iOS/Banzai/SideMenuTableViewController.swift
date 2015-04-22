//
//  SideMenuTableViewController.swift
//  Banzai
//
//  Created by CSSE Department on 12/17/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

class SideMenuTableViewController: UITableViewController {
    var selectedMenuItem : Int = 0
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Customize apperance of table view
        tableView.contentInset = UIEdgeInsetsMake(64.0, 0, 0, 0) //
        tableView.separatorStyle = .None
        tableView.backgroundColor = UIColor.clearColor()
        tableView.scrollsToTop = false
        
        // Preserve selection between presentations
        self.clearsSelectionOnViewWillAppear = false
        
        tableView.selectRowAtIndexPath(NSIndexPath(forRow: selectedMenuItem, inSection: 0), animated: false, scrollPosition: .Middle)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Table view data source
    
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // Return the number of sections.
        return 1
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // Return the number of rows in the section.
        return 7
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        
        var cell = tableView.dequeueReusableCellWithIdentifier("CELL") as? UITableViewCell
        
        if (cell == nil) {
            cell = UITableViewCell(style: UITableViewCellStyle.Default, reuseIdentifier: "CELL")
            cell!.backgroundColor = UIColor.clearColor()
            cell!.textLabel?.textColor = UIColor.darkGrayColor()
            let selectedBackgroundView = UIView(frame: CGRectMake(0, 0, cell!.frame.size.width, cell!.frame.size.height))
            selectedBackgroundView.backgroundColor = UIColor.grayColor().colorWithAlphaComponent(0.2)
            cell!.selectedBackgroundView = selectedBackgroundView
        }
        
        switch (indexPath.row) {
        case 0:
            cell!.textLabel?.text = "Dashboard"
        case 1:
            cell!.textLabel?.text = "Server Details"
        case 2:
            cell!.textLabel?.text = "OS Usage"
        case 3:
            cell!.textLabel?.text = "Browser Usage"
        case 4:
            cell!.textLabel?.text = "System Errors"
        case 5:
            cell!.textLabel?.text = "Settings"
        case 6:
            cell!.textLabel?.text = "Log Out"
        default:
            cell!.textLabel?.text = "ERROR..."
        }
        
        return cell!
    }
    
    override func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        return 50.0
    }
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        
        println("did select row: \(indexPath.row)")
        
        if (indexPath.row == selectedMenuItem) {
            return
        }
        selectedMenuItem = indexPath.row
        
        //Present new view controller
        let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main",bundle: nil)
        var destViewController : UIViewController
        switch (indexPath.row) {
        case 0:
            destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("DashboardViewController") as UIViewController
            sideMenuController()?.setContentViewController(destViewController)
            break
        case 1:
            destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("ServerDetailsViewController") as UIViewController
            sideMenuController()?.setContentViewController(destViewController)
            break
        case 2:
            destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("OSUsageViewController") as UIViewController
            sideMenuController()?.setContentViewController(destViewController)
            break
        case 3:
            destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("BrowserUsageController") as UIViewController
            sideMenuController()?.setContentViewController(destViewController)
            break
        case 4:
            destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("SystemErrorsViewController") as UIViewController
            sideMenuController()?.setContentViewController(destViewController)
            break
        case 5:
            destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("SettingsViewController") as UIViewController
            sideMenuController()?.setContentViewController(destViewController)
            break
        case 6:
            destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("LoginViewController") as UIViewController
            topMostController()?.setContentViewController(destViewController)
            
            //self.presentViewController(destViewController, animated: true, completion: nil)
            //self.showViewController(destViewController, sender: destViewController)
            //destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("SettingsViewController") as UIViewController
            //let vc : AnyObject! = mainStoryboard.instantiateViewControllerWithIdentifier("LoginViewController")
            //self.showViewController(vc as UIViewController, sender: vc)
            break
        default:
            // ERROR!!!
            destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("LoginViewController") as UIViewController
            break
        }
        
    }
    
    /*
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue!, sender: AnyObject!) {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
    }
    */
    
}

