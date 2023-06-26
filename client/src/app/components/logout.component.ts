import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserManagementService } from '../services/user.management.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css'],
})
export class LogoutComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private userSvc: UserManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.url.subscribe((segments) => {
      if (segments.some((segment) => segment.path === 'logout')) {
        this.logout();
      }
    });
  }

  logout() {
    this.userSvc.clearInfo();
    console.log('logout');
    this.router.navigate(['/login']);
  }
}
