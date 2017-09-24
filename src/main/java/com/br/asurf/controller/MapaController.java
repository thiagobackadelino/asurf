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
import com.br.asurf.model.Praia;
import com.br.asurf.repository.Eventos;
import com.br.asurf.repository.Modalidades;
import com.br.asurf.repository.Praias;



@Controller
public class MapaController {

	
	@Autowired
	private Praias praias;

	
	@RequestMapping(value = "/montaMapa", method = RequestMethod.GET)
	public ModelAndView MontaAgenda() {
		 
		ModelAndView mv = new ModelAndView("Mapa");
		
		return mv;
	}
	
	@RequestMapping(value = "/mapas.json", method = RequestMethod.GET)	
	public @ResponseBody List<Praia> GetPraia(){

		List<Praia> praia = new ArrayList();
		List<Praia> praiasCL =  praias.findAll();
		
		for(int i = 0 ;i <= (praiasCL.size() - 1); i++){
			
			Praia praiaB = new Praia();
			praiaB.setDescricao(praiasCL.get(i).getDescricao());
			praiaB.setNome(praiasCL.get(i).getNome());
			praiaB.setLatitude(praiasCL.get(i).getLatitude());
			praiaB.setLongitude(praiasCL.get(i).getLongitude());
			praia.add(praiaB);
		}
						
		return praia;
		
	}
	
}