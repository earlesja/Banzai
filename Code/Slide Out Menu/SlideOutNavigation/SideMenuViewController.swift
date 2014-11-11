//
//  LeftViewController.swift
//  SlideOutNavigation
//
//  Created by James Frost on 03/08/2014.
//  Copyright (c) 2014 James Frost. All rights reserved.
//

import UIKit

@objc
protocol SideMenuViewControllerDelegate {
  func menuItemSelected()
}

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

    //tableView.reloadData()
  }

  // MARK: Table View Data Source

  func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }

  func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return 3
  }

  func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCellWithIdentifier(TableView.CellIdentifiers.AnimalCell, forIndexPath: indexPath) as AnimalCell
    cell.configure()
    return cell
  }

  // Mark: Table View Delegate
  func tableView(tableView: UITableView!, didSelectRowAtIndexPath indexPath: NSIndexPath!) {
    //let selectedAnimal = animals[indexPath.row]
    //delegate?.animalSelected(selectedAnimal)
  }

}

class AnimalCell: UITableViewCell {

  func configure() {
    
  }
}