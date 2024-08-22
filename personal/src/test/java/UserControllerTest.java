
import java.util.*;
import java.math.*;

import com.spz.common.Res;
import com.spz.personal.controller.UserController;
import com.spz.personal.entity.User;
import com.spz.personal.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Test
    public void testInsertUserByRegister() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword("password");
        user.setPhone("1234567890");
        user.setGender(Short.valueOf("1"));
        user.setImage("1.png");
    }

    @Test
    public void testCreateUserByWxLogin() throws Exception {
        User user = new User();
        user.setOpenId("test12345678");
//        userService
    }
}
