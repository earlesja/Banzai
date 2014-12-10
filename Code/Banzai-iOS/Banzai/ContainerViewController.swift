//
//  ContainerViewController.swift
//  Banzai
//
//  Created by CSSE Department on 12/10/14.
//  Copyright (c) 2014 Rose-Hulman. All rights reserved.
//

import UIKit

enum SlideOutState {
    case MenuCollapsed
    case MenuExpanded
}

class ContainerViewController: UIViewController, CenterViewControllerDelegate, UIGestureRecognizerDelegate {

    var centerNavigationController: UINavigationController!
    var centerViewController: CenterViewController!
    
    var currentState: SlideOutState = .MenuCollapsed {
        didSet {
            let shouldShowShadow = currentState != .MenuCollapsed
            showShadowForCenterViewController(shouldShowShadow)
        }
    }
    
    var sideMenuViewController: SideMenuViewController?
    let centerPanelExpandedOffset: CGFloat = 60
    
    override init() {
        super.init(nibName: nil, bundle: nil)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        centerViewController = UIStoryboard.centerViewController()
        centerViewController.delegate = self
        
        // wrap the centerViewController in a navigation controller, so we can push views to it
        // and display bar button items in the navigation bar
        centerNavigationController = UINavigationController(rootViewController: centerViewController)
        view.addSubview(centerNavigationController.view)
        addChildViewController(centerNavigationController)
        
        centerNavigationController.didMoveToParentViewController(self)
        
        let panGestureRecognizer = UIPanGestureRecognizer(target: self, action: "handlePanGesture:")
        centerNavigationController.view.addGestureRecognizer(panGestureRecognizer)
    }
    
    func toggleSideMenu() {
        let notAlreadyExpanded = (currentState != .MenuExpanded)
        
        if notAlreadyExpanded {
            addSideMenuViewController()
        }
        
        animateSideMenu(shouldExpand: notAlreadyExpanded)
    }
    
    func collapseSideMenu() {
        switch (currentState) {
        case .MenuExpanded:
            toggleSideMenu()
        default:
            break
        }
    }
    
    func addSideMenuViewController() {
        if (sideMenuViewController == nil) {
            sideMenuViewController = UIStoryboard.sideMenuViewController()
            addChildSideMenuController(sideMenuViewController!)
        }
    }
    
    func addChildSideMenuController(sideMenuController: SideMenuViewController){
        sideMenuController.delegate = centerViewController
        
        view.insertSubview(sideMenuController.view, atIndex: 0)
        addChildViewController(sideMenuController)
        sideMenuController.didMoveToParentViewController(self)
    }
    
    func animateSideMenu(#shouldExpand: Bool) {
        if (shouldExpand) {
            currentState = .MenuExpanded
            animateCenterPanelXPosition(targetPosition: CGRectGetWidth(centerNavigationController.view.frame) - centerPanelExpandedOffset)
        } else {
            animateCenterPanelXPosition(targetPosition: 0) { finished in
                self.currentState = .MenuCollapsed
                self.sideMenuViewController!.view.removeFromSuperview()
                self.sideMenuViewController = nil
            }
            
        }
    }
    
    func animateCenterPanelXPosition(#targetPosition: CGFloat, completion: ((Bool) -> Void)! = nil) {
        UIView.animateWithDuration(0.5, delay: 0, usingSpringWithDamping: 0.8, initialSpringVelocity: 0, options: .CurveEaseInOut, animations: {
            self.centerNavigationController.view.frame.origin.x = targetPosition
        }, completion: completion)
    }
    
    func showShadowForCenterViewController(shouldShowShadow: Bool) {
        if (shouldShowShadow) {
            centerNavigationController.view.layer.shadowOpacity = 0.8
        } else {
            centerNavigationController.view.layer.shadowOpacity = 0.0
        }
    }
    
    func handlePanGesture(recognizer: UIPanGestureRecognizer) {
        // we can determine whether the user is revealing the left or right
        // panel by looking at the velocity of the gesture
        let gestureIsDraggingFromLeftToRight = (recognizer.velocityInView(view).x > 0)
        
        switch(recognizer.state) {
        case .Began:
            if (currentState == .MenuCollapsed) {
                // If the user starts panning, and neither panel is visible
                // then show the correct panel based on the pan direction
                
                if (gestureIsDraggingFromLeftToRight) {
                    addSideMenuViewController()
                }
                
                showShadowForCenterViewController(true)
            }
        case .Changed:
            // If the user is already panning, translate the center view controller's
            // view by the amount that the user has panned
            // I THINK this is where the black menu is coming from.
            recognizer.view!.center.x = recognizer.view!.center.x + recognizer.translationInView(view).x
            recognizer.setTranslation(CGPointZero, inView: view)
        case .Ended:
            // When the pan ends, check whether the left or right view controller is visible
            if (sideMenuViewController != nil) {
                // animate the side panel open or closed based on whether the view has moved more or less than halfway
                let hasMovedGreaterThanHalfway = recognizer.view!.center.x > view.bounds.size.width
                animateSideMenu(shouldExpand: hasMovedGreaterThanHalfway)
            }
        default:
            break
        }
    }
    
}

private extension UIStoryboard {
    class func mainStoryboard() -> UIStoryboard {return UIStoryboard(name: "Main", bundle: NSBundle.mainBundle()) }
    
    class func sideMenuViewController() -> SideMenuViewController? {
        return mainStoryboard().instantiateViewControllerWithIdentifier("SideMenuViewController") as? SideMenuViewController
    }
    
    class func dashboardViewController() -> DashboardViewController? {
        return mainStoryboard().instantiateViewControllerWithIdentifier("DashboardViewController") as? DashboardViewController
    }
    
    class func centerViewController() -> CenterViewController? {
        return mainStoryboard().instantiateViewControllerWithIdentifier("CenterViewController") as? CenterViewController
    }
}
