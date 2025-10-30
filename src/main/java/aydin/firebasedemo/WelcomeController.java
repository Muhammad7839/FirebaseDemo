package aydin.firebasedemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class WelcomeController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void onRegisterClicked() {
        String email = safe(emailField.getText());
        String pass  = safe(passwordField.getText());
        try {
            UserAuth.register(email, pass);
            messageLabel.setText("Registered successfully.");
        } catch (Exception e) {
            messageLabel.setText(shortMsg(e));
        }
    }

    @FXML
    private void onSignInClicked() {
        String email = safe(emailField.getText());
        String pass  = safe(passwordField.getText());
        try {
            boolean ok = UserAuth.signIn(email, pass);
            if (ok) {
                messageLabel.setText("Sign in successful.");
                DemoApp.setRoot("primary");
            } else {
                messageLabel.setText("Invalid credentials.");
            }
        } catch (Exception e) {
            messageLabel.setText(shortMsg(e));
        }
    }

    private String safe(String s) { return s == null ? "" : s.trim(); }
    private String shortMsg(Exception e) {
        String m = e.getMessage();
        return (m == null || m.isBlank()) ? "Error" : m;
    }
}