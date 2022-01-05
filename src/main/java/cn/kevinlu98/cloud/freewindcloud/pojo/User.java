package cn.kevinlu98.cloud.freewindcloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 16:27
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Data
@Builder
@Entity
@Table(name = "fwc_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;

    private String avatar;

    private Integer role;

    private String email;
    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, columnDefinition = "0")
    private Long limitSize;
}
