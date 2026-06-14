export const PATTERN_ERROR_MESSAGE = {
    EMAIL_PATTERN: 'Please enter a valid email address',
    PHONE_PATTERN: 'Please enter a valid phone number',
    WHATSAPP_NUMBER_PATTERN: 'Please enter a valid whatsApp number',
    CITY_PATTERN: 'Please enter a valid city',
    STATE_PATTERN: 'Please enter a valid state',
    PAN_PATTERN: 'Please enter a valid pan',
    ADDRESS_PATTERN: 'Please enter a valid address',
    PIN_PATTERN: 'Please enter a vaild pincode',
    GST_PATTERN: 'Please enter a valid GST',
    PASSWORD_PATTERN: 'Password must contain atleast one lowercase letter, one uppercase letter, one number, one special character (#?!@$%^&*-) and length should be of atleast 6 characters.',
};

export const REQUIRED_ERROR_MESSAGE = {
    NAME_REQUIRED: '* User name is required',
    FIRST_NAME_REQUIRED: '* First name is required',
    FIRST_NAME_LENGHT: 'First name must be at least 2 characters long',
    LAST_NAME_REQUIRED: '* Last name is required',
    COMPANY_NAME_REQUIRED: '* Company name is required',
    DESIGNATION_REQUIRED: '* Designation is required',
    ROLE_REQUIRED: '* User role is required',
    EMAIL_REQUIRED: '* Email address is required',
    EMAIL_VALIDATION: '* Email address is not valid',
    PAN_REQUIRED: '* Pan number is required',
    ADDRESS_REQUIRED: '* Address is required',
    PHONE_REQUIRED: '* Phone is required',
    WHATSAPP_PHONE_REQUIRED: '* WhatsApp number is required',
    QUERY_REQUIRED: '* Please mention your query',
    OTP_REQUIRED: '* OTP is required',
    PASSWORD_REQUIRED: '* Password is required',
    CONFIRM_PASSWORD_REQUIRED: '* Confirm password is required',
    BUSINESS_CAT_REQUIRED: '* Business category is required',
    BUSINESS_CAT_OTHER_REQUIRED: '* Please tell your Business Category',
    PASSWORD_MATCH_REQUIED: '* Password and confirm password do not match',
    STATE_REQUIED: '* State is required',
    CITY_REQUIED: '* City is required',
    CITY_OTHER_REQUIED: '* Please specify your City',
    PIN_REQUIED: '* Pin code is required',
    USER_TYPE_REQUIED: '* Please confirm if you are an agency.',
    OLD_PASSWORD_REQUIRED: '* Please enter your old password',
    NEW_PASSWORD_REQUIRED: '* Please enter your new password',
    OLD_NEW_PASSWORD_SHOULD_NOT_MATCH:'* New password should not match old password',
    RENEW_PASSWORD_REQUIRED: '* Please re-enter your new password',
    GST_REQUIRED: '* GST number is required',
    ADVERTISING_CHANNEL_REQUIRED: '* Channel of advertising is required',
    CLIENT_TYPE_REQUIRED: '* Type of client is required',
    DATE_REQUIRED: '* Date is required',
    CAMPAIGN_NAME_REQUIRED: 'Campaign name is required',
    CAMPAIGN_DURATION_REQUIRED: 'Campaign duration is required',
    RO_FILE_REQUIRED: '* RO file is required',
    PDC_FILE_REQUIRED: '* PDC file is required',
    AD_DESCRIPTION_REQUIRED: '* Ad description is required',
    TIME_SLOT_REQUIRED:'* Please select time slot',
    DURATION_REQUIRED: '* Duration field is required',
    FILE_REQUIRED: '* File field is required',
    AD_CONTENT_REQUIRED: '* Ad content is required'
}
export const PACKAGE_ERROR_MESSAGE = {
    PACKAGE_TITLE_VALIDATION: '* Please enter package name',
    DESCRIPTION_VALIDATION: '* Please enter description',
    CUSTOMER_VALIDATION: '* Please select customer type',
    LOCATION_VALIDATION: '* Please select location ',
    NUMBER_INSERTION_VALIDATION: '* Please enter number of insertion',
    PRICE_PER_VALIDATION: "* Please enter price per Sqcm",
    OFFER_TITLE_VALIDATION: '* Please enter offer title',
    EDITION_VALIDATION: "* Please select edition",
    SUB_EDITION_VALIDATION: '* Please select subedition',
    CATEGORY_VALIDATION: '* Please select category',
    PAGE_POSITION_VALIDATION: '* Please select page position',
    AD_SIZE_VALIDATION: '* Please select ad size',
    LARGE_DATA_MSG: 'Number of matching items is too large. Please use the filters to limit the selection should be equal or less than 20',
    NOT_FOUND_MSG: 'No item found. Please change your selections.',
    PROCEED_DATA_MSG: '* Please select atleast one item before proceeding to the next step'
}

