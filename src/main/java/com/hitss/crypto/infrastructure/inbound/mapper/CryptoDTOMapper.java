package com.hitss.crypto.infrastructure.inbound.mapper;

import com.hitss.crypto.domain.model.CryptoPrice;
import com.hitss.crypto.infrastructure.inbound.dto.CryptoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface CryptoDTOMapper {
    CryptoResponseDTO toResponse(CryptoPrice domain);
}