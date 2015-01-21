//
//  ViewController.swift
//  Banzai
//
//  Created by Jonathan Earles on 12/2/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

class LoginController: UIViewController {

    var dataObject: AnyObject?
    
    @IBOutlet weak var emailField: UITextField!
    @IBOutlet weak var passwordField: UITextField!
    @IBOutlet weak var signinButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func backgroundTap(sender: AnyObject){
        self.view.endEditing(true)
    }
}
