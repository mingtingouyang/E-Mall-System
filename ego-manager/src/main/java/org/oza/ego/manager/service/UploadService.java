package org.oza.ego.manager.service;

import org.oza.ego.base.vo.UploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 用于上传文件，将上传的文件按日期归类到文件夹
     * @param file 前端浏览器文件上传获得的 MultiPartFile 对象
     * @param filePath 上传到服务器的路径，如图片上传到 /ego/images
     * @return 封装好的上传结果对象
     */
    UploadResult upload(String filePath, MultipartFile file);
}
