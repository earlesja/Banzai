//
//  SideMenuController.swift
//  Banzai
//
//  Created by CSSE Department on 12/9/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

@objc
protocol SideMenuViewControllerDelegate {
    func screenSelected(screenNumber: Int)
}

class SideMenuViewController: UITableViewController, UITableViewDelegate {
    
    @IBOutlet var menuTable: UITableView!
    var delegate: SideMenuViewControllerDelegate?
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        
        println("You selected row # \(indexPath.row)")
        delegate?.screenSelected(indexPath.row)
    }
}
