package com.alerttvapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.alerttvapp.models.Equipamento;

public interface EquipamentoRepository extends CrudRepository<Equipamento, String>{
	Equipamento findByMacAddress(String macAddress);

}
