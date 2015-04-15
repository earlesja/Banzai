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
        
        testFunct()
    }
    
    func testFunct() {
        let url = NSURL(string: "http://pan-banzai.cloudapp.net/banzai/api/data/historicaldata")
        var request = NSMutableURLRequest(URL: url)
        var innerDictionary = NSDictionary(objects: [7, 2], forKeys: ["MetricId", "ApplicationID"])
        var outerDictionary = NSDictionary(objects: ["Day", 3000000, [innerDictionary]], forKeys: ["GroupBy", "TimeFrame", "WidgetMetrics"])
        var error : NSError? = nil

        var jsonData = NSJSONSerialization.dataWithJSONObject(outerDictionary, options: NSJSONWritingOptions.PrettyPrinted, error: &error)
        request.HTTPMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("Basic dXNlck5hbWU6cGFzc3dvcmQ=", forHTTPHeaderField: "Authorization")
        request.HTTPBody = jsonData!
        
        NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue()) { (response, data, error) -> Void in
            if error != nil {
                println("Uh oh... there was an error.")
            } else {
                var responseText = NSString(data: data, encoding: NSASCIIStringEncoding)
                println("Response: \(responseText)")
            }
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func toggleSideMenu(sender: AnyObject) {
        toggleSideMenuView()
    }
    
    @IBAction func refreshData(sender: AnyObject) {
        println("Refresh the System Errors page")
    }
}
