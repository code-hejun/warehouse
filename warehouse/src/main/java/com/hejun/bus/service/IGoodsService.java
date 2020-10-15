package com.hejun.bus.service;

import com.hejun.bus.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hj
 * @since 2020-09-21
 */
public interface IGoodsService extends IService<Goods> {

    void deleteGoodsById(Integer id);

}
