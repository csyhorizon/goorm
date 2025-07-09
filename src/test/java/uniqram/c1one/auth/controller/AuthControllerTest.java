package uniqram.c1one.auth.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("ROLE_USER 사용자만 /api/user/test 접근 성공")
    @WithMockUser(username = "user01", roles = "USER")
    void userAccessUserEndpoint() throws Exception {
        mockMvc.perform(get("/api/user/test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("ROLE_USER 사용자 /api/admin/test 접근 시 403 Forbidden")
    @WithMockUser(username = "user01", roles = "USER")
    void userAccessAdminEndpoint_ShouldForbidden() throws Exception {
        mockMvc.perform(get("/api/admin/test"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("ROLE_ADMIN 사용자만 /api/admin/test 접근 성공")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminAccessAdminEndpoint() throws Exception {
        mockMvc.perform(get("/api/admin/test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("ROLE_ADMIN 사용자 /api/user/test 접근 시 403 Forbidden (권한 제한 시나리오)")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminAccessUserEndpoint_ShouldForbidden() throws Exception {
        mockMvc.perform(get("/api/user/test"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("인증되지 않은 사용자 /api/user/test 접근 시 리다이렉트")
    void anonymousAccessUserEndpoint_ShouldForbidden() throws Exception {
        mockMvc.perform(get("/api/user/test"))
                .andExpect(status().is3xxRedirection());
    }
}