package com.example.backend.mappers;

import com.example.backend.dtos.product.ProductRequestDTO;
import com.example.backend.dtos.product.ProductResponseDTO;
import com.example.backend.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "productId", target = "productId")
    ProductResponseDTO fromEntityToResponseDTO(Product product);

    @Mapping(target = "productId", ignore = true)
    Product fromRequestDTOToEntity(ProductRequestDTO productRequestDTO);

    @Mapping(target = "productId", ignore = true)
    void updateEntityFromRequestDTO(ProductRequestDTO productRequestDTO, @MappingTarget Product product);
}
