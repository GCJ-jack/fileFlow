package com.intern.design.fileExport;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.*;
import com.intern.design.bean.User;
import com.intern.design.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvExport extends AbstractFileExport{

    protected CsvExport(UserMapper userMapper) {
        super(userMapper);
    }

    @Override
    public FileType supportedFileType() {
        return FileType.CSV;
    }

    @Override
    protected List<User> readFile(String filepath) {
        CsvReadConfig cfg = CsvReadConfig.defaultConfig()
                .setHeaderLineNo(0)    // 明确第0行是表头
                .setTrimField(true);   // 去前后空格
        // 如果你用分号/Tab 作分隔，放开下面一行：
        // cfg.setFieldSeparator(';'); // 或 '\t'

        CsvReader r = CsvUtil.getReader(cfg);
        CsvData d = r.read(FileUtil.file(filepath));

        // 打印一下看是否真的识别到表头（调试完可删除）
        System.out.println("HEADER = " + d.getHeader());

        if (d.getHeader() != null && !d.getHeader().isEmpty()) {
            // 表头需和 User 字段名一致：name,gender,idCard,phone,creatorId,updaterId
            return d.getRows().stream()
                    .map(CsvRow::getFieldMap)
                    .map(m -> BeanUtil.toBean(m, User.class))
                    .collect(Collectors.toList());
        } else {
            // 没识别到表头 → 按下标兜底（列顺序要固定）
            return d.getRows().stream().map(row -> {
                User u = new User();
                u.setName(row.get(0));
                u.setGender(Integer.valueOf(row.get(1)));
                u.setIdCard(row.get(2));
                u.setPhone(row.get(3));
                u.setCreatorId(Long.valueOf(row.get(4)));
                u.setUpdaterId(Long.valueOf(row.get(5)));
                return u;
            }).collect(Collectors.toList());
        }
    }

    @Override
    protected boolean needProcessData() {
        return false;
    }
}
