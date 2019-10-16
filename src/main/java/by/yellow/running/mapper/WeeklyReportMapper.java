package by.yellow.running.mapper;

import by.yellow.running.entity.WeeklyReportEntity;
import by.yellow.running.model.WeeklyReport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeeklyReportMapper {
    WeeklyReportMapper INSTANCE = Mappers.getMapper(WeeklyReportMapper.class);
    WeeklyReportEntity modelToEntity(WeeklyReport WeeklyReport);
    WeeklyReport entityToModel(WeeklyReportEntity WeeklyReportEntity);
}
