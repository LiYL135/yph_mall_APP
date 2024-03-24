package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 分类管理
 * @Date: 2024/2/1 10:27
 */
@Mapper
public interface CategoryMapper {

    // 根据分类id查询它下面的所有的子分类数据
    List<Category> selectByParentId(Long parentId);

    // 查询该分类下子分类的数量
    int countByParentId(Long id);

    // 查询所有分类，返回集合
    List<Category> selectAll();

    // 批量插入分类数据，供ExcelListener监听器使用
    public abstract void batchInsert(List<Category> categoryList);

}
