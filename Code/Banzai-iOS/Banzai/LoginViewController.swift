//
//  ViewController.swift
//  Banzai
//
//  Created by Jonathan Earles on 12/2/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {

    var dataObject: AnyObject?
    
    @IBOutlet weak var emailField: UITextField!
    @IBOutlet weak var passwordField: UITextField!
    @IBOutlet weak var usernameWarning: UILabel!
    @IBOutlet weak var passwordWarning: UILabel!
    let settings = NSUserDefaults.standardUserDefaults()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        println("Started")
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func backgroundTap(sender: AnyObject){
        self.view.endEditing(true)
    }
    
    @IBAction func signInButtonPressed(sender: AnyObject) {
        var emailText = emailField.text
        var passwordText = passwordField.text
        println("Email: \(emailText) Password: \(passwordText)")
        
        emailText == "" ? (usernameWarning.hidden = false) : (usernameWarning.hidden = true)
        passwordText == "" ? (passwordWarning.hidden = false) : (passwordWarning.hidden = true)
        
        if emailText != "" && passwordText != "" {
            
            JHProgressHUD.sharedHUD.showInView(self.view, withHeader: "Logging In", andFooter: "")
            
            var fullLoginText = emailText + ":" + passwordText
            let utf8str: NSData = fullLoginText.dataUsingEncoding(NSUTF8StringEncoding)!
            let base64Encoded:NSString = utf8str.base64EncodedStringWithOptions(NSDataBase64EncodingOptions(rawValue: 0))
            let credentialString = "Basic " + (base64Encoded as String)
            
            var error : NSError? = nil
            var response : NSURLResponse? = nil
            let url = NSURL(string: "http://pan-banzai.cloudapp.net/banzai/api/Data/DimMetric")
            var request = NSMutableURLRequest(URL: url!)
            request.addValue(credentialString, forHTTPHeaderField: "Authorization")
            
            NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue()) { (response, data, error) -> Void in
                var statusCode = (response as! NSHTTPURLResponse).statusCode
                println(statusCode)
                
                if error != nil {
                    println("Uh oh... there was an error logging in.")
                    let alertController = UIAlertController(title: "", message: "", preferredStyle: UIAlertControllerStyle.Alert)
                    statusCode == 500 ? (alertController.title = "Incorrect username or password.") : (alertController.title = "There was an error logging in (status code: \(statusCode)).")
                    let okAction = UIAlertAction(title: "OK", style: UIAlertActionStyle.Default) { (action) -> Void in
                    }
                    alertController.addAction(okAction)
                    self.presentViewController(alertController, animated: true, completion: nil)
                } else {
                    
                    if statusCode == 200 {
                        self.settings.setObject(base64Encoded, forKey: "credentials")
                        let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main",bundle: nil)
                        let destViewController = mainStoryboard.instantiateViewControllerWithIdentifier("DashboardViewController") as! UIViewController
                        self.sideMenuController()?.setContentViewControllerWithoutMenu(destViewController)
                    } else {
                        let alertController = UIAlertController(title: "", message: "", preferredStyle: UIAlertControllerStyle.Alert)
                        statusCode == 500 ? (alertController.title = "Incorrect username or password.") : (alertController.title = "There was an error logging in (status code: \(statusCode)).")
                        let okAction = UIAlertAction(title: "OK", style: UIAlertActionStyle.Default) { (action) -> Void in
                        }
                        alertController.addAction(okAction)
                        self.presentViewController(alertController, animated: true, completion: nil)
                    }
                }
                JHProgressHUD.sharedHUD.hide()
            }
        }
    }
}

//var rs = response as NSHTTPURLResponse
//println("Response: \(rs.statusCode)")
