package com.lyl.yph.manager.controller;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.manager.service.CategoryBrandService;
import com.lyl.yph.model.dto.product.CategoryBrandDto;
import com.lyl.yph.model.entity.product.Brand;
import com.lyl.yph.model.entity.product.CategoryBrand;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类品牌管理
 */
@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService ;

    /**
     *  分页查询分类品牌关系信息
     * @param page
     * @param limit
     * @param CategoryBrandDto
     * @return
     */
    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<CategoryBrand>> findByPage(@PathVariable Integer page, @PathVariable Integer limit, CategoryBrandDto CategoryBrandDto) {
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findByPage(page, limit, CategoryBrandDto);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 添加功能
     * @param categoryBrand
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 修改功能
     * @param categoryBrand
     * @return
     */
    @PutMapping("updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 删除功能
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        categoryBrandService.deleteById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 根据分类id获取对应的品牌数据
     * @param categoryId
     * @return
     */
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId) {
        List<Brand> brandList =   categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(brandList , ResultCodeEnum.SUCCESS) ;
    }

}