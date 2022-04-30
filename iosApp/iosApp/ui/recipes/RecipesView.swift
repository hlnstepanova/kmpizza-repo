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
            List(state.recipes, id: \.id) { recipe in
                    Text(recipe.title)
            }
            .listStyle(PlainListStyle())
    }
}

