//
//  DashboardTableCell.swift
//  Banzai
//
//  Created by Jonathan Earles on 1/21/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

import UIKit

class DashboardTableCell: UITableViewCell {

    @IBOutlet weak var serverName: UILabel!
    @IBOutlet weak var circleArea: UIView!
    @IBOutlet weak var cpuArea: UIView!
    @IBOutlet weak var memArea: UIView!
    @IBOutlet weak var diskArea: UIView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
