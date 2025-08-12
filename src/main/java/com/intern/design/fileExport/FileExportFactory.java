package com.intern.design.fileExport;

import cn.hutool.extra.spring.SpringUtil;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileExportFactory {

    private static final Map<FileType, FileExport> CACHE;

    static {
        Map<String, FileExport> beans = SpringUtil.getBeansOfType(FileExport.class);
        CACHE = beans.values().stream()
                .collect(Collectors.toConcurrentMap(
                        FileExport::supportedFileType,
                        Function.identity(),
                        (v1, v2) -> v2
                ));
    }

    public static FileExport getFileExport(FileType fileType){
        if (!CACHE.containsKey(fileType)) {
            throw new IllegalArgumentException("找不到对应类型: " + fileType.getType());
        }


        return CACHE.get(fileType);
    }

    public static FileExport getFileExport(String type) {
        FileType fileType = FileType.from(type);

        return getFileExport(fileType);
    }


}
