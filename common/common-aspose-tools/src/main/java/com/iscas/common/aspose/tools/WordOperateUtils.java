package com.iscas.common.aspose.tools;

import com.aspose.words.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/11 13:29
 * @since jdk11
 */
@SuppressWarnings({"rawtypes", "unused"})
public class WordOperateUtils {

    static {
        LicenseUtils.initLicense();
    }

    private WordOperateUtils() {
    }

    /**
     * 设置书签值
     *
     * @param is   文档输入流
     * @param os   文档输出流
     * @param saveFormat 文件输出格式，{@link SaveFormat#DOC} 或 {@link SaveFormat#DOCX}
     * @param name 书签名称
     * @param val  书签值
     * @throws Exception 异常
     * @date 2022/7/11
     * @since jdk11
     */
    public static void setBookmarkVal(InputStream is, OutputStream os, int saveFormat, String name, String val) throws Exception {
        Document doc = new Document(is);
        BookmarkCollection bookmarkCollection = doc.getRange().getBookmarks();
        for (Bookmark bookmark : bookmarkCollection) {
            if (Objects.equals(bookmark.getName(), name)) {
                bookmark.setText(val);
                break;
            }
        }
        doc.save(os, saveFormat);
    }

    /**
     * 根据书签名获取书签
     *
     * @param is   文档输入流
     * @param name 书签名称
     * @return java.util.List<com.aspose.words.Bookmark>
     * @throws Exception 异常
     * @date 2022/7/11
     * @since jdk11
     */
    public static Bookmark getBookmark(InputStream is, String name) throws Exception {
        return getBookMarks(is).stream().filter(b -> Objects.equals(name, b.getName())).findFirst().orElse(null);
    }

    /**
     * 获取书签
     *
     * @param is 文档输入流
     * @return java.util.List<com.aspose.words.Bookmark>
     * @throws Exception 异常
     * @date 2022/7/11
     * @since jdk11
     */
    public static java.util.List<Bookmark> getBookMarks(InputStream is) throws Exception {
        Document doc = new Document(is);
        BookmarkCollection bookmarkCollection = doc.getRange().getBookmarks();
        java.util.List<Bookmark> bookmarks = new ArrayList<>();
        return StreamSupport.stream(bookmarkCollection.spliterator(), false).collect(Collectors.toList());
    }


    /**
     * 清洁文档，将所有的批注和修订去除
     *
     * @param is         文档输入
     * @param os         文档输出
     * @param saveFormat 文件输出格式，{@link SaveFormat#DOC} 或 {@link SaveFormat#DOCX}
     * @throws Exception 异常
     * @date 2022/7/11
     * @since jdk11
     */
    public static void cleanWord(InputStream is, OutputStream os, int saveFormat) throws Exception {
        Document doc = new Document(is);
        // 获取批注
        NodeCollection childNodes = doc.getChildNodes(NodeType.COMMENT, true);
        childNodes.clear();
        // 接受所有修订
        doc.acceptAllRevisions();
        doc.save(os, saveFormat);
    }

    /**
     * 拼接word，如果bookmark为空，从最后拼接，如果bookmark不为空，从此书签位置拼接
     *
     * @param mainIs     主文件输入流
     * @param addIs      拼接的文件输入流
     * @param os         文件输出的流
     * @param saveFormat 文件输出格式，{@link SaveFormat#DOC} 或 {@link SaveFormat#DOCX}
     * @param isPortrait 是否纵向纸张
     * @throws Exception 异常
     * @date 2022/7/11
     * @since jdk11
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    public static void appendWord(InputStream mainIs, InputStream addIs, OutputStream os, int saveFormat, boolean isPortrait) throws Exception {
        appendWord(mainIs, addIs, os, saveFormat, isPortrait, null);
    }

    /**
     * 拼接word，如果bookmark为空，从最后拼接，如果bookmark不为空，从此书签位置拼接
     *
     * @param mainIs     主文件输入流
     * @param addIs      拼接的文件输入流
     * @param os         文件输出的流
     * @param saveFormat 文件输出格式，{@link SaveFormat#DOC} 或 {@link SaveFormat#DOCX}
     * @param isPortrait 是否纵向纸张
     * @param bookmark   从书签位置拼接
     * @throws Exception 异常
     * @date 2022/7/11
     * @since jdk11
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    public static void appendWord(InputStream mainIs, InputStream addIs, OutputStream os, int saveFormat, boolean isPortrait, String bookmark) throws Exception {
        Document mainDoc = new Document(mainIs);
        Document addDoc = new Document(addIs);
        appendWord(mainDoc, addDoc, isPortrait, bookmark);
        mainDoc.save(os, saveFormat);
    }


    /**
     * 拼接word，从文档最后拼接
     *
     * @param mainDoc    主文件
     * @param addDoc     拼接的文件
     * @param isPortrait 是否纵向纸张
     * @return com.aspose.words.Document
     * @throws Exception 异常
     * @date 2022/7/11
     * @since jdk11
     */
    public static Document appendWord(Document mainDoc, Document addDoc, boolean isPortrait) throws Exception {
        return appendWord(mainDoc, addDoc, isPortrait, null);
    }

