package com.suercopo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.suercopo.Repository.MaquinaRepository;
import com.suercopo.model.Maquina;

@SpringBootApplication
public class SupercopoApiApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SupercopoApiApplication.class, args);
	}
	
	@Autowired
	private MaquinaRepository maquinaRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Maquina maquina1 = new Maquina(null, "MAQUINA 1");
		Maquina maquina2 = new Maquina(null, "MAQUINA 1");
		Maquina maquina3 = new Maquina(null, "MAQUINA 1");
		Maquina maquina4 = new Maquina(null, "MAQUINA 1");
		
		maquinaRepository.saveAll(Arrays.asList(maquina1, maquina2, maquina3, maquina4));
	}

}
