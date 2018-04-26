var addurl=$("addurl").val();
$(document).ready(function() {
	baidu_echarts();
	baidu_echarts1();
});

function baidu_echarts(){
	var dom = document.getElementById("echarts");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
//	    title: {
//	        text: '折线图堆叠'
//	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: false,
	        data: ['周一','周二','周三','周四','周五','周六','周日']
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [
	        {
	            name:'邮件营销',
	            type:'line',
	            stack: '总量',
	            data:[120, 132, 101, 134, 90, 230, 210]
	        },
	        {
	            name:'联盟广告',
	            type:'line',
	            stack: '总量',
	            data:[220, 182, 191, 234, 290, 330, 310]
	        },
	        {
	            name:'视频广告',
	            type:'line',
	            stack: '总量',
	            data:[150, 232, 201, 154, 190, 330, 410]
	        },
	        {
	            name:'直接访问',
	            type:'line',
	            stack: '总量',
	            data:[320, 332, 301, 334, 390, 330, 320]
	        },
	        {
	            name:'搜索引擎',
	            type:'line',
	            stack: '总量',
	            data:[820, 932, 901, 934, 1290, 1330, 1320]
	        }
	    ]
	};
	;
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	}
}

function baidu_echarts1(){
	var dom = document.getElementById("echarts1");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		legend: {},
	    tooltip: {},
	    dataset: {
	        source: [
	            ['product', '邮件营销','联盟广告','视频广告','直接访问','搜索引擎'],
	            ['周一', 1,2,3,4,5],
	            ['周二', 11,12,13,14,15],
	            ['周三', 21,22,23,24,25],
	            ['周四', 31,32,33,34,35],
	            ['周五', 41,42,43,44,45],
	            ['周六', 51,52,53,54,55],
	            ['周日', 61,62,63,64,65],
	            ['周一1', 1,2,3,4,5],
	            ['周二1', 11,12,13,14,15],
	            ['周三1', 21,22,23,24,25],
	            ['周四1', 31,32,33,34,35],
	            ['周五1', 41,42,43,44,45],
	            ['周六1', 51,52,53,54,55],
	            ['周日1', 61,62,63,64,65],
	            ['周一2', 1,2,3,4,5],
	            ['周二2', 11,12,13,14,15],
	            ['周三2', 21,22,23,24,25],
	            ['周四2', 31,32,33,34,35],
	            ['周五2', 41,42,43,44,45],
	            ['周六2', 51,52,53,54,55],
	            ['周日2', 61,62,63,64,65]
	        ]
	    },
	    xAxis: {
	    	type: 'category',
	    },
	    yAxis: {
	        type: 'value'
	    },
	    // Declare several bar series, each will be mapped
	    // to a column of dataset.source by default.
	    series: [
	        {type: 'line'},
	        {type: 'line'},
	        {type: 'line'},
	        {type: 'line'},
	        {type: 'line'}
	    ]
	};
	;
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	}
}
