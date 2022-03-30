package com.futureeducation.commonmodule.utill;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * package
 * 新增加适配android 10 对文件的一些操作
 */
public class ExternalStorage {
    /**
     * 外部存储根目录
     */
    private String sdkStorageRoot = null;

    private static ExternalStorage instance;

    private static final String TAG = "ExternalStorage";

    private boolean hasPermission = true; // 是否拥有存储卡权限

    private Context context;

    private ExternalStorage() {

    }

    synchronized public static ExternalStorage getInstance() {
        if (instance == null) {
            instance = new ExternalStorage();
        }
        return instance;
    }

    public void init(Context context, String sdkStorageRoot) {
        this.context = context;
        // 判断权限
        hasPermission = checkPermission(context);

        if (!TextUtils.isEmpty(sdkStorageRoot)) {
            File dir = new File(sdkStorageRoot);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (dir.exists() && !dir.isFile()) {
                this.sdkStorageRoot = sdkStorageRoot;
                if (!sdkStorageRoot.endsWith("/")) {
                    this.sdkStorageRoot = sdkStorageRoot + "/";
                }
            }
        }

        if (TextUtils.isEmpty(this.sdkStorageRoot)) {
            loadStorageState(context);
        }

        createSubFolders();
    }

    private void loadStorageState(Context context) {
        String externalPath = Environment.getExternalStorageDirectory().getPath();
        this.sdkStorageRoot = externalPath + "/" + context.getPackageName() + "/";
    }

