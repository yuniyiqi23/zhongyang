package com.zhongyang.web.utils;/**
 * Created by TW on 2019/11/4 18:42
 */

import com.zhongyang.web.pojo.Locator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: JavaAutoTest
 * @description: 网页定位元素工具类
 * @author: liu yan
 * @create: 2019-11-04 18:42
 */
public class LocatorUtils {

    private static Map<String, Map<String, Locator>> getAllLocators = new HashMap<String, Map<String, Locator>>();

    /**
     * @Description: 静态代码块，获取全部定位元素
     * @Param:
     * @return:
     * @Author: Adam
     * @Date: 2019/11/4
     */
    static {
        String pagePath = LocatorUtils.class.getClassLoader().getResource
                ("page").getPath();
        readXMLFiles(pagePath);
//        getAllLocators = getPageLocators();
    }


    public static Map<String, Locator> getLocatorsByPageName(String pageName) {
        return getAllLocators.get(pageName);
    }

    public static Locator getLocatorByPageNameAndLocatorName(String pageName,
                                                             String locatorName) {
        return getAllLocators.get(pageName).get(locatorName);
    }

    /**
     * @Description: 获取页面定位元素
     * @Param: []
     * @return: java.util.Map<java.lang.String                               ,                               java.util.Map                               <                               java.lang.String                               ,                               Locator>>
     * @Author: Adam
     * @Date: 2019/11/4
     */
    private static void getPageLocators(String filePath) {
        InputStream inputStream = null;
        SAXReader saxReader = new SAXReader();
//        Map<String, Map<String, Locator>> pagesMap = new HashMap<>();
        try {
            inputStream = new FileInputStream(filePath);
            Document document = saxReader.read(inputStream);
            Element root = document.getRootElement();
//            List<Element> pageElements = root.elements("page");
            // 查找页面
//            for (Element page : pageElements) {
//                System.out.println(page.attributeValue("pageName"));
            List<Element> locatorElements = root.elements("locator");
            // 查找定位元素
            Map<String, Locator> locatorList = new HashMap<String, Locator>();
            for (Element item : locatorElements) {
                String name = item.attributeValue("name");
                Locator locator = new Locator(name,
                        item.attributeValue("type"), item.attributeValue
                        ("value"));
                locatorList.put(name, locator);
            }
            getAllLocators.put(root.attributeValue("pageName"), locatorList);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 获取文件路径下的所有xml文件
     * @Param: [filePath]
     * @return: void
     * @Author: Adam
     * @Date: 2019/11/5
     */
    public static void readXMLFiles(String filePath) {
        File file = new File(filePath);
        File[] subFiles = file.listFiles();
        for (File item : subFiles) {
            if (item.isFile() && item.getName().endsWith(".xml")) {
                // 解析xml文件
                getPageLocators(item.getAbsolutePath());
//                System.out.println(item.getAbsolutePath());
            } else if (item.isDirectory()) {
                readXMLFiles(item.getAbsolutePath());
            }
//            System.out.println(item.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        Locator locator = getLocatorByPageNameAndLocatorName("登录页面", "登录按钮");
        System.out.println(locator);
    }

}
