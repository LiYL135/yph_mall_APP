package com.lyl.yph.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.lyl.yph.manager.mapper.CategoryMapper;
import com.lyl.yph.model.vo.product.CategoryExcelVo;

import java.util.List;

/**
 * 监听器,监听解析到的数据，用于easyexcel
 * @param <T>
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {

    /**
       每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    //获取mapper对象
    private CategoryMapper categoryMapper;
    //构造方式传递mapper
    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    // 每解析一行数据就会调用一次该方法
    @Override
    public void invoke(T o, AnalysisContext analysisContext) {  
        CategoryExcelVo data = (CategoryExcelVo)o;
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            // 调用方法一次性批量添加数据库里面
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // excel解析完毕以后需要执行的代码
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    // 保存的方法
    private void saveData() {
        categoryMapper.batchInsert(cachedDataList);
    }
}