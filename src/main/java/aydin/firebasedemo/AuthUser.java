package aydin.firebasedemo;

// must be public and in its own file
public class AuthUser {
    public String email;
    public String password;

    public AuthUser() {}  // empty constructor required by Firestore

    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
}