    private void createSubFolders() {
        boolean result = true;
        File root = new File(sdkStorageRoot);
        if (root.exists() && !root.isDirectory()) {
            root.delete();
        }
        for (StorageType storageType : StorageType.values()) {
            result &= makeDirectory(sdkStorageRoot + storageType.getStoragePath());
        }
        if (result) {
            createNoMediaFile(sdkStorageRoot);
        }
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    private boolean makeDirectory(String path) {
        File file = new File(path);
        boolean exist = file.exists();
        if (!exist) {
            exist = file.mkdirs();
        }
        return exist;
    }

    protected static String NO_MEDIA_FILE_NAME = ".nomedia";

    private void createNoMediaFile(String path) {
        File noMediaFile = new File(path + "/" + NO_MEDIA_FILE_NAME);
        try {
            if (!noMediaFile.exists()) {
                noMediaFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件全名转绝对路径（写）
     *
     * @param fileName 文件全名（文件名.扩展名）
     * @return 返回绝对路径信息
     */
    public String getWritePath(String fileName, StorageType fileType) {
        return pathForName(fileName, fileType, false, false);
    }

    private String pathForName(String fileName, StorageType type, boolean dir,
                               boolean check) {
        String directory = getDirectoryByDirType(type);
        StringBuilder path = new StringBuilder(directory);

        if (!dir) {
            path.append(fileName);
        }

        String pathString = path.toString();
        File file = new File(pathString);

        if (check) {
            if (file.exists()) {
                if ((dir && file.isDirectory())
                        || (!dir && !file.isDirectory())) {
                    return pathString;
                }
            }

            return "";
        } else {
            return pathString;
        }
    }

    /**
     * 返回指定类型的文件夹路径
     *
     * @param fileType
     * @return
     */
    public String getDirectoryByDirType(StorageType fileType) {
        return sdkStorageRoot + fileType.getStoragePath();
    }

    /**
     * 根据输入的文件名和类型，找到该文件的全路径。
     *
     * @param fileName
     * @param fileType
     * @return 如果存在该文件，返回路径，否则返回空
     */
    public String getReadPath(String fileName, StorageType fileType) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }

        return pathForName(fileName, fileType, false, true);
    }

    public boolean isSdkStorageReady() {
        String externalRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        LogUtils.e("当前应用根目录：" + externalRoot);
        if (this.sdkStorageRoot.startsWith(externalRoot)) {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } else {
            return true;
        }
    }

    /**
     * 获取外置存储卡剩余空间
     *
     * @return
     */
    public long getAvailableExternalSize() {
        return getResidualSpace(sdkStorageRoot);
    }

    /**
     * 获取目录剩余空间
     *
     * @param directoryPath
     * @return
     */
    private long getResidualSpace(String directoryPath) {
        try {
            StatFs sf = new StatFs(directoryPath);
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            long availCountByte = availCount * blockSize;
            return availCountByte;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * SD卡存储权限检查
     */
    private boolean checkPermission(Context context) {
        if (context == null) {
            Log.e(TAG, "checkMPermission context null");
            return false;
        }

        // 写权限有了默认就赋予了读权限
        PackageManager pm = context.getPackageManager();
        if (PackageManager.PERMISSION_GRANTED !=
                pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, context.getApplicationInfo().packageName)) {
            Log.e(TAG, "without permission to access storage");
            return false;
        }

        return true;
    }

    /**
     * 有效性检查
     */
    public boolean checkStorageValid() {
        if (hasPermission) {
            return true; // M以下版本&授权过的M版本不需要检查
        }

        hasPermission = checkPermission(context); // 检查是否已经获取权限了
        if (hasPermission) {
            Log.i(TAG, "get permission to access storage");

            // 已经重新获得权限，那么重新检查一遍初始化过程
            createSubFolders();
        }
        return hasPermission;
    }

    //保存或者下载文件到公有目录，保存Bitmap同理，
    // 如Download，MIME_TYPE类型可以自行参考对应的文件类型，这里只对APK作出说明
    public void copyToDownloadAndroidQ(Context context, String sourcePath, String fileName, String saveDirName) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, "application/vnd.android.package-archive");
        values.put(MediaStore.Downloads.RELATIVE_PATH, "Download/" + saveDirName.replaceAll("/", "") + "/");

        Uri external = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            external = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        } else {
            external = Uri.parse(sourcePath);
        }
        ContentResolver resolver = context.getContentResolver();

        Uri insertUri = resolver.insert(external, values);
        if (insertUri == null) {
            return;
        }

        String mFilePath = insertUri.toString();

        InputStream is = null;
        OutputStream os = null;
        try {
            os = resolver.openOutputStream(insertUri);
            if (os == null) {
                return;
            }
            int read;
            File sourceFile = new File(sourcePath);
            if (sourceFile.exists()) { // 文件存在时
                is = new FileInputStream(sourceFile); // 读入原文件
                byte[] buffer = new byte[1444];
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                is.close();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(is, os);
        }

    }

    private void close(InputStream is, OutputStream os) {

        try {
            if (is != null) {
                is.close();

            }
            if (os != null) {
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    //2、判断公有目录文件是否存在，自Android Q开始，公有目录File API都失效，不能直接通过new File(path).exists();
    // 判断公有目录文件是否存在，正确方式如下：
    public boolean isAndroidQFileExists(Context context, String path) {
        if (context == null) {
            return false;
        }
        AssetFileDescriptor afd = null;
        ContentResolver cr = context.getContentResolver();
        try {
            Uri uri = Uri.parse(path);
            afd = cr.openAssetFileDescriptor(Uri.parse(path), "r");
            if (afd == null) {
                return false;
            } else {
                close(afd);
            }
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            close(afd);
        }
        return true;
    }

    /**
     * 通过MediaStore保存，兼容AndroidQ，保存成功自动添加到相册数据库，无需再发送广告告诉系统插入相册
     *
     * @param context      context
     * @param sourceFile   源文件
     * @param saveFileName 保存的文件名
     * @param saveDirName  picture子目录
     * @return 成功或者失败
     */
    public boolean saveImageWithAndroidQ(Context context,
                                         File sourceFile,
                                         String saveFileName,
                                         String saveDirName) {
        //扩展名字
        //  String extension = BitmapUtil.getExtension(sourceFile.getAbsolutePath());

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image");
        values.put(MediaStore.Images.Media.DISPLAY_NAME, saveFileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.TITLE, "Image.png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + saveDirName);

        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = context.getContentResolver();

        Uri insertUri = resolver.insert(external, values);
        BufferedInputStream inputStream = null;
        OutputStream os = null;
        boolean result = false;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
            if (insertUri != null) {
                os = resolver.openOutputStream(insertUri);
            }
            if (os != null) {
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
            }
            result = true;
        } catch (IOException e) {
            result = false;
        } finally {
            close(os, inputStream);
        }
        return result;
    }

    private void close(OutputStream os, BufferedInputStream inputStream) {
    }

    private void close(AssetFileDescriptor afd) {
        if (afd != null) {
            try {
                afd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*
    我们可以看到当目标版本从Android P开始，Canvas.clipPath(@NonNull Path path, @NonNull Region.Op op) ; 已经被废弃，而且是包含异常风险的废弃API，只有 Region.Op.INTERSECT 和Region.Op.DIFFERENCE 得到兼容，目前不清楚google此举目的如何，
    仅仅如此简单就抛出异常提示开发者适配，几乎所有的博客解决方案都是如下简单粗暴：
     *
     * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    canvas.clipPath(path);
} else {
    canvas.clipPath(path, Region.Op.XOR);// REPLACE、UNION 等
}

  最佳适配方案
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
             Path mPathXOR = new Path();
             mPathXOR.moveTo(0,0);
             mPathXOR.lineTo(getWidth(),0);
             mPathXOR.lineTo(getWidth(),getHeight());
             mPathXOR.lineTo(0,getHeight());
             mPathXOR.close();
             //以上根据实际的Canvas或View的大小，画出相同大小的Path即可
             mPathXOR.op(mPath0, Path.Op.XOR);
             canvas.clipPath(mPathXOR);
               }else {
                  canvas.clipPath(mPath0, Region.Op.XOR);
                  }

     * */
}
