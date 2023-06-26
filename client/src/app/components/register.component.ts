import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserManagementService } from '../services/user.management.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  error!: string;

  constructor(
    private fb: FormBuilder,
    private userSvc: UserManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.createForm();
    this.error = '';
  }

  createForm() {
    return this.fb.group({
      name: this.fb.control('', [Validators.required]),
      email: this.fb.control('', [Validators.required, Validators.email]),
      password: this.fb.control('', [Validators.required]),
    });
  }

  submit() {
    console.log(this.registerForm.value);
    this.userSvc.registerUser(this.registerForm.value)
      .then((res) => {
        console.log(res);
        this.userSvc.setUserToken(res.token);
        this.userSvc.setUserId(res.userId);
        this.userSvc.isAuthenticated();
        this.router.navigate(['/registration']);
      })
      .catch((error) => {
        const err = error.error;
        this.error = err.message;
        console.log(this.error);
      });
    this.registerForm.reset();
  }

  login() {
    this.router.navigate(['/login']);
  }

  isFormValid() {
    return this.registerForm.valid;
  }
}
