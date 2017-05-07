package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Alimentacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Alimentacion entity.
 */
@SuppressWarnings("unused")
public interface AlimentacionRepository extends JpaRepository<Alimentacion,Long> {

}
