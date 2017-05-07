package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Placa;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Placa entity.
 */
@SuppressWarnings("unused")
public interface PlacaRepository extends JpaRepository<Placa,Long> {

}
