package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "dane_drogowe", uniqueConstraints = {@UniqueConstraint(columnNames = {"data"})}, indexes = {@Index(columnList = "data")})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DaneDrogowe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Date data;

    @Column(nullable = false)
    private Integer tydzien;

    @Column(name = "srednia_strefa_granica_UA", nullable = false)
    private Integer sredniaStrefaGranicaUA;

    @Column(name = "srednia_strefa_granica_BY_LT", nullable = false)
    private Integer sredniaStrefaGranicaBYLT;

    @Column(name = "srednia_strefa_granica_RU", nullable = false)
    private Integer sredniaStrefaGranicaRU;

    @Column(name = "srednia_strefa_granica_MB", nullable = false)
    private Integer sredniaStrefaGranicaMB;

    @Column(name = "srednia_strefa_granica_DE", nullable = false)
    private Integer sredniaStrefaGranicaDE;

    @Column(name = "srednia_strefa_granica_CZ", nullable = false)
    private Integer sredniaStrefaGranicaCZ;

    @Column(name = "srednia_strefa_granica_SK", nullable = false)
    private Integer sredniaStrefaGranicaSK;

    @Column(name = "srednia_strefa_centralna", nullable = false)
    private Integer sredniaStrefaCentralna;

    @Column(name = "srednia_woj_DS", nullable = false)
    private Integer sredniaWojDS;

    @Column(name = "srednia_woj_KP", nullable = false)
    private Integer sredniaWojKP;

    @Column(name = "srednia_woj_LB", nullable = false)
    private Integer sredniaWojLB;

    @Column(name = "srednia_woj_LS", nullable = false)
    private Integer sredniaWojLS;

    @Column(name = "srednia_woj_LD", nullable = false)
    private Integer sredniaWojLD;

    @Column(name = "srednia_woj_MP", nullable = false)
    private Integer sredniaWojMP;

    @Column(name = "srednia_woj_MZ", nullable = false)
    private Integer sredniaWojMZ;

    @Column(name = "srednia_woj_OP", nullable = false)
    private Integer sredniaWojOP;

    @Column(name = "srednia_woj_PK", nullable = false)
    private Integer sredniaWojPK;

    @Column(name = "srednia_woj_PL", nullable = false)
    private Integer sredniaWojPL;

    @Column(name = "srednia_woj_PM", nullable = false)
    private Integer sredniaWojPM;

    @Column(name = "srednia_woj_SL", nullable = false)
    private Integer sredniaWojSL;

    @Column(name = "srednia_woj_SK", nullable = false)
    private Integer sredniaWojSK;

    @Column(name = "srednia_woj_WM", nullable = false)
    private Integer sredniaWojWM;

    @Column(name = "srednia_woj_WP", nullable = false)
    private Integer sredniaWojWP;

    @Column(name = "srednia_woj_ZP", nullable = false)
    private Integer sredniaWojZP;
}
