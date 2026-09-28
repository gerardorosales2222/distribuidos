package com.distribuidos.auto.service;

import com.distribuidos.auto.model.Auto;

import java.util.List;

public interface IAutoService {

    public void crearAuto(Auto auto);

    public List<Auto> listarAutos();

}
