function init_echart_funnel(container) {
  var chartDom = document.getElementById(container);
  var myChart = echarts.init(chartDom, window.gTheme);

  var option = {
    title: {
      text: "城市线级招聘数量分析",
      //top: "5%",
      //left: "2%",
      textStyle: {
        // color: "#17c0ff",
        color: "orange",
        fontSize: "12",
      },
    },
    tooltip: {
      trigger: "item",
      formatter: "{b} : {c}",
    },
    series: [
      {
        type: "funnel",
        left: "15%",
        top: "20%",
        width: "55%",
        height: "65%",
        sort: "ascending",
        // 坐标轴显示的字样式
        label: {
          position: "right",
          textStyle: {
            color: "rgba(255, 255, 255, 0.8)",
          },
        },
        data: [],
      },
    ],
  };

  window.addEventListener("resize", function () {
    myChart.resize();
  });
  myChart.setOption(option);
}
function async_echart_funnel(container, filename) {
  $.getJSON(filename).done(function (data) {
    var myChart = echarts.init(document.getElementById(container));

    myChart.setOption({
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
