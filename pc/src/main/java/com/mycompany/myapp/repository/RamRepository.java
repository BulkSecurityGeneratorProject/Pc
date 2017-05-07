package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ram;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ram entity.
 */
@SuppressWarnings("unused")
public interface RamRepository extends JpaRepository<Ram,Long> {

}
