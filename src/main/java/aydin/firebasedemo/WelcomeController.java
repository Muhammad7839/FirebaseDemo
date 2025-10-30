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
        String email = emailField.getText();
        String pass  = passwordField.getText();
        if (email.isBlank() || pass.isBlank()) {
            messageLabel.setText("Email and password required.");
            return;
        }
        try {
            UserAuth.register(email, pass);
            messageLabel.setText("Registered successfully.");
        } catch (Exception e) {
            messageLabel.setText("Register error: " + shortMsg(e));
        }
    }

    @FXML
    private void onSignInClicked() {
        String email = emailField.getText();
        String pass  = passwordField.getText();
        if (email.isBlank() || pass.isBlank()) {
            messageLabel.setText("Email and password required.");
            return;
        }
        try {
            boolean ok = UserAuth.signIn(email, pass);
            if (ok) {
                messageLabel.setText("Sign in successful.");
                DemoApp.setRoot("primary"); // go to data access screen
            } else {
                messageLabel.setText("Invalid credentials.");
            }
        } catch (Exception e) {
            messageLabel.setText("Sign in error: " + shortMsg(e));
        }
    }

    private String shortMsg(Exception e) {
        String m = e.getMessage();
        return m == null ? e.getClass().getSimpleName() : m.split("\\R", 2)[0];
    }
}