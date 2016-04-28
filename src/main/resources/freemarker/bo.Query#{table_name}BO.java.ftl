package ${package}.bo;

import java.util.Date;
import com.shangban.common.bo.BaseQueryBO;

/**
 * @ClassName: ${table_name}查询BO
 * @Description: by CodeGenerate
 * @author lijiabei
 * @date ${.now?date}
 */
public class Query${table_name}BO extends BaseQueryBO{

	<#list table_columns as column>
	/** ${column.columnComments} <#if column.isPrimary = "1">( 主键 )</#if> */
	private ${column.dataType} ${column.dataName};
	</#list>
	
	<#list table_columns as column>
	/** 获取 ${column.columnComments} <#if column.isPrimary = "1">( 主键 )</#if> */
	public ${column.dataType} get${column.dataName?cap_first}(){
		return ${column.dataName};
	}
	
	/** 设定 ${column.columnComments} <#if column.isPrimary = "1">( 主键 )</#if> */
	public void set${column.dataName?cap_first}(${column.dataType} ${column.dataName}){
		this.${column.dataName} = ${column.dataName};
	}

	</#list>
}