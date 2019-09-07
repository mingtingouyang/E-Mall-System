package org.oza.ego.base.vo;

/**
 * 用于封装上传结果供前台响应
 */
public class UploadResult {
    private int error; //0 表示成功 1 表示失败

    private String url; //上传成功后，存储图片的地址

    private String message; //失败时的错误信息

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
