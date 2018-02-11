package com.ch.progress;


/**
 * 
 * 创建人：fantasy <br>
 * 创建时间：2013-12-6 <br>
 * 功能描述： 文件上传进度<br>
 */
public class Progress {

	//启动状态0停止，1启动
	private int state=0;
	/** 已读字节 **/
	private long bytesRead = 0L;
	/** 已读MB **/
	private String mbRead = "0";
	/** 总长度  字节**/
	private long contentLength = 0L;
	/****/
	private int items;
	/** 已读百分比 **/
	private String percent="0%";
	/** 读取速度 **/
	private String speed;
	/** 开始读取的时间 **/
	private long startReatTime = System.currentTimeMillis();

	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getBytesRead() {
		return bytesRead;
	}

	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public String getPercent() {
		percent = NumUtil.getPercent(bytesRead, contentLength);
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getSpeed() {
		speed = NumUtil.divideNumber(
					NumUtil.divideNumber(bytesRead * 1000, System.currentTimeMillis()- startReatTime), 
					1000);
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public long getStartReatTime() {
		return startReatTime;
	}

	public void setStartReatTime(long startReatTime) {
		this.startReatTime = startReatTime;
	}

	public String getMbRead() {
		mbRead = NumUtil.divideNumber(bytesRead, 1000000);
		return mbRead;
	}

	public void setMbRead(String mbRead) {
		this.mbRead = mbRead;
	}

//	@Override
//	public String toString() {
//		return JsonUtil.getJsonString4JavaPOJO(this);
//	}
}
