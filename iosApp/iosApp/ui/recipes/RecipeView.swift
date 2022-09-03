//
//  RecipeView.swift
//  iosApp
//
//  Created by Lena Stepanova on 16.07.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import struct Kingfisher.KFImage
 
struct RecipeView: View {
    
    var item: RecipeUiModel
    
    var body: some View {
        HStack(alignment: .center) {
            KFImage(URL(string: "https://m.media-amazon.com/images/I/413qxEF0QPL._AC_.jpg"))
                .resizable()
                .frame(width: 80, height: 64, alignment: .center)
                .cornerRadius(8)
                .padding(.trailing)
            VStack(alignment: .leading) {
                Text(item.title)
                    .font(.body)
            }
        }
    }
}


