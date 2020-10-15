package com.hejun.bus.vo;

import com.hejun.bus.entity.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerVo extends Customer {

    private Integer page=1;
    private Integer limit=10;

    private Integer[] ids;

}
