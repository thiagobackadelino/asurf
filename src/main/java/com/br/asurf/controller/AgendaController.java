package com.br.asurf.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.br.asurf.model.Evento;
import com.br.asurf.repository.Eventos;
import com.br.asurf.repository.Modalidades;



@Controller
public class AgendaController {

	
	@Autowired
	private Eventos eventos;

	
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
		
		//Evento.addAll(eventos.findAll());
		
		List<Evento> eventosCL =  eventos.findAll();
		
		for(int i = 0 ;i <= (eventosCL.size() - 1); i++){
			
			Evento.add(new Evento(eventosCL.get(i).getTitle(),eventosCL.get(i).getStart(),eventosCL.get(i).getEnd(),eventosCL.get(i).getUrl()));
			
		}
				
//		/*ADICIONANDO OS Evento*/
//		Evento.add(new Evento("Estudar Linux",     anoAtual+"-"+mesAtual+"-02T12:00:00",anoAtual+"-"+mesAtual+"-02T13:30:00", null));
//		Evento.add(new Evento("Estudar Java", 	   anoAtual+"-"+mesAtual+"-02T14:00:00",anoAtual+"-"+mesAtual+"-02T14:30:00", null));
//		Evento.add(new Evento("Estudar C#", 	   anoAtual+"-"+mesAtual+"-02T15:00:00",anoAtual+"-"+mesAtual+"-02T15:30:00", null));
//		Evento.add(new Evento("Estudar SOA Suite", anoAtual+"-"+mesAtual+"-02T16:00:00",anoAtual+"-"+mesAtual+"-02T17:30:00", null));
//		Evento.add(new Evento("Estudar Jquery",    anoAtual+"-"+mesAtual+"-02T19:00:00",anoAtual+"-"+mesAtual+"-02T20:30:00", null));		
//		Evento.add(new Evento("Correr",     	   anoAtual+"-"+mesAtual+"-03T13:00:00",anoAtual+"-"+mesAtual+"-03T13:30:00", null));
//		Evento.add(new Evento("Reunião",	       anoAtual+"-"+mesAtual+"-05T12:00:00",anoAtual+"-"+mesAtual+"-05T13:30:00", null));		
//		Evento.add(new Evento("Dois dias de evento", anoAtual+"-"+mesAtual+"-07T12:00:00",anoAtual+"-"+mesAtual+"-08T12:00:00", null));
//		
//		Evento.add(new Evento("Publicar Artigo",   anoAtual+"-"+mesAtual+"-10T12:00:00",anoAtual+"-"+mesAtual+"-10T13:30:00", null));
//		Evento.add(new Evento("Reunião",	       anoAtual+"-"+mesAtual+"-10T15:00:00",anoAtual+"-"+mesAtual+"-10T18:30:00", null));		
//		
//		Evento.add(new Evento("Festa",  		   anoAtual+"-"+mesAtual+"-13T12:00:00",anoAtual+"-"+mesAtual+"-13T13:30:00", null));
//		Evento.add(new Evento("Festa 2",	       anoAtual+"-"+mesAtual+"-13T15:00:00",anoAtual+"-"+mesAtual+"-13T18:30:00", null));		
//		Evento.add(new Evento("Curso de Inglês",   anoAtual+"-"+mesAtual+"-15",null, null));				
//		Evento.add(new Evento("Blog Cícero",       anoAtual+"-"+mesAtual+"-23",null, "http://www.ciceroednilson.com.br"));
//				
		return Evento;
		
	}
	
}