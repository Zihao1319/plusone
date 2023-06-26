import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { MaterialModule } from 'src/material.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegistrationComponent } from './components/registration/registration.component';
import { ProfileComponent } from './components/match/profile.component';
import { Step1Component } from './components/registration/step1.component';
import { Step2Component } from './components/registration/step2.component';
import { Step3Component } from './components/registration/step3.component';
import { Step4Component } from './components/registration/step4.component';
import { Step5Component } from './components/registration/step5.component';
import { Step6Component } from './components/registration/step6.component';
import { SuccessComponent } from './components/registration/success.component';
import { MatStepperModule } from '@angular/material/stepper';
import { LoginComponent } from './components/login.component';
import { LogoutComponent } from './components/logout.component';
import { RegisterComponent } from './components/register.component';
import { SwiperModule } from 'swiper/angular';
import { ArrayJoinPipe } from './components/arrayJoin.pipe';
import { MatchComponent } from './components/match/match.component';
import { ChatComponent } from './components/chat/chat.component';
import { ChatlistComponent } from './components/chat/chatlist.component';
import { AioptionsComponent } from './components/chat/aioptions.component';
import { NavbarComponent } from './components/navbar.component';

// import { AuthInterceptor } from './auth.interceptor';

@NgModule({
  declarations: [
    ArrayJoinPipe,
    AppComponent,
    RegistrationComponent,
    ProfileComponent,
    Step1Component,
    Step2Component,
    Step3Component,
    Step4Component,
    Step5Component,
    Step6Component,
    SuccessComponent,
    LoginComponent,
    LogoutComponent,
    RegisterComponent,
    MatchComponent,
    ChatComponent,
    ChatlistComponent,
    AioptionsComponent,
    NavbarComponent,
  ],
  imports: [
    BrowserModule,
    SwiperModule,
    AppRoutingModule,
    MaterialModule,
    MatStepperModule,
    HttpClientModule,
    ReactiveFormsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000',
    }),
    BrowserAnimationsModule,
  ],
  providers: [
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
