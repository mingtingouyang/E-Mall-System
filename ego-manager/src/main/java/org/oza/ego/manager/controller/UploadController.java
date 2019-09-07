package org.oza.ego.manager.controller;

import org.oza.ego.base.vo.UploadResult;
import org.oza.ego.manager.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 用于处理图片上传，只接受 post 请求
     * @param uploadFile 前端上传的 MultipartFile 对象
     * @return 封装好的上传结果对象的 JSON 数据
     */
    @PostMapping("/pic/upload")
    public UploadResult upload(MultipartFile uploadFile) {
        UploadResult uploadResult = uploadService.upload("/ego/images", uploadFile);
        return  uploadResult;
    }
}
