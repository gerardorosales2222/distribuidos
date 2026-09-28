package com.distribuidos.auto.controller;

import com.distribuidos.auto.model.Auto;
import com.distribuidos.auto.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class AutoController {

    @Autowired
    private AutoService autoServ;

    @PostMapping ("/autos/crear")
    public String creaAuto(@RequestBody Auto auto){
        autoServ.crearAuto(auto);
        return "Auto creado...";
    }

    @GetMapping("/autos/listar")
    public List<Auto> listarAutos(){
        return autoServ.listarAutos();
    }

}
