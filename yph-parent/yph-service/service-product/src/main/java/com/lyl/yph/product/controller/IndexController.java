package com.lyl.yph.product.controller;

import com.lyl.yph.model.entity.product.Category;
import com.lyl.yph.model.entity.product.ProductSku;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.model.vo.h5.IndexVo;
import com.lyl.yph.product.service.CategoryService;
import com.lyl.yph.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value="/api/product/index")
@SuppressWarnings({"unchecked", "rawtypes"})
//@CrossOrigin //跨域
public class IndexController {

   @Autowired
   private CategoryService categoryService;

   @Autowired
   private ProductService productService;

   @Operation(summary = "获取首页数据")
   @GetMapping
   public Result<IndexVo> findData(){
      //商品一级分类
      List<Category> categoryList = categoryService.findOneCategory();
      //畅销商品列表，按销售排序，获取前10条记录
      List<ProductSku> productSkuList = productService.findProductSkuBySale();

      //封装数据到 indexVo 对象中
      IndexVo indexVo = new IndexVo() ;
      indexVo.setCategoryList(categoryList);
      indexVo.setProductSkuList(productSkuList);
      return Result.build(indexVo , ResultCodeEnum.SUCCESS);
   }

}