package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DaneGranicaDTO {

    private Long id;

    private Date data;

    private Integer tydzien;

    private Integer sumaPrzyjazduOgolem;

    private Integer sumaWyjazduOgolem;

    private Integer sumaPrzyjazduPolska;

    private Integer sumaWyjazduPolska;

    private Integer sumaPrzyjazduUkraina;

    private Integer sumaWyjazduUkraina;

    private Integer sumaPrzyjazduNiemcy;

    private Integer sumaWyjazduNiemcy;

    private Integer sumaPrzyjazduStanyZjednoczone;

    private Integer sumaWyjazduStanyZjednoczone;

    private Integer sumaPrzyjazduRumunia;

    private Integer sumaWyjazduRumunia;

    private Integer sumaPrzyjazduWielkaBrytania;

    private Integer sumaWyjazduWielkaBrytania;

    private Integer sumaPrzyjazduMoldawia;

    private Integer sumaWyjazduMoldawia;

    private Integer sumaPrzyjazduInne;

    private Integer sumaWyjazduInne;

    private Integer sumaPrzyjazduOddzialNadbuzanski;

    private Integer sumaWyjazduOddzialNadbuzanski;

    private Integer sumaPrzyjazduOddzialBieszczadzki;

    private Integer sumaWyjazduOddzialBieszczadzki;

    private Integer sumaPrzyjazduPgDrogowe;

    private Integer sumaWyjazduPgDrogowe;

    private Integer sumaPrzyjazduPgKolejowe;

    private Integer sumaWyjazduPgKolejowe;
}
