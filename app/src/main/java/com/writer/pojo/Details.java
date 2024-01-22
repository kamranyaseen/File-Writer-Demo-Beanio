package com.writer.pojo;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

@Record
public class Details {
	@Field(at=20,length=14)
	public String referenceNo;
	@Field(at=40,length=16)
	public String amount;
	
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
