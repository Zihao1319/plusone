package com.plueone.server.service;

import com.plueone.server.exception.ResourceNotFoundException;
import com.plueone.server.repo.ImageRepository;
import com.plueone.server.repo.UserInfoDeletion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoDeletionService {

    @Autowired
    private ImageRepository imgRepo;

    @Autowired
    private UserInfoDeletion userInfoDelRepo;

    public int deleteUser(String userId) {
        return userInfoDelRepo.deleteUser(userId);
    }

    public int deleteProfile(String userId) {
        return userInfoDelRepo.deleteProfile(userId);
    }

    public int deletePreference(String userId) {
        return userInfoDelRepo.deletePreference(userId);
    }

    public int deleteImage(Integer imageId, String imageUrl) {

        try {
            imgRepo.delete(imageUrl);
            return userInfoDelRepo.deleteImage(imageId);

        } catch (Exception ex) {
            throw new ResourceNotFoundException("File doesn't exist");
        }

    }

    public int deleteInterest(String userId, Integer interestId) {
        return userInfoDelRepo.deleteInterest(userId, interestId);
    }

    public int deleteSubInterest(String userId, Integer subInterestId) {
        return userInfoDelRepo.deleteSubInterest(userId, subInterestId);
    }

    public int deletePersonality(String userId, Integer id) {
        return userInfoDelRepo.deletePersonality(userId, id);
    }

    public int deleteAnswer(String userId, Integer id) {
        return userInfoDelRepo.deleteAnswer(userId, id);
    }

    public int deleteAllAnswers(String userId) {
        return userInfoDelRepo.deleteAllAnswers(userId);
    }

    public int deleteLanguage(String userId, Integer id) {
        return userInfoDelRepo.deleteLanguage(userId, id);
    }

}
