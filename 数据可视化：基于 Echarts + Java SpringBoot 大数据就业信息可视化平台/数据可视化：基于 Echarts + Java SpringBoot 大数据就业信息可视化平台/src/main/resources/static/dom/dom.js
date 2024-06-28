
var idContainer_2_1 = "container_2_1";
var idContainer_2_2 = "container_2_2";
var idContainer_2_3 = "container_2_3";

var chartDom_2_1 = document.getElementById(idContainer_2_1);
var chartDom_2_2 = document.getElementById(idContainer_2_2);
var chartDom_2_3 = document.getElementById(idContainer_2_3);


function asyncData_2() {
    $.getJSON("json/dom.json").done(function (data) {
        chartDom_2_1.innerHTML = data[0] + '元' ;
        chartDom_2_2.innerHTML = data[1] + "个";
        chartDom_2_3.innerHTML = data[2] + "%";
    });
  }

  asyncData_2();

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