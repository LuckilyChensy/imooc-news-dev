package com.imooc.files.controller;

import com.imooc.api.files.FileUploaderControllerApi;
import com.imooc.files.resource.FileResource;
import com.imooc.files.service.UploaderService;
import com.imooc.grace.result.GraceJSONResult;
import com.imooc.grace.result.ResponseStatusEnum;
import com.imooc.pojo.bo.NewAdminBO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FileUploaderController implements FileUploaderControllerApi {

    final static Logger logger = LoggerFactory.getLogger(FileUploaderController.class);

    @Autowired
    private UploaderService uploaderService;

    @Autowired
    private FileResource fileResource;

    @Override
    public GraceJSONResult uploadFace(String userId, MultipartFile file) throws Exception {

        String path = "";

        if (file != null) {
            // 获得文件上传的名称
            String fileName = file.getOriginalFilename();

            // 判断文件名不能为空
            if (StringUtils.isNotBlank(fileName)) {

                String fileNameArr[] = fileName.split("\\.");
                // 获得后缀
                String suffix = fileNameArr[fileNameArr.length - 1];

                // 判断后缀符合我们的预定义规范
                if (!suffix.equalsIgnoreCase("png") && !suffix.equalsIgnoreCase("jpg") && !suffix.equalsIgnoreCase("jpeg")) {
                    return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_FORMATTER_FAILD);
                }

                // 执行上传
                path = uploaderService.uploadFdfs(file, suffix);

                logger.info("path = " + path);

                String finalPath = "";

                if (StringUtils.isNotBlank(path)) {
                    finalPath = fileResource.getHost() + path;
                } else {
                    return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
                }

                return GraceJSONResult.ok(finalPath);

            } else {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
            }
        } else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
        }

    }

    @Override
    public GraceJSONResult uploadSomeFiles(String userId, MultipartFile[] files) throws Exception {
        return null;
    }

    @Override
    public GraceJSONResult uploadToGridFS(NewAdminBO newAdminBO) throws Exception {
        return null;
    }

    @Override
    public void readInGridFS(String faceId, HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    @Override
    public GraceJSONResult readFace64InGridFS(String faceId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

}
