package com.adote.api.core.usecases.chavePix.get;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.gateway.ChavePixGateway;

import java.util.List;

public class GetAllChavesCaseImpl implements GetAllChavesCase {

    private final ChavePixGateway gateway;

    public GetAllChavesCaseImpl(final ChavePixGateway gateway) {
        this.gateway = gateway;
    }


    @Override
    public List<ChavePix> execute() {
        return gateway.getAllChaves();
    }
}
