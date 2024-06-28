function init_echart_bar_age(container) {
  // 基于准备好的dom，初始化echarts实例
  var myChart = echarts.init(document.getElementById(container), gTheme);
  option = {
    title: {
      text: "平均薪资待遇最高的行业",
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
      formatter: "{a} <br/>{b}: {c} ({d}%)",
      position: function (p) {
        //其中p为当前鼠标的位置
        return [p[0] + 10, p[1] - 10];
      },
    },

    grid: {
      left: "3%",
      right: "15%",
      bottom: "3%",
      top: "30%",
      containLabel: true,
    },

    xAxis: {
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
      data: [],
      axisLabel: {
        textStyle: {
          color: "rgba(255,255,255,.8)",
          //fontSize: 14,
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
        name: "平均薪资",
        type: "bar",
        // stack: "total",
        // label: {
        //   show: true,
        // },
        markLine: {
          // 图形是否不响应和触发鼠标事件
          silent: true,
          lineStyle: {
            color: "red",
          },
          label: {
            textStyle: {
              color: "rgba(255,255,255,.8)",
              //fontSize: 14,
            },
          },
          data: [
            {
              yAxis: 4000,
            },
            {
              yAxis: 5000,
            },
          ],
        },
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

function async_echart_bar_age(container, filename) {
  $.getJSON(filename).done(function (data) {
    var myChart = echarts.init(document.getElementById(container));
    myChart.setOption({
      xAxis: { data: getKeys(data) },
      series: [{ data: data }],
    });
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
//     dataType: 'char', // 期望返回的数据类型为JSON
//     success: function (data) {
//       // 初始化ECharts实例
//       var myChart = echarts.init(containerElement, window.gTheme);
//
//       // 使用从后端接口获取的数据设置图表选项
//       // 确保数据结构与ECharts兼容
//       if (data) {
//         myChart.setOption({
//             xAxis: { data: getKeys(data) },
//             series: [{ data: data }],
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

