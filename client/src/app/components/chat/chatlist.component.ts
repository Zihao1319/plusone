import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { defaultIfEmpty, lastValueFrom, map } from 'rxjs';
import { ChatService } from 'src/app/services/chat.service';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { UserManagementService } from 'src/app/services/user.management.service';

@Component({
  selector: 'app-chatlist',
  templateUrl: './chatlist.component.html',
  styleUrls: ['./chatlist.component.css'],
})
export class ChatlistComponent implements OnInit {
  chatProfiles: any[] = [];
  userId!: string | null;
  isLoading: boolean = false;

  private IMAGE_URL = 'https://ozh2923.sgp1.digitaloceanspaces.com/';

  constructor(
    private router: Router,
    private chatSvc: ChatService,
    private userSvc: UserManagementService,
    private infoRtrvSvc: InfoRetrievalService
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;
    this.userId = this.userSvc.getUserId();
    this.chatProfiles = await this.fetchAllChatProfiles(this.userId!);
    this.isLoading = false;
  }

  async fetchAllChatProfiles(userId: string) {
    const res = await this.chatSvc.getChatIds(this.userId!);

    const promises = res.map(async (p: any) => {
      const chatId = p.chatId;

      let otherUserId: string = '';

      if (p.userOneId !== this.userId) {
        otherUserId = p.userOneId;
      } else if (p.userTwoId !== this.userId) {
        otherUserId = p.userTwoId;
      }

      const profile = await this.getUserCompleteProfile(otherUserId);
      const imageUrl = await this.getUserImage(otherUserId);
      const lastMessageInfo = await this.getLatestMessage(chatId);


      let lastMessage = '';
      let lastSenderId = '';

      if (lastMessageInfo !== null) {
        lastMessage = lastMessageInfo['content'];
        lastSenderId = lastMessageInfo['senderId'];
      }

      return {
        chatId: chatId,
        currentUserId: otherUserId,
        name: profile.name,
        gender: profile.gender,
        imageUrl: imageUrl,
        lastMessage: lastMessage,
        lastSenderId: lastSenderId,
      };
    });
    const profiles = await Promise.all(promises);
    return profiles;
  }

  async getUserImage(userId: string) {
    const res = await this.infoRtrvSvc.getAllImages(userId);
    const images = res.images;
    if (images.length > 0) {
      return this.IMAGE_URL + images[0].url;
    } else {
      return null;
    }
  }

  async getUserCompleteProfile(userId: string) {
    const res = await this.infoRtrvSvc.getCompleteProfile(userId);
    const profile = this.populateProfile(res);
    return profile;
  }

  populateProfile(res: any) {
    const profile: any = {};
    profile.name = res['Profile'].name;
    profile.gender = res['Profile'].gender;

    return profile;
  }

  async getLatestMessage(chatId: number) {
    const messages = this.chatSvc.getMessages(chatId).pipe(
      defaultIfEmpty([]),
      map((data: any) => data[data.length - 1])
    );

    const lastMessage = await lastValueFrom(messages);

    if (lastMessage) {
      return lastMessage;
    } else {
      return null;
    }
  }

  navigateToChat(userId: string) {
    this.router.navigate([`/chat/${userId}`]);
  }

  navigateToMatches() {
    this.router.navigate(['/matches']);
  }
}
