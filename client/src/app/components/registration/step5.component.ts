import { Component, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { OptionsList } from 'src/app/models/Options';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { InfoUploadService } from 'src/app/services/info.upload.service';
import { UserManagementService } from 'src/app/services/user.management.service';

@Component({
  selector: 'app-step5',
  templateUrl: './step5.component.html',
  styleUrls: ['./step5.component.css'],
})
export class Step5Component implements OnInit {
  promptForm!: FormGroup;
  answerArray!: FormArray;
  previousAnswers: any[] = [];
  isEditMode: Boolean = false;
  token!: string;
  userId!: string | null

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
    this.token = this.userSvc.token;
    
    this.userId = this.userSvc.getUserId();

    if (!this.userId){
      this.userSvc.clearInfo();
      this.router.navigate(['/login']);
    }

    this.activatedRoute.url.subscribe((segments) => {
      this.isEditMode = segments.some((segment) => segment.path === 'step5');
      console.log(this.isEditMode);
    });

    this.promptForm = this.createForm();

    if (this.isEditMode) {
      await this.getProfile();
      console.log(this.data);
    }

    this.fetchOptions();

    if (Object.keys(this.data).length !== 0) {
      this.promptForm = this.createWithPreviousAnswers();
    }
  }

  async fetchOptions() {
    this.options = await this.infoRtrvSvc.getOptions();
  }

  async getProfile() {
    await this.infoRtrvSvc.getCompleteProfile(this.userId!).then((res) => {
      this.data = {};
      this.data = res['Answers'];
    });
  }

  createForm() {
    this.answerArray = this.fb.array([], [Validators.required]);
    return this.fb.group({
      promptId: this.fb.control('', [Validators.required]),
      answers: this.answerArray,
    });
  }

  createWithPreviousAnswers() {
    this.answerArray = this.fb.array([], [Validators.required]);

    this.previousAnswers = Object.values(this.data);

    for (const answer of this.previousAnswers) {
      const questionGroup = this.fb.group({
        promptId: answer.promptId,
        promptQuestion: answer.promptQuestion,
        answer: this.fb.control(answer.answer, [Validators.required]),
      });
      this.answerArray.push(questionGroup);
    }

    return this.fb.group({
      promptId: this.fb.control('', [Validators.required]),
      answers: this.answerArray,
    });
  }

  add() {
    const promptId = this.promptForm.value.promptId;
    // console.log('now', promptId);
    const promptsOptionsArr = this.options.prompts;
    const pastPromptsArr = this.answerArray?.value.map((q: any) =>
      Number(q.promptId)
    );
    // console.log('past', pastPromptsArr);

    if (pastPromptsArr.includes(promptId)) {
      console.log('huat');
      return;
    }

    const selected = promptsOptionsArr.find(
      (question: any) => question.promptId === promptId
    );

    if (selected) {
      const questionGroup = this.fb.group({
        promptId: promptId,
        promptQuestion: selected.promptQuestion,
        answer: this.fb.control('', [Validators.required]),
      });
      this.answerArray.push(questionGroup);
    }
  }

  delete(idx: number) {
    this.answerArray.removeAt(idx);
  }

  next() {
    this.onNext.next(this.answerArray.value);
    // console.log(this.answerArray.value);
  }

  previous() {
    this.onPrevious.next(null);
  }

  isFormValid(): boolean {
    return this.answerArray.valid;
  }
  editForm() {
    // console.log(this.answerArray.value);
    this.infoUploadSvc.performPromptDataOps(
      this.userId!,
      this.answerArray.value
    );
    this.router.navigate(['/match']);
  }
}
