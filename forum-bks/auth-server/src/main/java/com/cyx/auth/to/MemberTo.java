package com.cyx.auth.to;

import lombok.*;

//登录传输对象
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Setter
@Getter
public class MemberTo {
    String username;
    String password;
    String code;
}
