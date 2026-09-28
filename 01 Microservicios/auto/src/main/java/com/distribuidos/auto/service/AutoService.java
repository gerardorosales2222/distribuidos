package com.distribuidos.auto.service;

import com.distribuidos.auto.model.Auto;
import com.distribuidos.auto.repository.IAutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService implements IAutoService{

    @Autowired
    private IAutoRepository repoAuto;

    @Override
    public void crearAuto(Auto auto) {
        repoAuto.save(auto);
    }

    @Override
    public List<Auto> listarAutos() {
        return repoAuto.findAll();
    }
}
