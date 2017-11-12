package com.br.asurf.controller;


import java.util.Arrays;

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

import com.br.asurf.model.Dificuldade;
import com.br.asurf.model.Praia;
import com.br.asurf.model.Sexo;
import com.br.asurf.repository.Modalidades;
import com.br.asurf.repository.Praias; 
 


@Controller
public class PraiaController {
	
	@Autowired
	private Praias praias;
	
	@Autowired
	private Modalidades modalidades;

	@RequestMapping(value = "/praias", method = RequestMethod.GET)
	public ModelAndView novo(Praia praia) {
		ModelAndView modelAndView = new ModelAndView("ListaPraias");
		modelAndView.addObject("praias", praias.findAll()); 
		modelAndView.addObject("dificuldades", Arrays.asList(Dificuldade.values()));
		modelAndView.addObject(new Praia());
		return modelAndView;
	}
	
	public ModelAndView refreshview(@Valid Praia praia , BindingResult result ,
			RedirectAttributes attributes){
		
		ModelAndView modelAndView = new ModelAndView("ListaPraias");
		modelAndView.addObject("praias", praias.findAll());
		modelAndView.addObject("dificuldades", Arrays.asList(Dificuldade.values()));
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
	
	
	@RequestMapping(value = "/associarpraiamodalidade", method = RequestMethod.POST)
	public ModelAndView associarpraiamodalidade(@Valid Praia praia, BindingResult result ,
			RedirectAttributes attributes) {

		Praia assPraia = 	praias.findOne(praia.getId());
		assPraia.setModalidades(praia.getModalidades());
		this.praias.save(assPraia);
		ModelAndView modelAndView = new ModelAndView("redirect:/praias");
		attributes.addFlashAttribute("mensagem","Praia salvo com sucesso");
		return modelAndView;
		
		
	}
	
    @GetMapping("/praia/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
    	ModelAndView modelAndView = new ModelAndView("ListaPraias"); 
    	modelAndView.addObject( this.praias.findOne(id)); 
		modelAndView.addObject("praias", praias.findAll()); 
		modelAndView.addObject("dificuldades", Arrays.asList(Dificuldade.values()));
        return modelAndView;
    }
	
	
    @GetMapping("/praia/associarmodalidade/{id}")
    public ModelAndView associarmodalidade(@PathVariable("id") Long id) {
    	ModelAndView modelAndView = new ModelAndView("AssociarPraiaModalidade"); 
    	modelAndView.addObject( this.praias.findOne(id)); 
		modelAndView.addObject("praias", praias.findAll()); 
		modelAndView.addObject("modalidades",modalidades.findAll());
        return modelAndView;
    }
    
    @GetMapping("/praia/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id,
			RedirectAttributes attributes) {
    	if(this.praias.findOne(id).getEventos().size() >= 1) {
    		ModelAndView modelAndView = new ModelAndView("redirect:/praias"); 
    		attributes.addFlashAttribute("mensagem","Praia não excluida, Eventos associoados!");
            return modelAndView;
    		
    	}else {
    		this.praias.delete(id);
    		ModelAndView modelAndView = new ModelAndView("redirect:/praias"); 
    		attributes.addFlashAttribute("mensagem","Praia excluida com sucesso");
            return modelAndView;
    	}
    	
    }
    
 
	
}
