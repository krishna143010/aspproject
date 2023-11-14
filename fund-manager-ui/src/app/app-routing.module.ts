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

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'home',component:HomeComponent},
  {path:'login',component:LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'transaction',component:TransactionComponent,canActivate:[AuthGuard]},
  {path:'manageusers',component:UserComponent,canActivate:[AuthGuard]},
  {path:'**',component: StatusComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
