package by.yellow.running.mapper;

import by.yellow.running.entity.RunningEntity;
import by.yellow.running.model.Running;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RunningMapper {
    RunningMapper INSTANCE = Mappers.getMapper(RunningMapper.class);
    RunningEntity modelToEntity(Running running);
    Running entityToModel(RunningEntity runningEntity);
}
