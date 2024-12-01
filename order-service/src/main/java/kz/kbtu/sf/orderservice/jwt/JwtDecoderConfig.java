package kz.kbtu.sf.orderservice.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtDecoderConfig {
    private static final String SECRET = "+WfDP5OkARhQlcodvYk2qdY1EtaDyBb5CWssEZ4KBZfaiMcP7sy9kw4xgFujp2nklUXYyBv9VJgVmTgg1JIJ4aSM5VWCTym2kTdXbB4UnhmuzuklUyZeVxxEPqyBSa0TOB//5yU5NbA/bZZXh/nVlQKflDszDrlaaF+qrLHY57jK";

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HS512");
        return NimbusJwtDecoder.withSecretKey(keySpec).build();
    }
}
