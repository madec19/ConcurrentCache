package com.sap.cache.dto;

public class ResponseDTO {

	private String status;
	private Object data;

	public ResponseDTO(String status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}

	public ResponseDTO() {

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseDTO [status=" + status + ", data=" + data + "]";
	}
	
	

}
