import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { OptionsList } from '../models/Options';
import { UserManagementService } from './user.management.service';

@Injectable({
  providedIn: 'root',
})
export class InfoRetrievalService {
  private BACKEND_API: string = 'https://rebel-lip-production.up.railway.app/api';
  // private BACKEND_API: string = 'http://localhost:8080/api';

  private OPTIONS_API: string = this.BACKEND_API + '/options';
  private IMAGES_API: string = this.BACKEND_API + '/images';
  private COMPLETE_PROFILE_API = this.BACKEND_API + '/profile';
  private PREFERENCE_API = this.BACKEND_API + '/preference';
  private LANGUAGE_API: string = this.BACKEND_API + '/language';
  private PERSONALITY_API: string = this.BACKEND_API + '/personality';
  private INTEREST_API: string = this.BACKEND_API + '/interest';
  private SUB_INTEREST_API: string = this.BACKEND_API + '/subinterest';
  private ANSWER_API: string = this.BACKEND_API + '/answer';

  constructor(
    private httpClient: HttpClient,
    private userSvc: UserManagementService
  ) {}

  headers = this.userSvc.setTokenRequest();

  getOptions() {

    return lastValueFrom(
      this.httpClient.get<OptionsList>(this.OPTIONS_API, { headers: this.headers })
    );
  }

  getAllImages(userId: string) {
    return lastValueFrom(
      this.httpClient.get<any>(this.IMAGES_API + '/' + userId, { headers: this.headers })
    );
  }

  getCompleteProfile(userId: string) {
    return lastValueFrom(
      this.httpClient.get<any>(this.COMPLETE_PROFILE_API + '/' + userId, { headers: this.headers })
    );
  }

  getUserPreference(userId: string) {
    return lastValueFrom(
      this.httpClient.get<any>(this.PREFERENCE_API + '/' + userId, { headers: this.headers })
    );
  }

  getUserLanguage(userId: string) {
    return lastValueFrom(
      this.httpClient.get<any>(this.LANGUAGE_API + '/' + userId, { headers: this.headers })
    );
  }

  getUserPersonalities(userId: string) {
    return lastValueFrom(
      this.httpClient.get<any>(this.PERSONALITY_API + '/' + userId, { headers: this.headers })
    );
  }

  getUserInterests(userId: string) {
    return lastValueFrom(
      this.httpClient.get<any>(this.INTEREST_API + '/' + userId, { headers: this.headers })
    );
  }

  getUserSubInterests(userId: string) {
    return lastValueFrom(
      this.httpClient.get<any>(this.SUB_INTEREST_API + '/' + userId, { headers: this.headers })
    );
  }

  getUserAnswers(userId: string) {
    return lastValueFrom(
      this.httpClient.get<any>(this.ANSWER_API + '/' + userId, { headers: this.headers })
    );
  }
}