export const DISCOUNT_COUPONS_ERROR_MESSAGE = {
    FIX_DISCOUNT_VALUE_VALIDATION: '* Discount value cannot be less than min value',
    PERCENT_DISCOUNT_VALUE_VALIDATION: '* Percent cannot be greater than 100',
    ACCOUNT_TYPE_VALIDATION: '* Account type should match with packages account type',
    COUPON_NAME_REQUIRED: '* Please enter coupon name',
    COUPON_TYPE_REQUIRED: '* Please enter discount type',
    COUPON_PERCENTAGE_REQUIRED: '* Please enter discount percentage',
    NUMBER_OF_USE_REQUIRED: '* Please enter number of use',
    CHANNEL_TYPE_REQUIRED: '* Please select channel type'
}

export const VOUCHER_MESSAGE = {
    ADMIN_MAX_VOUCHER_VALIDATION: 'Maximum accepted amount is 10,00,00,000.',
    MAX_VOUCHER_VALIDATION: 'Maximum accepted amount is 1,00,00,000.',
    MIN_VOUCHER_VALIDATION: 'Minimum amount should be multiple of 1,00,000',
    CHEQUE_NUMBER_REQUIRED: 'Cheque number is required',
    CHEQUE_NUMBER_VALIDATION: 'Cheque number must be in numbers',
    BANK_NAME_REQUIRED: 'Bank name is required',
    BANK_NAME_VALIDATION: 'Please enter a valid bank name',
    IFSC_REQUIRED: 'IFSC code is required',
    AMOUNT_PLEDDEDED: 'Maximum accepted amount is 1,00,00,000.'
}

