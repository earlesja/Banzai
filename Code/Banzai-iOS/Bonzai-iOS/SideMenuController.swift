//
//  SideMenuController.swift
//  Banzai-iOS
//
//  Created by CSSE Department on 11/11/14.
//  Copyright (c) 2014 Rose-Hulman CSSE Department. All rights reserved.
//

import UIKit

class SideMenuViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    @IBOutlet weak var tableView: UITableView!
    
    var delegate: SideMenuViewControllerDelegate?
    
    struct TableView {
        struct CellIdentifiers {
            static let AnimalCell = "AnimalCell"
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.reloadData()
    }
    
    // MARK: Table View Data Source
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    
    //func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    //    let cell = tableView.dequeueReusableCellWithIdentifier(TableView.CellIdentifiers.AnimalCell, forIndexPath: indexPath) as AnimalCell
    //    cell.configureForAnimal(animals[indexPath.row])
    //    return cell
    //}
    
    // Mark: Table View Delegate
    //func tableView(tableView: UITableView!, didSelectRowAtIndexPath indexPath: NSIndexPath!) {
    //    let selectedAnimal = 3
    //    delegate?.animalSelected(selectedAnimal)
    //}
    
}
