import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
declare var $: any;

@Injectable({
  providedIn: 'root'
})
export class UtililtyFunctions {

  onLoginSuccessfully: Subject<any> = new Subject<any>();
  onLogoutSuccessfully: Subject<any> = new Subject<any>();
  onResendingOTP: Subject<any> = new Subject<any>();
  onAddingFirstClient: Subject<any> = new Subject<any>();
  onClientStatusChange: Subject<any> = new Subject<any>();
  onBookAdvPDFUploadSuccessfully: Subject<any> = new Subject<any>();
  onSaveRadioPackageData: Subject<any> = new Subject<any>();
  onSaveDigitalPackageData: Subject<any> = new Subject<any>();
  showDigitalAddPkgBtn: Subject<any> = new Subject<any>();
  onBookAdvRadAddNewPkg: Subject<any> = new Subject<any>();
  onBookAdvDigiAddNewPkg: Subject<any> = new Subject<any>();
  onDeleteSavePackage: Subject<any> = new Subject<any>();
  onDeleteDigitalPackage: Subject<any> = new Subject<any>();
  onFooterLinksClick: Subject<any> = new Subject<any>();
  onIframeCloseClick: Subject<any> = new Subject<any>();
  showNotAuthorizedPopup: Subject<any> = new Subject<any>();
  currentLoggedUserData;
  isUserLoggedIn() {
    let loginedUserData = this.getUserMeData();
    if (loginedUserData && loginedUserData != null) {
      return loginedUserData;
    }
    return false;
  }
  isUserOnlyAcc() {
    let loginedUserData = this.getUserMeData();
    if (loginedUserData && loginedUserData != null) {
      if (loginedUserData.isAccreditedCustomer == '1' && (loginedUserData.isPremiumPrintCustomer == '0' || loginedUserData.isPremiumPrintCustomer == null ||
        loginedUserData.isPremiumRadioCustomer == '0' || loginedUserData.isPremiumRadioCustomer == null ||
        loginedUserData.isPremiumDigitalCustomer == '0' || loginedUserData.isPremiumDigitalCustomer == null)) {
        return true
      } else {
        return false
      }
    }
  }

  isSfdcIdNull() {
    let loginedUserData = this.getUserMeData();
    if (loginedUserData && loginedUserData != null) {
      if (loginedUserData.sfdcId == null || loginedUserData.sfdcId === "") {
        return true;
      }
      else {
        return false;
      }
    }
  }

  isUserTokenPresent() {
    let userToken = this.getUserTokenData();
    if (userToken && userToken != null) {
      return userToken;
    }
    return false;
  }

  isAdminLoggedIn() {
    let loginedUserData = this.getUserMeData();
    if (loginedUserData && loginedUserData != null && loginedUserData.role && loginedUserData.role === 'Admin') {
      return loginedUserData;
    }
    return false;
  }

  omit_special_char(event) {
    //keycode for % is 37
    return event.keyCode == 37 ? false : true;
  }

  isLoggedInFlag() {
    if (this.isRememberMe()) {
      return localStorage.getItem("isLogin");
    } else {
      return sessionStorage.getItem("isLogin");
    }
  }

  toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });

  isRememberMe() {
    let localStorageUserData = localStorage.getItem("userToken");
    if (localStorageUserData) {
      return true;
    }
    return false;
  }

  saveUserToken(data) {
      sessionStorage.setItem("userToken", JSON.stringify(data.data));
  }

  saveLoginedUserData(data) {
    if (this.isRememberMe()) {
      localStorage.setItem("loginedUserData", JSON.stringify(data.data));
    } else {
      sessionStorage.setItem("loginedUserData", JSON.stringify(data.data));
    }
  }

  getUserMeData() {
      return JSON.parse(sessionStorage.getItem("loginedUserData"))
    
  }
  isMarkAcc(val) {
    if (val) {
      if (val.isAccreditedCustomer == null || val.isAccreditedCustomer == '0') {
        return false;
      } else {
        return true

      }
    }
  }
  isUserPDCEnabled(val) {
    if (val) {
      if (val.isPdcEnabled == null || val.isPdcEnabled == '0') {
        return false;
      } else {
        return true
      }
    }
  }
  isPremiumPrintCustomer(val) {
    if (val) {
      if (val.isPremiumPrintCustomer == null || val.isPremiumPrintCustomer == '0') {
        return false;
      } else {
        return true

      }
    }

  }
  isPremiumDigitalCustomer(val) {
    if (val) {
      if (val.isPremiumDigitalCustomer == null || val.isPremiumDigitalCustomer == '0') {
        return false;
      } else {
        return true
      }
    }

  }

  isPremiumRadioCustomer(val) {
    if (val.isPremiumRadioCustomer == null || val.isPremiumRadioCustomer == '0') {
      return false;
    } else {
      return true
    }
  }

  getUserTokenData() {
    
      return JSON.parse(sessionStorage.getItem("userToken"));
    
  }

  saveIsLogin() {
    if (this.isRememberMe()) {
      localStorage.setItem("isLogin", "true");
    } else {
      sessionStorage.setItem("isLogin", "true");
    }
  }

  removeAdBookSessionValues() {
    sessionStorage.setItem("bookAdvertisementsAdDates", JSON.stringify(null));
    sessionStorage.setItem("ReadymadePackageOfferDay", JSON.stringify(null));
    sessionStorage.setItem("ReadymadePackageId", JSON.stringify(null));
    sessionStorage.setItem("ReadymadeOfferId", JSON.stringify(null));
    sessionStorage.setItem("bookAdvertisementsPublicationsType", JSON.stringify(null));
    sessionStorage.setItem("bookAdvertisementsIssue", JSON.stringify(null));
    sessionStorage.setItem("bookAdvertisementsProductCategory", JSON.stringify(null));
    sessionStorage.setItem("bookAdvertisementsEditions", JSON.stringify(null));
    sessionStorage.setItem("bookAdvertisementsSubEditions", JSON.stringify(null));
    sessionStorage.setItem("bookAdvertisementsCombinedEditionSubEditionsID", null);
    sessionStorage.setItem("bookAdvertisementsCombinedEditionSubEditionsName", null);
    sessionStorage.setItem("bookAdvertisementsPosition", JSON.stringify(null));
    sessionStorage.setItem("bookAdvertisementsAdditionalSpeicification", JSON.stringify(null));
    sessionStorage.setItem("bookAdvertisementsSize", JSON.stringify(null));
    sessionStorage.setItem("bookAdVolume", JSON.stringify(null));
    sessionStorage.setItem("bookAdSizeWidth", JSON.stringify(null));
    sessionStorage.setItem("bookAdSizeHeight", JSON.stringify(null));
    sessionStorage.setItem("bookAdvertisementsAdDates", JSON.stringify(null));
    sessionStorage.setItem("bookAdPDFName", JSON.stringify(null));
    sessionStorage.setItem("bookAdPDFBase64String", JSON.stringify(null));
    sessionStorage.setItem("bookAdPDFURL", JSON.stringify(null));
    sessionStorage.removeItem('editionWiseUrl');
    sessionStorage.removeItem('hasMultiEditionFile');
    sessionStorage.removeItem('fileUploadStatus');
    sessionStorage.removeItem('pdfUrl')
    sessionStorage.setItem("packageRateKey", null);
    sessionStorage.setItem("rateKey", null);
    

  }
  uploadLaterVariable() {
    sessionStorage.removeItem("bookAdvertisementsCombinedEditionSubEditionsID");
    sessionStorage.removeItem("bookAdvertisementsCombinedEditionSubEditionsName");
    sessionStorage.removeItem("fileUploadStatusUpload");
    sessionStorage.removeItem("bookAdVolume");
    sessionStorage.removeItem("bookAdSizeHeight");
    sessionStorage.removeItem("bookAdSizeWidth");
    sessionStorage.removeItem("packageId");
    sessionStorage.removeItem("packageId");
    sessionStorage.removeItem("paymentStatus");


    sessionStorage.removeItem("ReadymadePackageId");
    sessionStorage.removeItem("ReadymadeOfferId");
    sessionStorage.removeItem("fileUploadStatus");
    sessionStorage.removeItem("hasMultiEditionFile");
    sessionStorage.removeItem("bookAdvertisementsEditions");
    sessionStorage.removeItem("bookAdvertisementsIssue");
    sessionStorage.removeItem("bookAdvertisementsPublicationsType");
    sessionStorage.removeItem("bookAdvertisementsPosition");
    sessionStorage.removeItem("bookAdvertisementsSize");
    sessionStorage.removeItem("bookAdvertisementsSubEditions");
    sessionStorage.removeItem("bookAdvertisementsCombinedEditionSubEditionsName");
    sessionStorage.removeItem("bookAdvertisementsCombinedEditionSubEditionsID");
    sessionStorage.removeItem("bookAdvertisementsAdditionalSpeicification");
    sessionStorage.removeItem("bookAdSizeHeight");
    sessionStorage.removeItem("bookAdvertisementsLocations");
    sessionStorage.removeItem("bookAdvertisementsProductCategory");
    sessionStorage.removeItem("bookAdvertisementsAdDates");
    sessionStorage.removeItem("bookAdVolume");
    sessionStorage.removeItem("bookAdSizeWidth");
    sessionStorage.removeItem("bookAdPDFBase64String");
    sessionStorage.removeItem("bookAdPDFName");
    

  }

  readyMadePackageSessionClean() {
    sessionStorage.setItem("bookAdvertisementsLocations", null);
    sessionStorage.setItem("ReadymadePackageRateDay", null);
    sessionStorage.setItem("ReadymadePackageOfferDay", null);
    sessionStorage.setItem("ReadymadePackageId", null);
    sessionStorage.setItem("ReadymadeOfferId", null);
    sessionStorage.setItem("editionsEligibleForWeekend", null);
    sessionStorage.setItem("editionsEligibleForWeekday", null);
    sessionStorage.setItem('editionWiseUrl', null);
    sessionStorage.setItem('hasMultiEditionFile', null)
  }

  checksessionVaraibles() {

    if ((JSON.parse(window.sessionStorage.getItem("bookAdvertisementsPublicationsType")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsIssue")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsProductCategory")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsEditions")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsSubEditions")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsPosition")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsSize")))
      && (
        (JSON.parse(window.sessionStorage.getItem("bookAdVolume")) ||
          ((JSON.parse(window.sessionStorage.getItem("bookAdSizeWidth"))) && (JSON.parse(window.sessionStorage.getItem("bookAdSizeHeight"))))
        ))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsAdDates")))
      //&& (JSON.parse(window.sessionStorage.getItem("bookAdPDFBase64String")) || sessionStorage.getItem("pdfUrl"))
    ) {
      return true;
    }
    else {
      return false;
    }
  }

  checksessionVaraiblesWithoutPDF() {
    if ((JSON.parse(window.sessionStorage.getItem("bookAdvertisementsPublicationsType")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsIssue")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsProductCategory")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsEditions")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsSubEditions")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsPosition")))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsSize")))
      && (
        (JSON.parse(window.sessionStorage.getItem("bookAdVolume")) ||
          ((JSON.parse(window.sessionStorage.getItem("bookAdSizeWidth"))) && (JSON.parse(window.sessionStorage.getItem("bookAdSizeHeight"))))
        ))
      && (JSON.parse(window.sessionStorage.getItem("bookAdvertisementsAdDates")))
    ) {
      return true;
    }
    else {
      return false;
    }
  }

  removeAllCompletePackageArray() {
    sessionStorage.setItem("bookAdvertisementsPackageDataArray", JSON.stringify(null));
  }


  removeSessionVariableAfterAdBookingStep(steps) {
    //remove others
    if (steps == 1) {
      sessionStorage.setItem("bookAdvertisementsAdDates", null);
      sessionStorage.setItem("bookAdvertisementsIssue", null);
      sessionStorage.setItem("bookAdvertisementsEditions", null);
      sessionStorage.setItem("bookAdvertisementsSubEditions", null);
      sessionStorage.setItem("bookAdvertisementsCombinedEditionSubEditionsID", null);
      sessionStorage.setItem("bookAdvertisementsCombinedEditionSubEditionsName", null);
      sessionStorage.setItem("bookAdvertisementsPosition", null);
      sessionStorage.setItem("bookAdvertisementsAdditionalSpeicification", JSON.stringify(null));
      sessionStorage.setItem("bookAdvertisementsSize", null);
      sessionStorage.setItem("bookAdVolume", null);
      sessionStorage.setItem("bookAdSizeWidth", null);
      sessionStorage.setItem("bookAdSizeHeight", null);
      sessionStorage.setItem('editionWiseUrl', null);
      sessionStorage.setItem('hasMultiEditionFile', null)
      sessionStorage.setItem('bookAdPDFBase64String', null);
      sessionStorage.setItem('bookAdPDFURL', null)
      sessionStorage.setItem('adContentFileDetails', null)
      sessionStorage.setItem("packageRateKey", null);
      sessionStorage.setItem("rateKey", null);
      
    } else if (steps == 2) {
      sessionStorage.setItem("bookAdvertisementsAdDates", null);
      sessionStorage.setItem("bookAdvertisementsEditions", null);
      sessionStorage.setItem("bookAdvertisementsSubEditions", null);
      sessionStorage.setItem("bookAdvertisementsCombinedEditionSubEditionsID", null);
      sessionStorage.setItem("bookAdvertisementsCombinedEditionSubEditionsName", null);
      sessionStorage.setItem("bookAdvertisementsPosition", null);
      sessionStorage.setItem("bookAdvertisementsAdditionalSpeicification", JSON.stringify(null));
      sessionStorage.setItem("bookAdvertisementsSize", null);
      sessionStorage.setItem("bookAdVolume", null);
      sessionStorage.setItem("bookAdSizeWidth", null);
      sessionStorage.setItem("bookAdSizeHeight", null);
      sessionStorage.setItem('editionWiseUrl', null);
      sessionStorage.setItem('hasMultiEditionFile', null)
      sessionStorage.setItem('bookAdPDFBase64String', null);
      sessionStorage.setItem('bookAdPDFURL', null);
      sessionStorage.setItem("rateKey", null);
      sessionStorage.setItem("packageRateKey", null);

    } else if (steps == 3) {
      sessionStorage.setItem("bookAdvertisementsAdDates", null);
      sessionStorage.setItem("bookAdvertisementsEditions", null);
      sessionStorage.setItem("bookAdvertisementsSubEditions", null);
      sessionStorage.setItem("bookAdvertisementsCombinedEditionSubEditionsID", null);
      sessionStorage.setItem("bookAdvertisementsCombinedEditionSubEditionsName", null);
      sessionStorage.setItem("bookAdvertisementsPosition", null);
      sessionStorage.setItem("bookAdvertisementsAdditionalSpeicification", JSON.stringify(null));
      sessionStorage.setItem("bookAdvertisementsSize", null);
      sessionStorage.setItem("bookAdVolume", null);
      sessionStorage.setItem("bookAdSizeWidth", null);
      sessionStorage.setItem("bookAdSizeHeight", null);
      sessionStorage.setItem('editionWiseUrl', null);
      sessionStorage.setItem('hasMultiEditionFile', null)
      sessionStorage.setItem('bookAdPDFBase64String', null);
      sessionStorage.setItem('bookAdPDFURL', null)
    } else if (steps == 4) {
      sessionStorage.setItem("bookAdvertisementsAdDates", null);
      sessionStorage.setItem("bookAdvertisementsPosition", null);
      sessionStorage.setItem("bookAdvertisementsAdditionalSpeicification", JSON.stringify(null));
      sessionStorage.setItem("bookAdvertisementsSize", null);
      sessionStorage.setItem("bookAdVolume", null);
      sessionStorage.setItem("bookAdSizeWidth", null);
      sessionStorage.setItem("bookAdSizeHeight", null);
      sessionStorage.setItem('editionWiseUrl', null);
      sessionStorage.setItem('hasMultiEditionFile', null)
      sessionStorage.setItem('bookAdPDFBase64String', null);
      sessionStorage.setItem('bookAdPDFURL', null)
    } else if (steps == 6) {
      sessionStorage.setItem("bookAdvertisementsAdDates", null);
      sessionStorage.setItem("bookAdPDFBase64String", null);
      sessionStorage.setItem("bookAdPDFName", null);
    } else if (steps == 7) {
      sessionStorage.setItem("bookAdPDFBase64String", null);
      sessionStorage.setItem("bookAdPDFName", null);
      sessionStorage.setItem("editionWiseUrl", null);
      sessionStorage.setItem("hasMultiEditionFile", 'false');
    }
  }

  hasPermission1(getKey: any): any {
    debugger;
    try {
      let loginedUserData = this.getUserMeData();
      if (!loginedUserData) return false;
      var key = getKey;
      console.log('datsaaaaaaaaaaaaaaaaaa' + loginedUserData.permissions.key);

      console.log('datsaaaaaaaaaaaaaaaaa11111a' + loginedUserData.permissions);
      //return loginedUserData.permissions.key 
      return loginedUserData.permissions.has(key);
    } catch (error) {
      console.log(error);
    }
  }

  hasPermission(permissionName: string): boolean {
   try{
    let loginedUserData = this.getUserMeData();
    if (!loginedUserData) return false;
    const permissionsData = loginedUserData.permissions;
    return permissionsData.hasOwnProperty(permissionName);
  } catch (error) {
    console.log(error);
  }
  }
}
