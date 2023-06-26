import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { BehaviorSubject, lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserManagementService {
  token!: string;
  userId!: string;
  viewOtherUserId!: string;

  private LOGIN_API: string = 'http://localhost:8080/auth/login';
  private REGISTER_API = 'http://localhost:8080/auth/register';

  private loggedInSub = new BehaviorSubject<boolean>(false);
  loggedIn$ = this.loggedInSub.asObservable();

  constructor(private httpClient: HttpClient, private router: Router) {}

  isAuthenticated() {
    const token = this.getUserToken();

    if (token) {
      this.loggedInSub.next(true);
    } else {
      this.loggedInSub.next(false);
    }
  }

  logout() {
    this.loggedInSub.next(false);
  }

  loginUser(loginInfo: any) {
    return lastValueFrom(this.httpClient.post<any>(this.LOGIN_API, loginInfo));
  }

  registerUser(regiserInfo: any) {
    return lastValueFrom(
      this.httpClient.post<any>(this.REGISTER_API, regiserInfo)
    );
  }

  setUserToken(token: string) {
    localStorage.setItem('token', token);
    this.token = token;
  }

  getUserToken() {
    const token = localStorage.getItem('token');
    console.log(token);

    if (!token) {
      this.clearInfo();
      this.router.navigate(['/login']);
    }

    return token;
  }

  setOtherUserId(userId: string) {
    this.viewOtherUserId = userId;
  }

  getOtherUserId() {
    return this.viewOtherUserId;
  }

  setUserId(userId: string) {
    localStorage.setItem('userId', userId);
    this.userId = userId;
  }

  getUserId() {
    const userId = localStorage.getItem('userId');
    return userId;
  }

  clearInfo() {
    localStorage.clear();
  }

  setTokenRequest() {
    // const token = this.userSvc.getUserToken();
    // console.log(token);

    // const headers = new HttpHeaders()
    //   .set('Authorization', `Bearer ${token}`)
    //   .set('content-type', 'application/json')
    //   .set('Access-Control-Allow-Origin', '*');

    const token = this.getUserToken();
    // console.log(token);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return headers;
  }
}
