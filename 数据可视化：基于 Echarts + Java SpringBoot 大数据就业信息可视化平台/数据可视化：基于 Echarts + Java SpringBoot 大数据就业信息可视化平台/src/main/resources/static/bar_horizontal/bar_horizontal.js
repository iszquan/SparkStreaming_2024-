function init_echart_bar_horizontal(container) {
  var chartDom = document.getElementById(container);
  var myChart = echarts.init(chartDom, window.gTheme);
  var option = {
    title: {
      text: "各个城市招聘数量",
      // top: "5%",
      // left: "2%",
      textStyle: {
        // color: "#3690be",
        color: "orange",
        fontSize: "12",
      },
    },
    tooltip: {
      trigger: "item",
      formatter: "{b}: {c} 个",
      position: function (p) {
        //其中p为当前鼠标的位置
        return [p[0] + 10, p[1] - 10];
      },
    },

    grid: {
      left: "5%",
      //   top: "30%",
      //   right: "5%",
      bottom: "2%",
      containLabel: true,
    },

    xAxis: [
      {
        name: "个",
        type: "value",
        min: 0,
        max: 100,
        nameLocation: "start",
        nameTextStyle: {
          color: "#3690be",
          //fontSize: 14,
        },
        axisLabel: {
          textStyle: {
            color: "rgba(255,255,255,.8)",
            //fontSize: 14,
          },
        },
        axisLine: {
          lineStyle: {
            color: "rgba(255,255,255,.8)",
          },
        },
        splitLine: {
          lineStyle: {
            color: "rgba(255,255,255,.1)",
          },
        },
      },
    ],
    yAxis: {
      // name: "城市",
      type: "category",
      data: [],
      axisLabel: {
        textStyle: {
          color: "rgba(255,255,255,.8)",
          //fontSize: 14,
        },
      },
      axisLine: {
        lineStyle: {
          color: "rgba(255,255,255,.8)",
        },
      },
      splitLine: {
        lineStyle: {
          color: "rgba(255,255,255,.1)",
        },
      },
    },
    series: [
      {
        // name: "个",
        type: "bar",
        xAxisIndex: 0,

        label: {
          show: true,
        },
        emphasis: {
          focus: "series",
        },
        data: [],
      },
    ],
  };

  // 使用刚指定的配置项和数据显示图表。
  myChart.setOption(option);
  window.addEventListener("resize", function () {
    myChart.resize();
  });
}

// // 读取本地json的方法获取数据
// function async_echart_bar_horizontal(container, filename) {
//   $.getJSON(filename).done(function (data) {
//     var myChart = echarts.init(
//       document.getElementById(container),
//       window.gTheme
//     );
//
//     myChart.setOption({
//       yAxis: { data: data["yAxis"]["data"] },
//       series: [{ data: data["series"][0]["data"] }],
//     });
//   }); //end $.getJSON}
// }

// ajax读取后端数据，传递参数为容器元素位置与后端接口url
function async_echart_bar_horizontal(container, url) {
  // 确保容器元素存在
  var containerElement = document.getElementById(container);
  if (!containerElement) {
    console.error('Container element not found:', container);
    return;
  }

  $.ajax({
    url: url, // 后端接口的URL
    dataType: 'json', // 期望返回的数据类型为JSON
    success: function (data) {
      // 初始化ECharts实例
      var myChart = echarts.init(containerElement, window.gTheme);

      // 使用从后端接口获取的数据设置图表选项
      // 确保数据结构与ECharts兼容
      if (data && data.yAxis && data.yAxis.data && data.series && data.series[0] && data.series[0].data) {
        myChart.setOption({
          yAxis: {
            data: data.yAxis.data
          },
          series: [{
            data: data.series[0].data
          }]
        });
      } else {
        console.error('Invalid data structure received from server:', data);
      }
    },
    error: function (jqXHR, textStatus, errorThrown) {
      // 处理请求失败的情况
      console.error("Error fetching data from server: " + textStatus, errorThrown);
    }
  }); // end $.ajax
}
