package com.br.asurf.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.asurf.model.Evento;
import com.br.asurf.model.Role;
import com.br.asurf.model.Sexo;
import com.br.asurf.model.Usuario;
import com.br.asurf.repository.Roles;
import com.br.asurf.repository.UsuariosRep;
 


@Controller
public class UsuarioController {
	
	@Autowired
	private UsuariosRep usuarios;
	@Autowired
	private Roles roles;

	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public ModelAndView novo(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("ListaUsuarios");
		modelAndView.addObject("usuarios", usuarios.findAll());
		modelAndView.addObject("sexos", Arrays.asList(Sexo.values()));
		modelAndView.addObject("roles", roles.findAll());
		modelAndView.addObject(new Usuario());
		return modelAndView;
	}
	
	@RequestMapping(value = "/eventosPart", method = RequestMethod.GET)
	public ModelAndView eventosPart(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("EventosPart");
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        Usuario user = usuarios.getUsuario(name);
        
        for(int i = 0; i < user.getEventos().size();i++ ){
        	
        	Evento evxp = user.getEventos().get(i);
        	
    		String startDay,startMonth,startYear,dataStart;
    		String endDay,endMonth,endYear,dataEnd;
    		
    		startDay = evxp.getStart().substring(8,10);
    		startMonth =  evxp.getStart().substring(5,7);
    		startYear =  evxp.getStart().substring(0,4);
    		dataStart = startDay+"/"+startMonth+"/"+startYear;
    		evxp.setStart(dataStart);
    		
    		user.getEventos().get(i).setStart(evxp.getStart());
    		
    		endDay = evxp.getEnd().substring(8,10);
    		endMonth =  evxp.getEnd().substring(5,7);
    		endYear =  evxp.getEnd().substring(0,4);
    		dataEnd = endDay+"/"+endMonth+"/"+endYear;
    		evxp.setEnd(dataEnd);
        	
    		user.getEventos().get(i).setEnd(evxp.getEnd());
        	
        }
        
        
        
        modelAndView.addObject("eventosPart", user.getEventos());
		modelAndView.addObject(user);
		return modelAndView;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("Notificacao");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        Usuario user = usuarios.getUsuario(name);
        
        List<Evento> eventos =  new ArrayList();
        
        for(int i = 0 ;i <= (user.getEventos().size() - 1 ); i++) {
        
        Evento ev = user.getEventos().get(i);
        
		String startDay,startMonth,startYear,dataStart;
		String endDay,endMonth,endYear,dataEnd;
		
		startDay = ev.getStart().substring(8,10);
		startMonth =  ev.getStart().substring(5,7);
		startYear =  ev.getStart().substring(0,4);
		dataStart = startDay+"/"+startMonth+"/"+startYear;
		ev.setStart(dataStart);
		
		endDay = ev.getEnd().substring(8,10);
		endMonth =  ev.getEnd().substring(5,7);
		endYear =  ev.getEnd().substring(0,4);
		dataEnd = endDay+"/"+endMonth+"/"+endYear;
		ev.setEnd(dataEnd);
		
		eventos.add(ev);
		
        }
        
        
        
        modelAndView.addObject("eventosPart", eventos);
		modelAndView.addObject(user);
		return modelAndView;
	}
	
	public ModelAndView refreshview(@Valid Usuario usuario , BindingResult result ,
			RedirectAttributes attributes){
		
		ModelAndView modelAndView = new ModelAndView("ListaUsuarios");
		modelAndView.addObject("usuarios", usuarios.findAll());
		modelAndView.addObject("sexos", Arrays.asList(Sexo.values()));
		modelAndView.addObject("roles", roles.findAll());
		return modelAndView;
		
	}
	
	public ModelAndView refreshviewCadastro(@Valid Usuario usuario , BindingResult result ,
			RedirectAttributes attributes){
		
		ModelAndView modelAndView = new ModelAndView("CadastroUsuario");
		modelAndView.addObject("usuarios", usuarios.findAll());
		modelAndView.addObject("sexos", Arrays.asList(Sexo.values()));
		modelAndView.addObject("roles", roles.findAll());
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/usuarios", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result ,
			RedirectAttributes attributes) {
		if(result.hasErrors()){
			return refreshview(usuario ,result, attributes);
		}
		else{
		if(usuario.getId() != null){
		List<Evento> ex = this.usuarios.findOne(usuario.getId()).getEventos();	
		usuario.setEventos(ex);
		}
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
    	modelAndView.addObject("roles", roles.findAll());
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
    
	@RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
	public ModelAndView cadastrar(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("CadastroUsuario");
		modelAndView.addObject("usuarios", usuarios.findAll());
		modelAndView.addObject("sexos", Arrays.asList(Sexo.values()));
		modelAndView.addObject("roles", roles.findAll());
		modelAndView.addObject(new Usuario());
		return modelAndView;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrarSalvar(@Valid Usuario usuario, BindingResult result ,
			RedirectAttributes attributes) {
		if(result.hasErrors()){
			return refreshviewCadastro(usuario ,result, attributes);
		}
		else{
		
		List<Role> roleCL = new ArrayList();
		roleCL.add(roles.findOne((long) 2));
		usuario.setRoles(roleCL);
		this.usuarios.save(usuario);
		ModelAndView modelAndView = new ModelAndView("redirect:/login");
		attributes.addFlashAttribute("mensagem","Usuario Cadastrado com sucesso");
		return modelAndView;
		}
		
	}
}
