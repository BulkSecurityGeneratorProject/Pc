package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Procesador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Procesador entity.
 */
@SuppressWarnings("unused")
public interface ProcesadorRepository extends JpaRepository<Procesador,Long> {

}
