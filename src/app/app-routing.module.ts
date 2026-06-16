import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddNewProductComponent } from './add-new-product/add-new-product.component';
import { AdminComponent } from './admin/admin.component';
import { BuyProductResolverService } from './buy-product-resolver.service';
import { BuyProductComponent } from './buy-product/buy-product.component';
import { CartComponent } from './cart/cart.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { OrderConfirmationComponent } from './order-confirmation/order-confirmation.component';
import { OrderDetaisComponent } from './order-detais/order-detais.component';
import { ProductResolveService } from './product-resolve.service';
import { ProductViewDetailsComponent } from './product-view-details/product-view-details.component';
import { RegisterComponent } from './register/register.component';
import { ShowProductDetailesComponent } from './show-product-detailes/show-product-detailes.component';
import { UserComponent } from './user/user.component';
import { AuthGuard } from './_auth/auth.guard';
import { MyTeamComponent } from './my-team/my-team.component';
import { AllTeamComponent } from './all-team/all-team.component';
import { MyProfileComponent } from './my-profile/my-profile.component';

import { DashboardComponent } from './dashboard/dashboard.component';
import { CommissionComponent } from './commission/commission.component';
import { EarningsComponent } from './earnings/earnings.component';
import { RewardsComponent } from './rewards/rewards.component';
import { AddMemberComponent } from './add-member/add-member.component';
import { InvestmentComponent } from './investment/investment.component';
import { RepurchaseBonusComponent } from './repurchase-bonus/repurchase-bonus.component';
import { RanksComponent } from './ranks/ranks.component';

import { WalletComponent } from './wallet/wallet.component';
import { AddressesComponent } from './addresses/addresses.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { NotificationPreferencesComponent } from './notification-preferences/notification-preferences.component';
import { InsuranceComponent } from './insurance/insurance.component';
import { ComplianceComponent } from './compliance/compliance.component';
import { NetworkTreeComponent } from './network-tree/network-tree.component';
import { SponsorComponent } from './sponsor/sponsor.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { RewardClaimsComponent } from './reward-claims/reward-claims.component';
import { TripsComponent } from './trips/trips.component';
import { ProfitSharingComponent } from './profit-sharing/profit-sharing.component';
import { TdsComponent } from './tds/tds.component';
import { PaymentHistoryComponent } from './payment-history/payment-history.component';
import { InvestmentDetailComponent } from './investment-detail/investment-detail.component';
import { SupportComponent } from './support/support.component';
import { SupportDetailComponent } from './support-detail/support-detail.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { TrainingComponent } from './training/training.component';
import { AnnouncementsComponent } from './announcements/announcements.component';
import { OrdersV2Component } from './orders-v2/orders-v2.component';
import { OrderDetailV2Component } from './order-detail-v2/order-detail-v2.component';
import { PublicPagesComponent } from './public-pages/public-pages.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { OtpVerifyComponent } from './otp-verify/otp-verify.component';
import { PincodeCheckComponent } from './pincode-check/pincode-check.component';
import { ProductReviewsComponent } from './product-reviews/product-reviews.component';
import { SubUsersComponent } from './sub-users/sub-users.component';
import { AdminReportsComponent } from './admin-reports/admin-reports.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { AdminEmailTemplatesComponent } from './admin-email-templates/admin-email-templates.component';
import { AdminSettingsComponent } from './admin-settings/admin-settings.component';
import { AdminConfigsComponent } from './admin-configs/admin-configs.component';
import { AdminApprovalsComponent } from './admin-approvals/admin-approvals.component';
import { AdminCatalogComponent } from './admin-catalog/admin-catalog.component';
import { AdminExpensePoolsComponent } from './admin-expense-pools/admin-expense-pools.component';
import { AdminFinanceComponent } from './admin-finance/admin-finance.component';
import { AdminUserTreeComponent } from './admin-user-tree/admin-user-tree.component';

import { CartV2Component } from './cart-v2/cart-v2.component';
import { AdminProductEditComponent } from './admin-product-edit/admin-product-edit.component';
import { AdminFileManagerComponent } from './admin-file-manager/admin-file-manager.component';
import { AdminManualTriggersComponent } from './admin-manual-triggers/admin-manual-triggers.component';
import { RolePermissionsComponent } from './role-permissions/role-permissions.component';
import { PaymentStatusComponent } from './payment-status/payment-status.component';


