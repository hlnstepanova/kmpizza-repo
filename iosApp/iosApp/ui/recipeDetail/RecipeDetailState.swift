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
    @Published private(set) var upload: KotlinBoolean? = nil
    
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
            let _ =  print("Upload result: \(upload)")
            self.upload = upload
            if ((self.upload?.boolValue ?? false) == true){
                let _ =  print("Here")
                self.uploadSuccess = true
                self.isPresented = false
            }
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}

