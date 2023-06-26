import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { UserManagementService } from './user.management.service';

@Injectable({
  providedIn: 'root',
})
export class InfoDeleteService {
  private BACKEND_API: string = 'https://rebel-lip-production.up.railway.app/api';
  // private BACKEND_API: string = 'http://localhost:8080/api';

  private IMAGES_API: string = this.BACKEND_API + '/image';
  private LANGUAGE_API: string = this.BACKEND_API + '/language';
  private INTEREST_API: string = this.BACKEND_API + '/interest';
  private SUB_INTEREST_API: string = this.BACKEND_API + '/subinterest';
  private PROMPT_API: string = this.BACKEND_API + '/prompt';
  private PERSONALITY_API: string = this.BACKEND_API + '/personality';

  constructor(
    private httpClient: HttpClient,
    private userSvc: UserManagementService
  ) {}

  headers = this.userSvc.setTokenRequest();

  deleteImage(imageId: number, url: string) {
    const params = new HttpParams().set('url', url);
    const deleteUrl = this.IMAGES_API + '/' + imageId;
    return lastValueFrom(
      this.httpClient.delete<any>(deleteUrl, {
        params: params,
        headers: this.headers,
      })
    );
  }

  deleteLanguage(userId: string, languageId: number) {
    const params = new HttpParams().set('id', languageId);
    const deleteUrl = this.LANGUAGE_API + '/' + userId;
    return lastValueFrom(
      this.httpClient.delete<any>(deleteUrl, {
        params: params,
        headers: this.headers,
      })
    );
  }

  deletePersonality(userId: string, personalityId: number) {
    const params = new HttpParams().set('id', personalityId);
    const deleteUrl = this.PERSONALITY_API + '/' + userId;
    return lastValueFrom(
      this.httpClient.delete<any>(deleteUrl, {
        params: params,
        headers: this.headers,
      })
    );
  }

  deleteInterest(userId: string, interestId: number) {
    const params = new HttpParams().set('id', interestId);
    const deleteUrl = this.INTEREST_API + '/' + userId;

    return lastValueFrom(
      this.httpClient.delete<any>(deleteUrl, {
        params: params,
        headers: this.headers,
      })
    );
  }

  deleteSubInterest(userId: string, subInterestId: number) {
    const params = new HttpParams().set('id', subInterestId);
    const deleteUrl = this.SUB_INTEREST_API + '/' + userId;

    return lastValueFrom(
      this.httpClient.delete<any>(deleteUrl, {
        params: params,
        headers: this.headers,
      })
    );
  }

  deleteAnswer(userId: string, promptId: number) {
    const params = new HttpParams().set('id', promptId);
    const deleteUrl = this.PROMPT_API + '/' + userId;

    return lastValueFrom(
      this.httpClient.delete<any>(deleteUrl, {
        params: params,
        headers: this.headers,
      })
    );
  }

  deleteAllAnswers(userId: string) {
    const deleteUrl = this.PROMPT_API + '/all/' + userId;
    return lastValueFrom(
      this.httpClient.delete<any>(deleteUrl, {
        headers: this.headers,
      })
    );
  }
}
