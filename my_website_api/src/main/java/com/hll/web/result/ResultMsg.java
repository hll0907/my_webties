package com.hll.web.result;

public class ResultMsg {
	private String message;
	private Object result;
	private int code;

	public ResultMsg(String message, Object result, int code) {
		super();
		this.message = message;
		this.result = result;
		this.code = code;
	}

	public ResultMsg(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	// 成功返回
	public static ResultMsg success(Object result) {
		return new ResultMsg("成功", result, 1);
	}

	// 失败返回
	public static ResultMsg failure() {
		return new ResultMsg("失败", null, 0);
	}

	// 失败返回
	public static ResultMsg failure(String message, Object result, int code) {
		return new ResultMsg(message, result, code);
	}

	@Override
	public String toString() {
		return "ResultMsg [message=" + message + ", result=" + result + ", code=" + code + "]";
	}

}
