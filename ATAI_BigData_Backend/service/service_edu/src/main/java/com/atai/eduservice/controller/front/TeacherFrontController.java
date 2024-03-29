package com.atai.eduservice.controller.front;

import com.atai.eduservice.entity.EduTeacher;
import com.atai.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atai.commonutils.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description="讲师页功能")
@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;


    //1 分页查询讲师的方法
    @ApiOperation(value = "分页查询讲师的方法")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        //返回分页所有数据
        return R.success().data(map);
    }

    //2 讲师详情的功能
    @ApiOperation(value = "讲师详情的功能")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //1 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        return R.success().data("teacher",eduTeacher);
    }
}












