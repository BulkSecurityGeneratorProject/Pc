package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DiscoDuro;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DiscoDuro entity.
 */
@SuppressWarnings("unused")
public interface DiscoDuroRepository extends JpaRepository<DiscoDuro,Long> {

}
