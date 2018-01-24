package main.java.com.madfooat.billinquiry.validators;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import main.java.com.madfooat.billinquiry.domain.Bill;
import main.java.com.madfooat.billinquiry.domain.BillInquiryExceptionReason;
import main.java.com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;


/**
 * @author Alia Shahin
 * Validate bill 
 */


public class BillValidator {

	public void validate(List<Bill> listOfBills) throws InvalidBillInquiryResponse{

		for(Bill bill : listOfBills){

			//validate dueDate not null
			if(bill.getDueDate() == null){
				System.out.println("BillValidator.validate(), error while validating bill: "+ BillInquiryExceptionReason.MISSING_DUE_DATE);
				throw new InvalidBillInquiryResponse(BillInquiryExceptionReason.MISSING_DUE_DATE);
			}else{
				//Bill due date should not be future date.
				Date dueDate = bill.getDueDate();
				Calendar todayCal = Calendar.getInstance();
				Date todayDate = todayCal.getTime();
				if (todayDate.before(dueDate)){
					System.out.println("BillValidator.validate(), error while validating bill: "+ BillInquiryExceptionReason.FUTURE_DUE_DATE);
					throw new InvalidBillInquiryResponse(BillInquiryExceptionReason.FUTURE_DUE_DATE);
				}

			}
			//validate dueAmount not null
			if(bill.getDueAmount() == null || bill.getDueAmount().compareTo(BigDecimal.ZERO) == 0){
				System.out.println("BillValidator.validate(), error while validating bill: "+ BillInquiryExceptionReason.MISSING_AMOUNT);
				throw new InvalidBillInquiryResponse(BillInquiryExceptionReason.MISSING_AMOUNT);
			}else{
				/*validate dueAmount in JOD
				 *Amount should be of valid format in Jordainian Dinar.*/
				if(getNumberOfDecimalPlaces(bill.getDueAmount()) > 3){
					System.out.println("BillValidator.validate(), error while validating bill: "+ BillInquiryExceptionReason.INVALID_AMOUNT_FORMAT);
					throw new InvalidBillInquiryResponse(BillInquiryExceptionReason.INVALID_AMOUNT_FORMAT);
				}
			}

			//Fees is optional and incase its thier it should be valid format in Jordainian Dinar and less than Amount.
			if(bill.getFees() != null){
				//validate fees in JOD
				if(getNumberOfDecimalPlaces(bill.getFees()) > 3){
					System.out.println("BillValidator.validate(), error while validating bill: "+ BillInquiryExceptionReason.INVALID_FEES_FORMAT);
					throw new InvalidBillInquiryResponse(BillInquiryExceptionReason.INVALID_FEES_FORMAT);
				}
				
				//validate fees less than dueAmount 
				if(bill.getDueAmount().compareTo(bill.getFees()) != 1){
					System.out.println("BillValidator.validate(), error while validating bill: "+ BillInquiryExceptionReason.FEES_MORE_THAN_AMOUNT);
					throw new InvalidBillInquiryResponse(BillInquiryExceptionReason.FEES_MORE_THAN_AMOUNT);
				}
			}

		}
	}
	
	/**
	 * @param bigDecimal
	 * @return int
	 * This method validates if the received amount is in JOD currency
	 * JOD can accept 3 decimal digits maximum
	 */
	private int getNumberOfDecimalPlaces(BigDecimal amount) {
	    String stringAmount = amount.stripTrailingZeros().toPlainString();
	    int index = stringAmount.indexOf(".");
	    return index < 0 ? 0 : stringAmount.length() - index - 1;
	}
}
