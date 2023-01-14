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
    
    init(id: Int64?, isPresented: Binding<Bool>, uploadSuccess: Binding<Bool> ) {
        self.recipeId = id?.toKotlinLong()
        state = RecipeDetailState(recipeId: id?.toKotlinLong(), isPresented: isPresented, uploadSuccess: uploadSuccess)
    }
    
    var body: some View {
        ScrollView {
            if (state.recipe?.images.isEmpty == false){
                KFImage(URL(string: state.recipe?.images.first?.image ?? ""))
                    .resizable()
                    .placeholder { Image("no_pizza").resizable() }
                    .frame(width: 180, height: 180)
            } else {
                RecipePlaceholderView(image: $state.image).padding(16)
            }
            
            if (recipeId != nil) {
                Text(state.recipe?.title ?? "")
                    .font(.headline)
                    .padding()
            } else {
                TextField("What is the name of this dish?", text: $state.title)
                    .multilineTextAlignment(.center)
                    .padding()
            }
            
            Text("Ingredients")
                .font(.subheadline)
            if (recipeId != nil) {
                Ingredients(ingredients: state.recipe?.ingredients)
            } else {
                EditIngredients(ingredients: $state.ingredients)
            }
            
            Text("Instructions")
                .font(.subheadline)
            if (recipeId != nil) {
                Instructions(instructions: state.recipe?.instructions)
            } else {
                EditInstructions(instructions: $state.instructions)
            }
            
            if (recipeId == nil) {
                Button("Save recipe") {
                    state.saveRecipe()
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
    
    @Binding var ingredients: [Ingredient]
    
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
        
        AddButton {
            ingredients.append(Ingredient(id: 0, name: name, amount: amount, metric: metric))
            name = ""
            amountString = ""
            metric = ""
        }.disabled(!isValid)
            .padding()
    }
}

struct EditInstructions: View {
    @Binding var instructions: [Instruction]
    
    @State private var description: String = ""
    
    private var isValid: Bool {
        return description != ""
    }
    
    var body: some View {
        
        Instructions(instructions: instructions)
        
        HStack {
            Text ("\(instructions.count  + 1). ")
            TextField("Description", text: $description)
                .frame(maxWidth: .infinity, alignment: .leading)
        }
        .font(.body)
        
        AddButton {
            instructions.append(Instruction(id: 0, order: Int32(instructions.count + 1), description: description))
            description = ""
        }.disabled(!isValid)
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
