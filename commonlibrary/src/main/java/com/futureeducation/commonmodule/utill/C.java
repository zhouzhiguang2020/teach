package com.futureeducation.commonmodule.utill;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class C {
    // 视频允许大小
    public static final long MAX_LOCAL_VIDEO_FILE_SIZE = 20 * 1024 * 1024;
    //] file type
    //music
    public static final int FILE_TYPE_MUSIC = 1;
    //视频video
    public static final int FILE_TYPE_VIDEO = 2;
    //picture
    public static final int FILE_TYPE_PICTURE = 3;

    // 关于文件后缀的常量
    public static final class FileSuffix {
        public static final String JPG = ".jpg";
        public static final String JPEG = ".jpeg";
        public static final String PNG = ".png";
        public static final String GIF = ".gif";
        public static final String WEBP = ".webp";

        public static final String M4A = ".m4a";

        public static final String THREE_3GPP = ".3gp";

        public static final String BMP = ".bmp";

        public static final String MP4 = ".mp4";

        public static final String AMR_NB = ".amr";

        public static final String APK = ".apk";

        public static final String AAC = ".aac";
        @NotNull
        public static final String MPEG = ".mpeg";
        @NotNull
        public static final String AVI = ".avi";
        @NotNull
        public static final String ASF = ".asf";
        @NotNull
        public static final String MOV = ".mov";
        @NotNull
        public static final String WMV = ".wmv";
        public static final String SANGP = ".3gp";
        @NotNull
        public static final String RM = ".rm";
        @NotNull
        public static final String RMVB = ".rmvb";
        public static final String FLV = ".flv";
        @NotNull
        public static final String F4V = ".f4v";
        @NotNull
        public static final String MP3 = ".mp3";
        public static final String WAV = ".wav";
        @NotNull
        public static final String MIDI = ".midi";
        @NotNull
        public static final String CDA = ".cda";
        @NotNull
        public static final String DVD = ".dvd";
    }

    //构建筛选录音文件
    public static String[] getExtensionsArray(int filetype) {
        List<String> list = new ArrayList<>();
        if (filetype == FILE_TYPE_MUSIC) {
            //添加所有音频文件
            list.add(getCorrection(FileSuffix.WMV));
            list.add(getCorrection(FileSuffix.MP3));
            list.add(getCorrection(FileSuffix.MIDI));
            list.add(getCorrection(FileSuffix.CDA));
            list.add(getCorrection(FileSuffix.DVD));
            list.add(getCorrection(FileSuffix.CDA));
            list.add(getCorrection(FileSuffix.M4A));

        } else if (filetype == FILE_TYPE_VIDEO) {
            //视频类
            list.add(getCorrection(FileSuffix.MPEG));
            list.add(getCorrection(FileSuffix.MP4));
            list.add(getCorrection(FileSuffix.AVI));
            list.add(getCorrection(FileSuffix.ASF));
            list.add(getCorrection(FileSuffix.MOV));
            list.add(getCorrection(FileSuffix.WMV));
            list.add(getCorrection(FileSuffix.SANGP));
            list.add(getCorrection(FileSuffix.RM));
            list.add(getCorrection(FileSuffix.RMVB));
            list.add(getCorrection(FileSuffix.FLV));
            list.add(getCorrection(FileSuffix.F4V));
        } else if (filetype == FILE_TYPE_PICTURE) {
            //图片类
            list.add(getCorrection(FileSuffix.JPG));
            list.add(getCorrection(FileSuffix.PNG));
            list.add(getCorrection(FileSuffix.BMP));
            list.add(getCorrection(FileSuffix.GIF));
            list.add(getCorrection(FileSuffix.WEBP));
            list.add(getCorrection(FileSuffix.JPEG));

        }
        String[] array = list.toArray(new String[list.size()]);
        return array;
    }

    public static String getCorrection(String content) {
        if (!TextUtils.isEmpty(content)) {
            content = content.substring(1);
        }
        return content;

    }

    // 关于mimetype的常量
    public static final class MimeType {
        public static final String MIME_JPEG = "image/jpeg";

        public static final String MIME_PNG = "image/png";

        public static final String MIME_BMP = "image/x-MS-bmp";

        public static final String MIME_GIF = "image/gif";

        public static final String MIME_AUDIO_3GPP = "audio/3gpp";

        public static final String MIME_AUDIO_MP4 = "audio/mp4";

        public static final String MIME_AUDIO_M4A = "audio/m4a";

        public static final String MIME_AUDIO_AMR_NB = "audio/amr";

        public static final String MIME_AUDIO_AAC = "audio/aac";

        public static final String MIME_TXT = "txt/txt";// 用 于PC长消息

        public static final String MIME_WAPPUSH_SMS = "message/sms";

        public static final String MIME_WAPPUSH_TEXT = "txt/wappush"; // 文字wappush

        public static final String MIME_MUSIC_LOVE = "music/love"; // 爱音乐

        public static final String MIME_MUSIC_XIA = "music/xia"; // 虾米音乐

        public static final String MIME_VIDEO_3GPP = "video/3gpp";

        public static final String MIME_VIDEO_ALL = "video/*";

        public static final String MIME_LOCATION_GOOGLE = "location/google";
    }
}
