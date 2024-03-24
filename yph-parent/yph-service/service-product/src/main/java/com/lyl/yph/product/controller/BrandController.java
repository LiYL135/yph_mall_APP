package com.lyl.yph.product.controller;

import com.lyl.yph.model.entity.product.Brand;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "品牌管理")
@RestController
@RequestMapping(value="/api/product/brand")
@SuppressWarnings({"unchecked", "rawtypes"}) //告诉编译器忽略指定的警告，不用在编译完成后出现警告信息
public class BrandController {
   
   @Autowired
   private BrandService brandService;
   
   @Operation(summary = "获取全部品牌")
   @GetMapping("findAll")
   public Result<List<Brand>> findAll() {
      List<Brand> list = brandService.findAll();
      return Result.build(list, ResultCodeEnum.SUCCESS);
   }

}