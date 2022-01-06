package cn.kevinlu98.cloud.freewindcloud.service;

import cn.kevinlu98.cloud.freewindcloud.common.PojoUtils;
import cn.kevinlu98.cloud.freewindcloud.mapper.UserMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 16:44
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean existsEmail(String email) {
        return userMapper.countByEmail(email) > 0;
    }

    public boolean existsUsername(String username) {
        return userMapper.countByUsername(username) > 0;
    }

    public User find(long id) {
        if (id < 1) return null;
        Optional<User> user = userMapper.findById(id);
        return user.orElse(null);
    }


    public Page<User> listUser(int page, int size) {
        page = Math.max(page, 1);
        size = Math.max(size, 1);
        page -= 1;
        Pageable pageable = PageRequest.of(page, size);
        return userMapper.findAll(pageable);
    }

    public User save(User user) {
        if (user.getId() == null) {
            return userMapper.save(user);
        }
        Optional<User> dbUserOpt = userMapper.findById(user.getId());
        User dbUser = dbUserOpt.orElse(null);
        if (dbUser != null) {
            PojoUtils.beanCopyWithIngore(user, dbUser);
            return userMapper.save(dbUser);
        } else {
            return userMapper.save(user);
        }
    }
}
