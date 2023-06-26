import { ThisReceiver } from '@angular/compiler';
import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { OptionsList } from 'src/app/models/Options';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { InfoUploadService } from 'src/app/services/info.upload.service';
import { UserManagementService } from 'src/app/services/user.management.service';

@Component({
  selector: 'app-step2',
  templateUrl: './step2.component.html',
  styleUrls: ['./step2.component.css'],
})
export class Step2Component implements OnInit {
  interestPersonalityForm!: FormGroup;

  isEditMode: Boolean = false;

  @Output()
  onNext = new Subject<any>();

  @Output()
  onPrevious: Subject<any> = new Subject<any>();

  options!: any;

  @Input() data!: any;

  token!: string;
  userId!: string | null;

  constructor(
    private fb: FormBuilder,
    private infoUploadSvc: InfoUploadService,
    private infoRtrvSvc: InfoRetrievalService,
    private userSvc: UserManagementService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void> {
    this.token = this.userSvc.token;

    this.userId = this.userSvc.getUserId();

    if (!this.userId) {
      this.userSvc.clearInfo();
      this.router.navigate(['/login']);
    }

    this.activatedRoute.url.subscribe((segments) => {
      this.isEditMode = segments.some((segment) => segment.path === 'step2');
      console.log(this.isEditMode);
    });

    this.interestPersonalityForm = this.createForm();

    if (this.isEditMode) {
      await this.getProfile();
      //to be investigated later
      this.interestPersonalityForm = this.createForm();
    }

    this.options = await this.fetchOptions();

    // console.log(this.data);
  }

  createForm() {
    return this.fb.group({
      personalityId: this.fb.control(this.data ? this.data.personalityId : '', [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(5),
      ]),
      interestId: this.fb.control(this.data ? this.data.interestId : '', [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(5),
      ]),
    });
  }

  async fetchOptions() {
    const list = await this.infoRtrvSvc.getOptions();
    return list;
  }

  async getProfile() {
    await this.infoRtrvSvc.getCompleteProfile(this.userId!).then((res) => {
      this.data = {};
      // console.log(res);

      if (res['Personalities']) {
        this.data.personalityId = res['Personalities'].map((p: any) => {
          return p.personalityId;
        });
      }

      if (res['Interests']) {
        this.data.interestId = res['Interests'].map((l: any) => {
          return l.interestId;
        });
      }
    });
  }

  getErrorMessages(control: string) {
    if (control === 'personalityId') {
      return 'Please select up to 5 personalities';
    } else if (control === 'interestId') {
      return 'Please select up to 5 interests';
    }

    return null;
  }

  next() {
    // this.infoSvc.uploadPersonalityInfo(
    //   '1e940c',
    //   this.interestPersonalityForm.value
    // );
    this.onNext.next(this.interestPersonalityForm.value);
    // console.log(this.interestPersonalityForm.value);
  }

  previous() {
    this.onPrevious.next(null);
  }

  isFormValid(): boolean {
    return this.interestPersonalityForm.valid;
  }

  async editForm() {
    // console.log(this.interestPersonalityForm.value);
    await this.infoUploadSvc.performPersonalityOps(
      this.userId!,
      this.interestPersonalityForm.value
    );
    await this.infoUploadSvc.performInterestOps(
      this.userId!,
      this.interestPersonalityForm.value
    );
    this.router.navigate(['/step3']);
  }
}
