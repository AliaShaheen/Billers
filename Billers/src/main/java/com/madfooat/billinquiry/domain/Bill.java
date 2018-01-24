package main.java.com.madfooat.billinquiry.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Bill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4885122595504945112L;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	//format all dates to the received format while parsing response
    private Date dueDate;
    private BigDecimal dueAmount;
    private BigDecimal fees;

    public Bill(){
    }
    
    public Bill(Date dueDate, BigDecimal dueAmount, BigDecimal fees){
    	this.dueDate = dueDate;
    	this.dueAmount = dueAmount;
    	this.fees = fees;
    }
    
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
    	this.dueDate = dueDate;
    }

    public BigDecimal getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(BigDecimal dueAmount) {
        this.dueAmount = dueAmount;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "dueDate=" + dueDate +
                ", dueAmount=" + dueAmount +
                ", fees=" + fees +
                '}';
    }
}
