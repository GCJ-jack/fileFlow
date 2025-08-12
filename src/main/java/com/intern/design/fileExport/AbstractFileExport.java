package com.intern.design.fileExport;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import com.intern.design.bean.User;
import com.intern.design.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileExport implements FileExport {

    private final UserMapper userMapper;

    @Autowired
    protected AbstractFileExport(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void export2Db(String filepath){
        checkPath(filepath);

        System.out.println("filepath校验通过");

        List<User> users = readFile(filepath);
        System.out.println("读取到users");
        System.out.println(users);

        // 3. 可能需要再做数据处理. 比如数据中有空格, 去空格
        if (needProcessData()) {
            users = processData(users);
            System.out.println("数据处理");
            System.out.println(users);
        }

        saveUsers(users);

    }

    protected abstract List<User> readFile(String filepath);

    protected abstract boolean needProcessData();

    protected List<User> processData(List<User> users) {
        throw new UnsupportedOperationException("不支持数据处理");
    }


    private void saveUsers(List<User> users) {
        // todo !!!真实开发中, 禁止在循环中去做数据库操作, 禁止在循环中做远程调用
        users.forEach(u -> { userMapper.insert(u); System.out.println("newId=" + u.getId()); });

    }


    private void checkPath(String path){
        if(StrUtil.isBlank(path)){
            throw new IllegalArgumentException("文件路径不能为空");
        }

        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("文件必须存在且不能是文件夹");
        }

        String type = FileTypeUtil.getType(file);
        if (!Objects.equals(supportedFileType().getType(), type)) {
            throw new IllegalArgumentException("文件类型异常: " + type);
        }

    }
}
