package org.oza.ego.base.vo;

/**
 * 用于封装搜索内容的对象
 */
public class SearchItem {
    private Long id;
    private String title;
    private String sellPoint;
    private Double price;
    private String image;
    private String categoryName;
    //用于解决多个图片的问题，上面的域 image 可能是多个图片
    private String[] images;

    public String[] getImages() {
        //如果 image 不为空，则分出图片地址并赋值
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

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "SearchItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sellPoint='" + sellPoint + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
