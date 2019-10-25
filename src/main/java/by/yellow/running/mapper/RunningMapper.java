package by.yellow.running.mapper;

import by.yellow.running.entity.RunningEntity;
import by.yellow.running.entity.UserEntity;
import by.yellow.running.model.Running;
import by.yellow.running.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
                UserMapper.class
        },
        imports = {
                UserEntity.class
        })
public interface RunningMapper {
    RunningEntity modelToEntity(Running running);
    RunningEntity modelToEntity(Running running, User user);
    Running entityToModel(RunningEntity runningEntity);
    Running entityToModel(RunningEntity runningEntity, UserEntity userEntity);
}
