package by.yellow.running.mapper;

import by.yellow.running.entity.RoleEntity;
import by.yellow.running.entity.RunningEntity;
import by.yellow.running.entity.UserEntity;
import by.yellow.running.model.Role;
import by.yellow.running.model.Running;
import by.yellow.running.model.User;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(
        componentModel = "spring",
        uses = {
                RoleMapper.class,
                RunningMapper.class
        },
        imports = {
                RoleEntity.class,
                RunningEntity.class
        }
)
public interface UserMapper {
    UserEntity modelToEntity(User user);
    UserEntity modelToEntity(User user, Set<Role> roles);
    UserEntity modelToEntity(User user, Running running);
    User entityToModel(UserEntity userEntity);
    User entityToModel(UserEntity userEntity, RunningEntity runningEntity);
    User entityToModel(UserEntity userEntity, Set<RoleEntity> roleEntities);
}
