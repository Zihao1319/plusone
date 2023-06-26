import { HttpErrorResponse } from '@angular/common/http';
import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { InfoDeleteService } from 'src/app/services/info.delete.service';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { InfoUploadService } from 'src/app/services/info.upload.service';
import { UserManagementService } from 'src/app/services/user.management.service';

@Component({
  selector: 'app-step6',
  templateUrl: './step6.component.html',
  styleUrls: ['./step6.component.css'],
})
export class Step6Component implements OnInit {
  @ViewChild('file') fileElem!: ElementRef;
  @ViewChild('imageX') imageElem!: ElementRef;

  isEditMode: Boolean = false;
  token!: string;
  userId!: string | null;

  imageForm!: FormGroup;
  selectedFile!: File | null;
  selectedFileName!: string;
  preview!: string;
  images: any = [];
  private IMAGE_URL = 'https://ozh2923.sgp1.digitaloceanspaces.com/';

  @Output()
  onSubmit = new Subject<any>();

  @Output()
  onPrevious: Subject<any> = new Subject<any>();

  constructor(
    private fb: FormBuilder,
    private UploadSvc: InfoUploadService,
    private InfoRtrvlSvc: InfoRetrievalService,
    private InfoDelSvc: InfoDeleteService,
    private userSvc: UserManagementService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.token = this.userSvc.token;
    this.userId = this.userSvc.getUserId();

    if (!this.userId){
      this.userSvc.clearInfo();
      this.router.navigate(['/login']);
    }

    this.activatedRoute.url.subscribe((segments) => {
      this.isEditMode = segments.some((segment) => segment.path === 'step6');
      console.log(this.isEditMode);
    });

    this.imageForm = this.createForm();
    this.getAllImages();
  }

  createForm() {
    return this.fb.group({
      imageId: this.fb.control('', [Validators.required]),
      url: this.fb.control('', [Validators.required]),
      image: this.fb.control('', [Validators.required]),
    });
  }

  selectFile(event: any) {
    this.selectedFileName = '';
    this.selectedFile = event.target.files[0];

    if (this.selectedFile) {
      const reader = new FileReader();

      reader.onload = (e: any) => {
        this.preview = e.target.result;
      };

      reader.readAsDataURL(this.selectedFile);
      this.selectedFileName = this.selectedFile.name;
    }
  }

  async getAllImages() {
    try {
      const res = await this.InfoRtrvlSvc.getAllImages(this.userId!);
      this.images = res.images.map((i: any) => {
        const imageUrl = this.IMAGE_URL + i.url;
        return { ...i, imageUrl };
      });

      console.log(this.images);
    } catch (error: any) {
      console.log(error);
    }
  }

  async uploadFiles() {
    await this.UploadSvc.uploadImage(this.fileElem, this.userId!).then((res) => {
      console.log(this.images);
      this.preview = '';
      this.getAllImages();
    });
    this.clearForm();
  }

  clearForm() {
    this.selectedFileName = '';
    this.selectedFile = null;
  }

  submit() {
    this.onSubmit.next(null);
  }

  previous() {
    this.onPrevious.next(null);
  }

  isFormValid(): boolean {
    return this.images.length !== 0;
  }

  delete(event: any, image: HTMLImageElement) {
    const imageId = image.id;
    const imageUrl = image.getAttribute('url');

    this.InfoDelSvc.deleteImage(Number(imageId), String(imageUrl)).then(() => {
      this.getAllImages();
    });
  }
}
