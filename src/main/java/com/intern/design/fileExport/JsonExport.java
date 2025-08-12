package com.intern.design.fileExport;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.intern.design.bean.User;
import com.intern.design.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonExport extends AbstractFileExport{

    protected JsonExport(UserMapper userMapper){
        super(userMapper);
    }

    @Override
    public FileType supportedFileType() {
        return FileType.JSON;
    }

    @Override
    protected List<User> readFile(String filepath) {
        String json = FileUtil.readUtf8String(filepath);
        TypeReference<List<User>> type = new TypeReference<List<User>>() {};
        return JSONUtil.toBean(json, type, false); // 外层必须是数组：[ {...}, {...} ]
    }

    @Override
    protected boolean needProcessData() { return true; }

    @Override
    protected List<User> processData(List<User> users) {
        return users.stream().map(this::clean).collect(Collectors.toList());
    }

    private User clean(User u) {
        u.setName(t(u.getName()));
        u.setIdCard(t(u.getIdCard()));
        u.setPhone(t(u.getPhone()));
        if (u.getGender() == null) u.setGender(0);
        if (u.getCreatorId() == null) u.setCreatorId(0L);
        if (u.getUpdaterId() == null) u.setUpdaterId(u.getCreatorId());
        // id / create_time / update_time 交给数据库
        return u;
    }

    private static String t(String s) { return s == null ? null : s.trim(); }
}
