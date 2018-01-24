package main.java.com.madfooat.billinquiry;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import main.java.com.madfooat.billinquiry.domain.Bill;
import main.java.com.madfooat.billinquiry.domain.BillInquiryExceptionReason;
import main.java.com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;
import main.java.com.madfooat.billinquiry.validators.BillValidator;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLParseBillInquiryResponse implements ParseBillInquiryResponse {

	/* 
	 * @see com.madfooat.billinquiry.ParseBillInquiryResponse#parse(java.lang.String)
	 * Custom parsing for the XML Response 
	 * Example (Jordan Electricity Response)
	 */
	public List<Bill> parse(String billerResponse)
			throws InvalidBillInquiryResponse {
		List<Bill> listofBills = new ArrayList<Bill>();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(billerResponse));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("bill");

			// iterate the bills
			for (int i = 0; i < nodes.getLength(); i++) {

				Element element = (Element) nodes.item(i);

				NodeList dueDate = element.getElementsByTagName("dueDate");
				Element line = (Element) dueDate.item(0);
				String dueDateV = getCharacterDataFromElement(line);

				//format all dates to the received format while parsing response
				DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				Date date = null;
				if(dueDateV !=null && !dueDateV.isEmpty()){
					date = format.parse(dueDateV);
				}
				

				NodeList dueAmount = element.getElementsByTagName("dueAmount");
				line = (Element) dueAmount.item(0);
				String dueAmountVal = getNumberDataFromElement(line);

				NodeList fees = element.getElementsByTagName("fees");

				line = (Element) fees.item(0);
				String feesVal = getNumberDataFromElement(line);

				Bill bill = new Bill(date, new BigDecimal(dueAmountVal), new BigDecimal(feesVal));
				listofBills.add(bill);

			}

			//validate bills 
			BillValidator billValidator = new BillValidator();
			billValidator.validate(listofBills);

		} catch (Exception e) {
			//log error message
			System.out.println("XMLParseBillInquiryResponse.parse(), error while parsing response " + e.getMessage() );
			//throw new InvalidBillInquiryResponse
			throw new InvalidBillInquiryResponse(BillInquiryExceptionReason.GENRAL_ERROR , e.getMessage());
		}
		
		
		
		
		return listofBills;
	}

	/**
	 * @param e
	 * @return String
	 * will return the value as String
	 */
	public static String getCharacterDataFromElement(Element e) {
		if (e != null) {
			Node child = e.getFirstChild();
			if (child instanceof CharacterData) {
				CharacterData cd = (CharacterData) child;
				return cd.getData();
			}
		}
		return "";
	}
	
	
	/**
	 * @param e
	 * @return String
	 *  will return the number as String
	 * and zero as string if there is no value
	 */
	public static String getNumberDataFromElement(Element e) {
		if (e != null) {
			Node child = e.getFirstChild();
			if (child instanceof CharacterData) {
				CharacterData cd = (CharacterData) child;
				return cd.getData();
			}
		}
		return "0";
	}
}
