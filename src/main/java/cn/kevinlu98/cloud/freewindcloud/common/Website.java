package cn.kevinlu98.cloud.freewindcloud.common;

import cn.kevinlu98.cloud.freewindcloud.common.enums.DriverType;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Site;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Size;
import cn.kevinlu98.cloud.freewindcloud.exception.NoDriverTypeException;
import cn.kevinlu98.cloud.freewindcloud.mapper.OptionMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.Driver;
import cn.kevinlu98.cloud.freewindcloud.pojo.Option;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import cn.kevinlu98.cloud.freewindcloud.service.CloudService;
import cn.kevinlu98.cloud.freewindcloud.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-04 20:53
 * Email: kevinlu98@qq.com
 * Description:
 */
@Slf4j
@Component
@CacheConfig(cacheNames = {"site_config"})
public class Website {

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    private DriverService driverService;


    @Cacheable(key = "targetClass + methodName")
    public String title() {
        return getValue(Site.SITE_TITLE);
    }

    @Cacheable(key = "targetClass + methodName")
    public String desc() {
        return getValue(Site.SITE_DESC);
    }

    @Cacheable(key = "targetClass + methodName")
    public String keywords() {
        return getValue(Site.SITE_KEYWORDS);
    }

    @Cacheable(key = "targetClass + methodName")
    public String icon() {
        return getValue(Site.SITE_ICON);
    }

    @Cacheable(key = "targetClass + methodName")
    public String logoWhite() {
        return getValue(Site.SITE_LOGO_WHITE);
    }

    @Cacheable(key = "targetClass + methodName")
    public String logoBlank() {
        return getValue(Site.SITE_LOGO_BLANK);
    }

    @Cacheable(key = "targetClass + methodName")
    public String avatarSize() {
        return getValue(Site.SITE_AVATAR_SIZE);
    }

    @Cacheable(key = "targetClass + methodName")
    public String defaultMaxSize() {
        return getValue(Site.SITE_DEFAULT_MAX_SIZE);
    }

    @Cacheable(key = "targetClass + methodName")
    public String uploadLimit() {
        return getValue(Site.SITE_UPLOAD_LIMIT);
    }

    @Cacheable(key = "targetClass + methodName")
    public String userDefaultAvatar() {
        return getValue(Site.SITE_USER_DEFAULT_AVATAR);
    }

    public User loginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((SecurityUser) authentication.getPrincipal()).getLoginUser();
    }

    public String usedSize() throws NoDriverTypeException {
        Driver driver = driverService.activeDriver();
        CloudService cloudService = CloudService.getInstance(DriverType.find(driver.getType()));
        return cloudService.usedSize(driver.getPath(), loginUser());
    }

    public int percent() throws NoDriverTypeException {
        float u = Size.convertBase(usedSize());
        float t = Size.convertBase(loginUser().getSize());
        return ((int) (u * 100 / t));
    }


    public String freeSize() throws NoDriverTypeException {
        float u = Size.convertBase(usedSize());
        float t = Size.convertBase(loginUser().getSize());
        return Size.BTrim.convert(t-u);
    }

    public String getValue(Site site) {
        Optional<Option> option = optionMapper.findById(site.getId());
        Option siteOpt = option.orElse(null);
        if (siteOpt == null) {
            siteOpt = new Option(site);
            optionMapper.save(siteOpt);
        }
        return siteOpt.getValue();
    }
}
