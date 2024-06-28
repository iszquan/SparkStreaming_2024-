function init_echart_map_china_map(container) {
  // 基于准备好的dom，初始化echarts实例
  var myChart = echarts.init(document.getElementById(container), window.gTheme);
  var data = [];
  var geoCoordMap = {};
  option = {
    title: {
      text: "全国就业岗位分析",
      top: "2%",
      left: "center",
      textStyle: {
        color: "hsl(200, 86%, 48%)",
        // color: "orange",
        fontSize: "20",
      },
    },

    tooltip: {
      trigger: "item",
      formatter: function (params) {
        console.log(params);
        value = 0;
        if (params.value) value = params.value;
        return params.seriesName + "<br>" + params.name + " : " + value + "万";
      },
    },
    visualMap: {
      type: "piecewise",
      splitNumber: 5,
      pieces: [
        { gt: 10000 },
        { gt: 1000, lte: 9999 },
        { gt: 500, lte: 999 },
        { gt: 100, lte: 499 },
        { gt: 10, lte: 99 },
        { gt: 1, lte: 9 },
        { lte: 0 },
      ],

      textStyle: {
        color: "#fff",
      },
      top: "bottom",
    },

    geo: [
      {
        map: "china",
        layoutCenter: ["50%", "50%"],
        zoom: 1,
        roam: true,
        // 地图放大或缩小的尺寸
        layoutSize: "100%",
        selectedMode: "single",
        label: {
          emphasis: {
            show: false,
          },
        },

        // 地图区域的多边形 图形样式。
        itemStyle: {
          // 图形阴影颜色。支持的格式同color。
          shadowColor: "black",
          // 图形阴影的模糊大小。
          shadowBlur: 10,
          // 阴影水平方向上的偏移距离。
          shadowOffsetX: 2,
          // 阴影垂直方向上的偏移距离。
          shadowOffsetY: 2,
        },
        // 高亮状态下的多边形和标签样式。
        emphasis: {
          itemStyle: {
            borderWidth: 3,
            borderColor: "purple",
            // areaColor: "#ffdead",
          },
        },
      },
    ],
    series: {
      name: "累计就业岗位",
      type: "map",
      coordinateSystem: "geo",
      geoIndex: 0,
      zoom: 1.2,
      roam: true,
    },
  };

  myChart.setOption(option);
  window.addEventListener("resize", function () {
    myChart.resize();
  });
}
// 地图加载数据
function async_echart_china(container, filename, section) {
  $.getJSON(filename).done(function (data) {
    var myChart = echarts.init(document.getElementById(container));
    series = "";
    data_section = [];
    if (section == "confirmAdd") {
      series = "新增就业岗位";
      data_section = data.map((item) => {
        return { name: item.name, value: item.confirmAdd };
      });
    }
    if (section == "confirm") {
      series = "累计就业岗位";
      data_section = data.map((item) => {
        return { name: item.name, value: item.confirm };
      });
    }
    if (section == "nowConfirm") {
      series = "现有就业岗位";
      data_section = data.map((item) => {
        return { name: item.name, value: item.nowConfirm };
      });
    }

    myChart.setOption({
      series: {
        name: series,
        data: data_section,
      },
    });
  });
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
