package com.intern.design.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("test_user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)   // 对应 bigint unsigned AUTO_INCREMENT
    private Long id;

    private String name;                         // name varchar(32)

    private Integer gender;                      // gender tinyint

    @TableField("id_card")
    private String idCard;                       // id_card varchar(32)

    private String phone;                        // phone varchar(32)

    @TableField("creator_id")
    private Long creatorId;                      // creator_id bigint unsigned

    @TableField("updater_id")
    private Long updaterId;                      // updater_id bigint unsigned

    @TableField("create_time")
    private LocalDateTime createTime;            // datetime，DB 默认 CURRENT_TIMESTAMP

    @TableField("update_time")
    private LocalDateTime updateTime;            // datetime，DB 默认 CURRENT_TIMESTAMP ON UPDATE
}
