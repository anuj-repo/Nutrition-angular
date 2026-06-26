export const API_PATH: any = {
  API_VERSION_V1: "/api",

  // ===== Existing legacy endpoints (UserController @RequestMapping("")) =====
  ALL_ORDER_DETAILS: "/getAllOrderDetails",
  ADD_PRODUCT: "/addNewProduct",
  GET_ALL_PRODUCTS: "/getAllProducts",
  DELETE_CART_ITEM: "/deleteCartItem/",
  GET_ORDER_DETAILS: "/getOrderDetails",
  GET_PRODUCT_DETAILS_BY_ID: "/getProductDetailsById/",
  DELETE_PRODUCT_DETAILS: "/deleteProductDetails/",
  GET_PRODUCT_DETAILS: "/getProductDetails/",
  PLACE_ORDER: "/placeOrder/",
  ADD_TO_CART: "/addToCart/",
  GET_CART_DETAILS: "/getCartDetails",
  GET_ALL_STATES: "/state",
  GET_ALL_CITY: "/city",
  ADD_USER: "/signUp",
  GET_COUNTRY: "/country",
  USER_ME: "/user/me",
  USER_DETAIL: "/user",
  GET_All_USER: "/getAllUsers",
  SIGNIN: "/auth/signin",
  SIGNOUT: "/auth/signout",
  CHECK_EMAIL_EXIST: "/emailUniqueness",
  CHECK_PAN_EXIST: "/panUniqueness",
  GET_ALL_USER_BY_ADMIN: "/getAllUsersByAdmin",
  UPDATE_USER_BY_ADMIN: "/updatePayment",
  ACTIVATE_USER: "/activateUser",
  USER_PROFILE: "/profile",
  CHANGE_USERPASSWORD: "/change-password",
  RESET_PASSWORD: "/reset-password",
  VERIFY_PASSWORD_RECOVERY_OTP: "/verify-password-recovery-otp",
  GET_PASSWORD_RECOVERY_OTP: "/get-password-recovery-otp",

  // ===== New MLM endpoints (controllers use @RequestMapping("/api/...") on top of context-path /api) =====
  // Dashboard
  DASHBOARD_SUMMARY: "/api/dashboard/me/summary",
  DASHBOARD_EARNINGS_GRAPH: "/api/dashboard/me/earnings/graph",
  DASHBOARD_TEAM_GROWTH: "/api/dashboard/me/team-growth",
  DASHBOARD_INCOME_BREAKDOWN: "/api/dashboard/me/income-breakdown",
  DASHBOARD_UPCOMING_REWARDS: "/api/dashboard/me/upcoming-rewards",

  // Network
  NETWORK_REFERRAL_LINK: "/api/network/me/referral-link",
  NETWORK_SPONSOR: "/api/network/me/sponsor",
  NETWORK_DIRECT: "/api/network/me/direct",
  NETWORK_TREE: "/api/network/me/tree",
  NETWORK_LEVEL: "/api/network/me/level",            // append /{level}
  NETWORK_STATS: "/api/network/me/stats",
  NETWORK_MEMBER: "/api/network/member",             // append /{userId}
  NETWORK_LEADERBOARD: "/api/network/me/leaderboard",
  NETWORK_USER_TREE: "/api/network",                 // append /{userId}/tree

  // Commissions
  COMMISSION_CONFIG: "/api/commissions/config",
  COMMISSION_LEDGER: "/api/commissions/me/ledger",
  COMMISSION_SUMMARY: "/api/commissions/me/summary",
  COMMISSION_MONTHLY: "/api/commissions/me/monthly",
  COMMISSION_PROJECTION: "/api/commissions/me/projection",
  COMMISSION_EARNINGS_TABLE: "/api/commissions/me/earnings-table",

  // Repurchase
  REPURCHASE_CONFIG: "/api/repurchase/config",
  REPURCHASE_CURRENT_CYCLE: "/api/repurchase/me/current-cycle",
  REPURCHASE_HISTORY: "/api/repurchase/me/history",

  // Ranks
  RANKS_ALL: "/api/ranks",
  RANKS_ME: "/api/ranks/me",
  RANKS_ME_PROGRESS: "/api/ranks/me/progress",
  RANKS_LEADERBOARD: "/api/ranks/leaderboard",

  // Rewards
  REWARDS_TIERS: "/api/rewards/tiers",
  REWARDS_ELIGIBLE: "/api/rewards/me/eligible",
  REWARDS_EARNED: "/api/rewards/me/earned",
  REWARDS_CLAIMS: "/api/rewards/me/claims",
  REWARDS_CLAIM: "/api/rewards/me",                  // append /{tierId}/claim
  REWARDS_TRIPS: "/api/rewards/me/trips",
  REWARDS_TRIP_BOOK: "/api/rewards/me/trip/book",

  // Investments
  INVESTMENT_PLANS: "/api/investments/plans",
  INVESTMENT_CALCULATOR: "/api/investments/calculator",
  INVESTMENT_SUBSCRIBE: "/api/investments/subscribe",
  INVESTMENT_MINE: "/api/investments/me",
  INVESTMENT_SCHEDULE: "/api/investments/me",        // append /{id}/schedule
  INVESTMENT_WITHDRAW: "/api/investments/me",        // append /{id}/withdraw

  // Wallet
  WALLET_BALANCE: "/api/wallet/me/balance",
  WALLET_TRANSACTIONS: "/api/wallet/me/transactions",
  WALLET_WITHDRAW: "/api/wallet/me/withdraw",
  WALLET_WITHDRAWALS: "/api/wallet/me/withdrawals",
  WALLET_TDS_SUMMARY: "/api/wallet/me/tds-summary",

  // Insurance
  INSURANCE_PLANS: "/api/insurance/plans",
  INSURANCE_ME: "/api/insurance/me",
  INSURANCE_ELIGIBILITY: "/api/insurance/me/eligibility",
  INSURANCE_CLAIM: "/api/insurance/me/claim",
  INSURANCE_CLAIMS: "/api/insurance/me/claims",
  INSURANCE_NOMINEE: "/api/insurance/me/nominee",

  // KYC moved into the registration form (handled inline in SignUpRequest).

  // Notifications
  NOTIFICATIONS_ME: "/api/notifications/me",
  NOTIFICATIONS_PREFERENCES: "/api/notifications/preferences",

  // Support
  SUPPORT_TICKETS_ME: "/api/support/tickets/me",
  SUPPORT_TICKET_CREATE: "/api/support/tickets",

  // Public
  PUBLIC_LANDING: "/api/public/landing",
  PUBLIC_FAQ: "/api/public/faq",
  PUBLIC_SPONSOR_VALIDATE: "/api/public/sponsor",   // append /{code}/validate

  // Auth Extension
  AUTH_CHECK_USERNAME: "/api/auth/check-username",
  AUTH_CHECK_MOBILE: "/api/auth/check-mobile",
  AUTH_OTP_SEND: "/api/auth/otp/send",
  AUTH_OTP_VERIFY: "/api/auth/otp/verify",

  // User Address
  USER_ADDRESS: "/api/user/me/address"
};
