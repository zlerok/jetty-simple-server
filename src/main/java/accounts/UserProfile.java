package accounts;

import java.io.Serializable;

public class UserProfile implements Serializable{
    private final String login;
    private final char[] password;

    public UserProfile(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }

}
