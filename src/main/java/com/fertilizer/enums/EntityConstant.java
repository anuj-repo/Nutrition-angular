package com.fertilizer.enums;

/**
 * @author Dhiraj
 *
 */
public enum EntityConstant {
	// common fields
	ID("id"), ADDRESS("address"), PIN("pin"), PINCODE("pincode"), STATUS("status"),
	ROLE_PREAPPANDER_FOR_SECURITY("ROLE_"), HTTPS("https://"), FORWARDSLASH("/"),

	COMPANY("company"), STATE("state"), COUNTRY("country"), PRIMARYUSERID("primaryUserId"),
	ACTIVECONTRACTID("activeContractId"), CITYNAME("cityName"), STATENAME("stateName"), COUNTRYNAME("countryName"),
	COMPTYPE("compType"), EMAIL("email"), STORECOUNT("storeCount"), CONTRACTFROM("contractFrom"),
	CONTRACTTO("contractTo"), CREATEDAT("createdAt"), UPDATEDAT("updatedAt"), ASC("asc"), DESC("desc"),
	PHONE1("phone1"), PHONE2("phone2"), LANDLINE("landline"), PRIMETIMESTARTSAT("primeTimeStartsAt"),
	PRIMETIMEENDSAT("primeTimeEndsAt"), COMPANYPROFILESETTINGS("companyProfileSettings"),
	CONTACTPERSON1NAME("contactPerson1Name"), CONTACTPERSON1PHONE("contactPerson1Phone"),
	CONTACTPERSON1EMAIL("contactPerson1Email"), CONTACTPERSON2NAME("contactPerson2Name"),
	CONTACTPERSON2PHONE("contactPerson2Phone"), CONTACTPERSON2EMAIL("contactPerson2Email"), CONTRACTO("contractTo"),
	CONTRACTREMARKS("contractRemarks"), LOGO("logo"), MUSICGROUP("musicGroup"), ZONE("zone"), REGION("region"),
	STOREMANAGER("storeManager"), STORECODE("storeCode"), STORENAME("storeName"), STORECOMMENT("storeComment"),
	GROUPNAME("groupName"), ISINTERNALADALLOWED("isInternalAdAllowed"), ISEXTERNALADALLOWED("isExternalAdAllowed"),
	BILLPAYMENTFREQUENCY("billPaymentFrequency"), CONTACTPERSON1PHONESTDCODE("contactPerson1PhoneStdCode"),
	CONTACTPERSON2PHONESTDCODE("contactPerson2PhoneStdCode"), REGIONNAME("regionName"),
	STORERUNSTATUS("storeRunStatus"), ZONENAME("zoneName"), COMPANYID("companyId"), AGENCYID("agencyId"),
	ISDETAILCOMPLETED("isDetailCompleted"),

	PURCHASEFOR("purchaseFor"), CLIENCOMPNAME("clientCompName"), COMPANYLOGO("companyLogo"), USERNAMES("userName"),
	ISCLIENTFORDIGITAL("isClientForDigital"), ISCLIENTFORRADIO("isClientForRadio"),
	ISCLIENTFORPRINT("isClientForPrint"), APPROVEDFORDIGITAL("approvedForDigital"),
	APPROVEDFORRADIO("approvedForRadio"), APPROVEDFORPRINT("approvedForPrint"),

	FORGETPASSWORDTOKEN("forgetPasswordToken"), PACKAGES("packages"), PACKAGEID("packageId"),
	RADIOPACKAGEDETAILS("RadioPackageDetails"), WALKTHROUGHVIDEOSEEN("walkThroughVideoSeen"),

	// company
	COMPCODE("compCode"), COMPNAME("compName"), COMPLOGO("logo"), COMPANYPROFILE("companyProfileSettings"),
	CONTRACTAPPLIEDFROM("contractAppliedFrom"), CONTRACTAPPLIEDTO("contractAppliedTo"),
	ISMASTERRULEVERIFIED("isMasterRuleVerified"), CONTRACTBILLINGRATE("contractBillingRate"), CITY("city"),
	CITYID("cityId"), STORE("store"), REGIONS("regions"), ZONES("zones"), GROUPS("groups"),

