package com.atai.compentition.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atai.compentition.entity.excel.CompeletionResult;
import com.atai.compentition.service.AtaiUserCompetitionService;
import com.atai.servicebase.exceptionhandler.MSException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CompetitionExcelListener extends AnalysisEventListener<CompeletionResult> {
    //手动注入
    public AtaiUserCompetitionService ataiUserCompetitionService;

    List<CompeletionResult> list = new ArrayList<CompeletionResult>();
    String userId ;
    String compentitionId;
    Map result = null;//结果集
    public CompetitionExcelListener(){}
    public CompetitionExcelListener(AtaiUserCompetitionService ataiUserCompetitionService) {
        this.ataiUserCompetitionService = ataiUserCompetitionService;
    }


    public CompetitionExcelListener(AtaiUserCompetitionService ataiUserCompetitionService, Map result, String userId, String competitionId) {
        this.ataiUserCompetitionService = ataiUserCompetitionService;
        this.result = result;
        this.userId = userId;
        this.compentitionId = competitionId;
    }


    //读取excel内容，一行一行进行读取
    @Override
    public void invoke(CompeletionResult compeletionResult, AnalysisContext analysisContext) {
        if(compeletionResult == null){
            throw new MSException(20001,"文件数据为空");
        }
//        System.out.println(JSON.toJSONString(compeletionResult));
        list.add(compeletionResult);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        int score =0;
        //获取提交的所有结果
        for(CompeletionResult a:list){
            String tmp= (String) result.get((int)Double.parseDouble(a.getId())+"");
            if(((int)Double.parseDouble(a.getResult())+"").equals(tmp)){
                score+=1;
            }
        }
        //计算分数，入库
        System.out.println("'score:"+score);
        //当前时间
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        Boolean flag = ataiUserCompetitionService.updateByUseridCompetitionId(userId, compentitionId, score, date);
        System.out.println(flag);
    }
}
