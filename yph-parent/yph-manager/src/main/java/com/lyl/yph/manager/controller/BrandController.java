package com.lyl.yph.manager.controller;

import com.github.pagehelper.PageInfo;
//import com.lyl.yph.common.log.annotation.Log;
//import com.lyl.yph.common.log.enums.OperatorType;
import com.lyl.yph.manager.service.BrandService;
import com.lyl.yph.model.entity.product.Brand;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理
 */
@RestController
@RequestMapping(value="/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService ;

    /**
     * 品牌列表（分页）
     * @param page
     * @param limit
     * @return
     */
//    @Log(title = "品牌列表",businessType = 0,operatorType = OperatorType.MANAGE)
    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<Brand>> findByPage(@PathVariable Integer page, @PathVariable Integer limit) {
        PageInfo<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 新增品牌
     * @param brand
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 品牌修改
     * @param brand
     * @return
     */
    @PutMapping("updateById")
    public Result updateById(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 删除品牌
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        brandService.deleteById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 查询所有品牌
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<Brand> list = brandService.findAll();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }

}