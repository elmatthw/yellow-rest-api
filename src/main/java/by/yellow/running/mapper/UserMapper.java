package by.yellow.running.mapper;

import by.yellow.running.entity.UserEntity;
import by.yellow.running.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserEntity modelToEntity(User user);
    User entityToModel(UserEntity userEntity);
}
