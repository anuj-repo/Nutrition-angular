import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AuthGuard } from './_auth/auth.guard';
import { AuthInterceptor } from './_auth/auth.interceptor';
import { UserService } from './_services/user.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AddNewProductComponent } from './add-new-product/add-new-product.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { DragDirective } from './drag.directive';
import { DigitsOnlyDirective } from './utils/digits-only.directive';
import { ShowProductDetailesComponent } from './show-product-detailes/show-product-detailes.component';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { ShowProductImagesDialogComponent } from './show-product-images-dialog/show-product-images-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ProductViewDetailsComponent } from './product-view-details/product-view-details.component';
import { BuyProductComponent } from './buy-product/buy-product.component';
import { OrderConfirmationComponent } from './order-confirmation/order-confirmation.component';
import { RegisterComponent } from './register/register.component';
import { CartComponent } from './cart/cart.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { OrderDetaisComponent } from './order-detais/order-detais.component';
import { MatSelectModule } from '@angular/material/select';
import { MyTeamComponent } from './my-team/my-team.component';
import { ToastrModule } from 'ngx-toastr';
import { AllTeamComponent } from './all-team/all-team.component';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_FORMATS, MAT_DATE_LOCALE, DateAdapter, NativeDateAdapter } from '@angular/material/core';

/** Custom date adapter that parses DD/MM/YYYY input. */
export class DdMmYyyyDateAdapter extends NativeDateAdapter {
  override parse(value: any): Date | null {
    if (typeof value === 'string') {
      // Accept DD/MM/YYYY or DD-MM-YYYY
      const parts = value.split(/[\/\-]/);
      if (parts.length === 3) {
        const day = +parts[0];
        const month = +parts[1] - 1;
        const year = +parts[2];
        if (!isNaN(day) && !isNaN(month) && !isNaN(year)) {
          return new Date(year, month, day);
        }
      }
    }
    return super.parse(value);
  }

  override format(date: Date, displayFormat: Object): string {
    if (displayFormat === 'input') {
      const day = date.getDate().toString().padStart(2, '0');
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const year = date.getFullYear();
      return `${day}/${month}/${year}`;
    }
    return date.toDateString();
  }
}

export const DD_MM_YYYY_FORMATS = {
  parse: { dateInput: 'input' },
  display: {
    dateInput: 'input',
    monthYearLabel: { year: 'numeric', month: 'short' },
    dateA11yLabel: { year: 'numeric', month: 'long', day: 'numeric' },
    monthYearA11yLabel: { year: 'numeric', month: 'long' },
  },
};
import { MatMenuModule } from '@angular/material/menu';
import { MyProfileComponent } from './my-profile/my-profile.component';
import { MatTreeModule } from '@angular/material/tree';
import { CdkTreeModule } from '@angular/cdk/tree';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTabsModule } from '@angular/material/tabs';
import { MatProgressBarModule } from '@angular/material/progress-bar';

// Original new MLM components
import { DashboardComponent } from './dashboard/dashboard.component';
import { CommissionComponent } from './commission/commission.component';
import { EarningsComponent } from './earnings/earnings.component';
import { RewardsComponent } from './rewards/rewards.component';
import { AddMemberComponent } from './add-member/add-member.component';
import { InvestmentComponent } from './investment/investment.component';
import { RepurchaseBonusComponent } from './repurchase-bonus/repurchase-bonus.component';
import { RanksComponent } from './ranks/ranks.component';

// Wave 1: User-facing essentials
import { WalletComponent } from './wallet/wallet.component';
import { AddressesComponent } from './addresses/addresses.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { NotificationPreferencesComponent } from './notification-preferences/notification-preferences.component';

// Wave 2: Insurance, Compliance, Network
import { InsuranceComponent } from './insurance/insurance.component';
import { ComplianceComponent } from './compliance/compliance.component';
import { NetworkTreeComponent } from './network-tree/network-tree.component';
import { SponsorComponent } from './sponsor/sponsor.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';

// Wave 3: Rewards extras, TDS, Payment, Investment detail
import { RewardClaimsComponent } from './reward-claims/reward-claims.component';
import { TripsComponent } from './trips/trips.component';
import { ProfitSharingComponent } from './profit-sharing/profit-sharing.component';
import { TdsComponent } from './tds/tds.component';
import { PaymentHistoryComponent } from './payment-history/payment-history.component';
import { InvestmentDetailComponent } from './investment-detail/investment-detail.component';

// Wave 4: Engagement and orders
import { SupportComponent } from './support/support.component';
import { SupportDetailComponent } from './support-detail/support-detail.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { TrainingComponent } from './training/training.component';
import { AnnouncementsComponent } from './announcements/announcements.component';
import { OrdersV2Component } from './orders-v2/orders-v2.component';
import { OrderDetailV2Component } from './order-detail-v2/order-detail-v2.component';

