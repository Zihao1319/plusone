package com.plueone.server.models.JwtAuth;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Role {
    private Integer id;
    private String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String name) {
        this.roleName = name;
    }

    public static Role populate(SqlRowSet rs) {
        Role r = new Role();

        r.setRoleName(rs.getString("name"));
        r.setId(rs.getInt("id"));

        return r;
    }
}
