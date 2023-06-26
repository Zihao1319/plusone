import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { UserManagementService } from './user.management.service';

@Injectable({
  providedIn: 'root',
})
export class AiService {
  private AI_BACKEND_API: string = 'https://rebel-lip-production.up.railway.app/api/ai';
  // private AI_BACKEND_API: string = 'http://localhost:8080/api/ai';

  constructor(
    private httpClient: HttpClient,
    private userSvc: UserManagementService
  ) {}

  headers = this.userSvc.setTokenRequest();

  generateAIResponse(userId: string, prompt: string) {
    return lastValueFrom(
      this.httpClient.post<any>(this.AI_BACKEND_API + '/' + userId, prompt, {
        headers: this.headers,
      })
    );
  }
}
