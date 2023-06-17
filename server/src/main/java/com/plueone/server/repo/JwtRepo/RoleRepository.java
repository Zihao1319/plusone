package com.plueone.server.repo.JwtRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.plueone.server.models.JwtAuth.Role;
import com.plueone.server.models.JwtAuth.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ROLE_SQL = "select * from roles where role_name = ?";

    private static final String INSERT_USER_ROLES_SQL = "insert into userRole (user_id, role_id) values (?, ?)";

    public Optional<Role> findByName(String name) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ROLE_SQL, name);

        if (rs.first()) {
            return Optional.of(Role.populate(rs));
        }

        return Optional.empty();
    }

    public int getRoleIdByRoleName(String roleName) {
        String sql = "SELECT role_id FROM role WHERE role_name = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, roleName);
    }

    public void insertUserRoles(User user, String userId) {

        List<Role> roles = user.getRoles();

        List<Object[]> params = roles.stream()
                .map(role -> new Object[] { userId, getRoleIdByRoleName(role.getRoleName()) })
                .collect(Collectors.toList());

        int[] added = jdbcTemplate.batchUpdate(INSERT_USER_ROLES_SQL, params);

        System.out.printf(">>>>>insertUserInterest rows updated: %d\n", added.length);
    }

}
