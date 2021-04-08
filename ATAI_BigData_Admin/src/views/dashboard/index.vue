<template>
  <div class="dashboard-container">
    <!-- <img src="../../../../ATAI_BigData_Front/assets/img/logo.png" />
    <div class="dashboard-text">Welcome to ATAI_BigData backend !</div>
    <div class="dashboard-text">用户名:{{ name }}</div>
    <div class="dashboard-text">用户角色:<span v-for="role in roles" :key="role">{{ role }}</span></div> -->
    <div class="chart-container">
      <div id="chart" class="chart" style="height:500px;width:100%" />
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import echarts from 'echarts'
export default {
  data() {
    return {
    option:{
  title: {
        text: '一天访问分布',
        subtext: '纯属虚构'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    toolbox: {
        show: true,
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['00:00', '01:15', '02:30', '03:45', '05:00', '06:15', '07:30', '08:45', '10:00', '11:15', '12:30', '13:45', '15:00', '16:15', '17:30', '18:45', '20:00', '21:15', '22:30', '23:45']
    },
    yAxis: {
        type: 'value',
        axisLabel: {
            formatter: '{value}'
        },
        axisPointer: {
            snap: true
        }
    },
    visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            lte: 6,
            color: 'green'
        }, {
            gt: 6,
            lte: 8,
            color: 'red'
        }, {
            gt: 8,
            lte: 14,
            color: 'green'
        }, {
            gt: 14,
            lte: 17,
            color: 'red'
        }, {
            gt: 17,
            color: 'green'
        }]
    },
    series: [
        {
            name: '访问量',
            type: 'line',
            smooth: true,
            data: [300, 280, 250, 260, 270, 300, 550, 500, 400, 390, 380, 390, 400, 500, 600, 750, 800, 700, 600, 400],
            markArea: {
                itemStyle: {
                    color: 'rgba(255, 173, 177, 0.4)'
                },
                data: [ [{
                    name: '早高峰',
                    xAxis: '07:30'
                }, {
                    xAxis: '10:00'
                }], [{
                    name: '晚高峰',
                    xAxis: '17:30'
                }, {
                    xAxis: '21:15'
                }] ]
            }
        }
    ]
 
    
    }
    }


  },
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  mounted:function(){
    this.setChart();
  },
   methods: {
  setChart() {
      this.chart = echarts.init(document.getElementById('chart'))

      this.chart.setOption(this.option)
     
  },
 
  
  }
  
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 23px;
    line-height: 22px;
    color: #408ad3;
    margin-left: 30px;
    margin-top: 25px;
  }
}
</style>