    /**
     * 拼接word，如果bookmark为空，从最后拼接，如果bookmark不为空，从此书签位置拼接
     *
     * @param mainDoc    主文件
     * @param addDoc     拼接的文件
     * @param isPortrait 是否纵向纸张
     * @param bookmark   从书签位置拼接
     * @return com.aspose.words.Document
     * @throws Exception 异常
     * @date 2022/7/11
     * @since jdk11
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    public static Document appendWord(Document mainDoc, Document addDoc, boolean isPortrait, String bookmark) throws Exception {
        long old = System.currentTimeMillis();
        DocumentBuilder builder = new DocumentBuilder(mainDoc);
        if (bookmark != null && bookmark.length() > 0) {
            //获取到书签
            BookmarkCollection bms = mainDoc.getRange().getBookmarks();
            Bookmark bm = bms.get(bookmark);
            if (bm != null) {
                builder.moveToBookmark(bookmark, true, false);
            }
        } else {
            builder.moveToDocumentEnd();
        }
        //            builder.writeln();
        builder.getPageSetup().setPaperSize(PaperSize.A4);
        Node insertAfterNode = builder.getCurrentParagraph().getPreviousSibling();
        insertDocumentAfterNode(insertAfterNode, mainDoc, addDoc);
        if (isPortrait) {
            // 纵向纸张
            builder.getPageSetup().setOrientation(Orientation.PORTRAIT);
        } else {
            // 横向纸张
            builder.getPageSetup().setOrientation(Orientation.LANDSCAPE);
        }
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
        long now = System.currentTimeMillis();
        System.out.println("拼接成功，共耗时：" + (now - old) + "毫秒");
        return mainDoc;
    }

    @SuppressWarnings("rawtypes")
    private static void insertDocumentAfterNode(Node insertAfterNode, Document mainDoc, Document srcDoc) throws Exception {
        if (insertAfterNode.getNodeType() != 8 & insertAfterNode.getNodeType() != 5) {
            throw new Exception("The destination node should be either a paragraph or table.");
        } else {
            CompositeNode dstStory = insertAfterNode.getParentNode();
            while (null != srcDoc.getLastSection().getBody().getLastParagraph()
                    && !srcDoc.getLastSection().getBody().getLastParagraph().hasChildNodes()) {
                srcDoc.getLastSection().getBody().getLastParagraph().remove();
            }
            NodeImporter importer = new NodeImporter(srcDoc, mainDoc, 1);
            int sectCount = srcDoc.getSections().getCount();
            for (int sectIndex = 0; sectIndex < sectCount; ++sectIndex) {
                Section srcSection = srcDoc.getSections().get(sectIndex);
                int nodeCount = srcSection.getBody().getChildNodes().getCount();
                for (int nodeIndex = 0; nodeIndex < nodeCount; ++nodeIndex) {
                    Node srcNode = srcSection.getBody().getChildNodes().get(nodeIndex);
                    Node newNode = importer.importNode(srcNode, true);
                    dstStory.insertAfter(newNode, insertAfterNode);
                    insertAfterNode = newNode;
                }
            }
        }
    }

}
