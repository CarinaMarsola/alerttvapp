package com.alerttvapp.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alerttvapp.models.Equipamento;
import com.alerttvapp.models.Sinal;
import com.alerttvapp.repository.EquipamentoRepository;

@Controller
public class EquipamentoController {
	
	@Autowired
	private EquipamentoRepository er; 
	
	@RequestMapping(value="/novoEquipamento", method=RequestMethod.GET)
	public ModelAndView novoEquipamento(){
		return new ModelAndView("equipamento/novoEquipamento");
	}

	@RequestMapping(value="/salvarEquipamento", method=RequestMethod.POST)
	public String salvarEquipamento(Equipamento equipamento){
		er.save(equipamento);
		return "redirect:/listarEquipamentos";
	}
	
	@RequestMapping("/listarEquipamentos")
	public ModelAndView listarEquipamento(){
		ModelAndView mv = new ModelAndView("equipamento/listaEquipamentos");
		Iterable<Equipamento> equipamentos = er.findAll();
		mv.addObject("equipamentos", equipamentos);
		return mv;
	}
	
	@RequestMapping("/deletarEquipamento")
	public String deletaEquipamento(String macAddress){
		Equipamento equipamento = er.findByMacAddress(macAddress);
		er.delete(equipamento);
		return "redirect:/listarEquipamentos";	
	}
	
	@RequestMapping(value="/editarEquipamento", method=RequestMethod.GET)
	public ModelAndView editarEquipamento(String macAddress){
		Equipamento equipamento = er.findByMacAddress(macAddress);
		ModelAndView mv = new ModelAndView("equipamento/editaEquipamento");
		mv.addObject("equipamento", equipamento);
		return mv;
	}
	
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() throws Exception{
		ModelAndView mv = new ModelAndView("equipamento/dashboard");
		mv.addObject("sinalEquipamento", this.sinalEquipamento());
		return mv;
	}
	

	public List<Sinal> sinalEquipamento() throws Exception{
		
		 List<Sinal> sinalEquipamento = new ArrayList(); 
		 Iterable<Equipamento> equipamentos = er.findAll();
		 for (Equipamento s : equipamentos) {
			 
			 
			 String json = this.getApi(s.getMacAddress());
			 
			 if (json != null){
				 
				 Sinal sinal = new Sinal();
				 String[] textoSeparado = json.split(",");
				 
				 double sinal1 = Double.parseDouble(textoSeparado[1].substring(9, 13)) * 100;
				 double sinal2= Double.parseDouble(textoSeparado[2].substring(9, 13)) * 100;
				 
				 sinal.setMacAddress(s.getMacAddress());
				 sinal.setCliente(s.getCliente());
				 sinal.setSinal1(sinal1 + "%");
				 sinal.setSinal2(sinal2 + "%");
				 	 
				 if((sinal1 + sinal2) < 0){
					 sinal.setCor("red");
				 }else{
					 sinal.setCor("green");
				 }
				 
				 sinalEquipamento.add(sinal);
				 
			 }
				
								 
		 }

		 return sinalEquipamento;
	}
	
	// HTTP GET request
	private String getApi(String macAddress) throws Exception {
 
		try {
			String url = "http://apiniveldesinais.azurewebsites.net/api/sinais/" + macAddress;
 
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		}
		catch (Exception e) {
			return null;
		}
 
	}
}