	// CITY
	STATEID("stateId"), COUNTRYID("countryId"), ZIPCODE("zipcode"), PUBLICUSE("publicUse"),
	// For user entity
	PRINTORDERLASTLOCATIONS("printOrderLastLocations"), RADIOORDERLASTLOCATIONS("radioOrderLastLocations"),
	DIGITALORDERLASTLOCATIONS("digitalOrderLastLocations"), ISPREMIUMRADIOCUSTOMER("isPremiumRadioCustomer"),
	ISPREMIUMPRINTCUSTOMER("isPremiumPrintCustomer"), ISPREMIUMDIGITALCUSTOMER("isPremiumDigitalCustomer"),
	ISPREMIUMORDER("isPremiumOrder"), ISACCREDITEDCUSTOMER("isAccreditedCustomer"), ISPDCENABLED("isPdcEnabled"),
	USERNAME("username"),
	USERTYPE("userType"), FNAME("fname"), LNAME("lname"), ROLE("role"), FULLNAME("fullName"),
	COMPANYHASUSER("companiesHasUsers"), COMPANYUSER("companyUser"), COMPANIES("companies"), USERLEVEL("userLevel"),
	ROLENAME("roleName"), ROLEIDENTIFIER("roleIdentifier"), STOREIDS("storeIds"), REGIONID("regionId"),
	ZONEID("zoneId"), GROUPMANAGERID("groupManagerId"), GROUPCOMMENT("groupComment"), ISDEFAULT("isDefault"),
	USERSPROFILESETTINGS("usersProfileSettings"), PHONE1STDCODE("phone1StdCode"), MUSICLICENSE("musicLicense"),
	PHONE2STDCODE("phone2StdCode"), STOREMANAGEREMAIL("storeManagerEmail"), ISPHONEVERIFIED("isPhoneVerified"),
	STOREMANAGERFIRSTNAME("storeManagerFirstName"), STOREMANAGERLASTNAME("storeManagerLastName"),
	MUSICGROUPNAME("musicGroupName"), STOREMANAGERPHONESTDCODE("storeManagerPhoneStdCode"),
	STOREMANAGERPHONE("storeManagerPhone"), ZONEIDS("zoneId"), GROUP("group"), GROUPID("groupId"),
	GROUPPLAYLISTID("groupPlayListId"), TIMESLOTID("timeSlotId"), DAYSLOTID("daySlotId"),
	PERSLOTDURATION("perSlotDuration"), NUMBEROFSLOTPERHOUR("numberOfSlotPerHour"), CHART("chart"),
	MULTIMEDIA("multimedia"), TITLE("title"), MEDIADURATION("mediaDuration"), GROUP_CONCAT("group_concat"),
	LANGUAGEID("languageId"), OPTIMUMVOLUMELEVEL("optimumVolumeLevel"), LANGUAGE("language"), GENREID("genreId"),
	LANGUAGES("languages"), SINGER1NAME("singer1Name"), ALBUMNAME("albumName"), MOODS("moods"),
	GENRETITLE("genreTitle"), SLOTTITLE("slotTitle"), SLOTFOR("slotFor"), STARTHOUR("startHour"), ENDHOUR("endHour"),
	STATECODE("stateCode"), CHARTSONGS("chartSongs"), CHARTTITLE("chartTitle"), ISPUBLICCHART("isPublicChart"),
	CHARTFORCOMPANY("chartForCompany"), CHARTLOGO("chartLogo"), ARTISTNAME("artistName"), GENDER("gender"),
	MOODNAME("moodName"), LYRICISTNAME("lyricistName"), LICENSENAME("licenseName"), LANGNAME("langName"),
	LANGUAGECOUNTRIES("languageCountries"), LABELNAME("labelName"), GENRENAME("genreName"),
	PARENTGENREID("parentGenreId"), FESTIVENAME("festiveName"), ERANAME("eraName"), ERASTARTYEAR("eraStartYear"),
	COMMENT("comment"), COUNTRYCODESHORT("countryCodeShort"), COUNTRYCODELONG("countryCodeLong"),
	COUNTRYNUMERICCODE("countryNumericCode"), SUBREGION("subRegion"), COMPOSERNAME("composerName"),
	CITYTYPE("cityType"), BRANDNAME("brandName"), PLAYLISTID("playListId"), NEWUPDATESTATUS("newUpdateStatus"),
	STOREID("storeId"), SCHEDULEDATE("scheduleDate"), SLOTNUMBER("slotNumber"), AUDIOLENGTH("audioLength"),
	SCHEDULEENDSAT("scheduleEndsAt"), USERONBOARDEDSTEP("userOnboardedStep"),
	MUSICSCHEDULENOTIFICATIONCOUNT("musicScheduleNotificationCount"), CHARTCONVERSIONCOUNT("chartConversionCount"),
	ROLES("roles"), GROUPSID("groupsId"), ZONESID("zonesId"), REGIONSID("regionsId"), PASSWORD("password"),
	COMPANIESHASUSERS("companiesHasUsers"), FIND_IN_SET("FIND_IN_SET"), CREATEDBY("createdBy"), SONGTYPE("songType"),
	MEDIANATURE("mediaNature"), SUBGENREID("subGenreId"), ERAID("eraId"), LICENSEID("licenseId"), MOODIDS("moodIds"),
	FESTIVESSEASONSID("festivesSeasonsId"), SINGER1ID("singer1Id"), SINGER2ID("singer2Id"), SINGER3ID("singer3Id"),
	SINGER4ID("singer4Id"), SINGER1GENDER("singer1Gender"), SINGER2GENDER("singer2Gender"),
	SINGER3GENDER("singer3Gender"), SINGER4GENDER("singer4Gender"), COMPOSERID("composerId"), TEMPOID("tempoId"),
	RATINGCHARTID("ratingChartId"), LYRICISTID("lyricistId"), ACTOR1ID("actor1Id"), ACTOR2ID("actor2Id"),
	ACTOR3ID("actor3Id"), SINGER2NAME("singer2Name"), SINGER3NAME("singer2Name"), SINGER4NAME("singer4Name"),
	LANGUAGETITLE("languageTitle"), LICENSETITLE("licenseTitle"), RELEASEYEAR("releaseYear"), MEDIAIMAGE("mediaImage"),
	FILENAME("fileName"), OTHERNOTIFICATIONS("otherNotifications"), GROUPPLAYLISTS("groupPlaylists"),
	GROUPCOMPANYID("groupCompanyId"), SONGFOLDER("songFolder"), ENTITYID("entityId"), BODY("body"), LOGGID("loggid"),
	COMPANYUSERCOMPANIESID("companyUserCompaniesId"), PLAYFORDAY("playForDay"), LOGINDATE("loginDate"),
	LOGGEDINTIME("loggedInTime"), LOGGEDOUTTIME("loggedOutTime"), STORECOUNTLRESULT("StoreCountlResult"),
	STORES("stores"), POINTS("points"), USER("user"), CONTENT("content"), TESTIMONIALTYPE("testimonialType"),
	SUBMITTEDON("submittedOn"), EVENTCODE("eventCode"), EVENTUSERS("eventUsers"), ISDEFAULTEVENT("isDefaultEvent"),
	EVENTPARTICIPATIONCODE("eventParticipationCode"), EVENTUSERSID("eventUsersId"), EVENTHIDEON("eventHideOn"),
	NAME("name"), EVENTNAME("eventName"), EVENTSTARTDATE("eventStartDate"), EVENTENDDATE("eventEndDate"),
	EVENTSTARTSAT("eventStartsAt"), EVENTENDSAT("eventEndsAt"), EVENTCITY("eventCity"), EVENTSTATE("eventState"),
	EVENTCOUNTRY("eventCountry"), EVENTADDRESS("eventAddress"), EVENTCOORDINATOR("eventCoordinator"),
	EVENTSHORTDESC("eventShortDesc"), EVENTDETAILS("eventDetails"), EVENTIMAGE("eventImage"), USERIMAGE("userImage"),
	EVENTVIDEO("eventVideo"), USERLOGO("userLogo"), UNDERSCORE("_"), USERCREDITS("userCredits"), CLIENTID("clientId"),
	LOYALTYPOINT("loyaltyPoint"), VOUCHERAMOUNT("voucherAmount"), DESIGNATION("designation"), EVENTS("events"),
	CONTACT("contact"), CONTACTSTDCODE("contactStdCode"), PARTICIPATIONSTATUS("participationStatus"),
	EVENTID("eventId"), USERID("userId"), VOUCHERCODE("voucherCode"), PLEDGEDAMOUNTPERCENT("pledgedAmountPercent"),
	USERCLIENT("userClient"), PAYMENTMODE("paymentMode"), PAYMENTSTATUS("paymentStatus"), PAYMENTAT("paymentAt"),
	DISPLAYTEXT("displayText"), ANONYMOUSUSER("anonymousUser"), VOUCHERS("vouchers"), SALUTATION("salutation"),
	VOUCHERSMOUNTWITHTAX("voucherAmountWithTax"), VOUCHERUSEDAMOUNT("voucherUsedAmount"),
	PLEDGEDUSEDAMOUNT("pledgedUsedAmount"), ISUSERFORPRINT("isUserForPrint"), ISUSERFORDIGITAL("isUserForDigital"),
	ISUSERFORRADIO("isUserForRadio"),
	// DiscountCoupon
	COUPONCODE("couponCode"), COUPONTYPE("couponType"), DISCOUNTTYPE("discountType"), DISCOUNTVALUE("discountValue"),
	MAXVLUE("maxDiscountValue"), STARTDATE("startDate"), ENDDATE("endDate"), NUMBEROFUSE("numberOfUse"),
	COUPONFOR("couponFor"), USERIDS("userIds"), CATID("catId"), ADPRODUCTCATEGORIES("adProductCategories"),
	USEREMAIL("userEmail"), ACCOUNTTYPE("accountType"), MINORDERVALUE("minOrderValue"), EDITION("edition"),
	SLAB("slab"), APPLICABLEIN("applicableIn"), LASTUSEDAT("lastUsedAt"), LASTUSEDBY("lastUsedBy"),
	SLABTITLE("slabTitle"),

