package top.flagshen.robot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.flagshen.robot.pojo.BadMessage;

import java.util.List;

/**
 * @author 150149
 */
@Mapper
@Repository
public interface RestrictMessageMapper {

    List<BadMessage> search();

}
