//
//  RecipeDetailState.swift
//  iosApp
//
//  Created by Lena Stepanova on 17.07.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Foundation
import shared
 
class RecipeDetailState: ObservableObject{
    
    let recipeId: KotlinLong?
    let viewModel: RecipeDetailsViewModel
    
    @Published private(set) var recipe: RecipeUiModel? = nil
    @Published private(set) var upload: Bool? = nil
    
    init(recipeId: KotlinLong?) {
        self.recipeId = recipeId
        viewModel = RecipeDetailsViewModel(id: recipeId)
        
        viewModel.observeRecipe { recipe in
            self.recipe = recipe
        }
    }
    
    func saveRecipe(){
        viewModel.saveRecipe()
    }
    
    deinit {
        viewModel.dispose()
    }
}