	// Gst
	GSTNUMBER("gstNumber"), PANNUMBER("panNumber"),

	// CategoryNew
	CATCODE("catCode"), PARENTCATEGORY("categoryParentId"), ISSPECIALCATEGORY("isSpecialCategory"),
	CATLEVEL("catLevel"), COMPANYSIZE("companySize"), BUSINESSCATEGORY("businessCategory"),
	BUSINESSCATEGORYOTHER("businessCategoryOther"), HASADVERTISEDBEFORE("hasAdvertisedBefore"),
	ADVERTISEDIN("advertisedIn"), ADVERTISEDOTHERIN("advertisedOtherIn"), RECEIVENOTIFICATION("receiveNotification"),
	ADBUSCATNAME("adBusCatName"), COMPANYTURNOVER("companyTurnOver"),

	// CategoryNew
	EVENTTIMESLOTDTO("eventTimeSlotDto"), EVENTTIMESLOT("eventTimeSlot"), PLEDGEDAMOUNT("pledgedAmount"),

	// PublicationCSVList
	PUBLICATIONTITLE("publicationTitle"), PUBLICATIONCODE("publicationCode"), ADTYPE("Ad Type"), ADSIZE("adSizes"),
	ADTYPETITLE("adTypeTitle"), ADTYPECODE("adTypeCode"), LOCATIONNAME("adLocationName"),
	LOCATIONCODE("adLocationCode"), PUBLICATIONS("publications"), PUBLICATIONIMAGE("publicationImage"),

