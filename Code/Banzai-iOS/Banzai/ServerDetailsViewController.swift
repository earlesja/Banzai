//
//  ServerDetailsViewController.swift
//  Banzai
//
//  Created by Chris O'Hara on 12/17/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

class ServerDetailsViewController: UIViewController {
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let backItem = UIBarButtonItem(title: "Back", style: UIBarButtonItemStyle.Plain, target: nil, action: nil)
        navigationItem.backBarButtonItem = backItem
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    @IBAction func refreshData(sender: AnyObject) {
        println("Refresh the Server Details page")
    }
}
