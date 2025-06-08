package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.hmdp.utils.RedisConstants.CACHE_SHOP_TYPE_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    private IShopTypeService typeService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 首页类型缓存
     * @return
     */
    public Result queryList() {

        //1.缓存中查询
        String shopTypeJson  = stringRedisTemplate.opsForValue().get(CACHE_SHOP_TYPE_KEY);
        //2.存在，直接返回
        if(StrUtil.isNotBlank(shopTypeJson )){
            // 存在，直接返回
            List<ShopType> shopTypes = JSONUtil.parseArray(shopTypeJson).toList(ShopType.class);
            return Result.ok(shopTypes);
        }
        //3.不存在，数据库查询
        List<ShopType> list = list();
        //4.数据库中不存在，返回错误
        if (list == null) {
            return Result.fail("店铺类型不存在");
        }
        //5.写入缓存
        stringRedisTemplate.opsForValue().set(CACHE_SHOP_TYPE_KEY, JSONUtil.toJsonStr(list));

        return Result.ok(list);
    }
}
