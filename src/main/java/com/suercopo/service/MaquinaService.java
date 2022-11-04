package com.suercopo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.suercopo.Repository.MaquinaRepository;
import com.suercopo.model.Maquina;

@Service
public class MaquinaService {
	
	@Autowired
	private MaquinaRepository maquinaRepository;
	
	
	public Maquina atualizar(Long codigo, Maquina maquina) {
		Maquina maquinaSalva = buscarPorCodigo(codigo);
		BeanUtils.copyProperties(maquina, maquinaSalva, "codigo");
		
		return maquinaRepository.save(maquinaSalva);
	}
	
	
	
	public Maquina buscarPorCodigo(Long codigo) {
		Maquina maquinaSlava = maquinaRepository.findById(codigo)
					.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return maquinaSlava;
	}
}
