package org.oza.ego.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.oza.ego.base.pojo.Content;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ContentMapper extends BaseMapper<Content> {
}
