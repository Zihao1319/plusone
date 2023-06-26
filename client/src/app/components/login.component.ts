import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';
import { UserManagementService } from '../services/user.management.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  error!: string;
  isLoading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private userSvc: UserManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.createForm();
    this.error = '';
  }

  createForm() {
    return this.fb.group({
      name: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required]),
    });
  }

  submit() {
    this.userSvc.clearInfo();
    this.userSvc
      .loginUser(this.loginForm.value)
      .then((res) => {
        // console.log(res.token);
        this.userSvc.setUserToken(res.token);
        this.userSvc.setUserId(res.userId);
        this.userSvc.isAuthenticated();
        this.router.navigate(['/matches']);
      })
      .catch((error: any) => {
        const err = error.error;
        this.error = err.message;
      });

    this.loginForm.reset();
  }

  isFormValid() {
    return this.loginForm.valid;
  }

  register() {
    this.router.navigate(['/register']);
  }
}