	// issue
	ISSUEID("issueId"), ISSUENAME("issueName"), ISSUECODE("issueCode"), ISSUETITLE("issueTitle"),
	ISSUEIMAGE("issueImage"), DISCRIPTION("desciption"), NUMBEROFCIRCULATION("numberOfCirculation"),

	DAYTYPE("dayTypes"), ADPOSITION("Ad Position"), ADTYPE2("adtype"), ADCOVERAGETITLE("adCoverageTitle"),
	COVERAGEID("coverageId"), RATE("rate"),

	// Promotions
	PROMONAME("promoName"), PROMOCODE("promoCode"), PROMOSTARTDATE("promoStartDate"), PROMOENDDATE("promoEndDate"),
	PROMOSHORTDESC("promoShortDesc"), PROMOIMAGE("promoImage"), LINKURL("linkUrl"),

	// AdProduct
	CATEGORYNAMEHEADER("Category Name*"), SFDCNAMEADER("SFDC Name*"), IMAGENAMEHEADER("Image Name"), CHANNEL("channel"),
	CATEGORYSAPCODE("categorySapCode"),SAPCODE("SapCode"), CATEGORYNAME("categoryName"), CATEGORYIMAGE("categoryImage"),

	// AdPosition
	PUBLICATIONNAMEHEADER("Publication Name*"), ADPOSITIONNAMEHEADER("Ad Position Name*"),
	ADPUBLICATION("adPublication"), ADPUBLICATIONS("adPublications"), ADPOSITIONTITLE("adPositionTitle"),
	ADPOSITIONCODE("adPositionCode"),