export const COMMON_ERROR_MESSAGE = {
    AMOUNT_REQUIRED: 'Amount is required',
    LOCATION_VALIDATION: '* Please select location ',
    DATE_REQUIRED: '* Please select date',
    START_END_DATE_VALIDATION: '* Please select start date and end date',
    UPLOAD_PDF_REQUIRED: 'Please upload pdf file',
    PRICE_NOT_FOUND: 'Price not found',
    UPLOAD_AUDIO_REQUIRED: 'Please upload audio file',
    UPLOAD_AUDIO_ALLOWED_FILE_MSG: 'Please upload mp3, wav, m4a, flac or acc file type only.',
    PDF_FILE_SIZE_VALIDATION: 'File is bigger than 20MB',
    SINGLE_PAGE_PDF_REQUIRED: 'Please upload single page pdf',
    FILE_NOT_PROPER: 'Upload file is not proper',
    PAYMENT_STATUS_CHANGE_MSG: 'Payment status has been changed successfully',
    PARMISSION_ERROR_MSG: "You don't have permission to the end point",
    CLIENT_REQUIRED_MSG: 'Please select client',
    PASSWORD_CONFIRMPASSWORD_ERROR_MSG: 'Password and confirm password must be same.',
    TERM_CONDITION_MSG: 'Please accept the terms and conditions.',
    OTP_VERIFY: 'We have sent you a verification OTP at ******',
    OTP_VERFY_SUCCESS: 'OTP verified succesfully',
    OTP_LIMIT: 'You have exhausted all your OTP attempts. Please log in again to verify the mobile phone',
    NOACTIVE_CLIENT_MSG: 'You do not have an active client.',
    CLIENT_NOT_APPROVE_MSG: 'Your account is under review. For any concerns, please write to support@nutritionforeverwellness.com',
    NO_CLIENT_MSG: 'You have no clients under your account. To purchase advertisement vouchers please add clients.',
    NO_ACCOUNT_CLIENT_MSG: 'You have not added any team member yet. To use all features of Stamina Core Plus, please add members under your account.',
    OTP_RESENT_LAST_ATTEMPT_MSG: 'This is your last attempt to resend otp',
    OTP_EXHAUSTED_ATTEMPT_MSG: 'You have exhausted all your OTP attempts.',
    VERIFY_PHONE: ' Please log in again to verify the mobile phone.',
    REASON_CANCEL_MSG: "* Please provide reason for rejection",
    TEXT_AREA_PPLACEHODER1: "Reason for rejection",
    TEXT_AREA_PPLACEHODER2: "Type a small brief(message)",
    CANT_CANCEL_ORDER: "You can not cancel order before",
    PREMIUM_CUSTOMER_INFO: "Our sales representative will call you with payment details",
    NO_DATA_FOUND: 'No record found',
    RO_FILE_SIZE_VALIDATION: 'RO file can not be bigger than 10MB',
    PDC_FILE_SIZE_VALIDATION: 'PDC file can not be bigger than 10MB',
    UPLOAD_RO_FILE_REQUIRED: 'Please upload pdf, jpeg, jpg, png, tiff file',
    AD_AUDIO_FILE_DURATION: 'Ad duration should not be more than 10',
    RADIO_AD_AUDIO_FILE_DURATION: 'Ad duration should not be more than ',
    AD_AUDIO_FILE_ERROR: 'Invalid file. Please try again with new ad file',
    AD_AUDIO_FILE_SIZE: 'Audio file is bigger than 10MB',
    AD_AUDIO_FILE_DURATION_ERROR: 'Please choose duration before uploading file.',
    AD_AUDIO_FILE_NOT_UPLOAD: '* Please upload file',
    AD_AUDIO_FILE_UPLOAD_ERROR: 'Invalid file. Please try again with new ad file',
    EMPTY_PDF_URL_ERROR: '* Please enter URL',
    INVALID_PDF_URL_ERROR: 'Invalid URL! Please enter a valid URL of Wetransfer with https',
    FILES_NOT_UPLOAD: 'Please upload files',
    FILES_OF_DOMAINS_NOT_UPLOAD: 'Please upload files related to all domains',
    DIGITAL_ALLOWED_FILE:'Invalid file format, Please upload only allowed file types',
    DIGITAL_INBANNER_INVALID_URL:'Provide valid youtube url.'

}

export const TOASTER_MESSAGE = {
    OFFER_PACKAGE_MSG: 'Please add insert and rate',
    EXPORT_DATA_MSG: 'Please select date to export data',
    COUPON_APPLICABLE_MINVALUE_ERROR_MSG: 'This coupon is only applicable on order Rs.',
    INVALID_COUPON_CODE_MSG: 'Invalid coupon code. Please select again.',
    HELP_AND_SUPPORT_SUCCESS_MSG: 'Thank you. We will contact you shortly.',
    ADVERTISEMENT_SELECTION_ERROR_MSG: 'Please select atleast one advertisement before proceeding.',
    ORDER_INVOICE_ERROR_MSG: 'Error in generating order invoice',
    FILE_UPLOADED_SUCCESS_MSG: 'File uploaded successfully',
    UNABLE_GENERATE_INVOICE: 'Unable to generate invoice',
    CHANGE_PWD_MSG: 'You have successfully changed your password',
    LOGGED_OUT_MSG: 'You have been logged out. Please login again.',
    // PAYMENT_INCOMPLETE_MSG: 'Please complete your payment details',
    PAYMENT_INCOMPLETE_MSG: 'Server error!!',
    SET_AGENCY_TYPE_MSG: 'You have successfully updated the agency type',
    MAXIMUM_DATE_SELECTION: "Maximum dates selection is upto 30. ",
    SAME_NUMBER_OF_DATE_ALLOW: "Please choose the same number of dates ",
    SAME_WEEKDAYS_WEEKEND_ALLOW: "Please choose the same number and type of days as selected during the first booking attempt. ",
    UPLOAD_FILE_ABOVE_ERROR:"Please upload file in above field first.",
    RADIO_SELECT_DATE_ERROR: "You can not select more dates, please add new package or proceed to payment.",
    PUBLISH_DATE_CHANGE_COUNT_GREATER:"Date count can not be greater than your first booking attempt",
    PUBLISH_DATE_CHANGE_COUNT_LESS:"Date count can not be less than your first booking attempt",
    RADIO_ANY_SPECIFIC_VALUE_CHANGE_WARNINF:"You have changed the time value timing order is rearraged please refill the data!",
    DIGITAL_UNSELECT_DATES:"Click on clear button to unselect the dates.",
    DIGITAL_COPY_ALL_WARNING: "Fields are empty, Please fill the data for performing the action."
}

