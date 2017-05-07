package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Graficas;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Graficas entity.
 */
@SuppressWarnings("unused")
public interface GraficasRepository extends JpaRepository<Graficas,Long> {

}
