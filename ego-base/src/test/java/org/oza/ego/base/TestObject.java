package org.oza.ego.base;

public class TestObject {
    private String message;
    private Integer status_code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "message='" + message + '\'' +
                ", status_code=" + status_code +
                '}';
    }
}
