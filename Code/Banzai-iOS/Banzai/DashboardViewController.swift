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
        
        // wrap the centerViewController in a navigation controller, so we can push views to it
        // and display bar button items in the navigation bar
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return 3;
    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.Subtitle, reuseIdentifier: "MyTestCell")
        
        cell.textLabel?.text = "Row #\(indexPath.row)"
        cell.detailTextLabel?.text = "Subtitle #\(indexPath.row)"
        
        return cell
    }

}