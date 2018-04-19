package com.alerttvapp.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Equipamento {
	
	
	@Id
	//@NotEmpty
	//@Column(name="macAddress", unique=true, nullable=false)
	private String macAddress;
	
	//@NotEmpty
	private String cliente;
	
	private String cep;
	private String endereco;
	private String cidade;
	private String estado;
	
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	

}
