package uniqram.c1one.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {

   @Builder.Default
   private String tokenType = "Bearer";
   private String accessToken;
   private String refreshToken;
}
