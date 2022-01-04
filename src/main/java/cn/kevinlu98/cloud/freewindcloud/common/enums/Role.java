package cn.kevinlu98.cloud.freewindcloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 16:32
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Getter
@AllArgsConstructor
public enum Role {

    ROLE_ADMIN(1, "admin", "管理员"),
    ROLE_USER(2, "user", "普通用户"),

    ;
    private final Integer value;
    private final String name;
    private final String desc;

    public static Role find(Integer value) {
        List<Role> roles = Arrays.stream(Role.values()).filter(x -> x.value.equals(value)).collect(Collectors.toList());
        return roles.size() > 0 ? roles.get(0) : Role.ROLE_ADMIN;
    }
}
