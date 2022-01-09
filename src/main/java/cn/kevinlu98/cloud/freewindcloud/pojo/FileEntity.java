package cn.kevinlu98.cloud.freewindcloud.pojo;

import cn.kevinlu98.cloud.freewindcloud.common.enums.FileType;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Date;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 22:36
 * Email: kevinlu98@qq.com
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    private String name;
    private String absPath;
    private boolean dir;
    private String size;
    private FileType type;
    private Date update;

    public static FileEntity fromFile(File file, String home) {
        FileEntity entity = new FileEntity();
        entity.setName(file.getName());
        entity.setSize(Size.BTrim.convert(FileUtils.sizeOf(file)));
        entity.setDir(file.isDirectory());
        entity.setAbsPath(file.getAbsolutePath().replaceFirst(home, ""));
        entity.setType(FileType.typeByFile(file));
        entity.setUpdate(new Date(file.lastModified()));
        return entity;
    }
}
