package org.oza.ego.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.oza.ego.base.pojo.ItemParam;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.base.vo.EgoResult;

public interface ItemParamService extends IService<ItemParam> {

    /**
     * 根据商品类别查询商品参数对象
     * @param itemCatId 商品类别 ID
     * @return data 为商品参数的 EgoResult
     */
    EgoResult getByItemCatId(Long itemCatId);

    /**
     * 将参数数据保存为 ItemParam 对象保存进数据库
     * @param itemCatId 类别 ID
     * @param paramData 数据的 Json 字符串
     * @return EgoResult 封装结果
     */
    EgoResult saveItemParam(Long itemCatId, String paramData);

    /**
     * 根据 当前页，页面大小 进行分页查询
     * @param curPage 当前页数
     * @param pageSize 页面容量
     * @return 封装好的分页对象
     */
    EUDataGridResult listAndPage(int curPage, int pageSize);
}
