package cn.kevinlu98.cloud.freewindcloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 17:13
 * Email: kevinlu98@qq.com
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fwc_driver")
public class Driver {

    public static final int DRIVER_ACTIVE = 1;
    public static final int DRIVER_DEACTIVE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer type;

    private String path;

    private Integer isDefalut;

    private String maxSize;
}
