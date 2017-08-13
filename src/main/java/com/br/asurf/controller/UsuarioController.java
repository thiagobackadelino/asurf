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

import com.br.asurf.model.Sexo;
import com.br.asurf.model.Usuario;
import com.br.asurf.repository.Usuarios;
 


@Controller
public class UsuarioController {
	
	@Autowired
	private Usuarios usuarios;

	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public ModelAndView novo(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("ListaUsuarios");
		modelAndView.addObject("usuarios", usuarios.findAll());
		modelAndView.addObject("sexos", Arrays.asList(Sexo.values()));
		modelAndView.addObject(new Usuario());
		return modelAndView;
	}
	
	public ModelAndView refreshview(@Valid Usuario usuario , BindingResult result ,
			RedirectAttributes attributes){
		
		ModelAndView modelAndView = new ModelAndView("ListaUsuarios");
		modelAndView.addObject("usuarios", usuarios.findAll());
		modelAndView.addObject("sexos", Arrays.asList(Sexo.values()));
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/usuarios", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result ,
			RedirectAttributes attributes) {
		if(result.hasErrors()){
			return refreshview(usuario ,result, attributes);
		}
		else{
		this.usuarios.save(usuario);
		ModelAndView modelAndView = new ModelAndView("redirect:/usuarios");
		attributes.addFlashAttribute("mensagem","Usuario salvo com sucesso");
		return modelAndView;
		}
		
	}
	
    @GetMapping("/usuario/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
    	ModelAndView modelAndView = new ModelAndView("ListaUsuarios"); 
    	modelAndView.addObject( this.usuarios.findOne(id));
    	modelAndView.addObject("sexos", Arrays.asList(Sexo.values()));
		modelAndView.addObject("usuarios", usuarios.findAll()); 
        return modelAndView;
    }
    
    @GetMapping("/usuario/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id,
			RedirectAttributes attributes) {
    	this.usuarios.delete(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/usuarios"); 
		attributes.addFlashAttribute("mensagem","Usuario excluido com sucesso");
        return modelAndView;
    }
    
 
	
}
