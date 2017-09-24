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
		
		List<Evento> eventosCL =  eventos.findAll();
		
		for(int i = 0 ;i <= (eventosCL.size() - 1); i++){
			
			Evento.add(new Evento(eventosCL.get(i).getTitle(),eventosCL.get(i).getStart(),eventosCL.get(i).getEnd(),eventosCL.get(i).getUrl()));
			
		}
						
		return Evento;
		
	}
	
}