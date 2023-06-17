package com.plueone.server.repo.JwtRepo;

import java.util.Optional;
import java.util.UUID;

import com.plueone.server.models.JwtAuth.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_USER_SQL = "insert into user (user_id, name, email, password) values (?,?,?,?)";

    public static final String INSERT_USER_ROLES_SQL = "insert into roles (user_id)";

    private static final String GET_USER_SQL = "select * from user where name = ?";

    private static final String GET_USER_ROLE_SQL = "select u.user_id, u.name as name, u.email, u.phone, u.password, ur.role_id as role_id, r.role_name as role_name"
            + " from user u"
            + " join userRole ur on ur.user_id = u.user_id"
            + " join roles r on r.id = ur.role_id"
            + " where name = ?";

    private static final String CHECK_USER_EMAIL_EXISTS = "SELECT EXISTS(SELECT 1 FROM user WHERE email = ?) AS email_exists";

    private static final String CHECK_USER_NAME_EXISTS = "SELECT EXISTS(SELECT 1 FROM user WHERE name = ?) AS name_exists";

    public Optional<User> findByUserName(String name) {

        SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_USER_ROLE_SQL, name);

        if (rs.first()) {
            return Optional.of(User.populate(rs));
        }
        return Optional.empty();

    }

    public Boolean existsByEmail(String email) {
        int count = jdbcTemplate.queryForObject(CHECK_USER_EMAIL_EXISTS, (rs, rowNum) -> rs.getInt(1), email);

        return count > 0 ? true : false;
    }

    public Boolean existsByName(String name) {
        int count = jdbcTemplate.queryForObject(CHECK_USER_NAME_EXISTS, (rs, rowNum) -> rs.getInt(1), name);

        return count > 0 ? true : false;
    }

    public String createUser(User user) {

        String userId = UUID.randomUUID().toString().substring(0, 6);
        user.setUserId(userId);

        int rowUpdated = jdbcTemplate.update(INSERT_USER_SQL, user.getUserId(),
                user.getName(),
                user.getPassword(),
                user.getEmail());

        System.out.printf(">>>>>rows updated: %d\n", rowUpdated);

        return userId;
    }

    
}
