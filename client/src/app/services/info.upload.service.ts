import { HttpClient, HttpParams } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { InfoDeleteService } from './info.delete.service';
import { InfoRetrievalService } from './info.retrieval.service';
import { UserManagementService } from './user.management.service';

@Injectable({
  providedIn: 'root',
})
export class InfoUploadService {
  private BACKEND_API: string =
    'https://rebel-lip-production.up.railway.app/api';
  // private BACKEND_API: string = 'http://localhost:8080/api';

  private IMAGE_POST_API = this.BACKEND_API + '/image';

  constructor(
    private httpClient: HttpClient,
    private infoRtrvSvc: InfoRetrievalService,
    private infoDelSvc: InfoDeleteService,
    private userSvc: UserManagementService
  ) {}

  headers = this.userSvc.setTokenRequest();

  uploadImage(imageFileElem: ElementRef, userId: String) {
    // console.log(userId);
    const formData = new FormData();
    const imageFile = imageFileElem.nativeElement.files[0];
    formData.append('file', imageFile);

    return lastValueFrom(
      this.httpClient.post<any>(this.IMAGE_POST_API + '/' + userId, formData, {
        headers: this.headers,
      })
    );
  }

  uploadProfileData(userId: string, profileData: any) {
    const PROFILE_POST_API = this.BACKEND_API + '/profile/' + userId;

    const excludedKey = 'languageId';
    const updatedProfileData = { ...profileData };
    delete updatedProfileData[excludedKey];

    return lastValueFrom(
      this.httpClient.post<any>(PROFILE_POST_API, profileData, {
        headers: this.headers,
      })
    );
  }

  async performLanguageOps(userId: string, profileData: any) {
    const newLang = profileData.languageId.map((l: any) => {
      const lang = { languageId: l };
      return lang;
    });

    const res = await this.infoRtrvSvc.getUserLanguage(userId);
    const langInDb = res.languages.map((l: any) => {
      const lang = { languageId: l.languageId };
      return lang;
    });

    console.log(langInDb);
    console.log(newLang);

    const added = newLang.filter(
      (obj1: { languageId: any }) =>
        !langInDb.some(
          (obj2: { languageId: any }) => obj2.languageId === obj1.languageId
        )
    );

    // console.log(added);

    if (Object.keys(added).length !== 0) {
      this.uploadLanguageData(userId, added);
    }

    const deleted = langInDb.filter(
      (obj1: { languageId: any }) =>
        !newLang.some(
          (obj2: { languageId: any }) => obj2.languageId === obj1.languageId
        )
    );
    // console.log(deleted);

    deleted.forEach((d: any) => {
      this.infoDelSvc.deleteLanguage(userId, d.languageId);
    });
  }

  uploadLanguageData(userId: string, languages: any) {
    const LANGUAGE_POST_API = this.BACKEND_API + '/language/' + userId;

    return lastValueFrom(
      this.httpClient.post<any>(LANGUAGE_POST_API, languages, {
        headers: this.headers,
      })
    );
  }

  registerLanguageData(userId: string, profileData: any) {
    const newLang = profileData.languageId.map((l: any) => {
      const lang = { languageId: l };
      return lang;
    });

    const LANGUAGE_POST_API = this.BACKEND_API + '/language/' + userId;

    return lastValueFrom(
      this.httpClient.post<any>(LANGUAGE_POST_API, newLang, {
        headers: this.headers,
      })
    );
  }

  async performPersonalityOps(userId: string, interestPersonalityData: any) {
    const personalityIds = interestPersonalityData.personalityId;
    console.log(personalityIds);
    const personalities = personalityIds.map((d: any) => {
      const personalityId = { personalityId: d };
      return personalityId;
    });

    const newPersonalities = personalityIds.map((p: any) => {
      const id = { personalityId: p };
      return id;
    });

    const res = await this.infoRtrvSvc.getUserPersonalities(userId);
    const personalitiesInDb = res.personalities.map((p: any) => {
      const personality = { personalityId: p.personalityId };
      return personality;
    });

    console.log(personalitiesInDb);
    console.log(newPersonalities);

    const added = newPersonalities.filter(
      (obj1: { personalityId: any }) =>
        !personalitiesInDb.some(
          (obj2: { personalityId: any }) =>
            obj2.personalityId === obj1.personalityId
        )
    );

    // console.log('added', added);

    if (Object.keys(added).length !== 0) {
      this.uploadPersonalityInfo(userId, added);
    }

    const deleted = personalitiesInDb.filter(
      (obj1: { personalityId: any }) =>
        !newPersonalities.some(
          (obj2: { personalityId: any }) =>
            obj2.personalityId === obj1.personalityId
        )
    );
    // console.log('deleted', deleted);

    deleted.forEach((d: any) => {
      this.infoDelSvc.deletePersonality(userId, d.personalityId);
    });
  }

