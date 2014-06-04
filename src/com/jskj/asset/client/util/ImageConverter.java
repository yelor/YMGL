/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.util;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

/**
 *
 * @author haitao
 */
public class ImageConverter {
    
    // JGP格式
    public static final String JPG = "jpeg";
    // GIF格式
    public static final String GIF = "gif";
    // PNG格式
    public static final String PNG = "png";
    // BMP格式
    public static final String BMP = "bmp";
 
    public static void pngToJPG(String source, String result){
        convert(source,JPG,result);
    }
    
    public static void gifToJPG(String source, String result){
        convert(source,JPG,result);
    }
    
    public static void bmpToJPG(String source, String result) throws FileNotFoundException, IOException{

		RenderedOp src2 = JAI.create("fileload", source);
		OutputStream os2 = new FileOutputStream(result);
		JPEGEncodeParam param2 = new JPEGEncodeParam();
		//指定格式类型，jpg 属于 JPEG 类型
		ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2, param2);
		enc2.encode(src2);
		os2.close();
		
    }
    
    /**
     * <p>Discription:[convert GIF->JPG GIF->PNG PNG->GIF(X) PNG->JPG ]</p>
     * @param source
     * @param formatName
     * @param result
     * @author:[shixing_11@sina.com]
     */
    public static void convert(String source, String formatName, String result)
    {
        try
        {
            File f = new File(source);
            f.canRead();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(result));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
//    public static void main(String[] args) throws IOException {
////        pngToJPG("C:\\Users\\haitao\\Desktop\\1.png","C:\\Users\\haitao\\Desktop\\1.jpg");
//        bmpToJPG("C:\\Users\\haitao\\Desktop\\1.bmp","C:\\Users\\haitao\\Desktop\\1.jpg");
//    }
}
