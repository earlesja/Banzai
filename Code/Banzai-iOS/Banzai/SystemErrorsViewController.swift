//
//  SystemErrorsViewController.swift
//  Banzai
//
//  Created by Chris O'Hara on 12/17/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

class SystemErrorsViewController: UIViewController {
    
    
    @IBOutlet weak var thelabel: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
    }
    
    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    @IBAction func refreshData(sender: AnyObject) {
        println("Refresh the System Errors page")
    }
}
