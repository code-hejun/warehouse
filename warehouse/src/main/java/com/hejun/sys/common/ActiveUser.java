package com.hejun.sys.common;

import com.hejun.sys.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * 活跃用户
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUser {

    private User user;

    private List<String> roles;

    private List<String> permissions;

}
