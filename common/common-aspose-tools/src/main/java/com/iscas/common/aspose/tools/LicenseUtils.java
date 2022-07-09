package com.iscas.common.aspose.tools;

import com.aspose.words.License;

import java.io.InputStream;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/9 12:53
 * @since jdk11
 */
public class LicenseUtils {
    private LicenseUtils() {
    }

    private volatile static License license = null;

    //    static {
//        String license =
//                "<License>\n" +
//                        "  <Data>\n" +
//                        "    <Products>\n" +
//                        "      <Product>Aspose.Cells for Java</Product>\n" +
//                        "      <Product>Aspose.Words for Java</Product>\n" +
//                        "      <Product>Aspose.Slides for Java</Product>\n" +
//                        "    </Products>\n" +
//                        "    <EditionType>Enterprise</EditionType>\n" +
//                        "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
//                        "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
//                        "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
//                        "  </Data>\n" +
//                        "  <Signature>datax</Signature>\n" +
//                        "</License>";
//        try {
//            new License().setLicense(new ByteArrayInputStream(license.getBytes("UTF-8")));
//        } catch (Exception e) {
//        }
//    }

    /**
     * 初始化license
     *
     * @date 2022/7/9
     * @since jdk11
     */
    public static void initLicense() {
        if (license == null) {
            synchronized (LicenseUtils.class) {
                if (license == null) {
                    try (InputStream is = ConvertUtils.class.getClassLoader().getResourceAsStream("license.xml")) {
                        license = new License();
                        license.setLicense(is);
                    } catch (Exception e) {
                        throw new RuntimeException("获取license出错", e);
                    }
                }
            }
        }
    }
}
