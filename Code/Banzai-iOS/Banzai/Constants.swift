//
//  Constants.swift
//  Banzai
//
//  Created by CSSE Department on 4/13/15.
//  Copyright (c) 2015 Rose-Hulman. All rights reserved.
//

import Foundation

class Constants {
    
    // SERVER Constants
    struct MetricIDs {
        struct AppTier {
            static let CPU1 = 79
            static let CPU2 = 80
            static let CPU3 = 81
            static let MEM1 = 85
            static let MEM2 = 86
            static let MEM3 = 87
            static let DISK1 = 93
            static let DISK2 = 94
            static let DISK3 = 95
        }
        
        struct WebTier {
            static let CPU1 = 82
            static let CPU2 = 83
            static let CPU3 = 84
            static let MEM1 = 88
            static let MEM2 = 89
            static let MEM3 = 90
            static let DISK1 = 96
            static let DISK2 = 97
            static let DISK3 = 98
        }
        
        struct DataTier {
            static let CPU = 91
            static let MEM = 92
            static let DISK = 99
        }
    }
    
    struct ServerIDs {
        static let APP1 = 23
        static let APP2 = 24
        static let APP3 = 25
        static let WEB1 = 26
        static let WEB2 = 27
        static let WEB3 = 28
        static let DISK = 37
    }
    
    struct BrowserIDs {
        static let IE8_1 = 23, IE8_2 = 30, IE8_3 = 39
        static let IE9_1 = 24, IE9_2 = 31, IE9_3 = 40
        static let IE10_1 = 25, IE10_2 = 32, IE10_3 = 41
        static let IE11_1 = 26, IE11_2 = 33, IE11_3 = 42
        static let Firefox_1 = 7, Firefox_2 = 17, Firefox_3 = 37
        static let Chrome_1 = 8, Chrome_2 = 18, Chrome_3 = 38
        static let Safari_1 = 27, Safari_2 = 34, Safari_3 = 43
    }
    
    struct OSIDs {
        static let WindowsVista_1 = 49, WindowsVista_2 = 50, WindowsVista_3 = 51
        static let Windows7_1 = 52, Windows7_2 = 53, Windows7_3 = 54
        static let Windows8_1 = 55, Windows8_2 = 56, Windows8_3 = 57
        static let Windows8_1_1 = 58, Windows8_1_2 = 59, Windows8_1_3 = 60
        static let Mac_1 = 64, Mac_2 = 65, Mac_3 = 66
        static let iOS_1 = 70, iOS_2 = 71, iOS_3 = 72
        static let Linux_1 = 67, Linux_2 = 68, Linux_3 = 69
        static let Android_1 = 73, Android_2 = 74, Android_3 = 75
    }
    
    struct UIColors {
        static let purple = UIColor(red: 142/255, green: 36/255, blue: 170/255, alpha: 1.0)
        static let deepPurple = UIColor(red: 94/255, green: 53/255, blue: 177/255, alpha: 1.0)
        static let indigo = UIColor(red: 57/255, green: 73/255, blue: 171/255, alpha: 1.0)
        static let blue = UIColor(red: 30/255, green: 136/255, blue: 229/255, alpha: 1.0)
        static let teal = UIColor(red: 0, green: 137/255, blue: 123/255, alpha: 1.0)
        static let green = UIColor(red: 67/255, green: 160/255, blue: 71/255, alpha: 1.0)
        static let gray = UIColor(red: 117/255, green: 117/255, blue: 117/255, alpha: 1.0)
        static let brown = UIColor(red: 109/255, green: 76/255, blue: 65/255, alpha: 1.0)
    }
}