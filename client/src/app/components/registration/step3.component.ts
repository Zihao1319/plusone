import {
  Component,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { InfoUploadService } from 'src/app/services/info.upload.service';
import { UserManagementService } from 'src/app/services/user.management.service';

@Component({
  selector: 'app-step3',
  templateUrl: './step3.component.html',
  styleUrls: ['./step3.component.css'],
})
export class Step3Component implements OnInit {
  subInterestForm!: FormGroup;
  subOptions!: any;

  @Output()
  onNext = new Subject<any>();

  @Output()
  onPrevious: Subject<any> = new Subject<any>();

  options!: any;

  token!: string;
  userId!: string | null;
  isEditMode: Boolean = false;
  isInterestEmpty: boolean = false;

  @Input()
  interests!: any;

  @Input() data!: any;

  colors = ['#90E0EF', '#FDE2F3', '#EEE2DE', '#C4B0FF', '#F0F0F0'];

  constructor(
    private fb: FormBuilder,
    private infoRtrvSvc: InfoRetrievalService,
    private infoUploadSvc: InfoUploadService,
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
      this.isEditMode = segments.some((segment) => segment.path === 'step3');
      console.log(this.isEditMode);
    });

    this.subInterestForm = this.createForm();

    if (this.isEditMode) {
      await this.getProfile();
      this.subInterestForm = this.createForm();
    }

    this.options = await this.fetchOptions();
    this.subOptions = await this.generateSubOptions(
      this.interests,
      this.options
    );
  }

  async fetchOptions() {
    const list = await this.infoRtrvSvc.getOptions();
    return list['subinterests'];
  }

  generateSubOptions(selectedInterests: any, options: any) {
    const subOptions: any = [];
    options.filter((sub: any) => {
      const interestId = sub.interestId;

      if (selectedInterests.includes(interestId)) {
        subOptions.push(sub);
      }
    });
    return subOptions;
  }

  async getProfile() {
    await this.infoRtrvSvc.getCompleteProfile(this.userId!).then((res) => {
      this.data = {};

      if (res['Interests']) {
        this.interests = res['Interests'].map((l: any) => {
          return l.interestId;
        });
      } else {
        this.isInterestEmpty = true;
      }

      if (res['SubInterests']) {
        this.data.subInterestId = res['SubInterests'].map((s: any) => {
          return s.subInterestId;
        });
      }
    });
  }

  createForm() {
    return this.fb.group({
      subInterestId: this.fb.control(this.data ? this.data.subInterestId : '', [
        Validators.required,
        Validators.minLength(1),
      ]),
    });
  }

  getErrorMessages(control: string) {
    if (control == 'subInterestId') {
      return 'Please select at least 1';
    }

    return null;
  }

  getColorByInterestId(interestId: number) {
    const index = this.interests.indexOf(interestId);
    return this.colors[index + 1];
  }

  next() {
    this.onNext.next(this.subInterestForm.value);
    // console.log(this.subInterestForm.value);
  }

  previous() {
    this.onPrevious.next(null);
  }

  isFormValid(): boolean {
    return this.subInterestForm.valid;
  }

  editForm() {
    // console.log(this.subInterestForm.value);
    this.infoUploadSvc.performSubInterestOps(
      this.userId!,
      this.subInterestForm.value
    );
    this.router.navigate(['/step4']);
  }

  navigateToStep2() {
    this.router.navigate(['/step2']);
  }
}
