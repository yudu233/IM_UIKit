package com.ycbl.im.uikit.common.log.sdk.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by huangjun on 2017/3/7.
 */

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static File getFile(final String path) {
        try {
            File file = new File(path);
            File dir = file.getParentFile();
            if (dir == null) {
                Log.e(TAG, "file's parent dir is null, path=" + file.getCanonicalPath());
                return null;
            }

            if (!dir.exists()) {
                if (dir.getParentFile().exists()) {
                    dir.mkdir(); // dir父目录存在用mkDir
                } else {
                    dir.mkdirs(); // dir父目录不存在用mkDirs
                }
            }

            if (!file.exists() && !file.createNewFile()) {
                file.createNewFile();
                Log.e(TAG, "can not create dest file, path=" + path);
                return null;
            }
            return file;
        } catch (Throwable e) {
            Log.e(TAG, "create dest file error, path=" + path, e);
        }

        return null;
    }

    public static boolean appendFile(final String message, final String path) {
        if (TextUtils.isEmpty(message)) {
            return false;
        }

        if (TextUtils.isEmpty(path)) {
            return false;
        }

        boolean written = false;
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(path, true));
            fw.write(message);
            fw.flush();
            fw.close();

            written = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return written;
    }

    public static boolean appendFile(final byte[] message, final String path) {
        if (message == null || message.length <= 0) {
            return false;
        }

        if (TextUtils.isEmpty(path)) {
            return false;
        }

        boolean written = false;
        try {
            FileOutputStream fw = new FileOutputStream(path, true);
            fw.write(message);
            fw.close();

            written = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return written;
    }

    public static synchronized void shrink(final String logPath, final int maxLength, final int baseLength) {
        File file = new File(logPath);
        if (file.length() < maxLength) {
            return;
        } else if (file.length() > Integer.MAX_VALUE) {
            file.delete();
            return;
        }

        File out = new File(logPath + "_tmp");
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(out);
            FileChannel input = fis.getChannel();

            input.position(file.length() - baseLength);
            FileChannel output = fos.getChannel();
            output.transferFrom(fis.getChannel(), 0, baseLength);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fis);
            close(fos);
        }

        if (out.exists()) {
            if (file.delete()) {
                out.renameTo(file);
            }
        }
    }

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePath(final String dirPath, final String fileName) {
        File dir = new File(dirPath);

        if (!dir.exists()) {
            if (dir.getParentFile().exists()) {
                dir.mkdir(); // dir父目录存在用mkDir
            } else {
                dir.mkdirs(); // dir父目录不存在用mkDirs
            }
        }

        return dirPath + File.separator + fileName;
    }

    // 是否包含扩展名
    public static boolean hasExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return ((dot > -1) && (dot < (filename.length() - 1)));
    }

    // 获取文件扩展名
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    // 获取文件名
    public static String getFileNameFromPath(String filepath) {
        if ((filepath != null) && (filepath.length() > 0)) {
            int sep = filepath.lastIndexOf('/');
            if ((sep > -1) && (sep < filepath.length() - 1)) {
                return filepath.substring(sep + 1);
            }
        }
        return filepath;
    }

    // 获取不带扩展名的文件名
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static String getExternalPackageDirectory(Context context) {
        String externalPath = Environment.getExternalStorageDirectory().getPath();
        return externalPath + File.separator + context.getPackageName();
    }

    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    /**
     * 删除已存储的文件
     */
    public static void deletefile(String filePath,String fileName) {
        try {
            // 找到文件所在的路径并删除该文件
            File file = new File(filePath, fileName);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    //读取指定目录下的所有TXT文件的文件内容
    public static String getFileContent(File file) {
        String content = "";
        if (!file.isDirectory()) {  //检查此路径名的文件是否是一个目录(文件夹)
            if (file.getName().endsWith("txt")) {//文件格式为""文件
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader
                                = new InputStreamReader(instream, "UTF-8");
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line = "";
                        //分行读取
                        while ((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                        instream.close();//关闭输入流
                    }
                } catch (java.io.FileNotFoundException e) {
                    Log.d("TestFile", "The File doesn't not exist.");
                } catch (IOException e) {
                    Log.d("TestFile", e.getMessage());
                }
            }
        }
        return content;
    }

    //判断文件是否存在
    public static boolean fileIsExists(String filePath)
    {
        try
        {
            File f = new File(filePath);
            if(!f.exists())
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
