import com.spz.personal.entity.User;
import com.spz.personal.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
