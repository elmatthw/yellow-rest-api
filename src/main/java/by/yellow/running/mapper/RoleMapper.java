package by.yellow.running.mapper;

import by.yellow.running.entity.RoleEntity;
import by.yellow.running.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    RoleEntity modelToEntity(Role role);
    Role entityToModel(RoleEntity roleEntity);
}
