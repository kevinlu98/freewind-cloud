package cn.kevinlu98.cloud.freewindcloud.service;

import cn.kevinlu98.cloud.freewindcloud.common.Passwd;
import cn.kevinlu98.cloud.freewindcloud.common.PojoUtils;
import cn.kevinlu98.cloud.freewindcloud.common.Website;
import cn.kevinlu98.cloud.freewindcloud.mapper.UserMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private Website website;

    public void delete(Long id){
        userMapper.deleteById(id);
    }

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


    public Page<User> listUser(String keyword, int page, int size) {
        page = Math.max(page, 1);
        size = Math.max(size, 1);
        page -= 1;
        Pageable pageable = PageRequest.of(page, size);
        return userMapper.findAll((Specification<User>) (root, query, cb) -> {
            if (!StringUtils.isEmpty(keyword)) {
                return cb.or(cb.like(root.get("username"), "%" + keyword + "%"), cb.like(root.get("nickname"), "%" + keyword + "%"), cb.like(root.get("email"), "%" + keyword + "%"));
            } else {
                return cb.conjunction();
            }
        }, pageable);
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

    public void repwd(Long id) {
        if (id == null) return;
        Optional<User> userOpt = userMapper.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(Passwd.md5(user.getUsername()));
            userMapper.save(user);
        }
    }

    public void reAvatar(Long id) {
        if (id == null) return;
        Optional<User> userOpt = userMapper.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setAvatar(website.userDefaultAvatar());
            userMapper.save(user);
        }
    }

    public void reNickname(Long id) {
        if (id == null) return;
        Optional<User> userOpt = userMapper.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setNickname(user.getUsername());
            userMapper.save(user);
        }
    }
}
