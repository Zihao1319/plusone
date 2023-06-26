import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Message } from '../models/Message';
import { UserManagementService } from './user.management.service';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  constructor(
    private httpClient: HttpClient,
    private userSvc: UserManagementService
  ) {}

  headers = this.userSvc.setTokenRequest();

  private BACKEND_API: string = 'http://localhost:8080/api';

  getChatId(userId1: string, userId2: string) {
    const GET_CHAT_ID = this.BACKEND_API + '/getchatid';

    const params = new HttpParams().set('id1', userId1).set('id2', userId2);

    return lastValueFrom(
      this.httpClient.get<any>(GET_CHAT_ID, {
        params: params,
        headers: this.headers,
      })
    );
  }

  getMessages(chatId: number) {
    const GET_MESSAGES = this.BACKEND_API + '/getmessages';
    const params = new HttpParams().set('chatId', chatId);
    return this.httpClient.get<Array<Message>>(GET_MESSAGES, {
      params: params,
      headers: this.headers,
    });
  }

  createChatId(userId1: string, userId2: string) {
    const CREATE_CHAT_ID = this.BACKEND_API + '/create/newchatid';
    const params = new HttpParams().set('id1', userId1).set('id2', userId2);
    console.log(this.headers);
    return lastValueFrom(
      this.httpClient.get<any>(CREATE_CHAT_ID, {
        params: params,
        headers: this.headers,
      })
    );
  }

  getChatIds(userId: string) {
    const params = new HttpParams().set('id', userId);
    const GET_ALL_CHATIDS = this.BACKEND_API + '/getchats';
    return lastValueFrom(
      this.httpClient.get<any>(GET_ALL_CHATIDS, {
        params: params,
        headers: this.headers,
      })
    );
  }
}
