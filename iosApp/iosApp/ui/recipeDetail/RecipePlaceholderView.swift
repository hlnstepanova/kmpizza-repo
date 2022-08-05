//
//  RecipePlaceholderView.swift
//  iosApp
//
//  Created by Lena Stepanova on 31.07.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import Kingfisher

struct RecipePlaceholderView: View {
    var body: some View {
        ZStack{
            KFImage(URL(string: "https://m.media-amazon.com/images/I/413qxEF0QPL._AC_.jpg"))
                .resizable()
                .frame(width: 200, height: 150)
            
            Image(systemName: "camera.circle.fill")
                .font(.system(size: 28, weight: .light))
                .foregroundColor(Color.black)
                .frame(width: 50, height: 50)
                .background(Color.accentColor)
                .clipShape(Circle())
        }
    }
}

