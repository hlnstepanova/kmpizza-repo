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
    @Binding var image: UIImage?
    @State private var showingImagePicker = false
    
    var body: some View {
        ZStack{
            if let image = image {
                Image(uiImage: image)
                    .resizable()
                    .frame(width: 200, height: 150)
            } else {
                KFImage
                    .url((URL(string: "https://m.media-amazon.com/images/I/413qxEF0QPL._AC_.jpg")))
                    .resizable()
                    .frame(width: 200, height: 150)
            }
            
            Image(systemName: "camera.circle.fill")
                .font(.system(size: 28, weight: .light))
                .foregroundColor(Color.black)
                .frame(width: 50, height: 50)
                .background(Color.accentColor)
                .clipShape(Circle())
            
        }
        .sheet(isPresented: $showingImagePicker){
            ImagePickerIOS(onImageSelected: {
                self.image = $0
            })
        }
        .onTapGesture {
            showingImagePicker.toggle()
        }
    }
}

