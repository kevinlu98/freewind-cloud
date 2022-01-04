package cn.kevinlu98.cloud.freewindcloud.mapper;

import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 16:42
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
public interface UserMapper extends JpaRepository<User, Long> {

    Integer countByRole(Integer role);

    User findUserByUsername(String username);

}
