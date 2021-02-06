package com.shijie.cmsshoppingcart.controllers;


import com.shijie.cmsshoppingcart.models.CategoryRepository;
import com.shijie.cmsshoppingcart.models.data.Category;
import com.shijie.cmsshoppingcart.models.data.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/categories")
public class AdminCategoriesController {

    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping
    public String index(Model model){
        List<Category> categories = categoryRepo.findAll();

        model.addAttribute("categories", categories);

        return "admin/categories/index";


    }

    @GetMapping("/add")
    public String add(Category category){

        return "admin/categories/add";
    }
    // or we can use another method to set up category model:
//    @ModelAttribute("category")
//    public Category getCategory(){
//        return new Category();
//    }


    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            return "admin/categories/add";
        }

        redirectAttributes.addFlashAttribute("message", "Category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = category.getName().toLowerCase().replace(" ", "-");

        Category categoryExists = categoryRepo.findByName(category.getName());

        if(categoryExists != null){
            redirectAttributes.addFlashAttribute("message", "Name Exists, Choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("categoryInfo", category);
        } else{
            category.setSlug(slug);
            category.setSorting(100);
            categoryRepo.save(category);

        }

        return "redirect:/admin/categories/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){

        Category category = categoryRepo.getOne(id);
        model.addAttribute("category", category);

        return "/admin/categories/edit";
    }




}
