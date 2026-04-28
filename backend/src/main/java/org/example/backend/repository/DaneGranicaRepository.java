package org.example.backend.repository;

import org.example.backend.entity.DaneGranica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface DaneGranicaRepository extends JpaRepository<DaneGranica, Long> {

    DaneGranica findByData(Date data);
}
