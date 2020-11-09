// 基于准备好的dom，初始化echarts实例
var myChart1 = echarts.init(document.getElementById('charts1'),'vintage');
var myChart2 = echarts.init(document.getElementById('charts2'),'dark');
var myChart3 = echarts.init(document.getElementById('charts3'));
var myChart4 = echarts.init(document.getElementById('charts4'));
var myChart5 = echarts.init(document.getElementById('charts5'));
var myChart6 = echarts.init(document.getElementById('charts6'));
var myChart7 = echarts.init(document.getElementById('charts7'));
var myChart8 = echarts.init(document.getElementById('charts8'));
var myChart9 = echarts.init(document.getElementById('charts9'));
// 指定图表的配置项和数据
var option = {
    title: {
        text: 'ECharts 入门示例'
    },
    tooltip: {},
    legend: {
        data:['销量']
    },
    xAxis: {
        data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
    }]
};
myChart1.setOption(option);
myChart2.setOption(option);
myChart3.setOption(option);
myChart4.setOption(option);
myChart5.setOption(option);
myChart6.setOption(option);
myChart7.setOption(option);
myChart8.setOption(option);
myChart9.setOption(option);





