package com.lyl.yph.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.lyl.yph.cart.service.CartService;
import com.lyl.yph.feign.product.ProductFeignClient;
import com.lyl.yph.model.entity.h5.CartInfo;
import com.lyl.yph.model.entity.product.ProductSku;
import com.lyl.yph.utils.AuthContextUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 统一生成redis中的key
     * @param userId
     * @return
     */
    private String getCartKey(Long userId) {
        //定义key user:cart:userId    可使用枚举类？？
        return "user:cart:" + userId;
    }

    /**
     * 添加购物车
     */
    @Override
    public void addToCart(Long skuId, Integer skuNum) {

        //1 获取当前登录用户的id（作为redis的hash类型的key值）
        // ThreadLocal中获取
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        //2 因为购物车放到redis里面
        //hash类型  key: userId  field: skuId  value: sku信息CartInfo
        //从redis里面获取购物车数据，根据用户id + skuId获取（hash类型key + field）？？？去学redis
        Object cartInfoObj =
                redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        CartInfo cartInfo = null ;

        //3 如果购物车中有该商品，则商品数量 相加！
        if(cartInfoObj != null) {  //添加到购物车商品已经存在的，把商品数量相加
            //cartInfoObj -- CartInfo
            cartInfo = JSON.parseObject(cartInfoObj.toString() , CartInfo.class);
            //数量相加
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            cartInfo.setIsChecked(1);  //isChecked表示选中状态，1表示选中
            cartInfo.setUpdateTime(new Date());
        }else {
            //4 当购物车中没有该商品的时候，则直接添加到购物车！
            cartInfo = new CartInfo();

            // 购物车数据是从商品详情得到 {skuInfo}  远程调用
            //远程调用实现：通过nacos + openFeign实现 根据skuId获取商品sku信息
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            // 设置相关数据到cartInfo中
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());

        }

        //添加到redis里面
        redisTemplate.opsForHash().put(cartKey , String.valueOf(skuId) , JSON.toJSONString(cartInfo));
    }

    //查询购物车
    @Override
    public List<CartInfo> getCartList() {

        // 获取当前登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId); //获得cartKey

        // 获取数据
        List<Object> cartInfoList = redisTemplate.opsForHash().values(cartKey);

        if (!CollectionUtils.isEmpty(cartInfoList)) {
            List<CartInfo> infoList = cartInfoList.stream().map(
                    cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class)) //字符串转为对象
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())) //根据创建时间排序
                    .collect(Collectors.toList()); //list集合
            return infoList ;
        }

        return new ArrayList<>() ;
    }

    //删除购物车商品
    @Override
    public void deleteCart(Long skuId) {

        // 获取当前登录的用户数据，cartKey
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        //删除 缓存对象 数据
        redisTemplate.opsForHash().delete(cartKey  ,String.valueOf(skuId)) ;
    }

    //更新购物车商品选中状态
    @Override
    public void checkCart(Long skuId, Integer isChecked) {

        // 获取当前登录的用户数据
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        // 判断是否有数据
        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));
        if(hasKey) {
            //获取商品数据
            String cartInfoJSON = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId)).toString();
            CartInfo cartInfo = JSON.parseObject(cartInfoJSON, CartInfo.class); //转为对象
            cartInfo.setIsChecked(isChecked); //设置选中状态
            //存入redis
            redisTemplate.opsForHash().put(cartKey , String.valueOf(skuId) , JSON.toJSONString(cartInfo));
        }

    }

    //更新购物车商品全部选中状态
    public void allCheckCart(Integer isChecked) {

        // 获取当前登录的用户数据
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        // 获取所有的购物项数据
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        //遍历设置所有选中状态
        if(!CollectionUtils.isEmpty(objectList)) {
            objectList.stream().map(cartInfoJSON -> {
                CartInfo cartInfo = JSON.parseObject(cartInfoJSON.toString(), CartInfo.class);
                cartInfo.setIsChecked(isChecked);
                return cartInfo ;
            }).forEach(cartInfo ->   //存入redis
                    redisTemplate.opsForHash().put(cartKey , String.valueOf(cartInfo.getSkuId()) , JSON.toJSONString(cartInfo)));

        }
    }

    //清空购物车
    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

    //获取购物项数据 - 选中的购物车 ---订单界面
    @Override
    public List<CartInfo> getAllCkecked() {
        //获取userId 构建key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        //redis中获取所有的购物项数据
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(objectList)) {
            List<CartInfo> cartInfoList = objectList.stream().map(cartInfoJSON ->
                            JSON.parseObject(cartInfoJSON.toString(), CartInfo.class)) //转为对象
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1) //过滤条件，选中状态
                    .collect(Collectors.toList()); //list
            return cartInfoList ;
        }
        return new ArrayList<>() ;
    }

    //删除生成订单的购物车商品
    @Override
    public void deleteChecked() {
        //获取userId，构建key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        //根据key获取redis所有value值
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(objectList)) {
            // 删除选中的购物项数据
            objectList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1) //找到选中的数据
                    .forEach(cartInfo -> redisTemplate.opsForHash()
                            .delete(cartKey , String.valueOf(cartInfo.getSkuId())));
        }
    }

}
