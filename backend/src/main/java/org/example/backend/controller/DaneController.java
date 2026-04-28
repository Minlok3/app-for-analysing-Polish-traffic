package org.example.backend.controller;

import org.example.backend.service.DaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@CrossOrigin
@RequestMapping("dane")
public class DaneController {
    private final DaneService daneService;

    @Autowired
    public DaneController(DaneService daneService) {
        this.daneService = daneService;
    }

    @GetMapping("/drogowe")
    public String sendDaneDrogowe(@RequestParam("date") Date date){
        String daneDrogowe = daneService.getDaneDrogowe(date);
        return daneDrogowe;
    }
    @GetMapping("/granica")
    public String sendDaneGranica(@RequestParam("date") Date date){
        String daneGranica = daneService.getDaneGranica(date);
        return daneGranica;
    }
}
