package com.rain.chat.utils;

import android.content.Context;

import com.rain.inputpanel.data.EmoticonEntity;
import com.rain.inputpanel.data.EmoticonPageSetEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/24 17:49
 * @Version : 1.0
 * @Descroption : 本地表情解析
 */
public class ParseDataUtils {

    /**
     * 从文件中解析表情
     *
     * @param context        上下文
     * @param filePath       存放文件的路径
     * @param assetsFileName 需要解压的文件名
     * @param xmlName        需要解析的xml文件名
     * @return
     */
    public static EmoticonPageSetEntity<EmoticonEntity> parseDataFromFile(Context context,
                                                                          String filePath,
                                                                          String assetsFileName,
                                                                          String xmlName) {
        String xmlFilePath = filePath + "/" + xmlName;
        File file = new File(xmlFilePath);
        if (!file.exists()) {
            try {
                unzip(context.getAssets().open(assetsFileName), filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        XmlUtil xmlUtil = new XmlUtil(context);
        return xmlUtil.ParserXml(filePath, xmlUtil.getXmlFromSD(xmlFilePath));
    }


    public static void unzip(InputStream is, String dir) throws IOException {
        File dest = new File(dir);
        if (!dest.exists()) {
            dest.mkdirs();
        }

        if (!dest.isDirectory())
            throw new IOException("Invalid Unzip destination " + dest);
        if (null == is) {
            throw new IOException("InputStream is null");
        }

        ZipInputStream zip = new ZipInputStream(is);
        ZipEntry ze;
        while ((ze = zip.getNextEntry()) != null) {
            final String path = dest.getAbsolutePath()
                    + File.separator + ze.getName();

            String zeName = ze.getName();
            char cTail = zeName.charAt(zeName.length() - 1);
            if (cTail == File.separatorChar) {
                File file = new File(path);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        throw new IOException("Unable to create folder " + file);
                    }
                }
                continue;
            }

            FileOutputStream fout = new FileOutputStream(path);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = zip.read(bytes)) != -1) {
                fout.write(bytes, 0, c);
            }
            zip.closeEntry();
            fout.close();
        }
    }
}
