import com.spz.common.JWTUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class JWTTest {
    @Test
    public void testJWTUtils(){
        Map<String,String> map =new HashMap<>();
        map.put("userId",String.valueOf(1));
        map.put("time","2024-08-22");
        map.put("day","2024-08-22");
        String token = JWTUtils.generateTokenByStringMap(map);
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lIjoiMjAyNC0wOC0yMiIsImV4cCI6MTcyNDI5NTY4MCwidXNlcklkIjoiMSIsImRheSI6IjIwMjQtMDgtMjIifQ.J2xcZJGYDi5Cr5hQQQuSprqIC8xQK8qQg5tUhvEYmTQ
        System.out.println(token);
        JWTUtils.verify(token);
//        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lIjoiMjAyNC0wOC0yMiIsImV4cCI6MTcyNDI5NTY4MCwidXNlcklkIjoiMSIsImRheSI6IjIwMjQtMDgtMjIifQ.J2xcZJGYDi5Cr5hQQQuSprqIC8xQK8qQg5tUhvEYmTQ";
        Map<String, String> mapByToken = JWTUtils.getStingMapByToken(token);
        System.out.println(mapByToken);
        mapByToken.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
//        System.out.println(mapByToken.);

    }

    @Test
    public void jwtIdInteger(){
        String token = JWTUtils.generateTokenById(1);
        System.out.println(token);
        System.out.println(JWTUtils.getIdByToken(token));
    }
}
