package org.oza.ego.base;

import org.junit.Test;
import org.oza.ego.base.utils.FTPUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FTPTest {
    @Test
    public void fptUploadTest() {
        //指定上传文件
        File uploadFile = new File("C:\\Users\\carlo\\Desktop\\test.jpg");
        try {
            FileInputStream inputStream = new FileInputStream(uploadFile);
            boolean upload = FTPUtil.upload("192.168.163.89", 21, "ftpuser", "ftp123456",
                    "/home/ftpuser", "/ego/images", "beauty.jpg", inputStream);
            if (upload)
                System.out.println("Success");
            else
                System.out.println("Fail");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
