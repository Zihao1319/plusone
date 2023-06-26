import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { InfoDeleteService } from './info.delete.service';
import { InfoRetrievalService } from './info.retrieval.service';
import { UserManagementService } from './user.management.service';

@Injectable({
  providedIn: 'root',
})
export class MatchServiceService {
  private BACKEND_API: string = 'http://localhost:8080/api/';

  constructor(
    private httpClient: HttpClient,
    private userSvc: UserManagementService
  ) {}

  headers = this.userSvc.setTokenRequest();

  getMatches(userId: string) {
    
    return lastValueFrom(
      this.httpClient.get<any>(this.BACKEND_API + 'matches/' + userId, {
        headers: this.headers,
      })
    );
  }
}
