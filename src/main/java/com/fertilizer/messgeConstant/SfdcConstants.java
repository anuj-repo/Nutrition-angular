package com.fertilizer.messgeConstant;

public final class SfdcConstants {
    /**
     * private constructor to avoid instance creation
     */
    private SfdcConstants(){
    }
    public static final String OPPORTUNITY_URL_PRINT = "apexrest/CreateAdworksOpportunity";
    public static final String OPPORTUNITY_URL_DIGITAL = "apexrest/CreateAdworksDigitalOpportunity";
    public static final String LOGIN_URL = "oauth2/token";
    public static final String ORDER_ID_ERR_MSZ = "Order Id Not Found.";
    public static final String INR = "INR";
    public static final String DIGITAL = "Digital";
    public static final Integer ONE =1;
    public static final String Zero ="0";
    public static final String CPM = "CPM";
    public static final String SFDC_REQUEST = "SFDC Request :";
    public static final String SFDC_RESPONSE = "SFDC Response :";
    public static final String PACKAGE_NOT_FOUND = "Package Not Found";
    public static final String PACKAGE_REJECTION_MESSAGE = "Package has been rejected successfully.";

    public static final String ORDER_REJECTION_MESSAGE = "Order has been rejected successfully.";
    public static final String REJECTION_REASON = "Please Provide Rejection Reason";
    public static final String COMPOSE_STATUS = " Compose Status - ";
    public static final String digitalRate = "apexrest/getDigitalRateProducts";
    public static final String printRate = "apexrest/getPrintRateProducts";
    public static final String PACKAGE_APPROVED_MESSAGE = "Package Approved by Admin id ";
    public static final String ORDER_APPROVAL_MESSAGE = "Order has been approved successfully";
    public static final String ADMIN_PACKAGE_APPROVAL_MESSAGE = "Package has been approved successfully";
    public static final String PACKAGE_ID_NOT_FOUND = "Package with this Id not found";
    public static final String PACKAGE_ACTION_FAILED = "Package Action Request failed";
    public static final String PACKAGE_REJECTED_MESSAGE = "Package Cancelled by Admin id ";

    public static final String RESEND_ORDER_SUCCESS = "Order Details Sent To SFDC For Digital Opportunity Creation";

    public static final String RESEND_ORDER_FAILURE = "Digital Opportunity Creation Failed";

    public static final String PRODUCT_NOT_FOUND = "There Is No Available Product For This Combination";

    public static final String OPPORTUNITY_FAILURE_MESSAGE = "No Opportunity Created";

    public static final String numbers = "0123456789";

    public static final String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
    public static final String INTERNAL_ORDER_NUMBER_ERROR = "RO number should be unique and cannot match with past entries";

    public static final String INTERNAL_ORDER_NUMBER_MANDATORY = "RO number is mandatory";
}




