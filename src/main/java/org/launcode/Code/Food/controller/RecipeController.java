package org.launcode.Code.Food.controller;
import org.launcode.Code.Food.models.Recipe;
import org.launcode.Code.Food.models.data.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("recipes",recipeRepository.findAll());
        return "recipe/index";
    }

    @GetMapping("add")
    public String displayAddRecipeForm(Model model) {
        model.addAttribute(new Recipe());
        return "recipe/add";
    }
    @PostMapping("add")
    public String processAddRecipeForm(@ModelAttribute @Valid Recipe newRecipe,
                                        Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "recipe/add";
        }
        recipeRepository.save(newRecipe);
        return "redirect:";
    }

    @GetMapping("view/{recipeId}")
    public String displayViewRecipe(Model model, @PathVariable int recipeId) {

        Optional<Recipe> optRecipe = recipeRepository.findById(recipeId);
        if (optRecipe.isPresent()) {
            Recipe recipe = (Recipe) optRecipe.get();
            model.addAttribute("recipe", recipe);
            return "recipe/view";
        } else {
            return "redirect:../";
        }
    }


    @GetMapping("delete")
    public String displayDeleteRecipeForm(Model model){
        model.addAttribute("title","Delete Recipe");
        model.addAttribute("recipes", recipeRepository.findAll());
        return "recipe/delete";
    }

    @PostMapping("delete")
    public String deleteRecipeListings(@RequestParam(required = false) int[] recipeIds){
        if(recipeIds!=null) {
            for (int id : recipeIds) {
                recipeRepository.deleteById(id);
            }
        }
        return "redirect:";
    }



}

