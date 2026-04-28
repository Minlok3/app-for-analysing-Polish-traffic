package org.example.backend.service;

import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public interface DaneService {
    String getDaneDrogowe(Date date);
    String getDaneGranica(Date date);
}
