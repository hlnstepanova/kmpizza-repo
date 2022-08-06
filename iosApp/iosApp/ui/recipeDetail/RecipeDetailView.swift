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
    
    @Environment(\.presentationMode) var presentationMode
    
    let recipeId: KotlinLong?
    @ObservedObject var state: RecipeDetailState
    
    init(id: KotlinLong?) {
        self.recipeId = id
        state = RecipeDetailState(recipeId: id)
    }
    
    var body: some View {
        ScrollView {
            KFImage(URL(string: "https://m.media-amazon.com/images/I/413qxEF0QPL._AC_.jpg"))
                .resizable()
                .frame(width: 200, height: 150)
            
            if (recipeId != nil) {
                Text(state.recipe?.title ?? "")
                    .font(.headline)
                    .padding()
            } else {
                TextField("What is the name of this dish?", text: Binding(
                    get: { state.recipe?.title ?? "" },
                    set: { state.viewModel.onTitleChanged(title: $0) }
                ))
                .multilineTextAlignment(.center)
                .padding()
            }
            
            Text("Ingredients")
                .font(.subheadline)
            if (recipeId != nil) {
                
                Ingredients(ingredients: state.recipe?.ingredients)
            } else {
                EditIngredients(ingredients: state.recipe?.ingredients, viewModel: state.viewModel )
            }
            
            Text("Instructions")
                .font(.subheadline)
            if (recipeId != nil) {
                Instructions(instructions: state.recipe?.instructions)
            } else {
                EditInstructions(instructions: state.recipe?.instructions, viewModel: state.viewModel )
            }
            
            if (recipeId == nil) {
                Button("Save recipe") {
                    state.saveRecipe()
                    presentationMode.wrappedValue.dismiss()
                }
                .buttonStyle(FilledButtonStyle())
                .padding()
            }
            
        }.padding()
      
    }
}

struct AddButton: View {
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            Image(systemName: "plus.circle.fill")
                .resizable()
                .frame(width: 30, height: 30)
                .foregroundColor(Color.accentColor)
                .shadow(color: .gray, radius: 0.2, x: 1, y: 1)
        }.padding()
    }
}

struct Ingredients: View {
    
    var ingredients: [Ingredient]?
    
    var body: some View {
        LazyVStack {
            ForEach(ingredients ?? [], id: \.self, content: { ingredient in
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
    }
}

struct Instructions: View {
    
    var instructions: [Instruction]?
    
    var body: some View {
        LazyVStack {
            ForEach(instructions ?? [], id: \.order, content: { instruction in
                HStack {
                    Text("\(instruction.order). ")
                        .font(.body)
                    Text(instruction.description_)
                        .font(.body)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
            })
        }
    }
}

struct EditIngredients: View {
    
    var ingredients: [Ingredient]?
    var viewModel: RecipeDetailsViewModel
    
    @State private var name: String = ""
    @State private var amountString: String = ""
    @State private var metric: String = ""
    
    private let formatter = NumberFormatter()
    
    private var amount: Double {
        return formatter.number(from: amountString)?.doubleValue ?? 0
    }
    
    private var isValid: Bool {
        return name != "" && amount > 0 && metric != ""
    }
    
    
    var body: some View {
        Ingredients(ingredients: ingredients)
        
        HStack {
            TextField("Ingredient", text: $name)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            HStack {
                TextField("Amount", text: $amountString)
                TextField("Metric", text: $metric)
            }
        }
        .font(.body)
        
        AddButton(action: {
            viewModel.onIngredientsChanged(ingredient: Ingredient(id: nil, name: name, amount: amount, metric: metric))
            name = ""
            amountString = ""
            metric = ""
        })
        .padding()
    }
}

struct EditInstructions: View {
    
    var instructions: [Instruction]?
    var viewModel: RecipeDetailsViewModel
    
    @State private var description: String = ""
    
    
    var body: some View {
        
        Instructions(instructions: instructions)
        
        HStack {
            Text ("\((instructions?.count ?? 0) + 1). ")
                .font(.body)
            TextField("Description", text: $description)
                .font(.body)
                .frame(maxWidth: .infinity, alignment: .leading)
            
        }
        
        
        AddButton(action: {
            viewModel.onInstructionsChanged(instruction: Instruction(id: nil, order: Int32((instructions?.count ?? 0) + 1), description: description))
            description = ""
        })
        
        .padding()
    }
}

struct FilledButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration
            .label
            .font(.title3)
            .foregroundColor(.white)
            .padding(.horizontal)
            .padding(.vertical, 12)
            .frame(maxWidth: .infinity)
            .background(configuration.isPressed ? Color.blue.opacity(0.3) : Color.blue)
            .cornerRadius(25)
    }
}
