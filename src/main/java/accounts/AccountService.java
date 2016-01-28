package accounts;


import database.DBException;
import database.DBService;
import database.dataset.UsersDataSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountService {
    private final Map<String, UserProfile> sessionIdToProfile = new HashMap<>();
    private final DBService dbService;

    public AccountService(DBService dbService) {
        this.dbService = dbService;
    }

    public void addNewUser(UserProfile userProfile) throws DBException {
        dbService.addUser(userProfile);
    }

    public Optional<UserProfile> getUserByLogin(String login) throws DBException {
        return dbService.getUser(login).map(UsersDataSet::toUserProfile);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
