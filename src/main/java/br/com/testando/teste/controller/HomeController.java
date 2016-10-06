package br.com.testando.teste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.testando.teste.support.BaseController;

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {

	@RequestMapping("/form")
	public ModelAndView form(Model model) {
		return new ModelAndView("/home/form");
	}

}