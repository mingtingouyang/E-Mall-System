package org.oza.ego.base.vo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * 自定义的一个封装好的响应结构
 */
public class EgoResult {

    //jackson 的一个 JSON 处理工具
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //响应状态
    private Integer status;

    //响应消息
    private String msg;

    //响应中的数据
    private Object data;

    /**
     * 根据 EgoResult 类型的 Json 数据，封装成 EgoResult 对象，集合也可以封装
     * @param jsonStr Json 字符串
     * @param clz EgoResult 中 data 的类字节码，如果是基础数据类型或字符串，填 null
     * @return 封装好的对象
     */
    public static EgoResult formatToEgoResult(String jsonStr, Class<?> clz) {
        try {
            //如果 data 封装的是基本类型以及字符串类型
            if (clz == null)
                return MAPPER.readValue(jsonStr, EgoResult.class);

            //根据 class 封装 data
            JsonNode tree = MAPPER.readTree(jsonStr); //获得 Json 字符串的树形图
            JsonNode dataNode = tree.get("data"); //获得 data 节点
            //先将 status 和 msg 封装进结果对象
            EgoResult egoResult = new EgoResult(tree.get("status").intValue(), tree.get("msg").asText());

            //如果 dataNode 是一个集合类型，且容量大于 0
            if (dataNode.isArray())
                //将 data 封装成 list 集合，参数二相当于 "List<clz>.class"
                egoResult.setData(MAPPER.readValue(dataNode.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clz)));
            //如果 dataNode 是个父节点 -> 是一个对象类型的 Json
            else if (dataNode.isObject())
                egoResult.setData(MAPPER.readValue(dataNode.traverse(), clz)); //根据该节点和字节码参数生成对象
            //否则直接封装成 String 类型
            else
                egoResult.setData(dataNode.asText());
            return egoResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 出现异常则返回 null
        return null;
    }

    /**
     * 重载的方法，没有 data 对象或者 data对象为基本类型
     * @param jsonStr EgoResult 类型的 Json 字符串
     * @return EgoResult 对象
     */
    public static EgoResult formatToEgoResult(String jsonStr) {
        return formatToEgoResult(jsonStr, null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EgoResult ok(Object obj) {
        return new EgoResult(200, "OK", obj);
    }

    public EgoResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public EgoResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public EgoResult() {
    }

    @Override
    public String toString() {
        return "EgoResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
