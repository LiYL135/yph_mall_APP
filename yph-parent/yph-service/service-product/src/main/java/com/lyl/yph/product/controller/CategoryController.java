package com.lyl.yph.product.controller;

import com.lyl.yph.model.entity.product.Category;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "分类接口管理")
@RestController
@RequestMapping(value="/api/product/category")
@SuppressWarnings({"unchecked", "rawtypes"})
//@CrossOrigin //跨域
public class CategoryController {
   
   @Autowired
   private CategoryService categoryService;

   @Operation(summary = "获取分类树形数据")
   @GetMapping("findCategoryTree")
   public Result<List<Category>> findCategoryTree(){
      List<Category> list = categoryService.findCategoryTree();
      return Result.build(list,  ResultCodeEnum.SUCCESS);
   }

}