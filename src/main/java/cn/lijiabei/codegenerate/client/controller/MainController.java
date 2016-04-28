package cn.lijiabei.codegenerate.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.lijiabei.codegenerate.client.bo.GenerateInfo;
import cn.lijiabei.codegenerate.client.bo.ProjectFreemarkBO;
import cn.lijiabei.codegenerate.client.domain.CodeColumn;
import cn.lijiabei.codegenerate.client.domain.CodeTable;
import cn.lijiabei.codegenerate.client.error.DBException;
import cn.lijiabei.codegenerate.client.error.ProcessException;
import cn.lijiabei.codegenerate.client.error.TableModuleException;
import cn.lijiabei.codegenerate.client.module.GenerateProcess;
import cn.lijiabei.codegenerate.client.module.TableModule;
import cn.lijiabei.codegenerate.client.utils.ValidateUtils;
import cn.lijiabei.codegenerate.client.view.PropertyFrame;
import cn.lijiabei.codegenerate.client.view.TableFrame;

public class MainController {

	static PropertyFrame propertyFrame = null;
	static TableFrame tableFrame = null;

	public static void main(String[] args) {
		ActionListener stepOneActionListener = new StepOneActionListener();
		ActionListener backActionListener = new BackActionListener();
		ActionListener generateActionListener = new GenerateActionListener();

		propertyFrame = new PropertyFrame(stepOneActionListener);
		propertyFrame.pack();
		propertyFrame.centered();
		propertyFrame.setVisible(true);

		tableFrame = new TableFrame(generateActionListener, backActionListener);
	}

	// 下一步，触发选择表格
	public static class StepOneActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			GenerateInfo generateInfo = propertyFrame.getGenerateInfo();
			String error = ValidateUtils.checkGenerateInfo(generateInfo);
			if (StringUtils.isNotEmpty(error)) {
				JOptionPane.showMessageDialog(null, error, "提示", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				TableModule tableModule = new TableModule();
				List<CodeTable> codeTableList = null;
				try {
					codeTableList = tableModule.queryCodeTableList(generateInfo);
				} catch (TableModuleException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "系统错误", JOptionPane.ERROR_MESSAGE);
					return;
				} catch (DBException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "系统错误", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (CollectionUtils.isEmpty(codeTableList)) {
					JOptionPane.showMessageDialog(null, "查询无数据", "提示", JOptionPane.WARNING_MESSAGE);
					return;
				}

				tableFrame.setCodeTable(codeTableList);// 设置表格数据
				propertyFrame.setVisible(false);// 前页面隐藏
				tableFrame.pack();// 表格页面行宽自动适应
				tableFrame.centered();// 表格显示在屏幕中央
				tableFrame.setVisible(true);// 表格设置为可见
			}
		}
	}

	// 开始生成代码
	public static class GenerateActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			GenerateInfo generateInfo = propertyFrame.getGenerateInfo();// 第一步设置的属性

			List<String> namelist = tableFrame.getSelectedTableNames();
			CodeTable codeTable = null;
			List<CodeTable> tableList = new ArrayList<CodeTable>();
			TableModule tableModule = new TableModule();

			try {
				List<CodeColumn> codeColumnList = null;
				for (String tableName : namelist) {
					codeTable = new CodeTable();
					codeTable.setTableName(tableName);
					codeColumnList = tableModule.queryCodeColumnList(generateInfo, tableName);
					codeTable.setColumns(codeColumnList);
					tableList.add(codeTable);
				}
			} catch (TableModuleException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "系统错误", JOptionPane.ERROR_MESSAGE);
				return;
			} catch (DBException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "系统错误", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// 模板路径
			String freemarkpath;
			try {
				freemarkpath = URLDecoder.decode(this.getClass().getResource("/freemarker").getPath(), "UTF-8");
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(null, e2.getMessage(), "系统错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 工程初始化文件路径
			String projectTmpletePath;
			try {
				projectTmpletePath = URLDecoder.decode(this.getClass().getResource("/projectTmp").getPath(), "UTF-8");
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(null, e2.getMessage(), "系统错误", JOptionPane.ERROR_MESSAGE);
				return;
			}

			ProjectFreemarkBO projectFreemarkBO = new ProjectFreemarkBO();
			projectFreemarkBO.setFreemarkpath(freemarkpath);
			projectFreemarkBO.setPackagePath(generateInfo.getPackagePath());
			projectFreemarkBO.setProjectTmpletePath(projectTmpletePath);
			projectFreemarkBO.setUserPath(generateInfo.getUserPath());

			// 生产流程对象
			GenerateProcess generateProcess = new GenerateProcess();
			// 将表、列原数据加工下
			if (!generateProcess.polishTableList(tableList, generateInfo.getDatabaseType())) {
				JOptionPane.showMessageDialog(null, "表数据加工异常！", "系统错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 初始化用户目录工程文件夹
			generateProcess.createTargetDir(projectFreemarkBO);
			// 生成文件
			try {
				generateProcess.doMainProcess(projectFreemarkBO, tableList);
			} catch (ProcessException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "系统错误", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				return;
			}
			JOptionPane.showMessageDialog(null, "成功，路径" + generateInfo.getUserPath(), "提示", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	// 上一步，返回属性设置
	public static class BackActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (null != tableFrame) {
				tableFrame.setVisible(false);
			}
			if (null != propertyFrame) {
				propertyFrame.setVisible(true);
			}
		}

	}
}
