# Billers
-When we call billers to inquiry about customers bills we received thier response containing all bills related to the customer, and ofcourse there are different message formats used by billers, i.e. Jordan Electricity repsonsed to our bill inquiry using JSON Format, while water authority responsed using XML format, and we have to validate the response for :

-For the JSON Parser I used ObjectMapper to get list of bills object

-And for XML Parser Used DocumentBuilderFactory and NodeList to get the bills.

-There was an issue while trying to parse the JSON response with the date format; fixed this by using JsonFormat annotation in Bill class.

-I Added BillInquiryExceptionReason enum with the validation error types, used them to throw a new Exception of type InvalidBillInquiryResponse with the proper error message after validating the bill.

-Also I added BillValidator to validate the below points from the JSON and the XML Parser:
1-Bill Due Date, Amount are required
2-Bill due date should not be future date.
3-Amount should be of valid format in Jordainian Dinar.
4-Fees is optional and incase its thier it should be valid format in Jordainian Dinar and less than Amount.

-Further comments will be found on the scr code.

-Some test cases are added to ParseInquiryResponseTest for both XML and JSON parsers.
-The original test cases:

test case #1: test if the provided XML response contains 2 bills
givenValidXMLResponse_WhenParse_ThenReturnValidBillsList
Result: Pass

test case #2: test if the provided XML response contains valid currency
givenInvalidXMLResponse_WhenParse_ThenThrowException
Result: BillValidator.validate(), error while validating bill: INVALID_AMOUNT_FORMAT

test case #3: test if the provided JSON response contains 2 bills
givenValidJSONResponse_WhenParse_ThenReturnValidBillsList
Result: Pass

test case #4: test if the provided JSON response have the right elements name
givenInvalidJSONResponse_WhenParse_ThenThrowException
Result: Unrecognized field "amount"

-The added test cases:

test case #5: test if the provided JSON response have fees more than provided amount
givenInvalidJSONResponse_WhenParse_ThenThrowException
Result: BillValidator.validate(), error while validating bill: FEES_MORE_THAN_AMOUNT

test case #6: test if the provided JSON response have a future dueDate
givenInvalidJSONResponse_WhenParse_ThenThrowException
Result: BillValidator.validate(), error while validating bill: FUTURE_DUE_DATE

test case #7: test if the provided XML response have a missing dueAmount
givenInvalidJSONResponse_WhenParse_ThenThrowException
Result: BillValidator.validate(), error while validating bill: MISSING_AMOUNT

test case #8: test if the provided XML response have a missing dueDate
givenInvalidJSONResponse_WhenParse_ThenThrowException
Result: BillValidator.validate(), error while validating bill: MISSING_DUE_DATE


