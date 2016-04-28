package ${package}.dao;

import java.util.List;

import ${package}.bo.Query${table_name}BO;
import ${package}.domain.${table_name}DO;

public interface ${table_name}Dao {

	/** 根据主键查询 **/
	public ${table_name}DO select${table_name}ById(Long ${j_primary_key});
	
	/** 查询列表，一次最多查出1000条 **/
	public List<${table_name}DO> select${table_name}List(Query${table_name}BO query);
	
	/** 分页查找 **/
	public List<${table_name}DO> select${table_name}Page(Query${table_name}BO query);
	
	/** 分页计数 **/
	public int select${table_name}Count(Query${table_name}BO query);

	/** 添加 **/
	public int insert${table_name}(${table_name}DO ${table_name?uncap_first});

	/** 完全修改 **/
	public int update${table_name}(${table_name}DO ${table_name?uncap_first});

	/** 选择性修改 **/
	public int update${table_name}Selective(${table_name}DO ${table_name?uncap_first});

	/** 删除 **/
	public int delete${table_name}(${table_name}DO ${table_name?uncap_first});
}