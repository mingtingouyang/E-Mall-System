package org.oza.ego.base.vo;

/**
 * 购物车内的商品
 */
public class CartItem {

    private Long id;
    private String title;
    private String image;
    private Long price;
    private Integer num;
    //将图片字符串切割成多个，只给予 get 方法
    private String[] images;

    public String[] getImages() {
        if (null != image) {
            images = image.split(",");
        }
        return images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
