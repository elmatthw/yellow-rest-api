package by.yellow.running.mapper;

import by.yellow.running.entity.RunningEntity;
import by.yellow.running.model.Running;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RunningMapper {
    RunningEntity modelToEntity(Running running);
    Running entityToModel(RunningEntity runningEntity);
}
