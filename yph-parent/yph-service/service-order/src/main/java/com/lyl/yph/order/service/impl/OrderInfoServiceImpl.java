package com.lyl.yph.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.yph.common.exception.GuiguException;
import com.lyl.yph.feign.cart.CartFeignClient;
import com.lyl.yph.feign.product.ProductFeignClient;
import com.lyl.yph.feign.user.UserFeignClient;
import com.lyl.yph.model.dto.h5.OrderInfoDto;
import com.lyl.yph.model.entity.h5.CartInfo;
import com.lyl.yph.model.entity.order.OrderInfo;
import com.lyl.yph.model.entity.order.OrderItem;
import com.lyl.yph.model.entity.order.OrderLog;
import com.lyl.yph.model.entity.product.ProductSku;
import com.lyl.yph.model.entity.user.UserAddress;
import com.lyl.yph.model.entity.user.UserInfo;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.model.vo.h5.TradeVo;
import com.lyl.yph.order.mapper.OrderInfoMapper;
import com.lyl.yph.order.mapper.OrderItemMapper;
import com.lyl.yph.order.mapper.OrderLogMapper;
import com.lyl.yph.order.service.OrderInfoService;
import com.lyl.yph.utils.AuthContextUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private CartFeignClient cartFeignClient ;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    // 确认下单
    @Override
    public TradeVo getTrade() {

        // 获取当前登录的用户的id
        //Long userId = AuthContextUtil.getUserInfo().getId();

        //远程调用： 获取选中的购物项列表数据
        List<CartInfo> cartInfoList = cartFeignClient.getAllCkecked();

        // 将 购物项数据 转换成 订单明细数据
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        }

        // 计算总金额 ？？
        BigDecimal totalAmount = new BigDecimal(0);
        for(OrderItem orderItem : orderItemList) {
            // 商品单价 * 商品数据 -- 最后 -- 总和
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        //订单结算界面数据对象
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);
        return tradeVo;
        
    }

    // 提交订单
    @Transactional
    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        //1 数据校验
        //获取订单明细
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        if (CollectionUtils.isEmpty(orderItemList)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        //！远程调用 -- 订单明细 -- 获取skuId - 根据skuId获取商品sku数据
        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            if(null == productSku) {
                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
            }
            //校验库存  订单中的商品数量 > 库中的商品数量
            if(orderItem.getSkuNum().intValue() > productSku.getStockNum().intValue()) {
                //库存不足
                throw new GuiguException(ResultCodeEnum.STOCK_LESS);
            }
        }

        //2 校验通过 构建订单数据，保存订单
        //threadLocal中获取用户信息
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderInfo orderInfo = new OrderInfo();
        //订单编号 时间：System.currentTimeMillis
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        //用户收货地址信息  ！远程调用--根据收货地址id，获取用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoDto.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2); //支付类型
        orderInfo.setOrderStatus(0); //订单类型
        orderInfoMapper.save(orderInfo); //保存 orderInfo

        //保存订单明细 orderItem
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }

        //记录日志 orderLog
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId()); //订单id
        orderLog.setProcessStatus(0); //订单状态
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);

        //！远程调用service-cart微服务接口 清空购物车数据
        cartFeignClient.deleteChecked();

        //返回订单id
        return orderInfo.getId();
    }

    //根据订单id，获取订单信息
    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.getById(orderId);
    }

    // 立即购买
    @Override
    public TradeVo buy(Long skuId) {
        //！远程调用- 通过skuId查询商品
        ProductSku productSku = productFeignClient.getBySkuId(skuId);

        List<OrderItem> orderItemList = new ArrayList<>();
        //订单明细
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1); //数量 1
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItemList.add(orderItem);

        // 计算总金额
        BigDecimal totalAmount = productSku.getSalePrice();
        //订单界面数据：订单明细+总金额
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);

        // 返回
        return tradeVo;
    }

    // 获取订单分页列表
    @Override
    public PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus) {
        PageHelper.startPage(page, limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        //根据 用户id、订单状态，分页获取订单信息列表
        List<OrderInfo> orderInfoList = orderInfoMapper.findUserPage(userId, orderStatus);

        orderInfoList.forEach(orderInfo -> {
            //根据订单id获取订单明细
            List<OrderItem> orderItem = orderItemMapper.findByOrderId(orderInfo.getId());
            orderInfo.setOrderItemList(orderItem);
        });

        return new PageInfo<>(orderInfoList);
    }

    // 根据orderNo 获取订单信息
    @Override
    public OrderInfo getByOrderNo(String orderNo) {
        //订单信息
        OrderInfo orderInfo = orderInfoMapper.getByOrderNo(orderNo);
        //订单明细
        List<OrderItem> orderItem = orderItemMapper.findByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItem);
        return orderInfo;
    }

    //支付完成，更新订单状态
    @Transactional
    @Override
    public void updateOrderStatus(String orderNo) {

        // 更新订单状态
        OrderInfo orderInfo = orderInfoMapper.getByOrderNo(orderNo);
        orderInfo.setOrderStatus(1); //1 待发货
        orderInfo.setPayType(2); // 支付宝方式 2
        orderInfo.setPaymentTime(new Date());
        orderInfoMapper.updateById(orderInfo);

        // 记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(1);
        orderLog.setNote("支付宝支付成功");
        orderLogMapper.save(orderLog);
    }

}