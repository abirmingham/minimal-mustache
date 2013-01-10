package com.github.infinitesummer.minimalmustache.util;

import com.github.mustachejava.TemplateFunction;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum ViewEnum {

    HOME("home/home-main", "sessioned"),
    ;

    private final String template;
    private final String decorator;
    private final String javascriptModule;
    private final List<String> errors;

    ViewEnum(String template, String decorator) {
        this.template         = template;
        this.decorator        = decorator;
        this.javascriptModule = null;
        errors = new ArrayList<>();
    }
    ViewEnum(String template, String decorator, String javascriptModule) {
        this.template         = template;
        this.decorator        = decorator;
        this.javascriptModule = javascriptModule;
        errors = new ArrayList<>();
    }


    public ViewEnum withError(String error) {
        errors.add(error);
        return this;
    }

    public ModelAndView toModelAndView() {
        return toModelAndView(new ModelMap());
    }
    public ModelAndView toModelAndView(Map model) {
        ModelAndView mv = new ModelAndView();

        // Namespace all attributes with __ __ in order to avoid conflicts with
        // attributes which are less magically added to the model
        mv.addObject("__errors__", errors);

        if (javascriptModule != null) {
            // Add special model attribute - a require.js module defined for booting up a specific page
            mv.addObject("__js-bootup-module__", javascriptModule);
        }

        // Add special model attribute - a yield function for embedding decorated view's partial,
        // TemplateFunction used so that resulting string's mustache tags will still be processed
        mv.addObject("__yield__", new TemplateFunction() {
            @Override public String apply(@Nullable String s) {
                return "{{> " + template + "}}";
            }
        });

        // Overwrite view name with decorator and return
        mv.setViewName("decoration/" + decorator + "-decorator");

        return mv;
    }
}
