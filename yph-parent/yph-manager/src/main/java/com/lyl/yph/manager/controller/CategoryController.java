package com.lyl.yph.manager.controller;

import com.lyl.yph.manager.service.CategoryService;
import com.lyl.yph.model.entity.product.Category;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping(value="/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "根据parentId获取下级节点")
    @GetMapping(value = "/findByParentId/{parentId}")
    public Result<List<Category>> findByParentId(@PathVariable Long parentId) {
        List<Category> list = categoryService.findByParentId(parentId);
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 分类的导出功能
     * @param response
     */
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }

    /**
     * 导入功能，导入+读取excel
     * @param file
     * @return
     */
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        categoryService.importData(file);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}