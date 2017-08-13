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

import com.br.asurf.model.Praia;
import com.br.asurf.repository.Praias; 
 


@Controller
public class PraiaController {
	
	@Autowired
	private Praias praias;

	@RequestMapping(value = "/praias", method = RequestMethod.GET)
	public ModelAndView novo(Praia praia) {
		ModelAndView modelAndView = new ModelAndView("ListaPraias");
		modelAndView.addObject("praias", praias.findAll()); 
		modelAndView.addObject(new Praia());
		return modelAndView;
	}
	
	public ModelAndView refreshview(@Valid Praia praia , BindingResult result ,
			RedirectAttributes attributes){
		
		ModelAndView modelAndView = new ModelAndView("ListaPraias");
		modelAndView.addObject("praias", praias.findAll()); 
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/praias", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Praia praia, BindingResult result ,
			RedirectAttributes attributes) {
		if(result.hasErrors()){
			return refreshview(praia ,result, attributes);
		}
		else{
		this.praias.save(praia);
		ModelAndView modelAndView = new ModelAndView("redirect:/praias");
		attributes.addFlashAttribute("mensagem","Praia salvo com sucesso");
		return modelAndView;
		}
		
	}
	
    @GetMapping("/praia/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
    	ModelAndView modelAndView = new ModelAndView("ListaPraias"); 
    	modelAndView.addObject( this.praias.findOne(id)); 
		modelAndView.addObject("usuarios", praias.findAll()); 
        return modelAndView;
    }
    
    @GetMapping("/praia/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id,
			RedirectAttributes attributes) {
    	this.praias.delete(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/praias"); 
		attributes.addFlashAttribute("mensagem","Praia excluida com sucesso");
        return modelAndView;
    }
    
 
	
}
