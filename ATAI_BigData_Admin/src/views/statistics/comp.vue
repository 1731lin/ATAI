<template>
  <div class="app-container">
   

    <div class="chart-container">
      <div id="chart" class="chart" style="height:500px;width:100%" />
    </div>
  </div>
</template>
<script>
//引入图表显示框架
import echarts from 'echarts'
//引入statistics.js文件
import statisticsApi from '@/api/sta/statistics'
export default {
    data() {
        return {
          
            option :{
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['算法大赛', '测试赛', '新人赛', '内部挑战赛']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value'
            },
            yAxis: {
                type: 'category',
                data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
            },
            series: [
                {
                    name: '算法大赛',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        show: true,
                        position: 'insideRight'
                    },
                    data: [320, 302, 301, 334, 390, 330, 320]
                },
                {
                    name: '测试赛',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        show: true,
                        position: 'insideRight'
                    },
                    data: [120, 132, 101, 134, 90, 230, 210]
                },
                {
                    name: '新人赛',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        show: true,
                        position: 'insideRight'
                    },
                    data: [220, 182, 191, 234, 290, 330, 310]
                },
                {
                    name: '内部挑战赛',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        show: true,
                        position: 'insideRight'
                    },
                    data: [150, 212, 201, 154, 190, 330, 410]
                }
            ]
        }
                }
    },
        mounted:function(){
        this.setChart();
    },
    methods:{
 
        //设置图表参数
        setChart() {
            debugger
            // 基于准备好的dom，初始化echarts实例
            this.chart = echarts.init(document.getElementById('chart'))


            this.chart.setOption(this.option)
        }
    }
}
</script>
