package com.lyl.yph.manager.service;

import com.lyl.yph.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 分类管理
 * @Date: 2024/2/1 10:24
 */
public interface CategoryService {

    // 根据parentId获取下级节点
    List<Category> findByParentId(Long parentId);

    // 分类的导出功能
    void exportData(HttpServletResponse response);

    // 导入功能，导入+读取excel
    void importData(MultipartFile file);
}