	// AdLocation AdPublication AdIssue AdEdition
	LOCATIONNAMEHEADER("Location Name*"), ISSUETYPEHEADER("Issue Type*"), EDITIONNAMEHEADER("Edition Name*"),

	// AdSubEdition
	ISSUENAMEHEADER("Issue Name*"), SUBEDITIONNAMEHEADER("Sub Edition Name*"),
	SUBEDITIONSFDCCODEHEARED("Sub Edition SFDC Code*"),

	// AdSize
	PAGEPOSITIONHEADER("Page Position*"), ADSIZEHEADER("Ad Size*"), SIZETITILE("sizeTitle"), SIZECODE("sizeCode"),

	// AdEdition
	EDITIONID("editionId"), POSITIONID("positionId"), PUBLICATIONID("publicationId"), LOCATIONID("locationId"),
	EDITIONNAME("editionName"), EDITIONCODE("editionCode"), SUBEDITIONID("subEditionId"),
	SUBEDITIONNAME("subEditionName"), SUBEDITIONCODE("subEditionCode"), SIZEID("sizeId"),

	// AddVolume
	ADSIZEINDEXHEADER("Ad_Size_Index__c"), PAGEPOSITIONCHEADER("Page_Position__c"), PUBLICATIONHEADER("Publication__c"),
	HEIGHTHEADER("Height"), WIDTHHEADER("Width"), VOLUMEHEADER("Volume__c"), HEIGHT("height"), WIDTH("width"),

	// AdLocationStation

	RADIOSTATIONHEADER("Radio Station*"), ISSUETYPE("Issue Type"),
	PUBLICATIONLOCATIONCODEHEADER("Publication_location_code*"), RADIOFREQUENCYHEADER("Frequency"),
	REVENUERANKINGHEADER("Revenue Ranking"), BRANDNAMEHEADER("Brand Name"), TAGLINEHEADER("Tag Line"),
	DESCRIPTIONHEADER("Description"), PUBLICATIONLOCATIONCODES("publicationLocationCode"),
	RADIOPUBLICATIONLOCATIONCODES("radioPublicationLocationCode"), RADIOFREQUENCY("radioFrequency"),
	ISPOPULARSTATION("isPopularStation"), POPULARITYINDEX("popularityIndex"), REVENUEINDEX("revenueIndex"),

//	AdPrintRate
	PRODUCTNAME("Product Name"), PRODUCTID("Product ID"), PRODUCTSALESREGION("Product Sales Region"),
	ADCATEGORY("Ad Category"), PUBLICATIONHEADER1("Publication"), PRODUCTCATEGORY("Product Category"),
	CLASSIFICATION("Classification"), DAY("Day"), BASEPRICE("Base Price"), ISSUE("Issue"),
	ADSIZEINDEXHEADER1("Ad Size Index"), EDITIONNAMEHEADER1("Edition"), SUBEDITIONNAMEHEADER1("Sub Edition"),
	PAGEPOSITIONHEADER1("Page Position"), POSITIONPREMIUMHEADER("Position Premium"),
	WEEKENDPREMIUMHEADER("Weekend Premium"), LISTPRICE("List Price"), TOTALRATECARD("Total Rate Card"),
	BENCHMARKPRICE("Benchmark Price"), MINPRICE("Minimum Price"), ISPREMIUMHEADER("Ispremium"),
	GOLDZONEAUTHHEADER("Gold Zone Authority"), SILVERZONEAUTHHEADER("Silver Zone Authority"),
	BRONZEZONEAUTHHEADER("Bronze Zone Authority"), COLORHEADER("Color"),

