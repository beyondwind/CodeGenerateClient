package cn.lijiabei.codegenerate.client.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cn.lijiabei.codegenerate.client.error.DBException;
import cn.lijiabei.codegenerate.client.utils.SelectSQLs;

public class MySQLConnectionCreater implements ConnectionCreater {

	private static final String mysqlUrl = "jdbc:mysql://";
	private static final String jdbcDriverClassName = "com.mysql.jdbc.Driver";

	public Connection createConnection(String ip, int port, String dbName, String userName, String password) throws DBException {
		return createConnection(ip, port, dbName, userName, password, "UTF8");
	}

	public Connection createConnection(String ip, int port, String dbName, String userName, String password, String characterEncoding) throws DBException {
		Connection connection = null;
		try {
			Class.forName(jdbcDriverClassName);// 动态加载mysql驱动
			StringBuffer sb = new StringBuffer(mysqlUrl);
			sb.append(ip).append(":").append(port).append("/information_schema?useUnicode=true&characterEncoding=").append(characterEncoding);
			connection = DriverManager.getConnection(sb.toString(), userName, password);
		} catch (ClassNotFoundException e) {
			throw new DBException("com.mysql.jdbc.Driver载入失败！", e);
		} catch (SQLException e) {
			throw new DBException("mysql connection 生成失败！", e);
		}
		return connection;
	}

	public String getQueryTablesSQL() {
		return SelectSQLs.MYSQL_QUERY_TABLES;
	}

	public String getQueryColumnsSQL() {
		return SelectSQLs.MYSQL_QUERY_COLUMNS;
	}

}
