package main.java.com.madfooat.billinquiry;

import java.util.List;

import main.java.com.madfooat.billinquiry.domain.Bill;
import main.java.com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;

public interface ParseBillInquiryResponse {

    public List<Bill> parse(String billerResponse) throws InvalidBillInquiryResponse;

}
