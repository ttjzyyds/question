onload = () => {
  $('#headerDivB').text('问卷分析');
  let info = $util.getPageParam('info');
  fillInfo(info);
};

const fillInfo = (info) => {
  var str = "";
  for (var i = 0; i < info.length; i++) {
    divId = "main" + i;
    str += '<div id="' + divId + '"' + ' style="width: 600px;height:200px;"></div>';
  }
  $('#container').html(str);

  for (var i = 0; i < info.length; i++) {
    divId = "main" + i;
    var count = [];
    var type = [];
    for (var j = 0; j < info[i].length; j++) {
      count.push(info[i][j].count);
      type.push(info[i][j].type);
    }

    var title = "";
    if (i == info.length - 1) {
      title = "多选题汇总";
    } else if (i == info.length - 2) {
      title = "单选题汇总";
    } else {
      title = '第' + (i + 1) + '题';
    }

    // 计算百分比
    var totalCount = count.reduce((a, b) => a + b, 0);
    var percent = count.map(c => ((c / totalCount) * 100).toFixed(2) + "%");

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById(divId));

    // 指定图表的配置项和数据
    var option = {
      title: {
        text: title
      },
      tooltip: {},
      legend: {
        data: ['选项']
      },
      xAxis: {
        data: type
      },
      yAxis: {},
      series: [
        {
          name: '数量',
          type: 'bar',
          data: count,
          barWidth: 17,
          itemStyle: {
            normal: {
              color: function (params) {
                var colorList = [
                  '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                  '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                  '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                ];
                return colorList[params.dataIndex];
              },
            }
          },
          label: {
            show: true,
            position: 'top',
            formatter: function (params) {
              var index = params.dataIndex;
              return params.value + ' (' + percent[index] + ')';
            }
          }
        }
      ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
  }
};
