<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="ECharts/echarts.min.js"></script>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript">
        $(function () {
            getCharts();
        })
        function getCharts() {
            $.get("workbench/transaction/getCharts.do",{},function (data) {
                var dataListName=[];
                $.each(data.dataList,function (i,n) {
                    dataListName[i]=n.name;
                })
                console.log(dataListName);
                var myChart = echarts.init(document.getElementById('main'));
                // 指定图表的配置项和数据
                option = {
                    title: {
                        text: '交易漏斗图',
                        subtext:'统计交易阶段数量的漏斗图'
                    },
                    toolbox: {
                        feature: {
                            dataView: { readOnly: true },//这是设置数据视图是否可以修改的，true表示不可以
                            restore: {},
                            saveAsImage: {}
                        }
                    },
                   /* legend: {
                        data: dataListName//可以获得名字，但是太长了，影响其他样式不好看
                    },*/
                    series: [
                        {
                            name: '交易漏斗图',//这个名字可以更改，改的是数据视图的名字，下面那个type是数据视图的类型名
                            type: 'funnel',
                            left: '10%',
                            top: 60,
                            bottom: 60,
                            width: '80%',
                            min: 0,
                            max: data.max,
                            minSize: '0%',
                            maxSize: '100%',
                            sort: 'descending',
                            gap: 2,
                            label: {
                                show: true,
                                position: 'inside'
                            },
                            labelLine: {
                                length: 10,
                                lineStyle: {
                                    width: 1,
                                    type: 'solid'
                                }
                            },
                            itemStyle: {
                                borderColor: '#fff',
                                borderWidth: 1
                            },
                            emphasis: {
                                label: {
                                    fontSize: 20
                                }
                            },
                            data: data.dataList/*[
                            { value: 60, name: 'Visit' },
                            { value: 40, name: 'Inquiry' },
                            { value: 20, name: 'Order' },
                            { value: 80, name: 'Click' },
                            { value: 100, name: 'Show' }
                        ]*/
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            },"json")
        }
    </script>
</head>
<body>
    <div id="main" style="width: 1000px;height:400px;"></div>
</body>
</html>
