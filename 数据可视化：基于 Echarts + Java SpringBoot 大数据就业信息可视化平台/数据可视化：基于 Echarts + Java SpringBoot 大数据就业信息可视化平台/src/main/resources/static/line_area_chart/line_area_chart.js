function init_echart_line_area_chart(container) {
  var chartDom = document.getElementById(container);
  var myChart = echarts.init(chartDom, window.gTheme);
  var option;

  option = {
    title: {
      text: "城市行业岗位类型统计",
      // top: 0,
      // left: "center",
      textStyle: {
        // color: "#17c0ff",
        color: "orange",
        fontSize: "12",
      },
    },
    grid: {
      left: "3%",
      right: "3%",
      bottom: "3%",
      top: "25%",
      containLabel: true,
    },

    tooltip: {
      trigger: "axis",
      axisPointer: {
        // Use axis to trigger tooltip
        type: "shadow", // 'shadow' as default; can also be 'line' or 'shadow'
      },
    },
    xAxis: {
      type: "category",
      boundaryGap: false,
      axisLabel: {
        textStyle: {
          color: "rgba(255,255,255,.8)",
          // fontSize: 14,
        },
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
      type: "value",
      axisLabel: {
        textStyle: {
          color: "rgba(255,255,255,.8)",
          // fontSize: 14,
        },
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
    series: [
      {
        data: [],
        type: "line",
        areaStyle: {},
      },
    ],
  };

  option && myChart.setOption(option);
  window.addEventListener("resize", function () {
    myChart.resize();
  });
}
function async_echart_line_area_chart(container, filename) {
  $.getJSON(filename).done(function (data) {
    var myChart = echarts.init(document.getElementById(container));
    myChart.setOption({
      xAxis: data["xAxis"],
      series: data["series"],
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
