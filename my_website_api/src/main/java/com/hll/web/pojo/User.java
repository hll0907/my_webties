package com.hll.web.pojo;

public class User {
	
    private Integer id;

	private String phone;

	private String nickname;

	private String salt;

	private String hashPassword;

	private String headPic;

	private Integer userType;

	private String openid;

	private String createTime;

	private String updateTime;

	private String lastVisitTime;

	private Integer integral;

	private String vipType;

	private Boolean status;

	private String actiCode;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(String lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getActiCode() {
		return actiCode;
	}

	public void setActiCode(String actiCode) {
		this.actiCode = actiCode;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", phone=" + phone + ", nickname=" + nickname + ", salt=" + salt + ", hashPassword="
				+ hashPassword + ", headPic=" + headPic + ", userType=" + userType + ", openid=" + openid
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", lastVisitTime=" + lastVisitTime
				+ ", integral=" + integral + ", vipType=" + vipType + ", status=" + status + ", actiCode=" + actiCode
				+ "]";
	}

}