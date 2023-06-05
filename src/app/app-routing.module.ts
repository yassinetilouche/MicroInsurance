import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './Components/not-found/not-found.component';
import { HomePageComponent } from './Components/home-page/home-page.component';
import { MenuComponent } from './Components/menu/menu.component';
import { ContactComponent } from './Components/contact/contact.component';
import { LoginComponent } from './Components/login/login.component';
import { SignUpComponent } from './Components/sign-up/sign-up.component';
import { AddContractComponent } from './Components/add-contract/add-contract.component';
import { ContractComponent } from './Components/contract/contract.component';
import { ListecontractComponent } from './Components/listecontract/listecontract.component';
import { ModifContractComponent } from './Components/modif-contract/modif-contract.component';
import { PaymentComponent } from './Components/payment/payment.component';
import { ForgotPasswordComponent } from './Components/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './Components/reset-password/reset-password.component';
import { AuthGuard } from './Guard/auth-guard.guard';

const routes: Routes = [
  {path:'homepage',component:HomePageComponent},
  {path:'payment',component:PaymentComponent},
  {path:'menu', component:MenuComponent,
  canActivate: [AuthGuard],
  canActivateChild: [AuthGuard],
  data: {
    allowedRole: ['ADMIN','INSURER']
  },
  children :[
    {path:'listcontract',component:ListecontractComponent},
    {path:'addcontract',component:AddContractComponent},
    {path:'modifcontract/:id',component:ModifContractComponent},
  ]
},
  {path:'contact',component:ContactComponent},
  {path: 'login', component: LoginComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'forgot-password', component: ForgotPasswordComponent},
  {path: 'reset_password', component: ResetPasswordComponent},
  {path:'',redirectTo:'homepage',pathMatch:'full'},
  {path:'**',component:NotFoundComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
