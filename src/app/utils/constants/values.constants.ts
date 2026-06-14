export const VALUE_CONSTANTS: any = {
    'BANNER_BASE_VALUE': 50,
    'MIN_VOUCHER_VALUE': 100000,
    'BRONZE_RANGE_MAX_VALUE': 1000000,
    'SILVER_RANGE_MAX_VALUE': 10000000,
    'PERCENTAGE_DISCOUNT': 0.1,
    'MAX_TOTAL_REGISTER_USER': 100,
    'STARTER_PLAN_RANGE_MIN_VALUE': 100000,
    'STARTER_PLAN_RANGE_MAX_VALUE': 1000000,
    'SMART_PLAN_RANGE_MAX_VALUE': 2500000,
    'CORPORATE_PLAN_MAX_VALUE': 5000000,
    'emailPattern': '^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$',
    'actioncomment': '^(?!.*--)(?!.*@@)[ A-Za-z0-9_.,@-]*$',
    'bankNamePattern': "/^[A-Za-z]+$/",
    'passwordPattern': "^(?=.{6,40})(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[*@#$%^&?!_-]).*$",
    'CityValidationPattern': '^[ A-Za-z]+$',
    'NotAllowOnlyWhiteSpace': '.*\\S.*',
    'isDirectlySaveInLocal': true,
    'gstValidationPattern': "[a-zA-Z0-9]{15}$",
    'CAMPAIGN_MULTIPLE': 1000,
    'MIN_CAMPAIGN_AMOUNT': 1000,
    'EXPORT_DATA_MAX_SIZE': 2000,
    'AD_RATE_PRINT_LIST_TOTAL': 20,
    'CAMPAIGN_MAX_RANGE': 5000,
    'AMOUNTPLEDGEDED': 100,
    'ADMINAMOUNTPLEDGEDED': 1000,
    'MAX_FILE_UPLOAD_SIZE': 20,
    "UPLOAD_FILE_BYTE": 20971520,
    "DIFFERENT_CREATIVE": 'different',
    'SAME_CREATIVE': 'same'
}

export const urls: any = {
    home_page_video: 'https://www.youtube.com/embed/LYHQR1h5QpM?autoplay=1',
    home_page_blog: 'https://nutritionforeverwellness.com/blog',
    home_page_newsletter: 'https://nutritionforeverwellness.com/newsletter',
    home_page_case_studies: 'https://nutritionforeverwellness.com/case-studies',
    facebook_page_url: 'https://www.facebook.com/nutritionforeverwellness/',
    facebook_page_text: 'facebook.com/nutritionforeverwellness',
    support_mail_id: 'mailto:support@nutritionforeverwellness.com',
    support_mail_id_text: 'support@nutritionforeverwellness.com',
    support_contact_num: 'tel:+919719330695',
    support_contact_num_text: '+91 97193 30695',
    walkthrough_video: 'https://nutritionforeverwellness.com/assets/video/StaminaCorePlus_Walkthrough.mp4',

    // Backwards-compatible aliases (older code may still reference adworks_*)
    adworks_mail_id: 'mailto:support@nutritionforeverwellness.com',
    adworks_mail_id_text: 'support@nutritionforeverwellness.com',
    adworks_contact_num: 'tel:+919719330695',
    adworks_contact_num_text: '+91 97193 30695',
    adworks_walkthrough_video: 'https://nutritionforeverwellness.com/assets/video/StaminaCorePlus_Walkthrough.mp4',
}
export const configUrl: any = {
    maintance_mode: false,
    visibleBookAddFlow: true,
    visibleBuyVoucherFlow: true,
}

export const allowedAdminUrls = [
    "https://admin.nutritionforeverwellness.com",
    "https://app.nutritionforeverwellness.com",
    "http://localhost:4200",
    "http://localhost:4400"
];
