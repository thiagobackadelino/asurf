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

import com.br.asurf.model.Modalidade;
import com.br.asurf.repository.Modalidades;
 


@Controller
public class ModalidadeController {
	
	@Autowired
	private Modalidades modalidades;

	@RequestMapping(value = "/modalidades", method = RequestMethod.GET)
	public ModelAndView novo(Modalidade modalidade) {
		ModelAndView modelAndView = new ModelAndView("ListaModalidades");
		modelAndView.addObject("modalidades", modalidades.findAll()); 
		modelAndView.addObject(new Modalidade());
		return modelAndView;
	}
	
	public ModelAndView refreshview(@Valid Modalidade modalidade , BindingResult result ,
			RedirectAttributes attributes){
		
		ModelAndView modelAndView = new ModelAndView("ListaModalidades");
		modelAndView.addObject("modalidades", modalidades.findAll()); 
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/modalidades", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Modalidade modalidade, BindingResult result ,
			RedirectAttributes attributes) {
		if(result.hasErrors()){
			return refreshview(modalidade ,result, attributes);
		}
		else{
		this.modalidades.save(modalidade);
		ModelAndView modelAndView = new ModelAndView("redirect:/modalidades");
		attributes.addFlashAttribute("mensagem","Modalidade salvo com sucesso");
		return modelAndView;
		}
		
	}
	
    @GetMapping("/modalidade/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
    	ModelAndView modelAndView = new ModelAndView("ListaModalidades"); 
    	modelAndView.addObject( this.modalidades.findOne(id)); 
		modelAndView.addObject("modalidades", modalidades.findAll()); 
        return modelAndView;
    }
    
    @GetMapping("/modalidade/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id,
			RedirectAttributes attributes) {
    	try{
    		this.modalidades.delete(id);
    		attributes.addFlashAttribute("mensagem","Modalidade excluido com sucesso");
    	}
    	catch(Exception e){
    		attributes.addFlashAttribute("mensagem","Modalidade Não pode ser excluida pois contem eventos relacionados");
    	}
		ModelAndView modelAndView = new ModelAndView("redirect:/modalidades"); 
		
        return modelAndView;
    }
    
 
	
}
