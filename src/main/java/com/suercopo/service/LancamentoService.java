package com.suercopo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.suercopo.Repository.LancamentoRepository;
import com.suercopo.model.Lancamento;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvar = buscarPorCodigo(codigo);
		BeanUtils.copyProperties(lancamento, lancamentoSalvar, "codigo");
		
		return lancamentoRepository.save(lancamentoSalvar);
	}
	
	
	public Lancamento buscarPorCodigo(Long codigo) {
		Lancamento lancamentoSalvo = lancamentoRepository.findById(codigo)
					.orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		return lancamentoSalvo;
		
	}
}
