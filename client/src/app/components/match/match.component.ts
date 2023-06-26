import { Component, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ChatService } from 'src/app/services/chat.service';
import { InfoUploadService } from 'src/app/services/info.upload.service';
import { MatchServiceService } from 'src/app/services/match.service.service';
import { UserManagementService } from 'src/app/services/user.management.service';

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css'],
})
export class MatchComponent implements OnInit {

  isProfileMode: Boolean = false;
  token!: string;
  userId!: string | null;
  interestScore!: number;
  subInterestScore!: number;
  index = 0;
  isLoading: boolean = false;

  potentialMatches: any[] = [];
  interestMatches: any[] = [];
  profileInterestMatches: any[] = [];
  profileMatches: any[] = [];
  displayArray: any[] = [];

  data!: any;

  constructor(
    private matchSvc: MatchServiceService,
    private userSvc: UserManagementService,
    private infoUploadSvc: InfoUploadService,
    private chatSvc: ChatService,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;
    this.userId = this.userSvc.getUserId();

    if (!this.userId) {
      this.userSvc.clearInfo();
      this.router.navigate(['/login']);
    }

    // this.userId = '1e940c';
    this.token = this.userSvc.token;
    this.interestScore = 0;
    this.subInterestScore = 0;

    await this.matchSvc.getMatches(this.userId!).then((res) => {
      if (res['potentialMatches'] !== '') {
        this.potentialMatches = res['potentialMatches'];
        this.potentialMatches = res['potentialMatches'].map((m: any) => {
          const userId = m['requestorId'];
          const interestScore = m['interestScore'];
          const subInterestScore = m['subInterestScore'];
          const status = m['status'];

          return {
            userId: userId,
            interestScore: interestScore,
            subInterestScore: subInterestScore,
            status: status,
          };
        });
        console.log('potential matches', this.potentialMatches.length);
      }

      if (res['interestMatches'] !== '') {
        this.interestMatches = res['interestMatches'].map((m: any) => {
          return {
            ...m,
            status: null,
          };
        });
        console.log('interestMatch', this.interestMatches.length);
      }

      if (res['profileInterestMatches'] !== '') {
        this.profileInterestMatches = res['profileInterestMatches'].map(
          (m: any) => {
            const userId = m['userId'];
            const interestScore = m['interestScore'];
            const subInterestScore = m['subInterestScore'];
            const status = null;

            return {
              userId: userId,
              interestScore: interestScore,
              subInterestScore: subInterestScore,
              status: status,
            };
          }
        );
        console.log(
          'profileinterest match',
          this.profileInterestMatches.length
        );
      }

      if (res['profileMatches'] !== '') {
        this.profileMatches = res['profileMatches'].map((m: any) => {
          const userId = m['userId'];
          const interestScore = m['interestScore'];
          const subInterestScore = m['subInterestScore'];
          const status = null;

          return {
            userId: userId,
            interestScore: interestScore,
            subInterestScore: subInterestScore,
            status: status,
          };
        });
        console.log('profilematch', this.profileMatches.length);
      }
      this.displayArray = this.potentialMatches.concat(
        this.profileInterestMatches
      );
      // console.log(this.displayArray);

      this.data = this.displayArray[this.index];
      this.updateFriendshipCount(this.displayArray.length);
      console.log(this.displayArray.length);
      this.isLoading = false;
    });

    // const combinedArr = [
    //   ...this.potentialMatches,
    //   ...this.profileInterestMatches,
    //   ...this.interestMatches,
    //   ...this.profileMatches,
    // ];
  }

  updateFriendshipCount(count: number) {
    this.matchSvc.updateReqCount(count);
  }

  next() {
    this.index++;
    this.data = this.displayArray[this.index];

    // to capture the current user
    // console.log('clicked in match', this.displayArray[this.index - 1]);
  }

  navigateToPreference() {
    this.router.navigate(['/step4']);
  }

  yes() {
    this.next();
    const currentUser = this.displayArray[this.index - 1];
    const requesteeId = currentUser.userId;
    // console.log(currentUser.status);

    if (currentUser.status === 'pending') {
      this.chatSvc
        .createChatId(this.userId!, requesteeId)
        .then((res) => {
          // console.log(res);
          const chatId = res.chatId;
        })
        .catch((error) => {
          console.log(error);
        });
      this.infoUploadSvc.updateFrienship(requesteeId, this.userId!, 'matched');
    } else {
      this.infoUploadSvc
        .sendFriendship(this.userId!, requesteeId, 'pending')
        .then((res) => {
          // console.log(res);
        });
    }
    this.updateFriendshipCount(this.displayArray.length);
  }

  no() {
    this.next();
    const currentUser = this.displayArray[this.index - 1];
    const requesteeId = currentUser.userId;
    console.log(currentUser.status);

    if (currentUser.status === 'pending') {
      this.infoUploadSvc.updateFrienship(this.userId!, requesteeId, 'maybe');
    } else {
      this.infoUploadSvc.sendFriendship(this.userId!, requesteeId, 'maybe');
    }
    this.updateFriendshipCount(this.displayArray.length);
  }

  maybe() {
    this.next();
  }
}
