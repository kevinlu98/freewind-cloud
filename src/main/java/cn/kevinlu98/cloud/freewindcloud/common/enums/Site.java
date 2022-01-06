package cn.kevinlu98.cloud.freewindcloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-04 20:56
 * Email: kevinlu98@qq.com
 * Description:
 */
@Getter
@AllArgsConstructor
public enum Site {

    SITE_TITLE(1, "title", "Freewind-Driver"),
    SITE_DESC(2, "desc", "freewind出品的个人网盘，个人网盘就选freewind-driver"),
    SITE_KEYWORDS(3, "keywords", "freewind,driver,网盘,云盘,个人网盘,私人网盘"),
    SITE_ICON(4, "icon", "/static/images/favicon.ico"),
    SITE_LOGO_BLANK(5, "logo_blank", "/static/images/logo/logo-blank.png"),
    SITE_LOGO_WHITE(6, "logo_white", "/static/images/logo/logo-white.png"),
    SITE_AVATAR_SIZE(7, "avatar_size", "1MB"),
    SITE_DEFAULT_MAX_SIZE(8, "default_max_size", "2GB"),
    SITE_UPLOAD_LIMIT(9, "upload_limit", "100MB"),
    SITE_USER_DEFAULT_AVATAR(10, "user_default_avatar", "https://gitee.com/kevinlu98/imgbed/raw/master/20220104/LB7RDT37g2yn.png"),
    ;
    private final Integer id;
    private final String name;
    private final String value;


}
