package auth;

public interface AuthService {

    boolean authUser(String username, String password) throws ClassNotFoundException;
    boolean renameUser(String username, String newName, String password)throws ClassNotFoundException;
}
