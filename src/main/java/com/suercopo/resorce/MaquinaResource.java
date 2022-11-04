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

import com.suercopo.Repository.MaquinaRepository;
import com.suercopo.model.Maquina;
import com.suercopo.service.MaquinaService;

@RestController
@RequestMapping("/maquinas")
public class MaquinaResource {
	
	@Autowired
	private MaquinaRepository maquinasRepository;
	
	@Autowired
	private MaquinaService maquinaService;
	
	@GetMapping
	public List<Maquina> listar() {
		return maquinasRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Maquina> buscarPorCodigo(@PathVariable Long codigo) {
		return maquinasRepository.findById(codigo)
				.map(ResponseEntity:: ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Maquina> registrarMAquinas(@RequestBody Maquina maquina, HttpServletRequest request ) {
		Maquina maquinaSalva = maquinasRepository.save(maquina);
		
	URI uri =	ServletUriComponentsBuilder.fromCurrentRequestUri().path("/codigo")
		.buildAndExpand("Location").toUri();
		
		
		return ResponseEntity.created(uri).body(maquinaSalva);
	}
	
	@PutMapping("/{codigo}")
	public Maquina editar(@PathVariable Long codigo,@RequestBody Maquina maquina) {
			
		
		return maquinaService.atualizar(codigo, maquina);
	}
	
	@DeleteMapping("/{codigo}")
	public void excluir(@PathVariable Long codigo) {
		maquinasRepository.deleteById(codigo);
	}
}
