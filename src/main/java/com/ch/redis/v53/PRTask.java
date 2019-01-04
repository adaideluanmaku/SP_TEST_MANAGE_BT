package com.ch.redis.v53;

import org.springframework.stereotype.Service;

/**
 * 
 * <ul>
 * <li>项目名称：PASSRH_PRMO </li>
 * <li>类名称：  PROverTask </li>
 * <li>类描述：1.1	PR自动预判已通过记录（PROVERTASK/PRWATINGTASK）   </li>
 * <li>创建人：jy </li>
 * <li>创建时间：2018年11月15日 </li>
 * <li>修改备注：</li>
 * </ul>
 */
@Service
public class PRTask implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long autoevid;// 唯一主键，每次审查都是唯一的
	private String caseid;
	private Long mhiscode;// 机构代码
	private Integer patStatus;// 病人类型( 门诊2;急诊3;住院1;出院0)
	private String checkDatetime;// 自动干预时间
	private Integer status;// 自动干预状态
	private String statusDesc;// 状态描述
	private Integer afterStatue;// 自动干预后续状态
	private String screenStr;// 审查输入串
	private String screenResult;// 审查结果
	
	private String deptcode;//add 病人所在部门

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public Long getAutoevid() {
		return autoevid;
	}

	public void setAutoevid(Long autoevid) {
		this.autoevid = autoevid;
	}

	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public Long getMhiscode() {
		return mhiscode;
	}

	public void setMhiscode(Long mhiscode) {
		this.mhiscode = mhiscode;
	}

	public Integer getPatStatus() {
		return patStatus;
	}

	public void setPatStatus(Integer patStatus) {
		this.patStatus = patStatus;
	}

	public String getCheckDatetime() {
		return checkDatetime;
	}

	public void setCheckDatetime(String checkDatetime) {
		this.checkDatetime = checkDatetime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Integer getAfterStatue() {
		return afterStatue;
	}

	public void setAfterStatue(Integer afterStatue) {
		this.afterStatue = afterStatue;
	}

	public String getScreenStr() {
		return screenStr;
	}

	public void setScreenStr(String screenStr) {
		this.screenStr = screenStr;
	}

	public String getScreenResult() {
		return screenResult;
	}

	public void setScreenResult(String screenResult) {
		this.screenResult = screenResult;
	}

}
