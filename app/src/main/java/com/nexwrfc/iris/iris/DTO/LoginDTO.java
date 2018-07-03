package com.nexwrfc.iris.iris.DTO;

/**
 * Created by diego on 18/06/2018.
 */

public class LoginDTO {

    private String alias;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String alias, String password) {
        this.alias = alias;
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
