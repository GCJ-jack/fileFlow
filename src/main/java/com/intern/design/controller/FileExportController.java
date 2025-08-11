package com.intern.design.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.misc.FieldUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/file")
public class FileExportController {

    private static final String DIR = "data";

    @PostMapping("/upload")
    public String uploadAndExport(MultipartFile file) throws IOException {
        if(Objects.isNull(file)||file.isEmpty()){
            throw new IllegalArgumentException("文件为空");
        }

        File saveFile = toSavePath(file.getOriginalFilename()).toFile();
        file.transferTo(saveFile);

        return  saveFile.getAbsolutePath();
    }

    private Path toSavePath(String originalFilename) {
        String name = IdUtil.fastSimpleUUID();

        String extName = FileUtil.extName(originalFilename);

        String fileName = name + StrUtil.DOT + extName;

        return Paths.get(DIR,fileName);
    }
}
