package aydin.firebasedemo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;

public class UserAuth {

    public static void register(String email, String password) throws Exception {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setEmailVerified(false)
                .setDisabled(false);

        UserRecord user = FirebaseAuth.getInstance().createUser(request);

        // Save email + password to Firestore (assignment requirement)
        DocumentReference doc = DemoApp.fstore.collection("AuthUsers").document(user.getUid());
        doc.set(new AuthUser(email, password)).get(); // wait so UI can show success
    }

    public static boolean signIn(String email, String password) throws Exception {
        // Look up by email to get UID
        UserRecord user = FirebaseAuth.getInstance().getUserByEmail(email);

        // Fetch stored credentials and compare
        DocumentSnapshot snap = DemoApp.fstore.collection("AuthUsers")
                .document(user.getUid())
                .get().get();

        if (!snap.exists()) return false;

        String savedEmail = snap.getString("email");
        String savedPassword = snap.getString("password");

        return email.equals(savedEmail) && password.equals(savedPassword);
    }

    // simple POJO for Firestore
    static class AuthUser {
        public String email;
        public String password;
        public AuthUser() {}
        public AuthUser(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}