export const POPUP_MESSAGE = {
    MARK_ACC_MSG: 'ARE YOU SURE YOU WANT TO MARK AS ACC ?',
    UNMARK_ACC_MSG: 'ARE YOU SURE YOU WANT TO UNMARK AS ACC?',
    SAVE_CHANGES_MSG: 'ARE YOU SURE YOU WANT TO SAVE THE CHANGES?',
    APPROVE_ORDER_MSG: 'ARE YOU SURE YOU WANT TO APPROVE ORDER ?',
    APPROVE_ORDER_MSG_PDC: 'Please confirm if you have successfully validated the Post-Dated-Cheque attached with this booking.',
    CHANGE_PAYMENT_STATUS_MSG: 'Are you sure, you want to change payment status?',
    CANCEL_ORDER: 'Do you want to cancel the order',
    CANCEL_PACKAGE: 'Do you want to reject the package',
    PAYMENT_INVOICE_MSG: 'YOUR PAYMENT LINK IS SENT TO',
    CHANGE_CURRENT_ORDER_DATA: 'Are you sure you want to proceed with another user , your ad booking progress will be lost!',
    CHANGE_CURRENT_PLATFORM: 'Are you sure you want to change the platform, your ad booking progress will be lost!',
    ACTIVE_CLIENT: 'Are you sure to activate this client?',
    DEACTIVE_CLIENT: 'Are you sure to deactivate this client?',
    CLIENT_APPROVED: 'This client will now go for approval to concerned team. Press OK to proceed',
    SAVE_CLIENT_MSG: 'If you want to save the changes please press OK',
    ORDER_PAYMENT_SUCCESS: 'Your order will be visible in order listing page',
    PUBLISH_DATE_CHANGE: 'ARE YOU SURE YOU WANT TO  CHANGE DATE ?',
    NEW_CREATIVE_UPLOAD: 'ARE YOU SURE THAT YOU WANT TO REPLACE WITH NEW CREATIVE?',
    ACTIVE_SUB_USER: 'Are you sure to activate this user?',
    DEACTIVE_SUB_USER: 'Are you sure to deactivate this user?',


}

export const EVENT_MESSAGE = {
    SLOT_SELECTION_ERROR_MSG: 'At first select slot date',
    SLOT_FULL_EROOR_MSG: 'This slot is full for this date'
}

export const BOOK_ADVERTISEMENT = {
    LOCATION_SELECTION_ERROR_MSG: 'Please select location for starting book advertisement journey.',
    INDUSTRY_SELECTION_ERROR_MSG: 'Please select industry for starting book advertisement journey.'
}

export const CAMPAIGN_ERROR_MESSAGE = {
    CAMPAIGN_MAX_AMOUNT_ERROR: 'Maximum accepted amount is 50,00,000.',
    CAMPAIGN_MIN_AMOUNT_ERROR: 'Minimum amount should be multiple of 1,000',
    CAMPAIGN_BUDGET: 'Allocation of print, radio and digital must be equal to total budget',
    CAMPAIGN_BUDGET_EXCCED: 'Your campaign budget has been exceeded, Please top-up your campaign budget or create an order without campaign '
}

