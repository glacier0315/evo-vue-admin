package com.glacier.common.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author glacier
 * @version 1.0
 * date 2020-11-08 20:05
 */
public class ContentTypeUtils {
    public static final String DOT = ".";
    
    private static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<>();
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    
    private ContentTypeUtils() {
    
    }
    
    static {
        CONTENT_TYPE_MAP.put("shtml", "text/html");
        CONTENT_TYPE_MAP.put("apk", "application/vnd.android.package-archive");
        CONTENT_TYPE_MAP.put("sisx", "application/vnd.symbian.install");
        CONTENT_TYPE_MAP.put("msi", "application/x-msdownload");
        CONTENT_TYPE_MAP.put("css", "text/css");
        CONTENT_TYPE_MAP.put("xml", "text/xml");
        CONTENT_TYPE_MAP.put("jpg", "image/jpeg");
        CONTENT_TYPE_MAP.put("js", "application/x-javascript");
        CONTENT_TYPE_MAP.put("atom", "application/atom+xml");
        CONTENT_TYPE_MAP.put("rss", "application/rss+xml");
        CONTENT_TYPE_MAP.put("mml", "text/mathml");
        CONTENT_TYPE_MAP.put("txt", "text/plain");
        CONTENT_TYPE_MAP.put("jad", "text/vnd.sun.j2me.app-descriptor");
        CONTENT_TYPE_MAP.put("wml", "text/vnd.wap.wml");
        CONTENT_TYPE_MAP.put("htc", "text/x-component");
        CONTENT_TYPE_MAP.put("png", "image/png");
        CONTENT_TYPE_MAP.put("tiff", "image/tiff");
        CONTENT_TYPE_MAP.put("wbmp", "image/vnd.wap.wbmp");
        CONTENT_TYPE_MAP.put("ico", "image/x-icon");
        CONTENT_TYPE_MAP.put("jng", "image/x-jng");
        CONTENT_TYPE_MAP.put("bmp", "image/x-ms-bmp");
        CONTENT_TYPE_MAP.put("svg", "image/svg+xml");
        CONTENT_TYPE_MAP.put("ear", "application/java-archive");
        CONTENT_TYPE_MAP.put("doc", "application/msword");
        CONTENT_TYPE_MAP.put("pdf", "application/pdf");
        CONTENT_TYPE_MAP.put("rtf", "application/rtf");
        CONTENT_TYPE_MAP.put("xls", "application/vnd.ms-excel");
        CONTENT_TYPE_MAP.put("ppt", "application/vnd.ms-powerpoint");
        CONTENT_TYPE_MAP.put("7z", "application/x-7z-compressed");
        CONTENT_TYPE_MAP.put("rar", "application/x-rar-compressed");
        CONTENT_TYPE_MAP.put("swf", "application/x-shockwave-flash");
        CONTENT_TYPE_MAP.put("rpm", "application/x-redhat-package-manager");
        CONTENT_TYPE_MAP.put("crt", "application/x-x509-ca-cert");
        CONTENT_TYPE_MAP.put("xhtml", "application/xhtml+xml");
        CONTENT_TYPE_MAP.put("zip", "application/zip");
        CONTENT_TYPE_MAP.put("kar", "audio/midi");
        CONTENT_TYPE_MAP.put("mp3", "audio/mpeg");
        CONTENT_TYPE_MAP.put("ogg", "audio/ogg");
        CONTENT_TYPE_MAP.put("m4a", "audio/x-m4a");
        CONTENT_TYPE_MAP.put("ra", "audio/x-realaudio");
        CONTENT_TYPE_MAP.put("3gp", "video/3gpp");
        CONTENT_TYPE_MAP.put("mp4", "video/mp4");
        CONTENT_TYPE_MAP.put("mpg", "video/mpeg");
        CONTENT_TYPE_MAP.put("mov", "video/quicktime");
        CONTENT_TYPE_MAP.put("flv", "video/x-flv");
        CONTENT_TYPE_MAP.put("m4v", "video/x-m4v");
        CONTENT_TYPE_MAP.put("mng", "video/x-mng");
        CONTENT_TYPE_MAP.put("asf", "video/x-ms-asf");
        CONTENT_TYPE_MAP.put("wmv", "video/x-ms-wmv");
        CONTENT_TYPE_MAP.put("avi", "video/x-msvideo");
    }
    
    /**
     * 根据“文件名的后缀”获取文件内容类型（而非根据File.getContentType()读取的文件类型）
     *
     * @param fileName 带验证的文件名
     * @return 返回文件类型
     */
    private static String getContentType(String fileName) {
        if (fileName.isEmpty() || fileName.lastIndexOf(DOT) < 0) {
            return ContentTypeUtils.APPLICATION_OCTET_STREAM;
        }
        return Optional.ofNullable(
                CONTENT_TYPE_MAP.get(fileName.substring(fileName.lastIndexOf(DOT) + 1)
                        .toLowerCase()))
                .orElse(ContentTypeUtils.APPLICATION_OCTET_STREAM);
    }
}
