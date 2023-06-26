import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { OptionsList } from 'src/app/models/Options';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { InfoUploadService } from 'src/app/services/info.upload.service';
import { UserManagementService } from 'src/app/services/user.management.service';

@Component({
  selector: 'app-step4',
  templateUrl: './step4.component.html',
  styleUrls: ['./step4.component.css'],
})
export class Step4Component implements OnInit {
  preferenceForm!: FormGroup;
  isEditMode: Boolean = false;
  token!: string;
  userId!: string | null;

  @Output()
  onNext = new Subject<any>();

  @Output()
  onPrevious: Subject<any> = new Subject<any>();

  options!: any;

  @Input() data!: any;

  constructor(
    private fb: FormBuilder,
    private infoRtrvSvc: InfoRetrievalService,
    private infoUploadSvc: InfoUploadService,
    private userSvc: UserManagementService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void> {
    this.preferenceForm = this.createForm();

    this.token = this.userSvc.token;

    this.userId = this.userSvc.getUserId();

    if (!this.userId){
      this.userSvc.clearInfo();
      this.router.navigate(['/login']);
    }

    this.activatedRoute.url.subscribe((segments) => {
      this.isEditMode = segments.some((segment) => segment.path === 'step4');
      console.log(this.isEditMode);
    });

    if (this.isEditMode) {
      await this.getProfile();
      this.preferenceForm = this.createForm();
    }
    this.fetchOptions();
  }

  async getProfile() {
    await this.infoRtrvSvc.getUserPreference(this.userId!).then((res) => {
      console.log(res);
      this.data = {};
      this.data = res;
    });
  }

  createForm() {
    return this.fb.group({
      genderPref: this.fb.control(this.data ? this.data.genderPref : '', [
        Validators.required,
      ]),
      dietPrefId: this.fb.control(this.data ? this.data.dietPrefId : '', [
        Validators.required,
      ]),
      racePrefId: this.fb.control(this.data ? this.data.racePrefId : '', [
        Validators.required,
      ]),
      minHeight: this.fb.control(this.data ? this.data.minHeight : 150, [
        Validators.required,
      ]),
      maxHeight: this.fb.control(this.data ? this.data.maxHeight : 180, [
        Validators.required,
      ]),
      minAge: this.fb.control(this.data ? this.data.minAge : 22, [
        Validators.required,
      ]),
      maxAge: this.fb.control(this.data ? this.data.maxAge : 40, [
        Validators.required,
      ]),
    });
  }

  async fetchOptions() {
    this.options = await this.infoRtrvSvc.getOptions();
  }

  next() {
    this.onNext.next(this.preferenceForm.value);
    console.log(this.preferenceForm.value);
  }

  previous() {
    this.onPrevious.next(null);
  }

  isFormValid(): boolean {
    return this.preferenceForm.valid;
  }

  editForm() {
    this.infoUploadSvc.uploadPreferenceData(
      this.userId!,
      this.preferenceForm.value
    );
    this.router.navigate(['/step5']);
  }
}
