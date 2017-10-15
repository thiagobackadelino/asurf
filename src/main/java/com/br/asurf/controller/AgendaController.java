package com.br.asurf.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.asurf.model.Evento;
import com.br.asurf.model.Usuario;
import com.br.asurf.repository.Eventos;
import com.br.asurf.repository.Modalidades;
import com.br.asurf.repository.UsuariosRep;



@Controller
public class AgendaController {

	
	@Autowired
	private Eventos eventos;
	
	@Autowired
	private UsuariosRep usuarios;

	
	@RequestMapping(value = "/montaAgenda", method = RequestMethod.GET)
	public ModelAndView MontaAgenda() {
		 
		ModelAndView mv = new ModelAndView("Agenda");
		
		return mv;
	}
	
	@RequestMapping(value = "/getEventos.json", method = RequestMethod.GET)	
	public @ResponseBody List<Evento> GetEventos(){

		List<Evento> Evento = new ArrayList();
				
		String mesAtual = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+ 1);
		
		if(mesAtual.length() <2)
			mesAtual = "0" + mesAtual;
		
		String anoAtual = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		
		List<Evento> eventosCL =  eventos.findAll();
		
		for(int i = 0 ;i <= (eventosCL.size() - 1); i++){
			
			Evento.add(new Evento(eventosCL.get(i).getTitle(),eventosCL.get(i).getStart(),eventosCL.get(i).getEnd(),eventosCL.get(i).getUrl()));
			
		}
						
		return Evento;
		
	}
	
	
    @GetMapping("/participar/{id}")
    public ModelAndView editar(@PathVariable("id") String evento) {
    	ModelAndView modelAndView = new ModelAndView("ParticiparEvento"); 
    	Evento ev = eventos.evento(evento);
    	
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
    	
    	
    	modelAndView.addObject(ev); 
    	modelAndView.addObject("praias", ev.getPraia()); 
		modelAndView.addObject("modalidades",ev.getModalidade());
        return modelAndView;
    }
    
    @RequestMapping(value = "/confirmarParticipacao", method = RequestMethod.POST)
    public ModelAndView confirmarParticipacao(Evento evento,RedirectAttributes attributes) {
    	
    	
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         String name = auth.getName(); //get logged in username
         Usuario user = usuarios.getUsuario(name);
         
         List<Evento> eventosAl = new ArrayList();
         
         eventosAl = eventos.findAll();
         
         
         
         List<Evento> eventossAnte = new ArrayList();
         
         eventossAnte.addAll(user.getEventos());
         //eventos.findOne(evento.getId());
         
         Evento evntp = eventos.findOne(evento.getId());
         
         

         
         if(!possuiEvento(eventossAnte,evento)){
        	 if(evntp.getUsuarios().size() <= 5){
                 eventossAnte.add(evntp);
                 user.setEventos(eventossAnte); 
                 this.usuarios.save(user);
                 attributes.addFlashAttribute("mensagem","Participação incluida com sucesso");
        		 
        	 }else{
        		 attributes.addFlashAttribute("mensagem","Vagas Não Disponiveis");
        	 }
         }else{
        	 attributes.addFlashAttribute("mensagem","Atenção você ja está participando desse evento!");
         }
         
         ModelAndView modelAndView = new ModelAndView("redirect:/eventosPart");
 		
 		return modelAndView;
    }
	
	public boolean possuiEvento(List<Evento>  eventossAnte,Evento evento){
        for(int i = 0;i < eventossAnte.size(); i++){
       	 if(eventossAnte.get(i).getId().equals(evento.getId())){
       		 return true;
       	 }
        }
		return false;
	}
	
	public boolean possuiVagaDisponivel(List<Evento>  eventosAl,Evento evento){
        for(int i = 0;i < eventosAl.size(); i++){
       	 if(eventosAl.get(i).getId().equals(evento.getId())){
       		 return true;
       	 }
        }
		return false;
	}
    
}