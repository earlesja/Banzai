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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //self.dashboardTable.tableFooterView = UIView()
        // wrap the centerViewController in a navigation controller, so we can push views to it
        // and display bar button items in the navigation bar
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return 3;
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(
            "DashboardCell", forIndexPath: indexPath)
                as DashboardTableCell
        
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
        tableView.beginUpdates()
        tableView.endUpdates()
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        
        if indexPath == tableView.indexPathForSelectedRow() {
            return 200.0
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