	// User Vochures
	VOUCHERCATID("voucherCatId"), TRANSACTIONTYPE("transactionType"), USEDAMOUNT("usedAmount"),
	ADMINACTIVATED("adminActivated"), TINYURL("tinyUrl"),

	// AdPrint Rate
	ADPRINTRATEID("adItemRateId"), SFDCPRODUCTCODE("sfdcProductCode"), SFDCPRODUCTNAME("sfdcProductName"),
	ADCATEGORYTYPE("adCategoryType"), PRODUCTCATID("productCatId"), RATEDAY("rateDay"), BASERATE("baseRate"),
	POSITIONPREMIUM("positionPremium"), WEEKENDPREMIUM("weekendPremium"), LISTRATE("listRate"), ITEMTYPE("itemType"),
	BENCHMARKRATE("benchmarkRate"), MINIMUMRATE("minimumRate"), ISPREMIUM("isPremium"), GOLDZONEAUTH("goldZoneAuth"),
	SILVERZONEAUTH("silverZoneAuth"), BRONZEZONEAUTH("bronzeZoneAuth"), COLOR("color"),
	PRODUCTCLASSIFICATION("productClassification"), TOTALRATE("totalRate"), RADIOPRODUCTTYPE("radioProductType"),
	RADIONFCTTYPE("radioNfctType"), RADIODAYPART("radioDayPart"),

	// campaign
	CAMPAIGNAME("campaignName"), CAMPAIGNAMOUNT("campaignAmount"), CAMPAIGNRADIOAMOUNT("campaignRadioAmount"),
	CAMPAIGNPRINTAMOUNT("campaignPrintAmount"), CAMPAIGNDIGITALAMOUNT("campaignDigitalAmount"),
	CAMPAIGNSTARTDATE("campaignStartDate"), CAMPAIGNENDDATE("campaignEndDate"), CAMPAIGNPURPOSE("campaignPurpose"),
	ISCAMPAIGNPRINT("isCampaignPrint"), ISCAMPAIGNDIGITAL("isCampaignDigital"), ISCAMPAIGNRADIO("isCampaignRadio"),
	CAMPAIGNID("campaignId"),

	// order
	BOOKINGDATE("bookingDate"), PACKAGEPRICE("packagePrice"), ORDERPAYMENTID("orderPaymentId"), USER_ID("user_id"),
	COMPOSESTATUS("composeStatus"), ORDERID("order_id"), ORDERS("orders"), ORDERPAYMENT("orderPayment"),
	INVNUMBER("invNumber"), ORDERTYPE("orderType"), ORDERFOR("orderFor"), ORDERAMOUNT("orderAmount"),
	PROMOID("promoId"), TOTALPROMODISCOUNT("totalPromoDiscount"), TOTALVOUCHERDISCOUNT("totalVoucherDiscount"),
	GSTPERCENT("gstPercent"), TOTALGSTVALUE("totalGstValue"), TOTALAGENCYDISCOUNT("totalAgencyDiscount"),
	INVPDFNAME("invPdfName"), INTERNALORDERNUMBER("internalOrderNumber"), ROFILENAME("roFileName"),PDCFILENAME("pdcFileName"),OWNERID("ownerId"),

