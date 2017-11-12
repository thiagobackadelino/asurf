package com.br.asurf.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	@RequestMapping(value = "/eventos/{id}", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Evento evento, BindingResult result ,@PathVariable("id") String id,
			RedirectAttributes attributes) throws ParseException {
		if(result.hasErrors()){
			return refreshview(evento ,result, attributes);
		}
		else{
			
		//evento.setEnd(evento.getStart());

		String datX = id.replaceAll("-","/");
			
		String startDay,startMonth,startYear,dataStart;
		String endDay,endMonth,endYear,dataEnd;
		
		
		
		Date hoje  = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		String dataH = sdf.format(hoje); 
		
		  try {

	            Date dateH = sdf.parse(dataH);
	            Date dataStartSel = sdf.parse(datX);
	            Date dataEndSel = sdf.parse(datX);
	            
	           if(dataStartSel.before(dateH) || dataEndSel.before(dateH)){
	       		attributes.addFlashAttribute("mensagem","Atenção selecionar dia maior que o dia atual.");
	           }else{
	       		
	        	
	       		startDay = datX.substring(0,2);
	       		startMonth =  datX.substring(3,5);
	       		startYear =  datX.substring(6,10);
	       		dataStart = startYear+"/"+startMonth+"/"+startDay;
	       		evento.setStart(dataStart+' '+evento.getStart());
	       		
	       		endDay = datX.substring(0,2);
	       		endMonth =  datX.substring(3,5);
	       		endYear =  datX.substring(6,10);
	       		dataEnd = endYear+"/"+endMonth+"/"+endDay;
	       		evento.setEnd(dataEnd+' '+evento.getEnd());
	       		

	       		
	       		
	       		evento.setUrl("participar/"+evento.getTitle());
	       		
	       		
	       		this.eventos.save(evento); 
	       		attributes.addFlashAttribute("mensagem","Evento salvo com sucesso");
 
	           }

	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
     		ModelAndView modelAndView = new ModelAndView("redirect:/montaAgenda"); 
     		return modelAndView;
		}
		
	}
	
    @GetMapping("/evento/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
    	ModelAndView modelAndView = new ModelAndView("ListaEventos"); 
    	
    	Evento ev = this.eventos.findOne(id);
    	
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
    	
    	modelAndView.addObject( ev); 
		modelAndView.addObject("eventos", eventos.findAll()); 
		modelAndView.addObject("modalidades", modalidades.findAll()); 
		modelAndView.addObject("praias", praias.findAll()); 
        return modelAndView;
    }
    
    @GetMapping("/evento/cancelar/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id,
			RedirectAttributes attributes) {
    	Evento ev = this.eventos.findOne(id);
    	ev.setAtivo(false);
    	this.eventos.save(ev);
		ModelAndView modelAndView = new ModelAndView("redirect:/eventos"); 
		attributes.addFlashAttribute("mensagem","Evento excluida com sucesso");
        return modelAndView;
    }
    
    
    @GetMapping("/evento/participantes/{id}")
    public ModelAndView participantes(@PathVariable("id") Long id) {
    	ModelAndView modelAndView = new ModelAndView("Participantes"); 
    	
    	Evento ev = this.eventos.findOne(id);
    	modelAndView.addObject("nome", ev.getTitle()); 
		modelAndView.addObject("participantes", ev.getUsuarios()); 
        return modelAndView;
    }
    
    
    
    @GetMapping("/evento/agendaEditar/{id}")
    public ModelAndView agendaEditar(@PathVariable("id") String id) {
    	ModelAndView modelAndView = new ModelAndView("ListaEventos"); 
    	
    	String startDay,startMonth,startYear,dataStart;
    	String datX = id.replaceAll("-","/");
    	
    	
    	startDay = id.substring(8,10);
		startMonth =  id.substring(5,7);
		startYear =  id.substring(0,4);
		dataStart = startDay+"/"+startMonth+"/"+startYear;
    	
	 
    	Evento ev = new Evento();
    	//ev.setStart(dataStart);
    	//ev.setEnd(dataStart);
    	
    	modelAndView.addObject( ev);
    	modelAndView.addObject("diaSel", dataStart.replaceAll("/","-"));
		modelAndView.addObject("eventos", eventos.eventosDodia(datX)); 
		modelAndView.addObject("modalidades", modalidades.findAll()); 
		modelAndView.addObject("praias", praias.findAll()); 
        return modelAndView;
    }
 
	
}
