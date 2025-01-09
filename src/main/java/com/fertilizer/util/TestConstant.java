package com.fertilizer.util;


public enum TestConstant {
	BASEURL("http://localhost:"),
	SIGNIN("/api/auth/signin"),SIGNOUT("/api/auth/signout"),API("/api/"),USERSIGNUP("sign-up"),SFDCSIGNIN("/api/auth/sfdc-login"),
	userList("/api/user/registered-users"),USERVOUCHERDETAILFORADMIN("/api/user/voucher_detail"),
	USERVOUCHERCOUNT("/api/user/users-vouchers-count"),USERUPDATAURL("/api/user/me"),ADMINUPDATEUSERUPDATAURL("/api/user/update"),
	USERME("/api/user/me"),USERDETAIL("/api/user/"),RESENDREGISTRATIONOTP("/api/get-resend-registration-otp"),USERPICTURE("/api/user/picture"),
	USERCITYLIST("/api/user-city"),USERANDVOUCHURElDETAIL("/api/user/detail"),USERDETAILFORGRAPHADMIN("/api/user/users-count"),
	STATE("/api/state"),CITY("/api/city"),PROMOTIONSLIST("/api/home/promotions/listing"),BANNERLIST("/api/home/banner/listing"), 
	AGENCYCLIENTDETAIL("/api/client/agency-clients-details"),CLIENTDETAIL("/api/client/user-agency-clients"),
	BUSINESSCATEGORYLIST("/api/ad-business-category/listing"),
	ADDPUBLICQUERY("/api/query/add"),ADDBROCHURE("/api/query/brochure"),
	GETMOBILEVERIFYOTP("/api/generate-otp"),VERIFYMOBLILEOTP("/api/verify-genratedotp"),
	TESTIMONIALLIST("/api/testimonial/listing"),
	VOUCHERSLABLIST("/api/voucher_slab"),ORDERSDECODE("/api/order/encq-request"),
	ADDCLIENT("/api/client"),UPDATECLIENT("/api/client"),VIEWCLIENTBYID("/api/client/"),VOUCHERLIST("/api/voucher")
	,GSTVALUE("/api/voucher/gstvalue"),USERVOUCHERPURCHASE("/api/voucher/user-voucher-purchase"),ADMINVOUCHERPURCHASE("/api/voucher/purchase-voucher"),
	FORGOTPASSWORDOTP("/api/get-password-recovery-otp"),VERIFYFORGOTPASSWORDRECOVERYOTP("/api/verify-password-recovery-otp"),
	USERCAMPAIGNSLIST("/api/campagin/list"),USERCAMPAIGNS("/api/campagin"),
	CREATEREADYMADEPACKAGE("/api/custom-package/create"),UPDATEREADYMADEPACKAGE("/api/custom-package/update"),VIEWREADYMADEPACKAGE("/api/custom-package/"),READYMADEPACKAGEUSERLIST("/api/custom-package/user-list"),ORDERSAPFROMSFDC("/api/order/sap-update"),
	READYMADEPACKAGELIST("/api/custom-package/list"),ORDERPAYMENTSUMMARY("/api/order/payment-summary"),ORDERPAYMENTDETAIL("/api/order/pd"),ORDERTEMPFILEUPLOAD("/api/order/temp-file-upload"),
	VIEWPACKAGEBYID("/api/order/package/"),VIEWRADIOPACKAGEBYID("/api/order/radio/package/"),PACKAGELISTINGFORUSER("/api/order/package/list"),VIEWPACKAGEBYADMIN("/api/order/package/view"),PACKAGELISTINGFORADMIN("/api/order/user/package/list"),
	GETLOCATIONS("/api/csv/ad-location/list"),GETPUBLICATIONS("/api/csv/ad-publication/list"),GETISSUE("/api/csv/ad-issue/list"),
	GETPRODUCTCATEGORIES("/api/csv/ad-product/list"),GETEDITIONLIST("/api/csv/ad-edition/list"),GETSUBEDITIONLIST("/api/csv/ad-subedition/list"),
	GETPOSITIONLIST("/api/csv/ad-position/list"),GETSIZELIST("/api/csv/ad-size/list"),GETVOLUMELIST("/api/csv/ad-volume/list"),
	GETEDITIONMULTILIST("/api/csv/ad-edition/multi-list"),GETSUBEDITIONMULTILIST("/api/csv/ad-subedition/multi-list"),ADPRINTRATELIST("/api/csv/ad-print-rate/list"),
	ADPRINTRATEMULTILIST("/api/csv/ad-print-rate/multi-list"),PRICECALCULATION("/api/csv/price"),
	ORDERPRICECALCULATION("/api/order/price"),
	VOUCHERLISTBYID("/api/voucher/pd/"),VOUCHERCOUNT("/api/voucher/count"),PURCHASEVOUCHERPAYMENT("/api/voucher/purchase-voucher-payment"),
	VOUCHERSCOUNT("/api/voucher/voucher-count"),USERVOCUHERPURCHASEBYID("/api/voucher/user-voucher-purchase/"),
	COMPANYLIST("/api/voucher/company_list"),
	INVOCIEBYID("/api/voucher/invoice/"),
	UPDATEPAYMENTSTATUSURL("/api/voucher/change-payment-status"),
	DELETEVOUCHER("/api/voucher/"), ORDERCREATE("/api/order/user-order-create"),ORDERUSERCANCEL("/api/order/user-cancel-package"),ADMINACTIONPACKAGE("/api/order/admin-action-package"),USERREDIRECT("/api/order/user-redirect"),
	USERORDERPURCHASE("/api/order/user-order-purchase"),UPLOADPACKAGECONTENT("/api/order/package/upload-content"),TEMPFILEUPLOAD("/api/order/temp-file-upload");
	private final String name;

	TestConstant(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	};
}
