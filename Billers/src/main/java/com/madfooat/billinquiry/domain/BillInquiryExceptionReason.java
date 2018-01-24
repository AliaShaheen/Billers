package main.java.com.madfooat.billinquiry.domain;

public enum BillInquiryExceptionReason {
	MISSING_DUE_DATE,
	MISSING_AMOUNT,
	FUTURE_DUE_DATE,
	INVALID_AMOUNT_FORMAT,
	INVALID_FEES_FORMAT,
	FEES_MORE_THAN_AMOUNT,
	UNABLE_TO_PARSE_RESPONSE,
	GENRAL_ERROR
}
