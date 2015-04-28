//
//  AppDelegate.swift
//  Banzai
//
//  Created by Jonathan Earles on 12/2/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    let settings = NSUserDefaults.standardUserDefaults()

    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        // Override point for customization after application launch.
        settings.setBool(false, forKey: "FetchedBrowserData")
        settings.setBool(false, forKey: "FetchedOSData")
        
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "browserPercentages")
        settings.setObject([0, 0, 0, 0, 0, 0, 0], forKey: "browserCounts")
        settings.setObject(["", "", "", "", "", "", ""], forKey: "browserDates")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "ie8Values")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "ie9Values")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "ie10Values")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "ie11Values")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "firefoxValues")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "chromeValues")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "safariValues")
        
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "osPercentages")
        settings.setObject([0, 0, 0, 0, 0, 0, 0, 0], forKey: "osCounts")
        settings.setObject(["", "", "", "", "", "", ""], forKey: "osDates")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "wVistaValues")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "w7Values")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "w8Values")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "w8OneValues")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "macValues")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "iOSValues")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "androidValues")
        settings.setObject([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], forKey: "linuxValues")
        
        return true
    }

    func applicationWillResignActive(application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(application: UIApplication) {
        // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
}

