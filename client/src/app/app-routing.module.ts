import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatComponent } from './components/chat/chat.component';
import { ChatlistComponent } from './components/chat/chatlist.component';
import { LoginComponent } from './components/login.component';
import { LogoutComponent } from './components/logout.component';
import { MatchComponent } from './components/match/match.component';
import { ProfileComponent } from './components/match/profile.component';
import { RegisterComponent } from './components/register.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { Step1Component } from './components/registration/step1.component';
import { Step2Component } from './components/registration/step2.component';
import { Step3Component } from './components/registration/step3.component';
import { Step4Component } from './components/registration/step4.component';
import { Step5Component } from './components/registration/step5.component';
import { Step6Component } from './components/registration/step6.component';
import { SuccessComponent } from './components/registration/success.component';

const routes: Routes = [
  { path: '', component: MatchComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'registration',
    component: RegistrationComponent,
  },
  {
    path: 'match',
    component: MatchComponent,
  },
  { path: 'step1', component: Step1Component },
  { path: 'step2', component: Step2Component },
  { path: 'step3', component: Step3Component },
  { path: 'step4', component: Step4Component },
  { path: 'step5', component: Step5Component },
  { path: 'step6', component: Step6Component },
  { path: 'profile', component: ProfileComponent },
  { path: 'profile/:userId', component: ProfileComponent },
  { path: 'viewprofiles', component: ProfileComponent },
  { path: 'chat/:otherUserId', component: ChatComponent },
  { path: 'chats', component: ChatlistComponent },

  // { path: 'success', component: SuccessComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
