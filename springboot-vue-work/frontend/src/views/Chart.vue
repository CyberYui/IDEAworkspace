<template>
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="testChart"></div>
</template>

<script>
    // 通过 echarts.init 方法初始化一个 echarts 实例并通过 setOption 方法生成一个简单图
    // 在 main.js 中有定义Vue.prototype.$echarts = echarts 所以这里可以用 $echarts 调用 init 函数
    export default {
        name: "Chart",
        // 使用的模版页面 https://echarts.apache.org/examples/zh/editor.html?c=bar-label-rotation
        mounted() {
            // 配置
            let app = {};
            let chartDom = document.getElementById('testChart');
            // 基于准备好的dom，初始化ECharts实例
            // 要想在 vue 文件中使用 ECharts ,必须用这样的方法
            let myChart = this.$echarts.init(chartDom);
            let option;
            // 传输列表
            let posList = [
                'left', 'right', 'top', 'bottom',
                'inside',
                'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
                'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
            ];

            app.configParameters = {
                rotate: {
                    min: -90,
                    max: 90
                },
                align: {
                    options: {
                        left: 'left',
                        center: 'center',
                        right: 'right'
                    }
                },
                verticalAlign: {
                    options: {
                        top: 'top',
                        middle: 'middle',
                        bottom: 'bottom'
                    }
                },
                position: {
                    options: posList.reduce(function (map, pos) {
                        map[pos] = pos;
                        return map;
                    }, {})
                },
                distance: {
                    min: 0,
                    max: 100
                }
            };

            app.config = {
                rotate: 90,
                align: 'left',
                verticalAlign: 'middle',
                position: 'insideBottomRight',
                distance: 15,
            };


            var labelOption = {
                show: true,
                position: app.config.position,
                distance: app.config.distance,
                align: app.config.align,
                verticalAlign: app.config.verticalAlign,
                rotate: app.config.rotate,
                // 显示格式,只显示数值在轴上
                formatter: '{c} ',
                fontSize: 18,
                rich: {
                    name: {
                    }
                }
            };

            option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: ['主题一', '主题二', '主题三', '主题四']
                },
                xAxis: [
                    {
                        type: 'category',
                        axisTick: {show: false},
                        data: ['主题一', '主题二', '主题三', '主题四', '主题五']
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '官方文档',
                        type: 'bar',
                        barGap: 0,
                        label: labelOption,
                        emphasis: {
                            focus: 'series'
                        },
                        data: [320, 332, 301, 334, 390]
                    },
                    {
                        name: '宣传手册',
                        type: 'bar',
                        label: labelOption,
                        emphasis: {
                            focus: 'series'
                        },
                        data: [220, 182, 191, 234, 290]
                    },
                    {
                        name: '个人文档',
                        type: 'bar',
                        label: labelOption,
                        emphasis: {
                            focus: 'series'
                        },
                        data: [150, 232, 201, 154, 190]
                    },
                    {
                        name: '实际情况',
                        type: 'bar',
                        label: labelOption,
                        emphasis: {
                            focus: 'series'
                        },
                        data: [98, 77, 101, 99, 40]
                    }
                ]
            };
            option && myChart.setOption(option);
        }
    };
</script>

<style scoped>
    #testChart{
        width: 1000px;
        height: 800px;
    }
</style>