  uploadPersonalityInfo(userId: string, personalityIds: any) {
    const PERSONALITY_POST_API = this.BACKEND_API + '/personality/' + userId;

    return lastValueFrom(
      this.httpClient.post<any>(PERSONALITY_POST_API, personalityIds, {
        headers: this.headers,
      })
    );
  }

  registerPersonalityInfo(userId: string, interestPersonalityData: any) {
    const personalityIds = interestPersonalityData.personalityId;
    const personalities = personalityIds.map((d: any) => {
      const personalityId = { personalityId: d };
      return personalityId;
    });

    const PERSONALITY_POST_API = this.BACKEND_API + '/personality/' + userId;

    return lastValueFrom(
      this.httpClient.post<any>(PERSONALITY_POST_API, personalities, {
        headers: this.headers,
      })
    );
  }

  async performInterestOps(userId: string, interestPersonalityData: any) {
    const interestIds = interestPersonalityData.interestId;
    const interests = interestIds.map((l: any) => {
      const interestId = { interestId: l };
      return interestId;
    });

    const newInterests = interestIds.map((l: any) => {
      const id = { interestId: l };
      return id;
    });

    const res = await this.infoRtrvSvc.getUserInterests(userId);
    const interestsInDb = res.interests.map((l: any) => {
      const interest = { interestId: l.interestId };
      return interest;
    });

    console.log(interestsInDb);
    console.log(newInterests);

    const added = newInterests.filter(
      (obj1: { interestId: any }) =>
        !interestsInDb.some(
          (obj2: { interestId: any }) => obj2.interestId === obj1.interestId
        )
    );

    // console.log('added', added);

    if (Object.keys(added).length !== 0) {
      this.uploadInterestInfo(userId, added);
    }

    const deleted = interestsInDb.filter(
      (obj1: { interestId: any }) =>
        !newInterests.some(
          (obj2: { interestId: any }) => obj2.interestId === obj1.interestId
        )
    );

    // console.log('deleted', deleted);

    deleted.forEach((d: any) => {
      this.infoDelSvc.deleteInterest(userId, d.interestId);
    });
  }

  uploadInterestInfo(userId: string, interestIds: any) {
    const INTERESTS_POST_API = this.BACKEND_API + '/add/interests/' + userId;
    return lastValueFrom(
      this.httpClient.post<any>(INTERESTS_POST_API, interestIds, {
        headers: this.headers,
      })
    );
  }

  registerInterestInfo(userId: string, interestPersonalityData: any) {
    const interestIds = interestPersonalityData.interestId;
    const interests = interestIds.map((l: any) => {
      const interestId = { interestId: l };
      return interestId;
    });

    const INTERESTS_POST_API = this.BACKEND_API + '/add/interests/' + userId;
    return lastValueFrom(
      this.httpClient.post<any>(INTERESTS_POST_API, interests, {
        headers: this.headers,
      })
    );
  }

