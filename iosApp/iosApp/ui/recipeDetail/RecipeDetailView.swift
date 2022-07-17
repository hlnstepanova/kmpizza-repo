//
//  RecipeDetailView.swift
//  iosApp
//
//  Created by Lena Stepanova on 17.07.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import Kingfisher

struct RecipeDetailView: View {
    
    let recipeId: KotlinLong?
    @ObservedObject var state: RecipeDetailState
    
    init(id: KotlinLong?) {
        self.recipeId = id
        state = RecipeDetailState(recipeId: id)
    }
    
    var body: some View {
        ScrollView {
            Text(state.recipe?.title ?? "")
                .font(.headline)
            KFImage(URL(string: "https://m.media-amazon.com/images/I/413qxEF0QPL._AC_.jpg"))
                .resizable()
                .frame(width: 180, height: 150)
                .padding(.trailing)
            
            Text("Ingredients")
                .font(.subheadline)
            LazyVStack {
                ForEach(state.recipe?.ingredients ?? [], id: \.id, content: { ingredient in
                    HStack {
                        Text(ingredient.name)
                            .font(.body)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        HStack {
                            Text("\(ingredient.amount, specifier: "%.1f")")
                            Text(ingredient.metric)
                                .font(.body)
                        }
                    }
                    
                })
            }
            .padding()
            
            Text("Instructions")
                .font(.subheadline)
            LazyVStack {
                ForEach(state.recipe?.instructions ?? [], id: \.id, content: { instruction in
                    HStack {
                        Text("\(instruction.order). ")
                            .font(.body)
                        Text(instruction.description_)
                            .font(.body)
                            .frame(maxWidth: .infinity, alignment: .leading)
                    }
                    .padding()
                    
                })
            }
        }
        
    }
}
