package com.cyx.auth.to;

import lombok.*;

//token传输对象
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Setter
@Getter
public class TokenTo {
    public long mId;
    public String access_token;
    public String refresh_token;
}
