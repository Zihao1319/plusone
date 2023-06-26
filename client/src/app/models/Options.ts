export interface Diet {
  dietId: number;
  dietName: string;
}

export interface Education {
  educationId: number;
  educationName: string;
}

export interface Horoscope {
  horoscopeId: number;
  horoscopeName: string;
}

export interface Interest {
  interestId: number;
  interestName: string;
}

export interface Subinterest {
  subInterestId: number;
  subInterestName: string;
  interestId: string;
}

export interface Language {
  languageId: number;
  languageName: string;
}

export interface Personality {
  personalityId: number;
  personalityType: string;
}

export interface Prompt {
  promptId: number;
  promptQuestion: string;
}

export interface Race {
  raceId: number;
  raceName: string;
}

export interface OptionsList {
  diets: Diet[];
  education: Education[];
  horoscopes: Horoscope[];
  interests: Interest[];
  subinterests: Subinterest[];
  languages: Language[];
  personalities: Personality[];
  prompts: Prompt[];
  races: Race[];
}
