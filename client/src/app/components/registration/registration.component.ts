import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatGridTileHeaderCssMatStyler } from '@angular/material/grid-list';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime } from 'rxjs';
import { OptionsList } from 'src/app/models/Options';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { Step1Component } from './step1.component';
import { Step2Component } from './step2.component';
import { validateBirthYear } from './validators';
import { MatStepper, MatStepperModule } from '@angular/material/stepper';
import { InfoUploadService } from 'src/app/services/info.upload.service';
import { MatDialog } from '@angular/material/dialog';
import { SuccessComponent } from './success.component';
import { UserManagementService } from 'src/app/services/user.management.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class RegistrationComponent implements OnInit {
  @ViewChild('stepper') stepper!: MatStepper;

  profileData: any = {};
  interestPersonalityData: any = {};
  subInterestData: any = {};
  preferenceData: any = {};
  promptData: any = {};
  userId!: string | null;
  token!: string;

  options!: OptionsList;
  isEditMode: Boolean = false;

  subOptions: any = [];
  interests: any = [];
  currentStep: number = 1;
  totalSteps: number = 6;

  constructor(
    private fb: FormBuilder,
    private infoRtrvSvc: InfoRetrievalService,
    private infoUploadSvc: InfoUploadService,
    private router: Router,
    private userSvc: UserManagementService,
    private dialog: MatDialog,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.isEditMode = false;
    this.token = this.userSvc.token;
    this.userId = this.userSvc.getUserId();

    if (!this.userId) {
      this.userSvc.clearInfo();
      this.router.navigate(['/login']);
    }

    this.infoRtrvSvc.getOptions().then((list) => {
      this.options = list;
      console.log(this.options);
    });
  }

  goToNextStep(data?: any): void {
    this.storeFormData(data);
    this.currentStep++;
    console.log('next to step', this.currentStep);
    this.stepper.next();

    if (this.currentStep === 3) {
      this.interests = this.interestPersonalityData.interestId;
    }
  }

  goToPreviousStep(): void {
    this.currentStep--;
    this.stepper.previous();
    console.log('back to step', this.currentStep);
  }

  storeFormData(data: any) {
    switch (this.currentStep) {
      case 1:
        this.profileData = { ...this.profileData, ...data };
        console.log(this.profileData);
        break;

      case 2:
        this.interestPersonalityData = {
          ...this.interestPersonalityData,
          ...data,
        };
        console.log(this.interestPersonalityData);
        break;

      case 3:
        this.subInterestData = { ...this.subInterestData, ...data };
        console.log(this.subInterestData);
        break;

      case 4:
        this.preferenceData = { ...this.preferenceData, ...data };
        console.log(this.preferenceData);
        break;

      case 5:
        this.promptData = { ...this.promptData, ...data };
        console.log(this.promptData);
        break;

      case 6:
        break;
    }
  }

  submit() {
    console.log('uploading to backend in progres...');
    console.log(this.promptData);

    const uploadOperations = [
      this.infoUploadSvc.uploadProfileData(this.userId!, this.profileData),
      this.infoUploadSvc.registerLanguageData(this.userId!, this.profileData),
      this.infoUploadSvc.registerPersonalityInfo(
        this.userId!,
        this.interestPersonalityData
      ),
      this.infoUploadSvc.registerInterestInfo(
        this.userId!,
        this.interestPersonalityData
      ),
      this.infoUploadSvc.registerSubInterestInfo(
        this.userId!,
        this.subInterestData
      ),
      this.infoUploadSvc.uploadPreferenceData(
        this.userId!,
        this.preferenceData
      ),
      this.infoUploadSvc.registerPromptData(this.userId!, this.promptData),
    ];

    Promise.all(uploadOperations)
      .then(() => {
        console.log('all operations completed...');

        const dialogRef = this.dialog.open(SuccessComponent, {
          width: '400px',
          data: { message: 'Success!' },
        });

        dialogRef.afterClosed().subscribe(() => {
          this.router.navigate(['/profile']);
        });
      })
      .catch((error) => {
        console.error('Error occured:', error);
      });
  }

  editStep(event: any) {
    console.log(event);
  }
}
