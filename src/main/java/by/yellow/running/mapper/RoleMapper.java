package by.yellow.running.mapper;

import by.yellow.running.entity.RoleEntity;
import by.yellow.running.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RoleMapper {
    RoleEntity modelToEntity(Role role);
    Role entityToModel(RoleEntity roleEntity);
}