export const PRINT_ORDERBOOKING_ERROR_MESSAGE = {
    PUBLICTATION_SELECTION_ERROR_MSG: 'Package creation is incomplete. Please choose publication type',
    ISSUETYPE_SELECTION_ERROR_MSG: 'Package creation is incomplete. Please choose issue type',
    CATEGORY_TYPE_SELECTION_ERROR_MSG: 'Package creation is incomplete. Please choose category type',
    EDITION_SUBEDITION_SELECTION_ERROR_MSG: 'Package creation is incomplete. Please choose edition/subEdition type',
    POSITION_SIZE_DIM_SELECTION_ERROR_MSG: 'Package creation is incomplete. Please choose position/size/dimensions',
    READYMADE_PACKAGE_POSITION_SIZE_DIM_SELECTION_ERROR_MSG: 'Package creation is incomplete. Please choose dimensions',
    PUBLICATION_DATE_SELECTION_ERROR_MSG: "You haven't chosen required dates of publication across listed editions",
    PUBLICATION_DATE_SELECTION_VALIDATION_ERROR_MSG: "Please note that dates highlighted in grey background are not available for respective editions. Kindly select the other dates to proceed further",
    SELECT_ACCEPT_PREVIEW: 'Please accept preview & process further',
    SELECT_PACKAGE_REQUIRED: "You don't have any package to proceed further"
    
}
export const RADIO_ORDERBOOKING_ERROR_MESSAGE = {
    RADIO_STATION_SELECTION__ERROR_MSG: 'Package creation is incomplete. Please choose radio station',
    ISSUETYPE_SELECTION_ERROR_MSG: 'Package creation is incomplete. Please choose issue type',
    CHOOSE_DATE_ERROR_MSG: 'Package creation is incomplete. Please choose ad date',
    CHOOSE_TIME_SLOT_SELECTION_ERROR_MSG: 'Package creation is incomplete. Please select time slot',
    CUSTUM_TIME_SLOT_FROM_SELECTION_ERROR_MSG: '* Please select from-time',
    CUSTUM_TIME_SLOT_TO_SELECTION_ERROR_MSG: '* Please select to-time',
    RADIO_DURATION_FREQ__SELECTION_ERROR_MSG: 'Package creation is incomplete. Please select ad duration',
    NFCT_MAX_CONTENT_LENGTH: 'Character length exceeding the maximum limit',
    NFCT_MAX_FREQUENCY_LIMIT_REACHED: "You can add maximum 1 RJ mentions per hour",
    NFCT_DAY_MAX_FREQUENCY_LIMIT_REACHED: "You can add maximum of 16 RJ mentions in a day"
}
export const DIGITAL_ORDERBOOKING_ERROR_MESSAGE = {
    DIGITAL_DOMAIN_SELECTION_ERROR_MSG: 'Package creation is incomplete. Please select at least 1 domain',
    CHOOSE_ALL_SIZE_ERROR:'Please select at least one size from the each ad types.',
    CHOOOSE_SIZE_ERROR: 'Please select sizes.',
    CAMPAIGN_DATE_ERROR: 'Campaign start date or end date value is incorrect or emtpy.',
    START_DATE_ERROR:'Start date value is incorrect or empty.',
    END_DATE_ERROR:'End date value is incorrect or empty.',
    START_DATE_GREATER_PRODUCT_ERROR:'Incorrect value start date of campaign should be minimal or equal value of products start date.',
    START_DATE_GREATER_ERROR:'Incorrect value start date should be less than end date.',
    COPY_FOR_ALL_SIZES_WARNING: 'The sizes of the first domain is not available in other domains, please select sizes of other domains separately.',
    COPY_FROM_PREVIOUS_WARNING: 'Selected size of previous domain is not available in current domain, please choose another size.',
    IMPRESSION_VALUE_ERROR: 'Impression will always be greater than zero.',
    IMPRESSION_EMPTY_ERROR_MSG: 'Impression is empty please fill the value.',
    IMPRESSION_NAN_ERROR_MSG: 'Impression will always be numbers.',
    CHECK_ERRORS:'Please check above errors.',
    GEOGRAPHY_VALUE_ERROR:'Geography value is required',
    INSTRUCTIONS_VALUE_ERROR:'Additional instructions is required',
    GEOGRAPHY_LIMIT_ERROR:'Geography should be of max 255 characters',
    INSTRUCTIONS_LIMIT_ERROR:'Additional instructions should be of max 255 characters'
}
export const TOOL_TIP_MESSAGE = {
    ACC_INFO_ADMIN: "Rates as mentioned in the RO shall be considered final",
    ACC_INFO_USER: "Rates as discussed with sales team shall be considered final"


}
