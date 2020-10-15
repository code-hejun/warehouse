package com.hejun.bus.vo;

import com.hejun.bus.entity.Provider;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderVo extends Provider {

    private Integer page=1;
    private Integer limit=10;

    /**
     * 批量删除供应商，存放供应商ID的数组
     */
    private Integer[] ids;

}
