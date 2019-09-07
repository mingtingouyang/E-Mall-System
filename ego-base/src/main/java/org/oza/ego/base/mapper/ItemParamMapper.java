package org.oza.ego.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.oza.ego.base.pojo.ItemParam;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ItemParamMapper extends BaseMapper<ItemParam> {

    /**
     * 分页查询两张表，并将数据封装到集合里
     * @param start 起始查询序号
     * @param pageSize 查询个数
     * @return 嵌套集合
     */
    @Select("SELECT p.id, p.item_cat_id AS itemCatId, t.name AS itemCatName," +
            "p.param_data AS paramData, p.created, p.updated " +
            "FROM tb_item_param p left join tb_item_cat t on " +
            "p.item_cat_id = t.id limit ${start}, ${pageSize}")
    List<Map<String, Object>> listAndPage(@Param("start") int start, @Param("pageSize") int pageSize);
}
