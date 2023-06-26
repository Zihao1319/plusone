import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Message } from 'src/app/models/Message';
import { ChatService } from 'src/app/services/chat.service';
import { UserManagementService } from 'src/app/services/user.management.service';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { FormControl } from '@angular/forms';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { AiService } from 'src/app/services/ai.service';
import { AioptionsComponent } from './aioptions.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
})
export class ChatComponent implements OnInit {
  @ViewChild('chat') chatContainer!: ElementRef;

  private BACKEND_API = 'http://localhost:8080/api';
  private IMAGE_URL = 'https://ozh2923.sgp1.digitaloceanspaces.com/';

  currUserId!: string | null;
  otherUserId!: string;
  chatId!: number;
  messages?: Observable<Array<Message>>;
  socket?: WebSocket;
  stompClient?: Stomp.Client;
  newMessage = new FormControl('');
  currUserImage!: string | null;
  otherUserImage!: string | null;
  currUserProfile!: any;
  otherUserProfile!: any;
  isLoading: boolean = false;
  isGPTLoading: boolean = false;
  headers!: any;

  constructor(
    private activatedRoute: ActivatedRoute,
    private chatSvc: ChatService,
    private userSvc: UserManagementService,
    private infoRtrvSvc: InfoRetrievalService,
    private aiSvc: AiService,
    private el: ElementRef,
    private router: Router,
    public dialog: MatDialog
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;

    this.headers = this.userSvc.setTokenRequest();
    this.currUserId = this.userSvc.getUserId();

    if (!this.currUserId) {
      this.userSvc.clearInfo();
      this.router.navigate(['/login']);
    }

    this.activatedRoute.params.subscribe((params) => {
      this.otherUserId = params['otherUserId'];
    });
    this.connectToChat();
    this.el.nativeElement.querySelector('#chat')?.scrollIntoView();

    this.isLoading = false;
  }

  scrollDown() {
    const container = this.el.nativeElement.querySelector('#chat');
    container.scrollTop = container.scrollHeight;
  }

  async connectToChat() {
    this.isLoading = true;
    this.chatId = await this.getChatId(this.currUserId!, this.otherUserId);
    this.loadChat();
    this.socket = new SockJS(this.BACKEND_API + '/chat', {
      headers: this.headers,
    });
    this.stompClient = Stomp.over(this.socket);

    this.stompClient.connect({}, (frame) => {
      this.stompClient!.subscribe(
        '/topic/messages/' + this.chatId,
        (response) => {
          this.loadChat();
        }
      );
    });
    this.isLoading = false;
  }

  async getChatId(currUserId: string, otherUserId: string) {
    try {
      const res = await this.chatSvc.getChatId(currUserId, otherUserId);
      return res['chatId'];
    } catch (error: any) {
      if (error.status === 404) {
        console.log('Chat ID not found');
      } else {
        console.error('An error occurred:', error);
      }
    }
  }

  sendMessage() {
    if (this.newMessage.value !== '') {
      this.stompClient!.send(
        '/app/chat/' + this.chatId,
        {},
        JSON.stringify({
          senderId: this.currUserId,
          timeStamp: 'to be defined in server',
          content: this.newMessage.value,
        })
      );
      this.newMessage.setValue('');
    }
  }

  async getUserImage(userId: string) {
    const res = await this.infoRtrvSvc.getAllImages(userId);
    const images = res.images;
    console.log(images);

    if (images.length > 0) {
      return this.IMAGE_URL + images[0].url;
    } else {
      return null;
    }
  }

  async getUserCompleteProfile(userId: string) {
    const res = await this.infoRtrvSvc.getCompleteProfile(userId);
    console.log(res);
    const profile = this.populateProfile(res);
    return profile;
  }

  populateProfile(res: any) {
    const profile: any = {};
    profile.name = res['Profile'].name;
    profile.gender = res['Profile'].gender;
    profile.interests = res['Interests'];
    profile.subInterests = res['SubInterests'];

    return profile;
  }

  formatTimeStamp(timeStamp: string) {
    const endDate = timeStamp.indexOf('-');
    return (
      timeStamp.substring(0, endDate) + ' ' + timeStamp.substring(endDate + 1)
    );
  }

  async loadChat() {
    this.isLoading = true;

    this.messages = this.chatSvc.getMessages(this.chatId);
    this.currUserImage = await this.getUserImage(this.currUserId!);
    this.otherUserImage = await this.getUserImage(this.otherUserId);
    this.otherUserProfile = await this.getUserCompleteProfile(this.otherUserId);
    this.currUserProfile = await this.getUserCompleteProfile(this.currUserId!);

    // console.log(this.currUserImage, this.otherUserImage);
    // console.log(this.otherUserProfile, this.currUserProfile.images);

    this.messages.subscribe((data) => {
      let msgs: Array<Message> = data;
      msgs.sort((a, b) => (a.messageId > b.messageId ? 1 : -1));
      this.messages = of(msgs);
      this.scrollDown();
    });
  }

  navigateToProfile() {
    this.userSvc.setOtherUserId(this.otherUserId);
    this.router.navigate(['/viewprofiles']);
  }

  sampledata = [
    { promptId: 1, prompt: 'Give me a pick up line' },
    { promptId: 2, prompt: 'Give me a joke' },
    { promptId: 2, prompt: 'Say something random...' },
  ];

  openAiDialog() {
    const dialogRef = this.dialog.open(AioptionsComponent, {
      width: '250px',
      data: this.sampledata,
    });

    dialogRef.afterClosed().subscribe(async (selected) => {
      if (selected) {
        this.getAiResponse(this.otherUserId, selected);
      } 
    });
  }

  getAiResponse(otherUserId: string, prompt: string) {
    this.isGPTLoading = true;
    this.aiSvc.generateAIResponse(otherUserId, prompt).then((res) => {
      const answer = res['aiResponse'].trim();
      this.newMessage.setValue(answer);
      this.isGPTLoading = false;
    });
  }
}