const routes: Routes = [
  // Public
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'otp-verify', component: OtpVerifyComponent },
  { path: 'pincode-check', component: PincodeCheckComponent },
  { path: 'forbidden', component: ForbiddenComponent },
  { path: 'about', component: PublicPagesComponent, data: { page: 'about' } },
  { path: 'contact', component: PublicPagesComponent, data: { page: 'contact' } },
  { path: 'why-choose-us', component: PublicPagesComponent, data: { page: 'why-choose-us' } },
  { path: 'faq', component: PublicPagesComponent, data: { page: 'faq' } },
  { path: 'landing', component: PublicPagesComponent, data: { page: 'landing' } },
  { path: 'product-info', component: ProductReviewsComponent },

  // Existing
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'user', component: UserComponent, canActivate: [AuthGuard], data: { roles: ['User'] } },
  {
    path: 'addNewProduct', component: AddNewProductComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] },
    resolve: { product: ProductResolveService }
  },
  { path: 'showProductDetailes', component: ShowProductDetailesComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'orderInformation', component: OrderDetaisComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'productViewDetails', component: ProductViewDetailsComponent, resolve: { product: ProductResolveService } },
  {
    path: 'buyProduct', component: BuyProductComponent, canActivate: [AuthGuard], data: { roles: ['User'] },
    resolve: { productDetails: BuyProductResolverService }
  },
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard], data: { roles: ['User'] } },
  { path: 'orderConfirm', component: OrderConfirmationComponent, canActivate: [AuthGuard], data: { roles: ['User'] } },
  { path: 'myOrders', component: MyOrdersComponent, canActivate: [AuthGuard], data: { roles: ['User'] } },
  { path: 'myTeam', component: MyTeamComponent, canActivate: [AuthGuard], data: { roles: ['User'] } },
  { path: 'myProfile', component: MyProfileComponent, canActivate: [AuthGuard], data: { roles: ['User'] } },
  { path: 'allTeam', component: AllTeamComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },

  // MLM core
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'commission', component: CommissionComponent, canActivate: [AuthGuard] },
  { path: 'earnings', component: EarningsComponent, canActivate: [AuthGuard] },
  { path: 'rewards', component: RewardsComponent, canActivate: [AuthGuard] },
  { path: 'add-member', component: AddMemberComponent, canActivate: [AuthGuard] },
  { path: 'investment', component: InvestmentComponent, canActivate: [AuthGuard] },
  { path: 'investment/:id', component: InvestmentDetailComponent, canActivate: [AuthGuard] },
  { path: 'repurchase-bonus', component: RepurchaseBonusComponent, canActivate: [AuthGuard] },
  { path: 'ranks', component: RanksComponent, canActivate: [AuthGuard] },

  // User screens (new)
  { path: 'wallet', component: WalletComponent, canActivate: [AuthGuard] },
  { path: 'addresses', component: AddressesComponent, canActivate: [AuthGuard] },
  { path: 'notifications', component: NotificationsComponent, canActivate: [AuthGuard] },
  { path: 'notification-preferences', component: NotificationPreferencesComponent, canActivate: [AuthGuard] },
  { path: 'insurance', component: InsuranceComponent, canActivate: [AuthGuard] },
  { path: 'compliance', component: ComplianceComponent, canActivate: [AuthGuard] },
  { path: 'network-tree', component: NetworkTreeComponent, canActivate: [AuthGuard] },
  { path: 'sponsor', component: SponsorComponent, canActivate: [AuthGuard] },
  { path: 'leaderboard', component: LeaderboardComponent, canActivate: [AuthGuard] },
  { path: 'reward-claims', component: RewardClaimsComponent, canActivate: [AuthGuard] },
  { path: 'trips', component: TripsComponent, canActivate: [AuthGuard] },
  { path: 'profit-sharing', component: ProfitSharingComponent, canActivate: [AuthGuard] },
  { path: 'tds', component: TdsComponent, canActivate: [AuthGuard] },
  { path: 'payment-history', component: PaymentHistoryComponent, canActivate: [AuthGuard] },
  { path: 'support', component: SupportComponent, canActivate: [AuthGuard] },
  { path: 'support/:id', component: SupportDetailComponent, canActivate: [AuthGuard] },
  { path: 'feedback', component: FeedbackComponent, canActivate: [AuthGuard] },
  { path: 'training', component: TrainingComponent, canActivate: [AuthGuard] },
  { path: 'announcements', component: AnnouncementsComponent, canActivate: [AuthGuard] },
  { path: 'orders-v2', component: OrdersV2Component, canActivate: [AuthGuard] },
  { path: 'orders-v2/:id', component: OrderDetailV2Component, canActivate: [AuthGuard] },
  { path: 'sub-users', component: SubUsersComponent, canActivate: [AuthGuard] },

  // Admin screens (new)
  { path: 'admin/reports', component: AdminReportsComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/users', component: AdminUsersComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/email-templates', component: AdminEmailTemplatesComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/settings', component: AdminSettingsComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/configs', component: AdminConfigsComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/expense-pools', component: AdminExpensePoolsComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/finance', component: AdminFinanceComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/approvals', component: AdminApprovalsComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/catalog', component: AdminCatalogComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/user-tree', component: AdminUserTreeComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },

  // Final coverage routes
  { path: 'cart-v2', component: CartV2Component, canActivate: [AuthGuard] },
  { path: 'admin/product-edit/:productId', component: AdminProductEditComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/files', component: AdminFileManagerComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/manual-triggers', component: AdminManualTriggersComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'admin/role-permissions', component: RolePermissionsComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'payment-status/:orderId', component: PaymentStatusComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
