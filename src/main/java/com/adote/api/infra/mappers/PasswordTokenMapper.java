package com.adote.api.infra.mappers;

import com.adote.api.core.entities.PasswordToken;
import com.adote.api.infra.persistence.entities.PasswordTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordTokenMapper {

    PasswordToken toDomain(PasswordTokenEntity entity);

    PasswordTokenEntity toEntity(PasswordToken domain);
}
