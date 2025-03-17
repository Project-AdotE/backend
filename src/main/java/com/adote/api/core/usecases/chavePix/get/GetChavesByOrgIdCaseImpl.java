package com.adote.api.core.usecases.chavePix.get;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.gateway.ChavePixGateway;

import java.util.List;

public class GetChavesByOrgIdCaseImpl implements GetChavesByOrgIdCase {

    private final ChavePixGateway gateway;

    public GetChavesByOrgIdCaseImpl(final ChavePixGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<ChavePix> execute(Long orgId) {
        return gateway.getChavePixByOrgId(orgId);
    }
}
