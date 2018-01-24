package main.java.com.madfooat.billinquiry.exceptions;

import main.java.com.madfooat.billinquiry.domain.BillInquiryExceptionReason;

public class InvalidBillInquiryResponse extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8170828098566317022L;

	
	private BillInquiryExceptionReason exceptionReason;
	private String message;

	public InvalidBillInquiryResponse(BillInquiryExceptionReason exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	
	public InvalidBillInquiryResponse(BillInquiryExceptionReason exceptionReason,
			String message) {
		this.exceptionReason = exceptionReason;
		this.message = message;
	}


	@Override
	public String getMessage() {
		return exceptionReason.toString();
	}
}
