package com.plueone.server.service;

import java.io.IOException;
import java.util.List;

import com.plueone.server.exception.ResourceNotFoundException;
import com.plueone.server.models.Answer;
import com.plueone.server.models.Image;
import com.plueone.server.models.Interest;
import com.plueone.server.models.Language;
import com.plueone.server.models.Personality;
import com.plueone.server.models.Preference;
import com.plueone.server.models.Profile;
import com.plueone.server.models.Subinterest;
import com.plueone.server.repo.ImageRepository;
import com.plueone.server.repo.UserInfoInsertion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InfoInsertionService {

    @Autowired
    private UserInfoInsertion userInfoIntRepo;

    @Autowired
    private ImageRepository imgRepo;

    private static final String IMAGE_URL = "https://ozh2923.sgp1.digitaloceanspaces.com/";

    // public String createOrUpdateUser(String name, String phoneNum, String email) {
    //     return userInfoIntRepo.createOrUpdateUser(name, phoneNum, email);
    // }

    public int createOrUpdateProfile(String userId, Profile profile) {
        return userInfoIntRepo.createOrUpdateProfile(userId, profile);
    }

    public int createOrUpdatePreference(String userId, Preference preference) {
        return userInfoIntRepo.createOrUpdatePreference(userId, preference);
    }

    // can be list
    public String insertImages(MultipartFile file, String userId) {

        try {
            Image image = new Image();

            String url = imgRepo.upload(file, userId);
            image.setUrl(url);
            image.setUserId(userId);
            
            userInfoIntRepo.insertImages(image);

            return url;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new ResourceNotFoundException("Image S3 upload failed");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new ResourceNotFoundException("Image url insertion failed");
        }

    }

    public int[] insertUserInterest(String userId, List<Interest> interests) {
        return userInfoIntRepo.insertUserInterest(userId, interests);
    }

    public int[] insertUserSubInterest(String userId, List<Subinterest> subInterests) {
        return userInfoIntRepo.insertUserSubInterest(userId, subInterests);
    }

    public int[] insertUserPersonality(String userId, List<Personality> personalities) {
        return userInfoIntRepo.insertUserPersonality(userId, personalities);
    }

    public int[] insertUserAnswers(String userId, List<Answer> answers) {
        return userInfoIntRepo.insertUserAnswers(userId, answers);
    }

    public int[] insertUserLanguages(String userId, List<Language> languages) {
        return userInfoIntRepo.insertUserLanguages(userId, languages);
    }

    public int editUserAnswer(Answer answer) {
        return userInfoIntRepo.editUserAnswer(answer);
    }

}
