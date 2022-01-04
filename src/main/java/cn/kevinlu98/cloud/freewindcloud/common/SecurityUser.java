package cn.kevinlu98.cloud.freewindcloud.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 17:57
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Getter
public class SecurityUser extends User {

    private cn.kevinlu98.cloud.freewindcloud.pojo.User loginUser;



    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities,cn.kevinlu98.cloud.freewindcloud.pojo.User loginUser) {
        super(username, password, authorities);
        this.loginUser = loginUser;
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }


}
