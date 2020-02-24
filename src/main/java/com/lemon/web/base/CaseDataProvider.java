package com.lemon.web.base;

import com.lemon.web.testcase.login.LoginFailureData;
import com.lemon.web.utils.ExcelUtil;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 所有测试用例对应的数据提供者
 * @author happy
 *
 */
public class CaseDataProvider {
	@DataProvider
	public static Object[][] dp(Method method) {
		// 获得执行测试用例的方法名
		String methodName = method.getName();
		// String methodName = "register_failure_test_case";
		// 分割
		String[] array = methodName.split("_");
		// 得到模块名、功能名、文件夹名、类名的第一部分 --》login
		String folderName = array[0];
		// 得到检查点的名称、类名的第二部分
		String caseFileName = array[1];
		// 组装出excel的路径:/testcase/login/failure.xlsx
		String excelPath = "/testcase/" + folderName + "/" + caseFileName + ".xlsx";
		// 得到类名的第一部分，首字母大写
		String classNamePart1 = (folderName.charAt(0) + "").toUpperCase() + folderName.substring(1);
		// 得到类名的第二部分，首字母大写
		String classNamePart2 = (caseFileName.charAt(0) + "").toUpperCase() + caseFileName.substring(1);
		String totalClassName = classNamePart1 + classNamePart2 + "Data";// LoginFailureData
		String classPath = "com.lemon.web.testcase." + folderName + "." + totalClassName;
		System.out.println(classPath);
		Class clazz = null;
		try {
			clazz = Class.forName(classPath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//得到数据列表
		List<Object> objs = ExcelUtil.readExcel(excelPath, 0, clazz);
		//组装到object类型的二维数组
		// 创建一个二维数组
		Object[][] datas = new Object[objs.size()][];
		for (int index = 0; index < objs.size(); index++) {
			Object[] itemArray = { objs.get(index) };// 每个LoginFailData对象保存到一个一维数组
			datas[index] = itemArray;// 把每个一维数组放到二维数组对应索引
		}
		
		return datas;

	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		/**
		 * 测试用例方法名：login_failure_test_case -->LoginFailureData
		 * 测试用例方法名：login_success_test_case -->LoginSuccessData
		 * 测试用例执行过程中：数据->excel 元素->xml login:元素 failure:login_failure--》数据
		 * 通过login和failure去确定要封装回的对象的类型 test_case:确定的
		 */
		String methodName = "login_failure_test_case";
		// String methodName = "register_failure_test_case";
		// 分割
		String[] array = methodName.split("_");
		// 得到模块名、功能名、文件夹名、类名的第一部分 --》login
		String folderName = array[0];
		// 得到检查点的名称、类名的第二部分
		String caseFileName = array[1];
		// 组装出excel的路径:/testcase/login/failure.xlsx
		String excelPath = "/testcase/" + folderName + "/" + caseFileName + ".xlsx";
		System.out.println(excelPath);
		// class --》LoginFailureData
		// String className = "LoginFailureData";
		// 得到类名的第一部分，首字母大写
		String classNamePart1 = (folderName.charAt(0) + "").toUpperCase() + folderName.substring(1);
		// 得到类名的第二部分，首字母大写
		String classNamePart2 = (caseFileName.charAt(0) + "").toUpperCase() + caseFileName.substring(1);
		String totalClassName = classNamePart1 + classNamePart2 + "Data";// LoginFailureData
		String classPath = "com.lemon.web.testcase." + folderName + "." + totalClassName;
		System.out.println(classPath);
		Class clazz = Class.forName(classPath);
		List<Object> objs = ExcelUtil.readExcel(excelPath, 0, clazz);
		for (Object object : objs) {
			System.out.println(object);
		}

	}
}
