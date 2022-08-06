//
//  RecipesView.swift
//  iosApp
//
//  Created by Lena Stepanova on 30.04.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RecipesView: View {
    
    @ObservedObject var state: RecipesState
    
    init() {
        state = RecipesState()
    }
    
    var body: some View {
        ZStack(alignment: .bottomTrailing) {
            List(state.recipes, id: \.id) { recipe in
                NavigationLink(destination: RecipeDetailView (id: recipe.id.toKotlinLong())) {
                    RecipeView(item: recipe)
                    
                }
            }
            .listStyle(PlainListStyle())
            FloatingActionButton()
                .padding()
                .frame(alignment: .bottomTrailing)
        }
    }
}

struct FloatingActionButton: View {
    var body: some View {
        NavigationLink(destination: RecipeDetailView (id: nil)) {
            Image(systemName: "plus.circle.fill")
                .resizable()
                .frame(width: 56, height: 56)
                .foregroundColor(Color.accentColor)
                .shadow(color: .gray, radius: 0.2, x: 1, y: 1)
        }
    }
}


