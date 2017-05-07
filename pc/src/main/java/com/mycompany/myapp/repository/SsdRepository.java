package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ssd;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ssd entity.
 */
@SuppressWarnings("unused")
public interface SsdRepository extends JpaRepository<Ssd,Long> {

}
