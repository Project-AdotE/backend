package com.adote.api.core.usecases.chavePix.get;

import com.adote.api.core.entities.ChavePix;

import java.util.List;

public interface GetChavesByOrgIdCase {

    List<ChavePix> execute(Long orgId);
}
