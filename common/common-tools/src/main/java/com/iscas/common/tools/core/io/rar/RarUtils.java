package com.iscas.common.tools.core.io.rar;

import com.github.junrar.Archive;
import com.github.junrar.UnrarCallback;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;


/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/14 15:11
 * @since jdk1.8
 */
public class RarUtils {

    /**
     * @param rarFileName rar file name
     * @param outFilePath output file path
     * @param callback callback
     * @throws Exception
     */
    public static void unrar(String rarFileName, String outFilePath, UnrarCallback callback) throws Exception {
        Archive archive = new Archive(new File(rarFileName), callback);
        if(archive == null){
            throw new FileNotFoundException(rarFileName + " NOT FOUND!");
        }
        if(archive.isEncrypted()){
            throw new Exception(rarFileName + " IS ENCRYPTED!");
        }
        List<FileHeader> files =  archive.getFileHeaders();
        for (FileHeader fh : files) {
            if(fh.isEncrypted()){
                throw new Exception(rarFileName + " IS ENCRYPTED!");
            }
            String fileName = fh.getFileNameString();
            if(fileName != null && fileName.trim().length() > 0){
                String saveFileName = outFilePath+ File.separator+fileName;
                File saveFile = new File(saveFileName);
                File parent =  saveFile.getParentFile();
                if(!parent.exists()){
                    parent.mkdirs();
                }
                if(!saveFile.exists()){
                    saveFile.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(saveFile);
                try {
                    archive.extractFile(fh, fos);
                } catch (RarException e) {
                    throw e;
                }finally{
                    try{
                        fos.flush();
                        fos.close();
                    }catch (Exception e){
                    }
                }
            }
        }
    }

    /**
     * 获取单个文件的MD5值！
     * @param file
     * @return
     */
//    public static String getFileMD5(File file) {
//        if (!file.isFile()) {
//            return null;
//        }
//        MessageDigest digest = null;
//        FileInputStream in = null;
//        byte buffer[] = new byte[1024];
//        int len;
//        try {
//            digest = MessageDigest.getInstance("MD5");
//            in = new FileInputStream(file);
//            while ((len = in.read(buffer, 0, 1024)) != -1) {
//                digest.update(buffer, 0, len);
//            }
//            in.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        BigInteger bigInt = new BigInteger(1, digest.digest());
//        return bigInt.toString(16);
//    }
//}


}
