package com.lyl.yph.manager.controller;

import com.lyl.yph.manager.service.ProductUnitService;
import com.lyl.yph.model.entity.base.ProductUnit;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * 商品单元数据管理
 */
@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {

    @Autowired
    private ProductUnitService productUnitService;

    //查询所有商品单元数据
    @GetMapping("findAll")
    public Result<List<ProductUnit>> findAll() {
        List<ProductUnit> productUnitList = productUnitService.findAll();
        return Result.build(productUnitList , ResultCodeEnum.SUCCESS) ;
    }
}