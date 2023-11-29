import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {HttpClientModule,HTTP_INTERCEPTORS} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RootTestComponent } from './root-test/root-test.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { TransactionComponent } from './transaction/transaction.component';

import {MatTooltipModule} from '@angular/material/tooltip';
import {MatIconModule} from '@angular/material/icon';
import {MatTableModule} from '@angular/material/table';
import { TokenInterceptorService } from './Service/token-interceptor.service';
import { UserComponent } from './user/user.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDialogModule } from '@angular/material/dialog';
import { ModalpopupComponent } from './modalpopup/modalpopup.component';
import { JwtModule } from '@auth0/angular-jwt';
import {MatMenuModule} from '@angular/material/menu';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { VerifyUserComponent } from './verify-user/verify-user.component';
import { AddClientComponent } from './add-client/add-client.component';
import { AddAccountComponent } from './add-account/add-account.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { TransactionStatementComponent } from './transaction-statement/transaction-statement.component';
import { MatSortModule} from '@angular/material/sort';
import { SummaryComponent } from './summary/summary.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { EditTransactionComponent } from './edit-transaction/edit-transaction.component';
import { AboutPageComponent } from './about-page/about-page.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ResetPassword } from './reset-password/reset-password.component';
import { SetInterestRateComponent } from './set-interest/set-interest.component';
import { DatePipe } from '@angular/common';
import { AvailableStocks } from './available-stocks/available-stocks.component';
import { SettleInterestRateComponent } from './settle-interest/settle-interest.component';





@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RootTestComponent,
    RegisterComponent,
    HomeComponent,
    TransactionComponent,
    UserComponent,
    ModalpopupComponent,
    VerifyUserComponent,
    AddClientComponent,
    AddAccountComponent,
    TransactionStatementComponent,
    SummaryComponent,
    EditTransactionComponent,
    AboutPageComponent,
    ChangePasswordComponent,
    ResetPassword,
    SetInterestRateComponent,
    AvailableStocks,
    SettleInterestRateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatTooltipModule,
    MatIconModule,
    MatTableModule,
    MatPaginatorModule,
    MatDialogModule,
    JwtModule,
    MatMenuModule,
    MatToolbarModule,
    MatProgressSpinnerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSortModule,
    MatExpansionModule
  ],
  providers: [{provide:HTTP_INTERCEPTORS,useClass:TokenInterceptorService,multi:true},DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
