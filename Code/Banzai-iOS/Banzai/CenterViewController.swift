//
//  CenterViewController.swift
//  Banzai
//
//  Created by CSSE Department on 12/10/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

@objc
protocol CenterViewControllerDelegate {
    optional func toggleSideMenu() // might not be needed
    optional func collapseSideMenu()
}

class CenterViewController: UIViewController, SideMenuViewControllerDelegate {
    
    @IBOutlet var mainView: UIView!
    @IBOutlet weak var centerView: UIView!
    
    var delegate: CenterViewControllerDelegate?
    
    func screenSelected(screenNumber: Int) {
        
        if let d = delegate {
            d.collapseSideMenu!()
        }
    }
}