	// Ad order package
	CAMPAIGNS("campaigns"), CLIENT("client"), PUBLICATION("publication"), VOLUMEID("volumeId"), VOLUME("volume"),
	VOUCHERGST("voucherGst"), LOCATIONSID("locationsId"), CATEGORYID("categoryId"),
	EDITIONSUBEDITIONIDS("editoinSubeditionIds"), EDITIONSUBEDITION("editoinSubedition"),EDITREQUESTCOUNT("editRequestCount"),
	PUBLISHINGDATES("publishingDates"), GSTVALUE("gstValue"), AGENCYDISCOUNT("agencyDiscount"),
	VOUCHERDISCOUNT("voucherDiscount"), PROMODISCOUNT("promoDiscount"), LENGTH("length"), BREADTH("breadth"),
	ADCONTENTFILENAME("adContentFileName"),ADCONTENTFILEDETAILS("adContentFileDetails"), PACKAGEAMOUNT("packageAmount"), SLABDISCOUNT("slabDiscount"),
	SLABDISCOUNTPERCENT("slabDiscountPercent"), VOUCHERDISCOUNTPERCENT("voucherDiscountPercent"),
	AGENCYDISCOUNTPERCENT("agencyDiscountPercent"), CANCELREASON("cancelReason"), REJECTIONREASON("rejectionReason"),
	PAYMENYREFUNDSTATUS("paymentRefundStatus"), PACKAGEPAYMENTID("packagePaymentId"), RADIOTIMESLOT("radioTimeSlot"),

	SFDCREQUESTID("sfdcRequestId"), RADIOTIMESLOTSTART("radioTimeSlotStart"), RADIOTIMESLOTEND("radioTimeSlotEnd"),
	SFDCOPPORTUNITYID("sfdcOpportunityId"), SFDCREQUESTSTATUS("sfdcRequestStatus"), RADIOADTYPE("radioAdType"),
	RADIOADLENGTH("radioAdLength"), RADIOPLAYDAYFREQUENCYCOUNT("radioPlayDayFrequencyCount"),

	BOOKEREMAIL("bookerEmail"), BOOKERPHONE("bookerPhone"), SALESEMAIL("salesEmail"),
	FILEUPLOADSTATUS("fileUploadStatus"), ADDITIONALSPECIFICATION("additionalSpecification"),
	RADIOADSUBTYPE("radioAdSubType"),ROLOGS("roLogs"),PDCLOGS("pdcLogs"),

	// item print rate
	LOCATION("locationCode"), PRODUCTCATCODE("productCatCode"), POSITIONCODE("positionCode"),
	ADEDITIONSRELATIONS("AdEditionsRelation"), ADSUBEDITIONRELATIONS("AdSubEditionRelations"),

	// Custom Package
	PACKAGETITLE("packageTitle"), CUSTYPE("custType"), PACKAGETYPE("packageType"), PACKAGEDESC("packageDesc"),
	NUMBEROFOFFERS("numberOfOffers"), MININSERTION("minInsertion"), MAXINSERTION("maxInsertion"), MINPRICES("minPrice"),
	MAXPRICE("maxPrice"),

	// clientCreation
	AGENCYNAMEHEADER("Agency Name"), CLIENTCOMPANYNAMEHEADER("Client Company Name"),
	AGENCYEMAILIDHEADER("Agency Email Id"), SFDCIDHEADER("SFDC ID's"),

	// item Radio Rate
	OBJECTID("Object ID (Product)"), PRODUCTNAMERADIO("Product Name"), STATION("Station"),
	PRODUCTFAMILY("Product Family"), DAYRADIO("Day"), DAYPART("Day Part"), CATEGORY("Category"),
	RADIOCHANNEL("Channel"), TYPEOFSPONSORSHIP("Type Of Sponsorship"), DIGITALTYPE("Digital Type"), RJNAME("RJ Name"),
	NFCTTYPE("NFCT Type"), TAGDURATION("Tag Duration"), INTRO1("intro1"), INTRO2("intro2"),
	HASMULTIEDITIONFILE("hasMultiEditionFile"), DIGITALDELIVERYTYPE("digitalDeliveryType"),

