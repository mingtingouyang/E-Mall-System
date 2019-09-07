package org.oza.ego.base.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;

public class FTPUtil {

    /**
     * 用于上传文件到 ftp 服务器
     * @param hostName 服务器地址
     * @param port 端口号，一般为 21
     * @param username 用户名
     * @param password 密码
     * @param basePath 用户路径，/home/<username>
     * @param filePath 文件夹路径，接在用户路径后
     * @param remoteFileName 存储的文件名
     * @param in 输入流
     * @return 布尔结果
     */
    public static boolean upload(String hostName, int port,
                                 String username, String password,
                                 String basePath, String filePath,
                                 String remoteFileName, InputStream in) {
        //1.创建客户端
        FTPClient ftpClient = new FTPClient();
        try {
            //2.建立连接
            ftpClient.connect(hostName, port);
            //3.登录校验
            ftpClient.login(username, password);
            //4.指定上传文件格式 - 二进制文件
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //5.指定被动模式
            ftpClient.enterLocalPassiveMode();
            //6.指定上传的目录，如果文件夹不存在，返回false
            boolean doneChangeDir = ftpClient.changeWorkingDirectory(basePath + filePath);
            //如果目录不存在，则手动创建
            if(!doneChangeDir) {
                String tmpPath = basePath;
                String[] dirNames = filePath.split("/");
                for (String dirName : dirNames) {
                    tmpPath = tmpPath + "/" + dirName;
                    //如果无法修改为这一级目录，则创建这一级目录
                    if (!ftpClient.changeWorkingDirectory(tmpPath)) {
                        ftpClient.makeDirectory(tmpPath);
                    }
                }
                //如果修改存储路径还是失败，则说明路径有误，直接返回失败
                if (!ftpClient.changeWorkingDirectory(tmpPath))
                    return false;
            }
            //7. 上传
            boolean result = ftpClient.storeFile(remoteFileName, in);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
