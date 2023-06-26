package com.plueone.server.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.plueone.server.models.Friendship;
import com.plueone.server.repo.FriendshipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendRepo;

    @Autowired
    private MatchingService matchSvc;

    public Optional<List<Friendship>> getAllFriendshipReq(String userId, String status) {

        final SqlRowSet rs = friendRepo.getAllFriendshipReq(userId, status);

        List<Friendship> friendList = new LinkedList<>();

        while (rs.next()) {
            Friendship f = new Friendship();
            f.setFriendshipId(rs.getInt("friendship_id"));
            f.setRequestorId(rs.getString("requestor_id"));
            f.setRequesteeId(rs.getString("requestee_id"));
            f.setStatus(rs.getString("status"));
            f.setInterestScore(matchSvc.getInterestMatchingScores(userId, f.getRequestorId()));
            f.setSubInterestScore(matchSvc.getSubInterestMatchingScores(userId, f.getRequestorId()));
            friendList.add(f);
        }
        if (friendList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(friendList);
        }
    }

    public int sendFriendReq(String requestorId, String requesteeId, String status) {
        return friendRepo.sendFriendReq(requestorId, requesteeId, status);
    }

    public int updateFriendReq(String requestorId, String requesteeId, String status) {
        return friendRepo.updateFriendShipStatus(requestorId, requesteeId, status);
    }
}
