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
    
    @Binding var isPresented: Bool
    @Binding var uploadSuccess: Bool
    
    @Published private(set) var recipe: RecipeUiModel? = nil

    @Published var title: String = "" {
        didSet {
            viewModel.onTitleChanged(title: title)
        }
    }
    
    @Published var ingredients: [Ingredient] = [] {
        didSet {
            if let ingredient = ingredients.last {
            viewModel.onIngredientsChanged(ingredient: ingredient)
            }
        }
    }
    
    @Published var instructions: [Instruction] = [] {
        didSet {
            if let instruction = instructions.last {
            viewModel.onInstructionsChanged(instruction: instruction)
            }
        }
    }
    
    @Published var image: UIImage? = nil {
        didSet {
            viewModel.onImageChanged(image: image ?? UIImage())
        }
    }
    
    init(recipeId: KotlinLong?, isPresented: Binding<Bool>, uploadSuccess: Binding<Bool>) {
        self.recipeId = recipeId
        self._isPresented = isPresented
        self._uploadSuccess = uploadSuccess
        
        viewModel = RecipeDetailsViewModel(id: recipeId)
        
        viewModel.observeRecipe { recipe in
            self.recipe = recipe
        }
    }
    
    func saveRecipe(){
        viewModel.saveRecipe()
        
        viewModel.observeUpload { upload in
            if ((upload?.boolValue ?? false) == true){
                self.uploadSuccess = true
                self.isPresented = false
            }
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}

