package aydin.firebasedemo;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public class UserAuth {

    // Register: create Auth user and store email+password in Firestore (per assignment)
    public static void register(String emailRaw, String passwordRaw) throws Exception {
        String email = trim(emailRaw);
        String password = trim(passwordRaw);

        if (email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password required.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // If user already exists, fail fast
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
            throw new IllegalStateException("User already exists.");
        } catch (FirebaseAuthException notFoundOk) {
            // continue if not found
        }

        UserRecord created = null;
        try {
            UserRecord.CreateRequest req = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setEmailVerified(false)
                    .setDisabled(false);

            created = FirebaseAuth.getInstance().createUser(req);

            // Save to Firestore (assignment requirement; OK to be plain-text here)
            DocumentReference doc = DemoApp.fstore.collection("AuthUsers").document(created.getUid());
            doc.set(new AuthUser(email, password)).get(); // wait so UI can show success

        } catch (Exception e) {
            // If Firestore write failed after creating auth user, roll back the auth user
            if (created != null) {
                try { FirebaseAuth.getInstance().deleteUser(created.getUid()); } catch (Exception ignored) {}
            }
            throw shortEx(e);
        }
    }

    // Sign in: check stored credentials match
    public static boolean signIn(String emailRaw, String passwordRaw) throws Exception {
        String email = trim(emailRaw);
        String password = trim(passwordRaw);
        if (email.isEmpty() || password.isEmpty()) return false;

        UserRecord user = FirebaseAuth.getInstance().getUserByEmail(email); // throws if not found

        DocumentSnapshot snap = DemoApp.fstore
                .collection("AuthUsers")
                .document(user.getUid())
                .get().get();

        if (!snap.exists()) return false;

        String savedEmail = snap.getString("email");
        String savedPassword = snap.getString("password");
        return email.equals(savedEmail) && password.equals(savedPassword);
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private static Exception shortEx(Exception e) {
        String m = e.getMessage();
        return new Exception(m == null ? e.getClass().getSimpleName() : m.split("\\R", 2)[0]);
    }
}