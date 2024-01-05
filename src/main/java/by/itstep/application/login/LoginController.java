package by.itstep.application.login;

import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;

    @GetMapping()
    public ApiResponse<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            if (username.isEmpty() || password.isEmpty()) {
                return ApiResponse.error("userName or password is empty");
            }
            System.out.println(username + " " + password);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    password
            );
            Authentication authenticated = authenticationManager.authenticate(authentication);
            if (!authenticated.isAuthenticated()) {
                return ApiResponse.error("user not authenticated");
            }

            SecurityContextHolder.getContext().setAuthentication(authenticated);
            return ApiResponse.success("Login successful!");

        } catch (AuthenticationException e) {
            return ApiResponse.error("check username or password");
        }
    }
}
