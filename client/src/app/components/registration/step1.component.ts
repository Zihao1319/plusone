import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime, Subject } from 'rxjs';
import { OptionsList } from 'src/app/models/Options';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { InfoUploadService } from 'src/app/services/info.upload.service';
import { UserManagementService } from 'src/app/services/user.management.service';
import { validateBirthYear } from './validators';

@Component({
  selector: 'app-step1',
  templateUrl: './step1.component.html',
  styleUrls: ['./step1.component.css'],
})
export class Step1Component implements OnInit {
  profileForm!: FormGroup;

  options!: any;
  isEditMode: Boolean = false;

  @Input() data!: any;

  @Output()
  onNext = new Subject<any>();
  userId!: string;

  constructor(
    private fb: FormBuilder,
    private infoRtrvSvc: InfoRetrievalService,
    private infoUploadSvc: InfoUploadService,
    private activatedRoute: ActivatedRoute,
    private userSvc: UserManagementService,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void> {
    // @ts-ignore
    this.userId = this.userSvc.getUserId();

    if (!this.userId) {
      this.userSvc.clearInfo();
      this.router.navigate(['/login']);
    }

    this.activatedRoute.url.subscribe((segments) => {
      this.isEditMode = segments.some((segment) => segment.path === 'step1');
      console.log(this.isEditMode);
    });

    this.profileForm = this.createProfileForm();

    if (this.isEditMode) {
      await this.getProfile();
      this.profileForm = this.createProfileForm();
      console.log(this.data);
    }

    this.options = await this.fetchOptions();

    this.profileForm
      .get('birthYear')
      ?.valueChanges.pipe()
      .subscribe((birthYear) => {
        console.log(birthYear);

        if (birthYear) {
          const currentYear = new Date().getFullYear();
          const age = currentYear - birthYear;

          if (age <= 0 || age >= 100) {
            return;
          }
          this.profileForm.get('age')?.setValue(age);
        }
      });
  }

  async fetchOptions() {
    return await this.infoRtrvSvc.getOptions();
  }
  // async fetchOptions() {
  //   await this.infoRtrvSvc.getOptions(this.token).then((list) => {
  //     this.options = list;
  //     console.log(this.options);
  //   });
  // }

  async getProfile() {
    await this.infoRtrvSvc.getCompleteProfile(this.userId!).then((res) => {
      console.log(res);
      this.data = res['Profile'];

      if (res["Language"]) {
        this.data.languageId = res['Language'].map((l: any) => {
          return l.languageId;
        });
      } 
    });
  }

  getErrorMessages() {
    if (this.profileForm.get('birthYear')?.errors?.['invalidBirthYear']) {
      return 'Invalid Birth Year';
    }

    if (this.profileForm.get('birthYear')?.errors?.['numInvalid']) {
      return 'Please input in YYYY format';
    }

    return null;
  }

  createProfileForm() {
    return this.fb.group({
      gender: this.fb.control(this.data ? this.data.gender : '', [
        Validators.required,
      ]),
      birthYear: this.fb.control('', [
        Validators.required,
        Validators.minLength(4),
        validateBirthYear,
      ]),
      horoscopeId: this.fb.control(
        this.data ? Number(this.data.horoscopeId) : '',
        [Validators.required]
      ),
      age: this.fb.control(this.data ? this.data.age : ''),
      height: this.fb.control(this.data ? this.data.height : '', [
        Validators.required,
      ]),
      weight: this.fb.control(this.data ? this.data.weight : '', [
        Validators.required,
      ]),
      raceId: this.fb.control(this.data ? Number(this.data.raceId) : '', [
        Validators.required,
      ]),
      location: this.fb.control(this.data ? this.data.location : '', [
        Validators.required,
      ]),
      job: this.fb.control(this.data ? this.data.job : '', [
        Validators.required,
      ]),
      religion: this.fb.control(this.data ? this.data.religion : '', [
        Validators.required,
      ]),
      educationId: this.fb.control(
        this.data ? Number(this.data.educationId) : '',
        [Validators.required]
      ),
      maritalStatus: this.fb.control(this.data ? this.data.maritalStatus : '', [
        Validators.required,
      ]),
      relationshipGoal: this.fb.control(
        this.data ? this.data.relationshipGoal : '',
        [Validators.required]
      ),
      smoking: this.fb.control(this.data ? this.data.smoking : '', [
        Validators.required,
      ]),
      drinking: this.fb.control(this.data ? this.data.drinking : '', [
        Validators.required,
      ]),
      exercise: this.fb.control(this.data ? this.data.exercise : '', [
        Validators.required,
      ]),
      dietId: this.fb.control(this.data ? Number(this.data.dietId) : '', [
        Validators.required,
      ]),
      languageId: this.fb.control(this.data ? this.data.languageId : '', [
        Validators.required,
      ]),
    });
  }

  isFormValid(): boolean {
    return this.profileForm.valid;
  }

  next() {
    // this.infoUploadSvc.uploadLanguageData('111111', this.profileForm.value);
    this.onNext.next(this.profileForm.value);
  }

  editForm() {
    console.log(this.profileForm.value);
    this.infoUploadSvc.uploadProfileData(this.userId!, this.profileForm.value);
    this.infoUploadSvc.performLanguageOps(this.userId!, this.profileForm.value);
    this.router.navigate(['/step2']);
  }
}
