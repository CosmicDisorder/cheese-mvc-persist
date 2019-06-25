package org.launchcode.controllers;


import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Categories");
        model.addAttribute("cheeses", cheeseDao.findAll());

        return "category/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCategoryForm(Model model) {

        model.addAttribute(new Category());
        model.addAttribute("title", "Add Category");

        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCategoryForm(Model model,
                                         @ModelAttribute @Valid Category Category,
                                         Errors errors) {
        if(errors.hasErrors()){
            model.addAttribute("title", "Add Category");
            return "category/add";
        }

        categoryDao.save(Category);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCategoryForm(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "category/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCategoryForm(@RequestParam int[] categoryIds) {

        for(int plzDelet : categoryIds){
            categoryDao.delete(plzDelet);
        }
        for(Cheese aCheese : cheeseDao.findAll()) {
            if (aCheese.getCategory() == null) {
                cheeseDao.delete(aCheese);
            }
        }

        return "redirect:";
    }

    @RequestMapping(value = "view/{categoryId}", method = RequestMethod.GET)
    public String viewCategory(@PathVariable int categoryId, Model model) {
        Category category = categoryDao.findOne(categoryId);
        ArrayList<Cheese> cheeseList = new ArrayList<>();
        for(Cheese cheese : cheeseDao.findAll()) {
            if(cheese.getCategory()==category) {
                cheeseList.add(cheese);
            }
        }

        model.addAttribute("title", category.getName());
        model.addAttribute("cheeses", cheeseList);

        return "category/view";
    }

}