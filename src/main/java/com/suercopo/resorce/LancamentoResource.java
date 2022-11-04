package com.suercopo.resorce;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.suercopo.Repository.LancamentoRepository;
import com.suercopo.model.Lancamento;
import com.suercopo.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@GetMapping
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPorCodigo(@PathVariable Long codigo) {
		return lancamentoRepository.findById(codigo)
				.map(ResponseEntity::ok)
					.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> registarLancamento(@RequestBody Lancamento lancamento, HttpServletRequest request) {
		Lancamento lancamentoSalvar = lancamentoRepository.save(lancamento);
		
	URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand("Location").toUri();
		
		return ResponseEntity.created(uri).body(lancamentoSalvar);
	}
	
	@PutMapping("/{codigo}")
	public Lancamento atualizar(@PathVariable Long codigo,@RequestBody Lancamento lancamento) {
		
		
		return lancamentoService.atualizar(codigo, lancamento);
	}
	
	@DeleteMapping("/{codigo}")
	public void excluir(@PathVariable Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}
}
