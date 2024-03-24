package com.lyl.yph.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.lyl.yph.model.entity.product.Category;
import com.lyl.yph.product.mapper.CategoryMapper;
import com.lyl.yph.product.service.CategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//商品分类
//@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //查询一级分类数据
    //category:one 统一起的名字
    @Override
    public List<Category> findOneCategory() {
        //1 查询redis，是否有所有一级分类
        String categoryOneJson = redisTemplate.opsForValue().get("category:one");

        //2 如果redis包含所有一级分类，直接返回
        if(StringUtils.hasText(categoryOneJson)){
            //categoryOneJson字符串转换成List<Category>
            List<Category> exitCategoryList = JSON.parseArray(categoryOneJson, Category.class);
//            log.info("从Redis缓存中查询到了所有的一级分类数据");
            return exitCategoryList ;
        }

        //3 如果redis里没有所有一级分类，查询数据库，把数据库查询内容返回，并把查询内容放到redis中
        List<Category> categoryList = categoryMapper.findOneCategory();
//        log.info("从数据库中查询到了所有的一级分类数据");
        redisTemplate.opsForValue().set("category:one" , JSON.toJSONString(categoryList) ,
                7 , TimeUnit.DAYS); //过期时间7天
        return categoryList ;
    }

    //获取分类树形数据
    //category::all
    @Cacheable(value = "category" , key = "'all'") //使用了spring cache
    @Override
    public List<Category> findCategoryTree() {
        //所有分类
        List<Category> categoryList = categoryMapper.findAll();
        //遍历所有分类list集合，通过条件 parentid=0 得到所有一级分类  filter为条件过滤
        List<Category> oneCategoryList = categoryList.stream().filter(
                item -> item.getParentId().longValue() == 0)
                .collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(oneCategoryList)) {
            //遍历
            oneCategoryList.forEach(oneCategory -> {
                //二级分类
                List<Category> twoCategoryList = categoryList.stream().filter(
                        item -> item.getParentId().longValue() == oneCategory.getId().longValue())
                        .collect(Collectors.toList());
                oneCategory.setChildren(twoCategoryList);

                if(!CollectionUtils.isEmpty(twoCategoryList)) {
                    twoCategoryList.forEach(twoCategory -> {
                        //三级分类
                        List<Category> threeCategoryList = categoryList.stream().filter(
                                item -> item.getParentId().longValue() == twoCategory.getId().longValue())
                                .collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }
        return oneCategoryList;
    }
}