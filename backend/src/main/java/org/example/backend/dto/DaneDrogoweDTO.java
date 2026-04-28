package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DaneDrogoweDTO {

    private Long id;

    private Date data;

    private Integer tydzien;

    private Integer sredniaStrefaGranicaUA;

    private Integer sredniaStrefaGranicaBYLT;

    private Integer sredniaStrefaGranicaRU;

    private Integer sredniaStrefaGranicaMB;

    private Integer sredniaStrefaGranicaDE;

    private Integer sredniaStrefaGranicaCZ;

    private Integer sredniaStrefaGranicaSK;

    private Integer sredniaStrefaCentralna;

    private Integer sredniaWojDS;

    private Integer sredniaWojKP;

    private Integer sredniaWojLB;

    private Integer sredniaWojLS;

    private Integer sredniaWojLD;

    private Integer sredniaWojMP;

    private Integer sredniaWojMZ;

    private Integer sredniaWojOP;

    private Integer sredniaWojPK;

    private Integer sredniaWojPL;

    private Integer sredniaWojPM;

    private Integer sredniaWojSL;

    private Integer sredniaWojSK;

    private Integer sredniaWojWM;

    private Integer sredniaWojWP;

    private Integer sredniaWojZP;
}
