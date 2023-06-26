package com.plueone.server.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.plueone.server.models.Friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FriendshipRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_FRIENDSHIP_REQ = "select * from friendship where requestee_id = ? and status = ?";

    private static final String INSERT_FRIENDSHIP_REQ = "insert into friendship (requestor_id, requestee_id, status) values (?,?,?)";

    private static final String UPDATE_FRIENDSHIP_STATUS = "update friendship set status = ? where requestor_id = ? and requestee_id = ?";

    // public List<Friendship> getAllFriendshipReq(String userId, String status) {

    //     final List<Friendship> friendList = new ArrayList<Friendship>();

    //     final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_FRIENDSHIP_REQ, userId, status);

    //     while (rs.next()) {
    //         Friendship f = new Friendship();
    //         f.setFriendshipId(rs.getInt("friendship_id"));
    //         f.setRequestorId(rs.getString("requestor_id"));
    //         f.setRequesteeId(rs.getString("requestee_id"));
    //         f.setStatus(rs.getString("status"));
    //         friendList.add(f);
    //     }
    //     return Collections.unmodifiableList(friendList);
    // }

    public SqlRowSet getAllFriendshipReq(String userId, String status) {

        return jdbcTemplate.queryForRowSet(GET_ALL_FRIENDSHIP_REQ, userId, status);

    }

    public int sendFriendReq(String requestorId, String requesteeId, String status) {

        int rowUpdated = jdbcTemplate.update(INSERT_FRIENDSHIP_REQ, requestorId, requesteeId, status);

        System.out.printf(">>>>>friendship rows inserted: %d\n", rowUpdated);

        return rowUpdated;
    }

    public int updateFriendShipStatus(String requestorId, String requesteeId, String status) {
        int rowUpdated = jdbcTemplate.update(UPDATE_FRIENDSHIP_STATUS, status, requestorId, requesteeId);

        System.out.printf(">>>>>friendship rows updated: %d\n", rowUpdated);

        return rowUpdated;
    }

}
