package com.lyl.yph.manager.controller;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.manager.service.ProductSpecService;
import com.lyl.yph.model.entity.product.ProductSpec;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品规格数据
 */

@RestController
@RequestMapping(value="/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService ;

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<ProductSpec>> findByPage(@PathVariable Integer page, @PathVariable Integer limit) {
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(page, limit);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 添加商品规格
     * @param productSpec
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 修改功能
     */
    @PutMapping("updateById")
    public Result updateById(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 删除功能
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result removeById(@PathVariable Long id) {
        productSpecService.deleteById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 查询所有商品规格
     * @return
     */
    @GetMapping("findAll")
    public Result findAll() {
        List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }

}