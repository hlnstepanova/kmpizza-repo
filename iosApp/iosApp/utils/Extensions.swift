//
//  Extensions.swift
//  iosApp
//
//  Created by Lena Stepanova on 17.07.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared

extension Int64 {
    func toKotlinLong() -> KotlinLong {
        return KotlinLong(value: self)
    }
}
