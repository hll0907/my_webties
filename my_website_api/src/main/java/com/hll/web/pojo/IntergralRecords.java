package com.hll.web.pojo;

public class IntergralRecords {
	private Integer id;

	private Integer userId;

	private Integer tempIntergral;

	private Integer intergral;

	private String createdAt;

	private String updatedAt;

	private String note;

	private String source;
	private Integer intergralType;

	public Integer getIntergralType() {
		return intergralType;
	}

	public void setIntergralType(Integer intergralType) {
		this.intergralType = intergralType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTempIntergral() {
		return tempIntergral;
	}

	public void setTempIntergral(Integer tempIntergral) {
		this.tempIntergral = tempIntergral;
	}

	public Integer getIntergral() {
		return intergral;
	}

	public void setIntergral(Integer intergral) {
		this.intergral = intergral;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "IntergralRecords [id=" + id + ", userId=" + userId + ", tempIntergral=" + tempIntergral + ", intergral="
				+ intergral + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", note=" + note + ", source="
				+ source + ", intergralType=" + intergralType + "]";
	}

}