  async performSubInterestOps(userId: string, subInterestData: any) {
    const newSubInterests = subInterestData.subInterestId.map((d: any) => {
      const subInterestId = { subInterestId: d };
      return subInterestId;
    });

    const res = await this.infoRtrvSvc.getUserSubInterests(userId);
    const subInterestsInDb = res.subinterests.map((s: any) => {
      const subInterest = { subInterestId: s.subInterestId };
      return subInterest;
    });

    console.log(subInterestsInDb);
    console.log(newSubInterests);

    const added = newSubInterests.filter(
      (obj1: { subInterestId: any }) =>
        !subInterestsInDb.some(
          (obj2: { subInterestId: any }) =>
            obj2.subInterestId === obj1.subInterestId
        )
    );

    // console.log('added', added);
    if (Object.keys(added).length !== 0) {
      this.uploadSubInterestInfo(userId, added);
    }

    const deleted = subInterestsInDb.filter(
      (obj1: { subInterestId: any }) =>
        !newSubInterests.some(
          (obj2: { subInterestId: any }) =>
            obj2.subInterestId === obj1.subInterestId
        )
    );

    // console.log('deleted', deleted);
    deleted.forEach((d: any) => {
      this.infoDelSvc.deleteSubInterest(userId, d.subInterestId);
    });
  }

  uploadSubInterestInfo(userId: string, subInterests: any) {
    const SUB_INTERESTS_POST_API =
      this.BACKEND_API + '/add/subinterests/' + userId;

    return lastValueFrom(
      this.httpClient.post<any>(SUB_INTERESTS_POST_API, subInterests, {
        headers: this.headers,
      })
    );
  }

  registerSubInterestInfo(userId: string, subInterestData: any) {
    const newSubInterests = subInterestData.subInterestId.map((d: any) => {
      const subInterestId = { subInterestId: d };
      return subInterestId;
    });

    const SUB_INTERESTS_POST_API =
      this.BACKEND_API + '/add/subinterests/' + userId;

    return lastValueFrom(
      this.httpClient.post<any>(SUB_INTERESTS_POST_API, newSubInterests, {
        headers: this.headers,
      })
    );
  }

  uploadPreferenceData(userId: string, preferenceData: any) {
    // console.log(preferenceData);

    const PREFERENCE_POST_API = this.BACKEND_API + '/preference/' + userId;

    return lastValueFrom(
      this.httpClient.post<any>(PREFERENCE_POST_API, preferenceData, {
        headers: this.headers,
      })
    );
  }

  async performPromptDataOps(userId: string, promptData: any) {
    const array = Object.values(promptData);
    const newAnswers = array.map((p: any) => {
      const data = { promptId: p.promptId, answer: p.answer };
      return data;
    });

    this.infoDelSvc.deleteAllAnswers(userId);

    // console.log(newAnswers);

    this.uploadPromptData(userId, newAnswers);
  }

  uploadPromptData(userId: string, promptData: any) {
    const PROMPT_POST_API = this.BACKEND_API + '/prompt/' + userId;

    return lastValueFrom(
      this.httpClient.post<any>(PROMPT_POST_API, promptData, {
        headers: this.headers,
      })
    );
  }

  registerPromptData(userId: string, promptData: any) {
    const array = Object.values(promptData);
    const newAnswers = array.map((p: any) => {
      const data = { promptId: p.promptId, answer: p.answer };
      return data;
    });

    const PROMPT_POST_API = this.BACKEND_API + '/prompt/' + userId;

    return lastValueFrom(
      this.httpClient.post<any>(PROMPT_POST_API, newAnswers, {
        headers: this.headers,
      })
    );
  }

  sendFriendship(requestorId: string, requesteeId: string, status: string) {
    const FRIEND_REQ: string =
      this.BACKEND_API + '/requestfriend/' + requestorId;

    const params = new HttpParams()
      .set('id', requesteeId)
      .set('status', status);

    // console.log(params);
    return lastValueFrom(
      this.httpClient.post<any>(FRIEND_REQ, params, {
        headers: this.headers,
      })
    );
  }

  updateFrienship(requestorId: string, requesteeId: string, status: string) {
    const FRIEND_REQ: string =
      this.BACKEND_API + '/updatefriendship/' + requestorId;

    const params = new HttpParams()
      .set('id', requesteeId)
      .set('status', status);

    console.log(requestorId, status);

    return lastValueFrom(
      this.httpClient.post<any>(FRIEND_REQ, params, {
        headers: this.headers,
      })
    );
  }
}

//0 {promptId: 1, promptQuestion: 'If you could have any superpower, what would it be and why?', answer: 'hualala'}
//1: {promptId: 6, promptQuestion: "What's the most unusual or unique talent you have?", answer: 'chewing gums'}
