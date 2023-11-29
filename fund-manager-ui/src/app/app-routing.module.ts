import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RootTestComponent } from './root-test/root-test.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { StatusComponent } from './status/status.component';
import { AuthGuard } from './Guard/auth.guard';
import { TransactionComponent } from './transaction/transaction.component';
import { UserComponent } from './user/user.component';
import { VerifyUserComponent } from './verify-user/verify-user.component';
import { AddClientComponent } from './add-client/add-client.component';
import { AddAccountComponent } from './add-account/add-account.component';
import { TransactionStatementComponent } from './transaction-statement/transaction-statement.component';
import { AuthFMGuard } from './Guard/auth-fm.guard';
import { AuthAdminGuard } from './Guard/auth-admin.guard';
import { SummaryComponent } from './summary/summary.component';
import { AboutPageComponent } from './about-page/about-page.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { AuthClientGuardGuard } from './Guard/auth-client-guard.guard';
import { ResetPassword } from './reset-password/reset-password.component';
import { FMorClientGuard } from './Guard/fmor-client.guard';
import { SetInterestRateComponent } from './set-interest/set-interest.component';
import { AvailableStocks } from './available-stocks/available-stocks.component';
import { SettleInterestRateComponent } from './settle-interest/settle-interest.component';

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'home',component:HomeComponent},
  {path:'login',component:LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'transaction',component:TransactionComponent,canActivate:[AuthFMGuard]},
  {path:'manageusers',component:UserComponent,canActivate:[AuthAdminGuard]},
  {path:'addClient',component:AddClientComponent,canActivate:[AuthFMGuard]},
  {path:'addAccount',component:AddAccountComponent,canActivate:[AuthFMGuard]},
  {path:'setInterest',component:SetInterestRateComponent,canActivate:[AuthFMGuard]},
  {path:'settleInterest',component:SettleInterestRateComponent,canActivate:[AuthFMGuard]},
  {path:'avlStcoks',component:AvailableStocks,canActivate:[AuthFMGuard]},
  {path:'statement',component:TransactionStatementComponent,canActivate:[AuthFMGuard]},
  {path:'summary',component:SummaryComponent,canActivate:[FMorClientGuard]},
  {path:'about',component:AboutPageComponent},
  {path:'changePassword',component:ChangePasswordComponent,canActivate:[AuthGuard]},
  {path:'resetPassword',component:ResetPassword},
  {path:'**',component: StatusComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
