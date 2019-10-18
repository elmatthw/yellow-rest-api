package by.yellow.running.mapper;

import by.yellow.running.entity.RoleEntity;
import by.yellow.running.entity.UserEntity;
import by.yellow.running.model.User;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {
                RoleMapper.class
        },
        imports = {
                RoleEntity.class
        }
)
public interface UserMapper {
    UserEntity modelToEntity(User user);
    User entityToModel(UserEntity userEntity);
}
