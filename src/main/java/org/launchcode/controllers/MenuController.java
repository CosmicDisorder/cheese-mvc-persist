package org.launchcode.controllers;

import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private MenuDao menuDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Menus");

        return "menu/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model) {

        model.addAttribute(new Menu());
        model.addAttribute("title", "Add Menu");

        return "menu/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String processAddMenuForm(Model model,
                                     @ModelAttribute @Valid Menu Menu,
                                     Errors errors) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        menuDao.save(Menu);
        return "redirect:";
    }

    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    public String viewMenu(@PathVariable int menuId, Model model) {
        Menu menu = menuDao.findOne(menuId);

        model.addAttribute("title", menu.getName());
        model.addAttribute("menu", menu);

        return "menu/view/" + menuId;
    }


}
