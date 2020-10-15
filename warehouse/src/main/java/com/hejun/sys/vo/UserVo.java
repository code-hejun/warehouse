package com.hejun.sys.vo;

import com.hejun.sys.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User {

    private Integer page = 1;
    private Integer limit = 10;

    private String code ;

}
