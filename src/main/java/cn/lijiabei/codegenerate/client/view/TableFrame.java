package cn.lijiabei.codegenerate.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import cn.lijiabei.codegenerate.client.domain.CodeTable;

public class TableFrame extends JFrame {

	private static final long serialVersionUID = -5788069886283682284L;

	private JPanel contentPane;
	private JTable jTable;
	private MyTableModel tmd;

	public TableFrame(ActionListener generate, ActionListener back){
		super("选择表");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

		Font font = new Font("宋体", Font.PLAIN, 15);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);

		tmd = new MyTableModel();
		jTable = new JTable(tmd);
		TableColumn firstColumn = jTable.getColumnModel().getColumn(0);
		firstColumn.setMaxWidth(40);
		firstColumn.setCellEditor(jTable.getDefaultEditor(Boolean.class));
		scrollPane.setViewportView(jTable);

		// next
		JPanel row8 = new JPanel();
		contentPane.add(row8);
		JButton backButton = new JButton("PREVIOUS STEP");
		backButton.addActionListener(back);
		backButton.setFont(font);
		row8.add(backButton);

		JButton submitButton = new JButton("NEXT STEP");
		submitButton.addActionListener(generate);
		submitButton.setFont(font);
		row8.add(submitButton);
	}

	// 布局居中方法
	public void centered() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int w = this.getWidth();
		int h = this.getHeight();
		this.setBounds((screenSize.width - w) / 2, (screenSize.height - h) / 2, w, h);
	}

	public void setCodeTable(List<CodeTable> codeTableList) {
		if (null != codeTableList) {
			tmd.setData(codeTableList);
		}
	}

	public List<String> getSelectedTableNames() {
		return tmd.getSelectedTableNames();
	}

	class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 741336608253335284L;

		private String[] column = { "#", "table name", "table comment" };

		private List<CodeTable> codeTableList = null;

		public MyTableModel(){
			codeTableList = new ArrayList<CodeTable>();
		}

		public void setData(List<CodeTable> codeTableList) {
			this.codeTableList = codeTableList;
			if (null != jTable) {
				jTable.updateUI();
			}
		}

		public List<String> getSelectedTableNames() {
			List<String> tableNameList = new ArrayList<String>();
			if (null != codeTableList) {
				for (CodeTable codeTable : codeTableList) {
					if (codeTable.getSelected()) tableNameList.add(codeTable.getTableName());
				}
			}
			return tableNameList;
		}

		public int getRowCount() {
			return codeTableList.size();
		}

		public String getColumnName(int col) {
			return column[col];
		}

		public int getColumnCount() {
			return column.length;
		}

		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 0) return Boolean.class;
			return Object.class;
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 0) return true;
			return false;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex > codeTableList.size()) return null;

			if (columnIndex == 0) return codeTableList.get(rowIndex).getSelected();
			if (columnIndex == 1) return codeTableList.get(rowIndex).getTableName();
			if (columnIndex == 2) return codeTableList.get(rowIndex).getTableComments();
			return null;
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (rowIndex > codeTableList.size()) return;

			if (columnIndex == 0) codeTableList.get(rowIndex).setSelected((Boolean) aValue);
		}

	}

}
