package com.jyw.portal.dto;

import com.jyw.portal.domain.User;
import lombok.Data;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-15
 */
@Data
public class LoginDTO {
    User user;
    String token;
}
