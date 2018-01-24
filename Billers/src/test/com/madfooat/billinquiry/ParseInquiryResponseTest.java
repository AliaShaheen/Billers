package test.com.madfooat.billinquiry;

import main.java.com.madfooat.billinquiry.JSONParseBillInquiryResponse;
import main.java.com.madfooat.billinquiry.ParseBillInquiryResponse;
import main.java.com.madfooat.billinquiry.XMLParseBillInquiryResponse;
import main.java.com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;

import org.junit.Test;

import static test.com.madfooat.billinquiry.TestConstants.*;
import static org.junit.Assert.assertEquals;

public class ParseInquiryResponseTest {

	private ParseBillInquiryResponse parseXMLInquiryResponse = new XMLParseBillInquiryResponse();
	private ParseBillInquiryResponse parseJSONInquiryResponse = new JSONParseBillInquiryResponse();

	@Test //1
	public void givenValidXMLResponse_WhenParse_ThenReturnValidBillsList() {
		assertEquals(2, parseXMLInquiryResponse.parse(VALID_XML_RESPONSE).size());
	}

	@Test(expected = InvalidBillInquiryResponse.class) //2
	public void givenInvalidXMLResponse_WhenParse_ThenThrowException() {
		parseXMLInquiryResponse.parse(INVALID_XML_RESPONSE);
	}

	@Test //3
	public void givenValidJSONResponse_WhenParse_ThenReturnValidBillsList() {
		assertEquals(2, parseJSONInquiryResponse.parse(VALID_JSON_RESPONSE).size());
	}

	@Test(expected = InvalidBillInquiryResponse.class) //4
	public void givenInvalidJSONResponse_WhenParse_ThenThrowException() {
		parseJSONInquiryResponse.parse(INVALID_JSON_RESPONSE);
	}

	@Test(expected = InvalidBillInquiryResponse.class) //5
	public void givenInvalidJSONFeesResponse_WhenValidate_ThenThrowException() {
		parseJSONInquiryResponse.parse(INVALID_FEES_RESPONSE);
	}

	@Test(expected = InvalidBillInquiryResponse.class) //6
	public void givenInvalidJSONDateResponse_WhenValidate_ThenThrowException() {
		parseJSONInquiryResponse.parse(INVALID_DATE_RESPONSE);
	}

	@Test(expected = InvalidBillInquiryResponse.class) //7
	public void givenEmptyAmountXMLResponse_WhenValidate_ThenThrowException() {
		parseXMLInquiryResponse.parse(MISSING_AMOUNT_RESPONSE);
	}
	
	@Test(expected = InvalidBillInquiryResponse.class) //8
	public void givenEmptyDateXMLResponse_WhenValidate_ThenThrowException() {
		parseXMLInquiryResponse.parse(MISSING_DATE_RESPONSE);
	}
}
