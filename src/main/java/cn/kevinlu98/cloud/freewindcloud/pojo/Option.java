package cn.kevinlu98.cloud.freewindcloud.pojo;

import cn.kevinlu98.cloud.freewindcloud.common.enums.Site;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-04 20:49
 * Email: kevinlu98@qq.com
 * Description:
 */
@Data
@Builder
@Entity
@Table(name = "fwc_option")
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    @Id
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 2048)
    private String value;

    public Option(Site site) {
        this.id = site.getId();
        this.name = site.getName();
        this.value = site.getValue();
    }
}
