package com.br.asurf.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.asurf.model.Role;
import com.br.asurf.repository.Roles; 
 


@Controller
public class RoleController {
	
	@Autowired
	private Roles roles;

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ModelAndView novo(Role role) {
		ModelAndView modelAndView = new ModelAndView("ListaRoles");
		modelAndView.addObject("roles", roles.findAll()); 
		modelAndView.addObject(new Role());
		return modelAndView;
	}
	
	public ModelAndView refreshview(@Valid Role role , BindingResult result ,
			RedirectAttributes attributes){
		
		ModelAndView modelAndView = new ModelAndView("ListaRoles");
		modelAndView.addObject("roles", roles.findAll()); 
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/roles", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Role role, BindingResult result ,
			RedirectAttributes attributes) {
		if(result.hasErrors()){
			return refreshview(role ,result, attributes);
		}
		else{
		this.roles.save(role);
		ModelAndView modelAndView = new ModelAndView("redirect:/roles");
		attributes.addFlashAttribute("mensagem","Role salvo com sucesso");
		return modelAndView;
		}
		
	}
	
    @GetMapping("/role/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
    	ModelAndView modelAndView = new ModelAndView("ListaRoles"); 
    	modelAndView.addObject( this.roles.findOne(id)); 
		modelAndView.addObject("usuarios", roles.findAll()); 
        return modelAndView;
    }
    
    @GetMapping("/role/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id,
			RedirectAttributes attributes) {
    	this.roles.delete(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/roles"); 
		attributes.addFlashAttribute("mensagem","Role excluida com sucesso");
        return modelAndView;
    }
    
 
	
}
