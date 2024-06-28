function init_echart_line_visualMap(container) {
  // 基于准备好的dom，初始化echarts实例
  var myChart = echarts.init(document.getElementById(container), gTheme);
  option = {
    title: {
      text: "热门岗位薪资待遇实时监测",
      // top: 0,
      // left: "center",
      textStyle: {
        // color: "#17c0ff",
        color: "orange",
        fontSize: "12",
      },
    },

    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b}: {c} ({d}%)",
      position: function (p) {
        //其中p为当前鼠标的位置
        return [p[0] + 10, p[1] - 10];
      },
    },

    grid: {
      left: "3%",
      right: "3%",
      bottom: "3%",
      top: "25%",
      containLabel: true,
    },

    xAxis: {
      name: "名称",
      type: "category",
      data: [],
      axisLabel: {
        textStyle: {
          color: "rgba(255,255,255,.8)",
          //fontSize: 14,
        },
        // formatter: "{value}%",
      },
      axisLine: {
        lineStyle: {
          color: "rgba(255,255,255,.2)",
        },
      },
      splitLine: {
        lineStyle: {
          color: "rgba(255,255,255,.1)",
        },
      },
    },
    yAxis: {
      name: "元",
      type: "value",
      data: [],
      axisLabel: {
        textStyle: {
          color: "rgba(255,255,255,.8)",
          //fontSize: 14,
        },
        formatter: "{value}",
      },
      axisLine: {
        lineStyle: {
          color: "rgba(255,255,255,.2)",
        },
      },
      splitLine: {
        lineStyle: {
          color: "rgba(255,255,255,.1)",
        },
      },
    },
    visualMap: {
      top: "top",
      left: "right",
      textStyle: {
        color: "rgba(255,255,255,.8)",
        //fontSize: 14,
      },
      pieces: [
        {
          gt: 0,
          lte: 100,
          color: "#FF0000",
        },
        {
          gt: 100,
          lte: 800,
          color: "#FFA500",
        },
        {
          gt: 800,
          lte: 900,
          color: "#2E8B57",
        },
      ],
    },
    series: [
      {
        name: "该薪资平均分布",
        type: "line",
        // stack: "total",
        // label: {
        //   show: true,
        // },
        // 使用系统函数
        markPoint: {
          label: {
            textStyle: {
              color: "rgba(255,255,255,.8)",
              //fontSize: 14,
            },
          },
          data: [
            { type: "max", name: "Max" },
            { type: "min", name: "Min" },
          ],
        },
        markLine: {
          data: [{ type: "average", name: "Avg" }],
        },
        // 自定义数据
        // markLine: {
        //   // 图形是否不响应和触发鼠标事件
        //   silent: true,
        //   label: {
        //     textStyle: {
        //       color: "rgba(255,255,255,.8)",
        //       //fontSize: 14,
        //     },
        //   },
        //   data: [
        //     {
        //       yAxis: 100,
        //       lineStyle: {
        //         color: "#FF0000",
        //       },
        //     },
        //     {
        //       yAxis: 800,
        //       lineStyle: {
        //         color: "#FFA500",
        //       },
        //     },
        //     {
        //       yAxis: 900,
        //       lineStyle: {
        //         color: "#2E8B57",
        //       },
        //     },
        //   ],
        // },
      },
    ],
  };

  // 使用刚指定的配置项和数据显示图表。
  myChart.setOption(option);
  window.addEventListener("resize", function () {
    myChart.resize();
  });
}

function getKeys(dataList) {
  var keys = [];
  var len = dataList.length;
  for (var i = 0; i < len; i++) keys.push(dataList[i].name);
  return keys;
}

function async_echart_line_visualMap(container, filename) {
  $.getJSON(filename).done(function (data) {
    var myChart = echarts.init(document.getElementById(container));
    myChart.setOption({
      xAxis: { data: getKeys(data) },
      series: [{ data: data }],
    });
  }); //end $.getJSON
}

// ajax读取后端数据，传递参数为容器元素位置与后端接口url
// function async_echart_bar_horizontal(container, url) {
//   // 确保容器元素存在
//   var containerElement = document.getElementById(container);
//   if (!containerElement) {
//     console.error('Container element not found:', container);
//     return;
//   }
//
//   $.ajax({
//     url: url, // 后端接口的URL
//     dataType: 'json', // 期望返回的数据类型为JSON
//     success: function (data) {
//       // 初始化ECharts实例
//       var myChart = echarts.init(containerElement, window.gTheme);
//
//       // 使用从后端接口获取的数据设置图表选项
//       // 确保数据结构与ECharts兼容
//       if (data && data.yAxis && data.yAxis.data && data.series && data.series[0] && data.series[0].data) {
//         myChart.setOption({
//           yAxis: {
//             data: data.yAxis.data
//           },
//           series: [{
//             data: data.series[0].data
//           }]
//         });
//       } else {
//         console.error('Invalid data structure received from server:', data);
//       }
//     },
//     error: function (jqXHR, textStatus, errorThrown) {
//       // 处理请求失败的情况
//       console.error("Error fetching data from server: " + textStatus, errorThrown);
//     }
//   }); // end $.ajax
// }
