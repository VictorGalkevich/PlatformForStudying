package by.itstep.application.service.user;

import by.itstep.application.entity.Student;
import by.itstep.application.entity.Teacher;
import by.itstep.application.entity.User;
import by.itstep.application.entity.type.UserType;
import by.itstep.application.registration.RegistrationRequest;
import by.itstep.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public void signUpUser(User user) {
        boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();

        if (userExists) {
            throw new IllegalStateException("Username already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));

    }

    public void singUpTeacherOrStudent(RegistrationRequest request) {
        User newUser = setAttributeForUSer(request);
        signUpUser(newUser);
        if (request.getType() == UserType.STUDENT) {
            Student newStudent = new Student();
            newStudent.setUser(newUser);
            studentService.registerStudent(newStudent);
        } else {
            Teacher newTeacher = new Teacher();
            newTeacher.setUser(newUser);
            teacherService.registerTeacher(newTeacher);
        }
        userRepository.save(newUser);
    }

    private static User setAttributeForUSer(RegistrationRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setType(request.getType());
        newUser.setFirstname(request.getFirstname());
        newUser.setLastname(request.getLastname());
        newUser.setPassword(request.getPassword());
        newUser.setRole(request.getRole());
        return newUser;
    }
}