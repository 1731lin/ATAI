package com.atai.compentition.controller;

import com.atai.compentition.entity.vo.CompetitionQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atai.compentition.entity.AtaiCompetition;
import com.atai.compentition.service.AtaiCompetitionService;
import com.atai.commonutils.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 比赛 前端控制器
 * </p>
 *
 * @author linshengbin
 * @since 2021-01-08
 */

@Api(description="比赛管理")
@RestController
@RequestMapping("/atitcompetition/atai-competition")
//@CrossOrigin
public class AtaiCompetitionController {
    //访问地址：http://localhost:8999/atitcompetition/atai-competition/findAll
    //把service注入
    @Autowired
    private AtaiCompetitionService ataiCompetitionService ;

    //1 查询比赛表所有数据getUserCompetition
    //rest风格
    @ApiOperation(value = "所有比赛列表")
    @GetMapping("findAll")
    public R findAllCompetition() {
        //调用service的方法实现查询所有的操作
        List<AtaiCompetition> list = ataiCompetitionService.list(null);
        return R.success().data("items",list);
    }


    //2 逻辑删除比赛的方法
    @ApiOperation(value = "逻辑删除比赛")
    @DeleteMapping("{id}")
    public R removeCompetition(@ApiParam(name = "id", value = "比赛ID", required = true)
                               @PathVariable String id){
        Boolean flag = ataiCompetitionService.removeById(id);
        if (flag){
            return R.success();
        }else {
            return R.error();
        }
    }

    //3 分页查询比赛的方法
    //current 当前页
    //limit 每页记录数
    @ApiOperation(value = "分页查询")
    @GetMapping("pageCompetition/{current}/{limit}")
    public R pageListCompetition(@PathVariable long current,
                                 @PathVariable long limit){
        //创建page对象
        Page<AtaiCompetition> pageCompetition = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        ataiCompetitionService.page(pageCompetition,null);
        long total = pageCompetition.getTotal();//总记录数
        List<AtaiCompetition> records = pageCompetition.getRecords(); //数据list集合
        return R.success().data("total",total).data("records",records);
    }



    //4 添加比赛接口的方法
    @ApiOperation(value = "添加比赛")
    @PostMapping("addCompetition")
    public R addCompetition(@RequestBody AtaiCompetition AtaiCompetition) {
        boolean save = ataiCompetitionService.save(AtaiCompetition);
        if(save) {
            return R.success();
        } else {
            return R.error();
        }
    }

    //5 根据比赛id进行查询
    @ApiOperation(value = "根据比赛id进行查询")
    @GetMapping("getCompetition/{id}")
    public R getCompetition(@PathVariable String id) {
        AtaiCompetition AtaiCompetition = ataiCompetitionService.getById(id);
        return R.success().data("competition",AtaiCompetition);
    }

    //6 比赛修改功能
    @ApiOperation(value = "比赛修改")
    @PostMapping("updateCompetition")
    public R updateCompetition(@RequestBody AtaiCompetition AtaiCompetition) {
        boolean flag = ataiCompetitionService.updateById(AtaiCompetition);
        if(flag) {
            return R.success();
        } else {
            return R.error();
        }
    }

    //7 条件查询带分页的方法
    @ApiOperation(value = "分页查询带条件")
    @PostMapping("pageCompetitionCondition/{current}/{limit}")
    public R pageCompetitionCondition(@PathVariable long current, @PathVariable long limit,
                                      @RequestBody(required = false) CompetitionQuery cmpetitionQuery ){  //@RequestBody(required = false)参数值可以为空
        //创建page对象
        Page<AtaiCompetition> pageCompetition = new Page<>(current,limit);

        //构建条件
        QueryWrapper<AtaiCompetition> wrapper = new QueryWrapper<>();

        // 多条件组合查询
        // mybatis学过 动态sql
        String name = cmpetitionQuery.getName();
        Integer level = cmpetitionQuery.getLevel();
        String begin = cmpetitionQuery.getBegin();
        String end = cmpetitionQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        ataiCompetitionService.page(pageCompetition ,wrapper);
        long total = pageCompetition.getTotal();//总记录数
        List<AtaiCompetition> records = pageCompetition.getRecords(); //数据list集合
        return R.success().data("total",total).data("records",records);
    }
}
