package com.suercopo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suercopo.model.Maquina;

@Repository
public interface MaquinaRepository extends JpaRepository<Maquina, Long> {

}
