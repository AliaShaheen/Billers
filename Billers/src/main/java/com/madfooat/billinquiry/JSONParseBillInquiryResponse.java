package main.java.com.madfooat.billinquiry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.madfooat.billinquiry.domain.Bill;
import main.java.com.madfooat.billinquiry.domain.BillInquiryExceptionReason;
import main.java.com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;
import main.java.com.madfooat.billinquiry.validators.BillValidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;


public class JSONParseBillInquiryResponse implements ParseBillInquiryResponse {
	
	/* 
	 * @see com.madfooat.billinquiry.ParseBillInquiryResponse#parse(java.lang.String)
	 * Custom parsing for the JSON Response 
	 * Example(Water Authority Response)
	 */
	public List<Bill> parse(String billerResponse)throws InvalidBillInquiryResponse {
		List<Bill> listofBills = new ArrayList<Bill>();
		try {
			listofBills = jsonArrayToObjectList(billerResponse, Bill.class);
			
			//validate bills 
			BillValidator billValidator = new BillValidator();
			billValidator.validate(listofBills);
			
		} catch (IOException e) {
			//log error message
			System.out.println("JSONParseBillInquiryResponse.parse(), error while parsing response "+ e.getMessage());
			//throw new InvalidBillInquiryResponse
			throw new InvalidBillInquiryResponse(BillInquiryExceptionReason.UNABLE_TO_PARSE_RESPONSE , e.getMessage());
		}
		
		return listofBills;
	}

	public List<Bill> jsonArrayToObjectList(String json, Class<Bill> tClass)
			throws IOException {
		//use ObjectMapper to get list of bills objects
		ObjectMapper mapper = new ObjectMapper();
		CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
		List<Bill> listofBills = mapper.readValue(json, listType);
		return listofBills;
	}

}
