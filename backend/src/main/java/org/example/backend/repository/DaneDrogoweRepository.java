package org.example.backend.repository;

import org.example.backend.entity.DaneDrogowe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface DaneDrogoweRepository extends JpaRepository<DaneDrogowe, Long> {

    DaneDrogowe findByData(Date data);
}
