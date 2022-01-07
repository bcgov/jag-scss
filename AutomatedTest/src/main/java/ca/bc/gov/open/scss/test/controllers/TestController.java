package ca.bc.gov.open.scss.test.controllers;

import ca.bc.gov.open.scss.test.services.TestService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {
    private TestService testService;

    @Autowired
    public TestController(TestService testService) throws IOException {

        this.testService = testService;
        this.testService.setAuthentication();
    }

    @GetMapping("/all")
    public String runAllTests() throws Exception {
        testService.runAllTests();
        return "TestComplete";
    }

    @GetMapping("/ping")
    public String ping() {
        return "ping";
    }
}
