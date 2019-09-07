package org.oza.ego.manager.service.impl;

import org.oza.ego.base.utils.FTPUtil;
import org.oza.ego.base.utils.IdUtil;
import org.oza.ego.base.vo.UploadResult;
import org.oza.ego.manager.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UploadServiceImpl implements UploadService {
    @Value("${ftp.hostName}")
    private String hostName;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.basePath}")
    private String basePath;
    @Value("${ftp.baseUrl}")
    private String baseUrl;

    @Override
    public UploadResult upload(String filePath, MultipartFile file) {
        UploadResult result = new UploadResult();
        //根据上传日期，修改服务器存储地址
        filePath += new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
        //获取文件的后缀
        String originalFilename = file.getOriginalFilename();
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //创建存储的文件名
        String remoteFileName = IdUtil.getFileName() + fileSuffix;
        try {
            //开始上传
            boolean uploadRes = FTPUtil.upload(hostName, port, username, password, basePath, filePath, remoteFileName, file.getInputStream());
            //成功上传
            if (uploadRes) {
                result.setError(0);
                result.setUrl(baseUrl + filePath + "/" + remoteFileName);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传失败或出现异常
        result.setError(1);
        result.setMessage("上传失败，请稍后再试！");
        return result;
    }
}
