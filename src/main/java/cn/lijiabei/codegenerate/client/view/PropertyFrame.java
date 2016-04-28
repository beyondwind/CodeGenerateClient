package cn.lijiabei.codegenerate.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cn.lijiabei.codegenerate.client.bo.GenerateInfo;
import cn.lijiabei.codegenerate.client.utils.GenerateConstants;

public class PropertyFrame extends JFrame {

	private static final long serialVersionUID = 108628408475442357L;

	private JPanel contentPane;
	private JTextField ipAddressText;
	private JTextField portText;
	private JTextField userNameText;
	private JPasswordField passwordText;
	private JRadioButton mysqlRadio;
	private JRadioButton oracleRadio;
	private JTextField dbNameText;
	private JTextField packagePathText;
	private JTextField userPathText;
	private JButton fileChooseBtn;
	private JFileChooser jfc;

	public PropertyFrame(ActionListener actionListener){
		super("设置属性");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

		Font font = new Font("宋体", Font.PLAIN, 15);

		// IP Address begin
		JPanel row1 = new JPanel();
		contentPane.add(row1);

		JLabel ipAddressLabel = new JLabel("  IP  Address");
		ipAddressLabel.setFont(font);
		row1.add(ipAddressLabel);

		ipAddressText = new JTextField();
		ipAddressText.setFont(font);
		ipAddressText.setColumns(10);
		ipAddressText.setText("127.0.0.1");
		row1.add(ipAddressText);
		// IP Address end

		// Port begin
		JPanel row2 = new JPanel();
		contentPane.add(row2);

		JLabel portLabel = new JLabel("            Port");
		portLabel.setFont(font);
		row2.add(portLabel);

		portText = new JTextField();
		portText.setFont(font);
		portText.setColumns(10);
		portText.setText("3306");
		row2.add(portText);
		// Port end

		// User Name begin
		JPanel row3 = new JPanel();
		contentPane.add(row3);

		JLabel userNameLabel = new JLabel("  User Name");
		userNameLabel.setFont(font);
		row3.add(userNameLabel);

		userNameText = new JTextField();
		userNameText.setFont(font);
		userNameText.setColumns(10);
		userNameText.setText("root");
		row3.add(userNameText);
		// User Name end

		// User Password begin
		JPanel row4 = new JPanel();
		contentPane.add(row4);

		JLabel passwordLabel = new JLabel("    Password");
		passwordLabel.setFont(font);
		row4.add(passwordLabel);

		passwordText = new JPasswordField();
		passwordText.setFont(font);
		passwordText.setColumns(10);
		passwordText.setText("root");
		row4.add(passwordText);
		// User Password end

		// Database Type begin
		JPanel row5 = new JPanel();
		contentPane.add(row5);

		JLabel dbTypeLabel = new JLabel("     DB Type");
		dbTypeLabel.setFont(font);
		row5.add(dbTypeLabel);

		mysqlRadio = new JRadioButton("MySQL");// 创建单选按钮
		mysqlRadio.setSelected(true);
		row5.add(mysqlRadio);// 应用单选按钮

		oracleRadio = new JRadioButton("Oracle");// 创建单选按钮
		row5.add(oracleRadio);// 应用单选按钮

		ButtonGroup group = new ButtonGroup();// 创建单选按钮组
		group.add(mysqlRadio);// 将mysqlRadio增加到单选按钮组中
		group.add(oracleRadio);// 将oracleRadio增加到单选按钮组中
		// Database Type end

		// Database Name/SID begin
		JPanel row6 = new JPanel();
		contentPane.add(row6);

		JLabel dbNameLabel = new JLabel("     DB Name");
		dbNameLabel.setFont(font);
		row6.add(dbNameLabel);

		dbNameText = new JTextField();
		dbNameText.setFont(font);
		dbNameText.setColumns(10);
		dbNameText.setText("gyl_b2bmall");
		row6.add(dbNameText);
		// Database Name/SID end

		// Package Path begin
		JPanel row7 = new JPanel();
		contentPane.add(row7);

		JLabel packagePathLabel = new JLabel("Package Path");
		packagePathLabel.setFont(font);
		row7.add(packagePathLabel);

		packagePathText = new JTextField();
		packagePathText.setFont(font);
		packagePathText.setColumns(10);
		packagePathText.setText("cn.lijiabei.mall");
		row7.add(packagePathText);
		// Package Path end

		JPanel row8 = new JPanel();
		contentPane.add(row8);
		JLabel userPathLabel = new JLabel("        User Path");
		userPathLabel.setFont(font);
		row8.add(userPathLabel);

		userPathText = new JTextField();
		userPathText.setFont(font);
		userPathText.setColumns(15);
		userPathText.setText("/Users/lijiabei/mywork");
		row8.add(userPathText);

		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("/Users/lijiabei/"));

		fileChooseBtn = new JButton("...");
		fileChooseBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
				int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
				if (state == 1) {
					return;
				} else {
					File f = jfc.getSelectedFile();// f为选择到的目录
					userPathText.setText(f.getAbsolutePath());
				}
			}
		});
		row8.add(fileChooseBtn);

		// next
		JPanel row9 = new JPanel();
		contentPane.add(row9);
		JButton submitButton = new JButton("NEXT STEP");
		submitButton.addActionListener(actionListener);
		submitButton.setFont(font);
		row9.add(submitButton);
	}

	// 布局居中方法
	public void centered() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int w = this.getWidth();
		int h = this.getHeight();
		this.setBounds((screenSize.width - w) / 2, (screenSize.height - h) / 2, w, h);
	}

	public GenerateInfo getGenerateInfo() {
		GenerateInfo generateInfo = new GenerateInfo();
		generateInfo.setIpAddress(ipAddressText.getText());
		generateInfo.setPort(portText.getText());
		generateInfo.setUserName(userNameText.getText());
		generateInfo.setPassword(new String(passwordText.getPassword()));
		if (mysqlRadio.isSelected()) {
			generateInfo.setDatabaseType(GenerateConstants.TYPE_MYSQL);
		} else if (oracleRadio.isSelected()) {
			generateInfo.setDatabaseType(GenerateConstants.TYPE_ORACLE);
		}
		generateInfo.setDbName(dbNameText.getText());
		generateInfo.setPackagePath(packagePathText.getText());
		generateInfo.setUserPath(userPathText.getText());
		return generateInfo;
	}
}
