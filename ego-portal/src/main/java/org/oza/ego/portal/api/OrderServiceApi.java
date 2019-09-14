package org.oza.ego.portal.api;

import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.base.vo.OrderDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("orderInstance") //目标实例
public interface OrderServiceApi {

    /**
     * 使用 feign 远程调用 order 服务的接口，会将对象参数转换成 json 字符串
     * @param orderDetail
     * @return
     */
    @RequestMapping("/order/create")
    EgoResult save(OrderDetail orderDetail);
}
