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

import com.br.asurf.model.Evento;
import com.br.asurf.model.Modalidade;
import com.br.asurf.model.Praia;
import com.br.asurf.repository.Eventos;
import com.br.asurf.repository.Modalidades;
import com.br.asurf.repository.Praias; 
 


@Controller
public class EventoController {
	
	@Autowired
	private Eventos eventos;
	@Autowired
	private Modalidades modalidades;
	@Autowired
	private Praias praias;

	@RequestMapping(value = "/eventos", method = RequestMethod.GET)
	public ModelAndView novo(Evento role) {
		ModelAndView modelAndView = new ModelAndView("ListaEventos");
		modelAndView.addObject("eventos", eventos.findAll()); 
		modelAndView.addObject("modalidades", modalidades.findAll()); 
		modelAndView.addObject("praias", praias.findAll()); 
		modelAndView.addObject(new Evento());
		modelAndView.addObject(new Modalidade());
		modelAndView.addObject(new Praia());
		return modelAndView;
	}
	
	public ModelAndView refreshview(@Valid Evento role , BindingResult result ,
			RedirectAttributes attributes){
		
		ModelAndView modelAndView = new ModelAndView("ListaEventos");
		modelAndView.addObject("eventos", eventos.findAll()); 
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/eventos", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Evento evento, BindingResult result ,
			RedirectAttributes attributes) {
		if(result.hasErrors()){
			return refreshview(evento ,result, attributes);
		}
		else{
		this.eventos.save(evento);
		ModelAndView modelAndView = new ModelAndView("redirect:/eventos");
		attributes.addFlashAttribute("mensagem","Evento salvo com sucesso");
		return modelAndView;
		}
		
	}
	
    @GetMapping("/evento/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
    	ModelAndView modelAndView = new ModelAndView("ListaEventos"); 
    	modelAndView.addObject( this.eventos.findOne(id)); 
		modelAndView.addObject("eventos", eventos.findAll()); 
		modelAndView.addObject("modalidades", modalidades.findAll()); 
		modelAndView.addObject("praias", praias.findAll()); 
        return modelAndView;
    }
    
    @GetMapping("/evento/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id,
			RedirectAttributes attributes) {
    	this.eventos.delete(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/eventos"); 
		attributes.addFlashAttribute("mensagem","Evento excluida com sucesso");
        return modelAndView;
    }
    
 
	
}
