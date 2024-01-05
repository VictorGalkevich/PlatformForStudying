package by.itstep.application.login;

import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    public ApiResponse<String> login(LoginRequest loginRequest){
        return null;
    }
}
