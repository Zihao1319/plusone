import {
  Component,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { SwiperOptions } from 'swiper';
import { UserManagementService } from './services/user.management.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'client';
  isLogin!: boolean;
  login$!: Subscription;

  constructor(private userSvc: UserManagementService, private router: Router) {}

  ngOnInit(): void {

    this.userSvc.isAuthenticated()

    this.login$ = this.userSvc.loggedIn$.subscribe((status) => {
      this.isLogin = status;
    });

    console.log(this.isLogin);
  }

  ngOnDestroy(): void {
    this.login$.unsubscribe();
  }

  
}
