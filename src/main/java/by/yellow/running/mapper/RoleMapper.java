package by.yellow.running.mapper;

import by.yellow.running.entity.RoleEntity;
import by.yellow.running.entity.UserEntity;
import by.yellow.running.model.Role;
import by.yellow.running.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
                UserMapper.class
        },
        imports = {
                UserEntity.class
        })
public interface RoleMapper {
    RoleEntity modelToEntity(Role role);
    RoleEntity modelToEntity(Role role, User user);
    Role entityToModel(RoleEntity roleEntity);
    Role entityToModel(RoleEntity roleEntity, UserEntity userEntity);
}
