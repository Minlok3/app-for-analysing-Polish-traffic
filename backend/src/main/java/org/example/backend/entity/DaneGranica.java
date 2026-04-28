package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "dane_granica", uniqueConstraints = {@UniqueConstraint(columnNames = {"data"})}, indexes = {@Index(columnList = "data")})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DaneGranica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Date data;

    @Column(nullable = false)
    private Integer tydzien;

    @Column(name = "suma_przyjazdu_ogolem", nullable = false)
    private Integer sumaPrzyjazduOgolem;

    @Column(name = "suma_wyjazdu_ogolem", nullable = false)
    private Integer sumaWyjazduOgolem;

    @Column(name = "suma_przyjazdu_polska", nullable = false)
    private Integer sumaPrzyjazduPolska;

    @Column(name = "suma_wyjazdu_polska", nullable = false)
    private Integer sumaWyjazduPolska;

    @Column(name = "suma_przyjazdu_ukraina", nullable = false)
    private Integer sumaPrzyjazduUkraina;

    @Column(name = "suma_wyjazdu_ukraina", nullable = false)
    private Integer sumaWyjazduUkraina;

    @Column(name = "suma_przyjazdu_niemcy", nullable = false)
    private Integer sumaPrzyjazduNiemcy;

    @Column(name = "suma_wyjazdu_niemcy", nullable = false)
    private Integer sumaWyjazduNiemcy;

    @Column(name = "suma_przyjazdu_stany_zjednoczone", nullable = false)
    private Integer sumaPrzyjazduStanyZjednoczone;

    @Column(name = "suma_wyjazdu_stany_zjednoczone", nullable = false)
    private Integer sumaWyjazduStanyZjednoczone;

    @Column(name = "suma_przyjazdu_rumunia", nullable = false)
    private Integer sumaPrzyjazduRumunia;

    @Column(name = "suma_wyjazdu_rumunia", nullable = false)
    private Integer sumaWyjazduRumunia;

    @Column(name = "suma_przyjazdu_wielka_brytania", nullable = false)
    private Integer sumaPrzyjazduWielkaBrytania;

    @Column(name = "suma_wyjazdu_wielka_brytania", nullable = false)
    private Integer sumaWyjazduWielkaBrytania;

    @Column(name = "suma_przyjazdu_moldawia", nullable = false)
    private Integer sumaPrzyjazduMoldawia;

    @Column(name = "suma_wyjazdu_moldawia", nullable = false)
    private Integer sumaWyjazduMoldawia;

    @Column(name = "suma_przyjazdu_inne", nullable = false)
    private Integer sumaPrzyjazduInne;

    @Column(name = "suma_wyjazdu_inne", nullable = false)
    private Integer sumaWyjazduInne;

    @Column(name = "suma_przyjazdu_oddzial_nadbuzanski", nullable = false)
    private Integer sumaPrzyjazduOddzialNadbuzanski;

    @Column(name = "suma_wyjazdu_oddzial_nadbuzanski", nullable = false)
    private Integer sumaWyjazduOddzialNadbuzanski;

    @Column(name = "suma_przyjazdu_oddzial_bieszczadzki", nullable = false)
    private Integer sumaPrzyjazduOddzialBieszczadzki;

    @Column(name = "suma_wyjazdu_oddzial_bieszczadzki", nullable = false)
    private Integer sumaWyjazduOddzialBieszczadzki;

    @Column(name = "suma_przyjazdu_pg_drogowe", nullable = false)
    private Integer sumaPrzyjazduPgDrogowe;

    @Column(name = "suma_wyjazdu_pg_drogowe", nullable = false)
    private Integer sumaWyjazduPgDrogowe;

    @Column(name = "suma_przyjazdu_pg_kolejowe", nullable = false)
    private Integer sumaPrzyjazduPgKolejowe;

    @Column(name = "suma_wyjazdu_pg_kolejowe", nullable = false)
    private Integer sumaWyjazduPgKolejowe;
}
