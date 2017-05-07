package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ordenador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ordenador entity.
 */
@SuppressWarnings("unused")
public interface OrdenadorRepository extends JpaRepository<Ordenador,Long> {

}
