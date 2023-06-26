import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MatchServiceService } from '../services/match.service.service';
import { UserManagementService } from '../services/user.management.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit, OnDestroy {
  @Input()
  isLogin!: boolean;

  friendshipCount!: number;
  friend$!: Subscription;

  constructor(
    private userSvc: UserManagementService,
    private router: Router,
    private matchSvc: MatchServiceService
  ) {}

  ngOnInit(): void {
    this.friend$ = this.matchSvc.requestCount$.subscribe((count) => {
      this.friendshipCount = count;
    });
  }

  ngOnDestroy(): void {
    this.friend$.unsubscribe();
  }

  navigateToMatches() {
    this.router.navigate(['/matches']);
  }

  navigateToChats() {
    this.router.navigate(['/chats']);
  }

  navigateToProfile() {
    this.router.navigate(['/profile']);
  }

  navigateToStep1() {
    this.router.navigate(['/step1']);
  }

  navigateToStep2() {
    this.router.navigate(['/step2']);
  }

  navigateToStep3() {
    this.router.navigate(['/step3']);
  }

  navigateToStep4() {
    this.router.navigate(['/step4']);
  }

  navigateToStep5() {
    this.router.navigate(['/step5']);
  }

  navigateToStep6() {
    this.router.navigate(['/step6']);
  }

  navigateToLogout() {
    this.router.navigate(['/logout']);
    this.userSvc.logout();
  }
}
