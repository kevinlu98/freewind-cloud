package cn.kevinlu98.cloud.freewindcloud.service;

import cn.kevinlu98.cloud.freewindcloud.common.Passwd;
import cn.kevinlu98.cloud.freewindcloud.common.SecurityUser;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Role;
import cn.kevinlu98.cloud.freewindcloud.mapper.UserMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 17:48
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Service
public class FwUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        Role role = Role.find(user.getRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return new SecurityUser(username, user.getPassword(), authorities);
    }
}