	// Advertiser data
	SERIALNUMBER("Serial Number"), SALESZONE("Sales Zone"), SALESBRANCHREGION("Sales BranchRegion"),
	SALESBRACH("Sales Branch"), CHANNELS("Channel"), ACCOUNTIDAGENCY("Account Id (Sold to Party/Agency)"),
	SOLDTOPARTY("Sold to Party"), ADVERTISERFORREPORT("Advertiser for Report"), VOLSQCMPUBLISHED("Vol_Sqcm_Published"),
	ADVERTISERCODE("Advertiser Code"),
	RECORDTYPE("Record Type"), ACCOUNTIDCLIENT("Account Id(Client/Advertiser)"),
	ADVERTISERNAMESFDC("Advertiser Name SFDC"), BUSINESSCATEGORYSFDC("Bussiness Catgeory SFDC"),
	GSTNOSFDC("GSTN No SFDC"), PANNOSFDC("PAN No SFDC"), LEADSOURCE("AdWorks"),
	CLIENTSYNCEDFROMDUMP("clientSyncedFromDump"), ADEXECUTIVEEMAIL("adworks@hindustantimes.com"),
	CATTEAMEMAIL1("Ravi.Bhatnagar@hindustantimes.com"), CATTEAMEMAIL2("shubham.swarup@hindustantimes.com"),

	ORDERGSTFORPRINT("5"), ORDERGSTFORRADIO("18"), ORDERGSTFORDIGITAL("18"), ACTION("action"), SFDCID("SFDC ID"),
	ACCOUNTID("Account ID"), SFDCIDD("sfdcId"),SFDCSAPCODE("sfdcSapCode"),

	// radio time slot
	ICON("icon"), STARTTIME("startTime"), ENDTIME("endTime"), TIMEBREAKUP("timeBreakUp"),
	SLOTIDENTIFIER("slotIdentifier"), DISPLAYORDER("displayOrder"),

	PRODUCTRECORDTYPE("Product Record Type"), PRODUCTLASTMODIFIEDDATE("Product: Last Modified Date"),

	VERTICAL("Vertical"), ACTIVE("Active (Product)"), PRODUCTCODE("ID"),

	UNITPRICE("UNITPRICE"), ER2("ER 2(Radio ER2 rate)"), ER3("ER 3(Radio ER3 rate)"), DOMAIN("domain"),
	DOMAINID("domainId"), ADTYPEID("adTypeId"),

	// Digital
	RECOMMENDEDRATE("Recommended Rate"), DELIVERYTYPE("Delivery Type"), DFPADTYPE("DFP Ad Type"),DIGITALCAMPAIGNSTARTDATE("digitalCampaignStartDate"),
	DIGITALCAMPAIGNENDDATE("digitalCampaignEndDate"),
	TYPEOFCLIENT("Type of Client"), IMPRESSIONS("impressions"), SIZE("size"), TYPEOFBUSINESS("Type of Business"),
	ROADBLOCKTYPE("Roadblock Type"), DIGITALGEOGRAPHY("digitalGeography"), AUDIENCEINTERESTTYPE("audienceInterestType"),

	PRODUCTTYPE("Product Type"), PRODUCTDESCRIPTION("Product Description"), PRODUCTCREATEDDATE("Product: Created Date"),
	PRODUCTCODE1("Product Code"), DIGITALIMPRESSIONCOUNT("digitalImpressionCount"),

	ISSUECLASSIFICATION("Issue Classification"), PLATFORM("Platform"), DFPTYPE("DFP Type"),NETWORK("Network"),
	DIGITALDOMAINNAME("digitalDomainName"), DOMAINNAME("domainName"), DOMAINCODE("domainCode"),
	DIGITALRATETYPE("digitalRateType"), DIGITALPLATFORM("digitalPlatformId"), DELIVERYTYPEID("deliveryTypeId"),
	PLATFORMNAME("platformName"), PLATFORMCODE("platformCode"), DELIVERYTYPENAME("deliveryTypeName"),
	DELIVERYTYPECODE("deliveryTypeCode"), PLATFORMID("platformId"), DB_DATE_FORMAT_Y_M_D_H_M_S("yyyy-MM-dd HH:mm:ss.S");

	private final String name;

	EntityConstant(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
