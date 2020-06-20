package moe.ofs.lsograde.mapper;

import moe.ofs.lsograde.model.ErrorItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LandingGradeErrorItemMapper {

    int insertListByLandingGradeId(@Param("id") Long id, @Param("errors") List<ErrorItem> list);
}
