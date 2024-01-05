package by.itstep.application.registration;


import by.itstep.application.repository.UserRepository;
import by.itstep.application.service.user.UserService;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final UserRepository userRepository;
    private final UserService userService;

    public ApiResponse<String> register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        var response = new ApiResponse<String>();

        if (!isValidEmail) {
            response = ApiResponse.error("Email not valid");
        } else if (userRepository.existsByUsername(request.getUsername())) {
            response = ApiResponse.error("Username already exists");
        } else if (userRepository.existsByEmail(request.getEmail())) {
            response = ApiResponse.error("Email already exists");
        } else {
            userService.singUpTeacherOrStudent(request);
            response = ApiResponse.success("Registration successful");
        }

        return response;
    }

}
