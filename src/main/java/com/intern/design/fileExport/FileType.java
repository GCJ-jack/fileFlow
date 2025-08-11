package com.intern.design.fileExport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@ToString
@AllArgsConstructor
public enum FileType {

    JSON("json"),
    CSV("csv");

    private String type;

    private static final Map<String,FileType> VALUE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(
                  FileType::getType,
                    Function.identity(),
                    (v1, v2) -> v2
            ));

    private FileType from(String file){
        if(!VALUE_MAP.containsKey(file)){
            throw new IllegalArgumentException("找不到对应类型: " + file);
        }

        return VALUE_MAP.get(file);
    }

}
