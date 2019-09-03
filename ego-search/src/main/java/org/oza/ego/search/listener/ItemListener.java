package org.oza.ego.search.listener;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.oza.ego.base.vo.SearchItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * activemq 的监听器，收到添加商品的消息则将文档添加到Solr服务中
 */
@Component
public class ItemListener implements MessageListener {

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            if (objectMessage.getObject() instanceof SearchItem) {
                SearchItem searchItem = (SearchItem) objectMessage.getObject();
                //添加文档到solr服务
                SolrInputDocument document = new SolrInputDocument();

                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSellPoint());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategoryName());

                httpSolrClient.add("ego", document);
                httpSolrClient.commit("ego");

                System.out.println("-消费者：-" + searchItem.getCategoryName() + ",标题：" + searchItem.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
