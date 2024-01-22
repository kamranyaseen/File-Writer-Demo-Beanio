package com.writer.pojo;

import org.beanio.annotation.*;
import org.beanio.annotation.Record;

@Record
public class Header {
	
	@Field(at=0, length=1)
	public String recordType;
	@Field(at=14, length=15)
	public String fileType;
	
	
	
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
}
