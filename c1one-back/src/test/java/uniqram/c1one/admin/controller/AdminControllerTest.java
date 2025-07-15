package uniqram.c1one.admin.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uniqram.c1one.auth.dto.SigninRequest;
import uniqram.c1one.auth.dto.SignupRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private String adminUsername;
    private Cookie accessTokenCookie;
    private Long user1Id;
    private int totalUserCount;

    @BeforeEach
    void setup() throws Exception {
        adminUsername = "admin_test_" + System.currentTimeMillis();
        SignupRequest adminSignup = new SignupRequest(adminUsername, "admin123", "admin123");
        mockMvc.perform(post("/api/admin/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminSignup)))
                .andExpect(status().isCreated());

        List<SignupRequest> users = List.of(
                new SignupRequest("testuser1", "pass123", "pass123"),
                new SignupRequest("testuser2", "pass456", "pass456")
        );
        for (SignupRequest req : users) {
            mockMvc.perform(post("/api/auth/join")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isCreated());
        }

        SigninRequest loginRequest = new SigninRequest(adminUsername, "admin123");
        MvcResult loginResult = mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String token = objectMapper.readTree(loginResult.getResponse().getContentAsString())
                .get("accessToken").asText();
        accessTokenCookie = new Cookie("access_token", token);

        MvcResult allUsersResult = mockMvc.perform(get("/api/admin/users")
                        .cookie(accessTokenCookie))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode usersArray = objectMapper.readTree(allUsersResult.getResponse().getContentAsString());
        totalUserCount = usersArray.size();

        for (JsonNode user : usersArray) {
            if ("testuser1".equals(user.get("username").asText())) {
                user1Id = user.get("id").asLong();
                break;
            }
        }

        assertThat(user1Id).isNotNull();
    }

    @Test
    @DisplayName("전체 사용자 조회 성공")
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/admin/users")
                        .cookie(accessTokenCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(totalUserCount));
    }

    @Test
    @DisplayName("블랙리스트 등록 성공")
    void blacklistUser() throws Exception {
        mockMvc.perform(post("/api/admin/users/blacklist/" + user1Id)
                        .cookie(accessTokenCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.blacklisted").value(true));
    }

    @Test
    @DisplayName("블랙리스트 해제 성공")
    void unblacklistUser() throws Exception {
        mockMvc.perform(post("/api/admin/users/blacklist/" + user1Id)
                        .cookie(accessTokenCookie))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/admin/users/unblacklist/" + user1Id)
                        .cookie(accessTokenCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.blacklisted").value(false));
    }

    @Test
    @DisplayName("온라인 사용자 조회 성공")
    void getOnlineUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/admin/users/online")
                        .cookie(accessTokenCookie))
                .andExpect(status().isOk())
                .andReturn();

        int onlineCount = objectMapper.readTree(result.getResponse().getContentAsString()).size();
        assertThat(onlineCount).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("관리자 대시보드 통계 조회 성공")
    void getDashboardStats() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard")
                        .cookie(accessTokenCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userCount").value(totalUserCount));
    }
}
