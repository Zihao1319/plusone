import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
  ViewEncapsulation,
} from '@angular/core';
import { SwiperModule } from 'swiper/angular';
import SwiperCore, {
  Navigation,
  Pagination,
  Scrollbar,
  A11y,
  Thumbs,
} from 'swiper';
import { UserManagementService } from 'src/app/services/user.management.service';
import { InfoRetrievalService } from 'src/app/services/info.retrieval.service';
import { ActivatedRoute, Router } from '@angular/router';

SwiperCore.use([Navigation, Pagination, Scrollbar, A11y]);

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class ProfileComponent implements OnInit, OnChanges {
  private IMAGE_URL = 'https://ozh2923.sgp1.digitaloceanspaces.com/';

  isProfileMode: Boolean = false;
  viewOtherProfileMode: Boolean = false;
  imageURL = '';
  images: any[] = [];
  profile!: any;
  interests!: any[];
  interestScore!: number;
  subInterestScore!: number;
  subInterests!: any[];
  languages!: any[];
  personalities!: any[];
  answers!: any[];
  userId!: string | null;
  gender!: string;
  isLoading: boolean = false;

  @Input()
  profileInterestMatches!: any;

  @Input()
  potentialMatches!: any;

  @Input()
  data!: any;

  constructor(
    private userSvc: UserManagementService,
    private infoRtrvSvc: InfoRetrievalService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.url.subscribe((segments) => {
      if (segments.some((segment) => segment.path === 'viewprofiles')) {
        this.isProfileMode = true;
        console.log('viewothermode: true');

        this.userId = this.userSvc.getOtherUserId();
        this.generateProfile(this.userId);
        
      } else if (segments.some((segment) => segment.path === 'profile')) {
        this.isProfileMode = true;
        console.log('profilemode: true');

        this.userId = this.userSvc.getUserId();

        if (!this.userId) {
          this.userSvc.clearInfo();
          this.router.navigate(['/login']);
        }

        this.generateProfile(this.userId!);
      }
    });
    // console.log(this.data);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['data'].currentValue == undefined) {
      console.log('gotcha');
      this.reset();
      return;
    }

    if (changes['data']) {
      this.reset();
      // console.log('data changed in profile ts', this.data);

      const userId = this.data.userId;
      this.interestScore = this.data.interestScore;
      this.subInterestScore = this.data.subInterestScore;
      this.generateProfile(userId);
    }
  }

  generateProfile(userId: string) {
    this.isLoading = true;
    this.infoRtrvSvc.getCompleteProfile(userId).then((res) => {
      // console.log(res);
      this.populateProfile(res);
      this.isLoading = false;
    });
  }

  populateProfile(res: any) {
    if (res['Images'] !== '') {
      this.images = res['Images'].map((i: any) => {
        const imageUrl = this.IMAGE_URL + i.url;
        return { ...i, imageUrl };
      });
    }
    this.profile = res['Profile'];
    this.interests = res['Interests'];
    this.subInterests = res['SubInterests'];
    this.languages = res['Language'];
    this.personalities = res['Personalities'];
    this.answers = res['Answers'];
  }

  reset() {
    this.userId = '';
    this.images = [];
    this.profile = '';
    this.interests = [];
    this.interestScore = 0;
    this.subInterestScore = 0;
    this.subInterests = [];
    this.languages = [];
    this.personalities = [];
    this.answers = [];
  }

  navigateToMatches() {
    this.router.navigate(['/matches']);
  }
}
