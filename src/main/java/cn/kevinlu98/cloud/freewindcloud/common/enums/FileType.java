package cn.kevinlu98.cloud.freewindcloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 22:36
 * Email: kevinlu98@qq.com
 * Description:
 */
@Getter
@AllArgsConstructor
public enum FileType {
    FILE_TYPE_3GP(1, "3gp", "audio/3gp", "/static/images/filetype/3gp.png"),
    FILE_TYPE_7Z(2, "7z", "application/7z", "/static/images/filetype/7z.png"),
    FILE_TYPE_AAC(3, "aac", "audio/aac", "/static/images/filetype/aac.png"),
    FILE_TYPE_AEP(4, "aep", "multipart/form-data", "/static/images/filetype/aep.png"),
    FILE_TYPE_AEPX(5, "aepx", "multipart/form-data", "/static/images/filetype/aepx.png"),
    FILE_TYPE_AET(6, "aet", "multipart/form-data", "/static/images/filetype/aet.png"),
    FILE_TYPE_AEX(7, "aex", "multipart/form-data", "/static/images/filetype/aex.png"),
    FILE_TYPE_AI(8, "ai", "multipart/form-data", "/static/images/filetype/ai.png"),
    FILE_TYPE_ASP(9, "asp", "text/txt", "/static/images/filetype/asp.png"),
    FILE_TYPE_ASPX(10, "aspx", "text/txt", "/static/images/filetype/aspx.png"),
    FILE_TYPE_AVI(11, "avi", "video/avi", "/static/images/filetype/avi.png"),
    FILE_TYPE_BAK(12, "bak", "text/txt", "/static/images/filetype/bak.png"),
    FILE_TYPE_BAT(13, "bat", "text/txt", "/static/images/filetype/bat.png"),
    FILE_TYPE_BMP(14, "bmp", "application/x-bmp", "/static/images/filetype/bmp.png"),
    FILE_TYPE_C(15, "c", "text/txt", "/static/images/filetype/c.png"),
    FILE_TYPE_CMD(16, "cmd", "text/txt", "/static/images/filetype/cmd.png"),
    FILE_TYPE_CPP(17, "cpp", "text/txt", "/static/images/filetype/cpp.png"),
    FILE_TYPE_CS(18, "cs", "text/txt", "/static/images/filetype/cs.png"),
    FILE_TYPE_CSS(19, "css", "text/txt", "/static/images/filetype/css.png"),
    FILE_TYPE_CSV(20, "csv", "text/txt", "/static/images/filetype/csv.png"),
    FILE_TYPE_DB(21, "db", "multipart/form-data", "/static/images/filetype/db.png"),
    FILE_TYPE_DBF(22, "dbf", "multipart/form-data", "/static/images/filetype/dbf.png"),
    FILE_TYPE_DIR(23, "dir", "multipart/form-data", "/static/images/filetype/dir.png"),
    FILE_TYPE_DIV(24, "div", "multipart/form-data", "/static/images/filetype/div.png"),
    FILE_TYPE_DLL(25, "dll", "multipart/form-data", "/static/images/filetype/dll.png"),
    FILE_TYPE_DOC(26, "doc", "application/msword", "/static/images/filetype/doc.png"),
    FILE_TYPE_DOCX(27, "docx", "application/msword", "/static/images/filetype/docx.png"),
    FILE_TYPE_DOT(28, "dot", "application/msword", "/static/images/filetype/dot.png"),
    FILE_TYPE_EPS(29, "eps", "multipart/form-data", "/static/images/filetype/eps.png"),
    FILE_TYPE_EXE(30, "exe", "multipart/form-data", "/static/images/filetype/exe.png"),
    FILE_TYPE_FLV(31, "flv", "video/flv", "/static/images/filetype/flv.png"),
    FILE_TYPE_GIF(32, "gif", "image/gif", "/static/images/filetype/gif.png"),
    FILE_TYPE_H(33, "h", "text/txt", "/static/images/filetype/h.png"),
    FILE_TYPE_HTM(34, "htm", "text/htm", "/static/images/filetype/htm.png"),
    FILE_TYPE_HTML(35, "html", "text/html", "/static/images/filetype/html.png"),
    FILE_TYPE_JAVA(36, "java", "text/txt", "/static/images/filetype/java.png"),
    FILE_TYPE_JPEG(37, "jpeg", "image/jpeg", "/static/images/filetype/jpeg.png"),
    FILE_TYPE_JPG(38, "jpg", "image/jpg", "/static/images/filetype/jpg.png"),
    FILE_TYPE_JS(39, "js", "text/txt", "/static/images/filetype/js.png"),
    FILE_TYPE_JSON(40, "json", "application/json", "/static/images/filetype/json.png"),
    FILE_TYPE_JSP(41, "jsp", "text/txt", "/static/images/filetype/jsp.png"),
    FILE_TYPE_LIB(42, "lib", "multipart/form-data", "/static/images/filetype/lib.png"),
    FILE_TYPE_M4V(43, "m4v", "video/m4v", "/static/images/filetype/m4v.png"),
    FILE_TYPE_MDB(44, "mdb", "multipart/form-data", "/static/images/filetype/mdb.png"),
    FILE_TYPE_MDF(45, "mdf", "multipart/form-data", "/static/images/filetype/mdf.png"),
    FILE_TYPE_MKV(46, "mkv", "video/mkv", "/static/images/filetype/mkv.png"),
    FILE_TYPE_MOV(47, "mov", "video/mov", "/static/images/filetype/mov.png"),
    FILE_TYPE_MP3(48, "mp3", "audio/mp3", "/static/images/filetype/mp3.png"),
    FILE_TYPE_MP4(49, "mp4", "video/mp4", "/static/images/filetype/mp4.png"),
    FILE_TYPE_MPEG(50, "mpeg", "video/mpeg", "/static/images/filetype/mpeg.png"),
    FILE_TYPE_NONE(51, "none", "multipart/form-data", "/static/images/filetype/none.png"),
    FILE_TYPE_PDF(52, "pdf", "application/pdf", "/static/images/filetype/pdf.png"),
    FILE_TYPE_PHP(53, "php", "text/txt", "/static/images/filetype/php.png"),
    FILE_TYPE_PNG(54, "png", "image/png", "/static/images/filetype/png.png"),
    FILE_TYPE_PPT(55, "ppt", "application/vnd.ms-powerpoint", "/static/images/filetype/ppt.png"),
    FILE_TYPE_PPTX(56, "pptx", "application/vnd.ms-powerpoint", "/static/images/filetype/pptx.png"),
    FILE_TYPE_PSD(57, "psd", "multipart/form-data", "/static/images/filetype/psd.png"),
    FILE_TYPE_PY(58, "py", "text/txt", "/static/images/filetype/py.png"),
    FILE_TYPE_RAR(59, "rar", "multipart/form-data", "/static/images/filetype/rar.png"),
    FILE_TYPE_RAW(60, "raw", "image/raw", "/static/images/filetype/raw.png"),
    FILE_TYPE_RM(61, "rm", "video/rm", "/static/images/filetype/rm.png"),
    FILE_TYPE_RMVB(62, "rmvb", "video/rmvb", "/static/images/filetype/rmvb.png"),
    FILE_TYPE_RTF(63, "rtf", "application/msword", "/static/images/filetype/rtf.png"),
    FILE_TYPE_SQL(64, "sql", "text/txt", "/static/images/filetype/sql.png"),
    FILE_TYPE_SVG(65, "svg", "image/svg", "/static/images/filetype/svg.png"),
    FILE_TYPE_TIFF(66, "tiff", "image/tiff", "/static/images/filetype/tiff.png"),
    FILE_TYPE_TMP(67, "tmp", "multipart/form-data", "/static/images/filetype/tmp.png"),
    FILE_TYPE_TXT(68, "txt", "text/txt", "/static/images/filetype/txt.png"),
    FILE_TYPE_VOB(69, "vob", "video/vob", "/static/images/filetype/vob.png"),
    FILE_TYPE_WAV(70, "wav", "audio/wav", "/static/images/filetype/wav.png"),
    FILE_TYPE_WDB(71, "wdb", "multipart/form-data", "/static/images/filetype/wdb.png"),
    FILE_TYPE_WMA(72, "wma", "audio/wma", "/static/images/filetype/wma.png"),
    FILE_TYPE_WMV(73, "wmv", "video/wmv", "/static/images/filetype/wmv.png"),
    FILE_TYPE_WPS(74, "wps", "multipart/form-data", "/static/images/filetype/wps.png"),
    FILE_TYPE_XD(75, "xd", "multipart/form-data", "/static/images/filetype/xd.png"),
    FILE_TYPE_XLS(76, "xls", "application/x-xls", "/static/images/filetype/xls.png"),
    FILE_TYPE_XLSX(77, "xlsx", "application/x-xls", "/static/images/filetype/xlsx.png"),
    FILE_TYPE_XML(78, "xml", "text/txt", "/static/images/filetype/xml.png"),
    FILE_TYPE_ZIP(79, "zip", "multipart/form-data", "/static/images/filetype/zip.png"),
    ;
    private final int value;
    private final String desc;
    private final String contentType;
    private final String icon;

    public static FileType typeByFile(File file) {
        if (file.isDirectory())
            return FILE_TYPE_DIR;
        String filename = file.getName();
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        List<FileType> types = Arrays.stream(FileType.values()).filter(x -> StringUtils.equalsIgnoreCase(ext, x.desc)).collect(Collectors.toList());
        return types.size() > 0 ? types.get(0) : FileType.FILE_TYPE_NONE;
    }
}