// Wave 5: Public/Auth
import { PublicPagesComponent } from './public-pages/public-pages.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { OtpVerifyComponent } from './otp-verify/otp-verify.component';
import { PincodeCheckComponent } from './pincode-check/pincode-check.component';
import { CookieBannerComponent } from './cookie-banner/cookie-banner.component';

// Wave 6: Misc + Admin
import { ProductReviewsComponent } from './product-reviews/product-reviews.component';
import { AvatarUploadComponent } from './avatar-upload/avatar-upload.component';
import { SubUsersComponent } from './sub-users/sub-users.component';
import { AdminReportsComponent } from './admin-reports/admin-reports.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { AdminEmailTemplatesComponent } from './admin-email-templates/admin-email-templates.component';
import { AdminSettingsComponent } from './admin-settings/admin-settings.component';
import { AdminConfigsComponent } from './admin-configs/admin-configs.component';
import { AdminApprovalsComponent } from './admin-approvals/admin-approvals.component';
import { AdminCatalogComponent } from './admin-catalog/admin-catalog.component';
import { AdminUserTreeComponent } from './admin-user-tree/admin-user-tree.component';
import { AdminExpensePoolsComponent } from './admin-expense-pools/admin-expense-pools.component';
import { AdminFinanceComponent } from './admin-finance/admin-finance.component';

// Wave 7: Final coverage components
import { CartV2Component } from './cart-v2/cart-v2.component';
import { AdminProductEditComponent } from './admin-product-edit/admin-product-edit.component';
import { AdminFileManagerComponent } from './admin-file-manager/admin-file-manager.component';
import { AdminManualTriggersComponent } from './admin-manual-triggers/admin-manual-triggers.component';
import { RolePermissionsComponent } from './role-permissions/role-permissions.component';
import { PaymentStatusComponent } from './payment-status/payment-status.component';
import { FooterComponent } from './footer/footer.component';

// Services
import { NetworkService } from './_services/network.service';
import { BackendApiService } from './_services/backend-api.service';
import { I18nService } from './_services/i18n.service';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AdminComponent,
    UserComponent,
    LoginComponent,
    HeaderComponent,
    ForbiddenComponent,
    AddNewProductComponent,
    DragDirective,
    DigitsOnlyDirective,
    ShowProductDetailesComponent,
    ShowProductImagesDialogComponent,
    ProductViewDetailsComponent,
    BuyProductComponent,
    OrderConfirmationComponent,
    RegisterComponent,
    CartComponent,
    MyOrdersComponent,
    OrderDetaisComponent,
    MyTeamComponent,
    AllTeamComponent,
    MyProfileComponent,
    DashboardComponent,
    CommissionComponent,
    EarningsComponent,
    RewardsComponent,
    AddMemberComponent,
    InvestmentComponent,
    RepurchaseBonusComponent,
    RanksComponent,
    WalletComponent,
    AddressesComponent,
    NotificationsComponent,
    NotificationPreferencesComponent,
    InsuranceComponent,
    ComplianceComponent,
    NetworkTreeComponent,
    SponsorComponent,
    LeaderboardComponent,
    RewardClaimsComponent,
    TripsComponent,
    ProfitSharingComponent,
    TdsComponent,
    PaymentHistoryComponent,
    InvestmentDetailComponent,
    SupportComponent,
    SupportDetailComponent,
    FeedbackComponent,
    TrainingComponent,
    AnnouncementsComponent,
    OrdersV2Component,
    OrderDetailV2Component,
    PublicPagesComponent,
    ForgotPasswordComponent,
    OtpVerifyComponent,
    PincodeCheckComponent,
    CookieBannerComponent,
    ProductReviewsComponent,
    AvatarUploadComponent,
    SubUsersComponent,
    AdminReportsComponent,
    AdminUsersComponent,
    AdminEmailTemplatesComponent,
    AdminSettingsComponent,
    AdminConfigsComponent,
    AdminApprovalsComponent,
    AdminCatalogComponent,
    AdminUserTreeComponent,
    AdminExpensePoolsComponent,
    AdminFinanceComponent,
    CartV2Component,
    AdminProductEditComponent,
    AdminFileManagerComponent,
    AdminManualTriggersComponent,
    RolePermissionsComponent,
    PaymentStatusComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatGridListModule,
    MatTableModule,
    MatIconModule,
    MatDialogModule,
    MatSelectModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatMenuModule,
    MatTreeModule,
    CdkTreeModule,
    MatTooltipModule,
    MatTabsModule,
    MatProgressBarModule,
    ToastrModule.forRoot({
      timeOut: 4000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
      progressBar: true,
      closeButton: true,
      newestOnTop: true,
      tapToDismiss: true,
      easing: 'ease-in',
      easeTime: 250
    })
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    { provide: DateAdapter, useClass: DdMmYyyyDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: DD_MM_YYYY_FORMATS },
    { provide: MAT_DATE_LOCALE, useValue: 'en-IN' },
    UserService,
    NetworkService,
    BackendApiService,
    I18nService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
