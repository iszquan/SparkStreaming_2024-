function init_echart_bar_ROA(container) {
  var chartDom = document.getElementById(container);
  var myChart = echarts.init(chartDom, window.gTheme);
  var option;

  option = {
    title: {
      text: "全国在招公司规模",
      //top: "5%",
      //left: "2%",
      textStyle: {
        // color: "#00ffff",
        color: "orange",
        fontSize: "12",
      },
    },
    grid: {
      left: "3%",
      right: "4%",
      bottom: "10%",
      top: "30%",
      containLabel: true,
    },
    tooltip: {
      trigger: "item",
      formatter: "{b}: {c} ",
      position: function (p) {
        //其中p为当前鼠标的位置
        return [p[0] + 10, p[1] - 10];
      },
    },
    xAxis: {
      name: "名称",
      type: "category",
      nameTextStyle: {
        color: "rgba(255,255,255,.8)",
        // fontSize: 12,
      },
      data: [],
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
      name: "数量（个）",
      type: "value",
      nameTextStyle: {
        color: "rgba(255,255,255,.8)",
        // fontSize: 12,
      },
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
        type: "bar",
        showBackground: true,
        backgroundStyle: {
          color: "rgba(180, 180, 180, 0.2)",
        },
      },
    ],
  };

  option && myChart.setOption(option);
  window.addEventListener("resize", function () {
    myChart.resize();
  });
}

// 读取本地json的方法获取数据
function async_echart_bar_ROA(container, filename) {
  $.getJSON(filename).done(function (data) {
    var myChart = echarts.init(
      document.getElementById(container),
      window.gTheme
    );
    myChart.setOption(data);
  }); //end $.getJSON
}

// // ajax读取后端数据，传递参数为容器元素位置与后端接口url
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
//       if (data) {
//         myChart.setOption(
//           data
//         );
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