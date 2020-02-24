package com.lemon.web.utils;/**
 * Created by TW on 2019/10/30 11:17
 */

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: JavaAutoTest
 * @description: 测试报告
 * @author: liu yan
 * @create: 2019-10-30 11:17
 */
public class IReporterImp implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> iSuites, String outputDirectory) {

        ArrayList<String> list = new ArrayList<String>();
        for (ISuite iSuite : iSuites) {
            Map<String, ISuiteResult> iSuiteResultMap = iSuite.getResults();
            System.out.println("所有执行的方法：" + iSuite.getAllInvokedMethods());
            System.out.println("获取所有@Test标注的方法：" + iSuite.getAllMethods());
            System.out.println("suiteName：" + iSuite.getName());
            System.out.println("输出路径：" + iSuite.getOutputDirectory());
            System.out.println("并发方式：" + iSuite.getParallel());
            System.out.println("报告路径：" + outputDirectory);

            for (ISuiteResult iSuiteResult : iSuiteResultMap.values()) {
                ITestContext iTestContext = iSuiteResult.getTestContext();
                IResultMap iResultMap = iTestContext.getPassedTests();

                Set<ITestResult> iTestResultset = iResultMap.getAllResults();
                for (ITestResult iTestResult : iTestResultset) {
                    System.out.println("测试方法：" + iTestResult.getName());
                    System.out.println("执行结果（1-成功，2-失败，3-skip）：" + iTestResult.getStatus());
                    System.out.println("开始时间：" + iTestResult.getStartMillis());
                    System.out.println("结束时间：" + iTestResult.getEndMillis());
                }
            }
        }
    }

}
