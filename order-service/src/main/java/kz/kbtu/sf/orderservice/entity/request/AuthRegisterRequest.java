package kz.kbtu.sf.orderservice.entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRegisterRequest {
    private String email;
    private String password;
}
