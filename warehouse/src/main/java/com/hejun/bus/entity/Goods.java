package com.hejun.bus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hj
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String goodsname;

    private Integer providerid;

    private String produceplace;

    private String size;

    private String goodspackage;

    private String productcode;

    private String promitcode;

    private String description;

    private Double price;

    private Integer number;

    private Integer dangernum;

    private String goodsimg;

    private Integer available;

    @TableField(exist = false)
    private String providername;

}
