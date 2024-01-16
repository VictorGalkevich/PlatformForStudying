package by.itstep.application.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String showTestPage() {
        return "test";
    }

    @GetMapping("/tests")
    public String showTestsPage() {
        return "tests";
    }
}
