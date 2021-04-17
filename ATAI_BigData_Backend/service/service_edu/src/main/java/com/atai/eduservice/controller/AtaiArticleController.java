package com.atai.eduservice.controller;


import com.atai.commonutils.result.R;
import com.atai.eduservice.entity.frontvo.ArticleContentFront;
import com.atai.eduservice.entity.frontvo.ArticleFrontVo;
import com.atai.eduservice.entity.vo.ArticleQuery;
import com.atai.eduservice.service.AtaiArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author linshengbin
 * @since 2021-04-14
 */
@Api(description="文章相关")
@RestController
@RequestMapping("/eduservice/atai-article")
public class AtaiArticleController {

    @Autowired
    private AtaiArticleService ataiArticleService;

    //1 分页条件查询文章列表
    //rest风格
    @ApiOperation(value = "分页条件查询文章列表")
    @PostMapping("pageArticleCondition/{current}/{limit}")
    public R findAllArticleCondition(@PathVariable long current, @PathVariable long limit,
                                     @RequestBody(required = false)  ArticleQuery articleQuery) {
        Page<ArticleFrontVo> pageArticle = new Page<>(current,limit);
        IPage<ArticleFrontVo> result = ataiArticleService.findAllArticleCondition(pageArticle,articleQuery);
        List<ArticleFrontVo> list = result.getRecords();
        boolean hasNext = result.getTotal()>current*limit?true:false;//下一页
        boolean hasPrevious = current>1?true:false;//上一页
        //调用service的方法实现查询所有的操作
        return R.success().data("total",result.getTotal())
                .data("records",list).data("current",result.getCurrent())
                .data("pages",result.getPages())
                .data("hasNext",hasNext)
                .data("hasPrevious",hasPrevious);

    }

    //2 文章id查询文章内容
    //rest风格
    @ApiOperation(value = "文章id查询文章内容")
    @GetMapping("getArticleById/{id}")
    public R getArticleById(@PathVariable String id) {
        ArticleContentFront articleContentFront = ataiArticleService.getArticleById(id);
        return R.success().data("data",articleContentFront);
    }
}

