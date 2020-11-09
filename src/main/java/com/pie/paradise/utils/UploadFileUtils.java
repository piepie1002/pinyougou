package com.pie.paradise.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author LIN
 * @since JDK 1.8
 */
public class UploadFileUtils {
    public static final String IMG_PREFIX = "IMG_";
    public static final String DATETIME_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String DATE_PATTERN = "yyyyMMdd";
    public static final String ROOT_PATH = "D:\\smart";
    private static final String IMAGE_PATH = "images";
    /**
     * 保存图片
     */
    public static String saveImage(MultipartFile multipartFile) throws IOException{
        String imagePath = IMAGE_PATH + File.separator +
                DateFormatUtils.format(new Date(),DATE_PATTERN);
       //保存图片的绝对路径
        File file = new File(ROOT_PATH +File.separator+imagePath);
        if (!file.exists()){
            file.mkdirs();
        }
        String imageNewName = getImageNewName(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),new File(file,imageNewName));
        return imagePath+File.separator+imageNewName;
    }

    /**
     *  获取文件的后缀名
     * @param fileName
     * @return
     */
    public static String getFileSuffixName(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 获取新的文件名
     * @param oldName
     * @return
     */
    public static String getImageNewName(String oldName){
        String format = DateFormatUtils.format(new Date(), DATETIME_PATTERN);
        return String.format("%s%s%s",IMG_PREFIX,format,getFileSuffixName(oldName));
    }
    //测试
    public static void main(String[] args) {
        String imageNewName = getImageNewName("1111.jpg");
        System.out.println(imageNewName);
    }
}
