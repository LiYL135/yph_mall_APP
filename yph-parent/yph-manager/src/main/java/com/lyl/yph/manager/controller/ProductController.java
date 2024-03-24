package com.lyl.yph.manager.controller;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.manager.service.ProductService;
import com.lyl.yph.model.dto.product.ProductDto;
import com.lyl.yph.model.entity.product.Product;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品管理： 商品基本数据、商品sku、商品详情
 */
@RestController
@RequestMapping(value="/admin/product/product")
public class ProductController {

    @Autowired
    private ProductService productService ;

    // 分页查询商品数据
    @GetMapping(value = "/{page}/{limit}")
    public Result<PageInfo<Product>> findByPage(@PathVariable Integer page, @PathVariable Integer limit, ProductDto productDto) {
        PageInfo<Product> pageInfo = productService.findByPage(page, limit, productDto);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    //保存商品数据 product=其他+sku+details：商品规格+商品详情
    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        productService.save(product);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //根据id查询商品详情，修改时数据回显
    @GetMapping("/getById/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.build(product , ResultCodeEnum.SUCCESS) ;
    }

    //保存修改数据 ??参数
    @PutMapping("/updateById")
    public Result updateById(@Parameter(name = "product", description = "请求参数实体类", required = true)
                                 @RequestBody Product product) {
        productService.updateById(product);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //删除商品
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@Parameter(name = "id", description = "商品id", required = true) @PathVariable Long id) {
        productService.deleteById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //商品审核
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id, @PathVariable Integer auditStatus) {
        productService.updateAuditStatus(id, auditStatus);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //商品 上架 |下架
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        productService.updateStatus(id, status);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}