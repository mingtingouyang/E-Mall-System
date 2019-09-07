package org.oza.ego.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.oza.ego.base.pojo.Item;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.base.vo.EgoResult;

public interface ItemService extends IService<Item> {
    /**
     * 分页查询后，将分页结果封装成前台页面需要的对象
     * @param curPage 当前页
     * @param rows 查询行数
     * @return 封装后的对象
     */
    EUDataGridResult listAndPage(int curPage, int rows);

    /**
     * 根据前台浏览器发送的请求参数，存储 Item 数据，
     * 需要保存 item 基本信息、item 描述、 item 参数，
     * 有事务处理。
     * @param item item 基本信息
     * @param Desc item 描述
     * @param paramData item 参数
     * @return EgoResult 对象封装修改结果
     */
    EgoResult saveItem(Item item, String Desc, String paramData);
}
