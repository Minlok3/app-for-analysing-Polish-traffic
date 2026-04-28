package org.example.backend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.backend.entity.DaneDrogowe;
import org.example.backend.entity.DaneGranica;
import org.example.backend.repository.DaneDrogoweRepository;
import org.example.backend.repository.DaneGranicaRepository;
import org.example.backend.service.DaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Service
public class DaneIMPL implements DaneService {

    private final DaneDrogoweRepository daneDrogoweRepository;
    private final DaneGranicaRepository daneGranicaRepository;

    @Autowired
    public DaneIMPL(DaneDrogoweRepository daneDrogoweRepository, DaneGranicaRepository daneGranicaRepository) {
        this.daneDrogoweRepository = daneDrogoweRepository;
        this.daneGranicaRepository = daneGranicaRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String getDaneDrogowe(Date date) {
        DaneDrogowe daneDrogowe = daneDrogoweRepository.findByData(date);
        if (daneDrogowe != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            // Registering the JavaTimeModule to handle Java 8 date/time types
            objectMapper.registerModule(new JavaTimeModule());
            // Disabling the timestamps to enforce date format
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            // Setting a custom date format
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

            String json = "";
            try {
                json = objectMapper.writeValueAsString(daneDrogowe);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return json;
        }
        else {
            return "Wrong date";
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String getDaneGranica(Date date) {
        DaneGranica daneGranica = daneGranicaRepository.findByData(date);
        if (daneGranica != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            // Registering the JavaTimeModule to handle Java 8 date/time types
            objectMapper.registerModule(new JavaTimeModule());
            // Disabling the timestamps to enforce date format
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            // Setting a custom date format
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

            String json = "";
            try {
                json = objectMapper.writeValueAsString(daneGranica);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return json;
        }
        else {
            return "Wrong date";
        }
    }
}
