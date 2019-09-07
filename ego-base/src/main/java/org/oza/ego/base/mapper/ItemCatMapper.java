package org.oza.ego.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.oza.ego.base.pojo.ItemCat;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ItemCatMapper extends BaseMapper<ItemCat> {
}
