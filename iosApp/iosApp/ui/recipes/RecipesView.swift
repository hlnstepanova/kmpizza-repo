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
    @State var isRecipeDetailsShown: Bool = false
    @State var uploadSuccess: Bool = false
    
    init() {
        state = RecipesState()
    }
    
    var body: some View {
        ZStack(alignment: .bottomTrailing) {
            List(state.recipes, id: \.id) { recipe in
                NavigationLink(destination: RecipeDetailView (id: recipe.id.toKotlinLong(), isPresented: self.$isRecipeDetailsShown, uploadSuccess: self.$uploadSuccess)) {
                    RecipeView(item: recipe)
                }
            }
            .listStyle(PlainListStyle())
            FloatingActionButton(isPresented: self.$isRecipeDetailsShown, uploadSuccess: self.$uploadSuccess)
                .padding()
                .frame(alignment: .bottomTrailing)
        }.onAppear {
            if uploadSuccess {
                state.viewModel.getRecipes()
            }
        }
    }
}

struct FloatingActionButton: View {
    @Binding var isPresented: Bool
    @Binding var uploadSuccess: Bool
    
    var body: some View {
        NavigationLink(destination: RecipeDetailView (id: nil, isPresented: $isPresented, uploadSuccess: self.$uploadSuccess)) {
            Image(systemName: "plus.circle.fill")
                .resizable()
                .frame(width: 56, height: 56)
                .foregroundColor(Color.accentColor)
                .shadow(color: .gray, radius: 0.2, x: 1, y: 1)
        }
        .simultaneousGesture(
            TapGesture().onEnded {
                isPresented = true
            })
    }
}



