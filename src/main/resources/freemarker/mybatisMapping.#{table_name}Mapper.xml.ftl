<?xml version="1.0" encoding="UTF-8" ?> 
<!DOCTYPE mapper 
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package}.dao.${table_name}Dao">
	<resultMap type="${package}.domain.${table_name}DO" id="${table_name}Result">
		<id property="${j_primary_key}" column="${primary_key}" />
		<#list table_columns as column><#if column.isPrimary != "1">
		<result property="${column.dataName}" column="${column.columnName}" />
		</#if></#list>
	</resultMap>
	
	<sql id="BaseSQL">
		<#list table_columns as column>${raw_table?substring(0,1)}.${column.columnName}<#if column_has_next>,</#if></#list>
	</sql>
	
	<sql id="Condition">
		1=1
		<#list table_columns as column>
		<if test="${column.dataName} != null">
			AND ${raw_table?substring(0,1)}.${column.columnName} = #${r"{"}${column.dataName}}
		</if>
		</#list>
	</sql>
	
	<!-- 根据主键查询 -->
	<select id="select${table_name}ById" parameterType="java.lang.Long" resultMap="${table_name}Result">
		SELECT
			 <include refid="BaseSQL" />
		FROM 
			${raw_table} ${raw_table?substring(0,1)}
		WHERE 
			${raw_table?substring(0,1)}.${primary_key}=#${r"{"}${j_primary_key}}
	</select>

	<!-- 查询列表，一次最多查出1000条 -->
	<select id="select${table_name}List" parameterType="${package}.bo.Query${table_name}BO" resultMap="${table_name}Result">
		SELECT 
			<include refid="BaseSQL" />
		FROM 
			${raw_table} ${raw_table?substring(0,1)}
		WHERE
			<include refid="Condition" />
		LIMIT 1000
	</select>
	
	<!-- 分页查找 -->
	<select id="select${table_name}Page" parameterType="${package}.bo.Query${table_name}BO" resultMap="${table_name}Result">
		SELECT 
			<include refid="BaseSQL" />
		FROM 
			${raw_table} ${raw_table?substring(0,1)}
		WHERE
			<include refid="Condition" />
		LIMIT ${r'#{pageIndex},#{pageSize}'}
	</select>
	
	<!-- 分页计数 -->
	<select id="select${table_name}Count" parameterType="${package}.bo.Query${table_name}BO" resultType="java.lang.Integer">
		SELECT 
			COUNT(1)
		FROM 
			${raw_table} ${raw_table?substring(0,1)}
		WHERE
			<include refid="Condition" />
	</select>

	<!-- 添加 -->
	<insert id="insert${table_name}" parameterType="${package}.domain.${table_name}DO" useGeneratedKeys="true" keyProperty="${j_primary_key}">
		INSERT INTO ${raw_table}(<#list table_columns as column><#if column.isPrimary != "1">${column.columnName}<#if column_has_next>,</#if></#if></#list>) 
		VALUES (<#list table_columns as column><#if column.isPrimary != "1">#${r"{"}${column.dataName}}<#if column_has_next>,</#if></#if></#list>)
	</insert>

	<!-- 完全修改 -->
	<update id="update${table_name}" parameterType="${package}.domain.${table_name}DO">
		UPDATE 
			${raw_table} 
		<set>
		<#list table_columns as column>
			<#if column.isPrimary != "1">
			${column.columnName} = #${r"{"}${column.dataName}}<#if column_has_next>,</#if>
			</#if>
		</#list>
		</set>
		WHERE 
			${primary_key}=#${r"{"}${j_primary_key}}
	</update>
	
	<!-- 选择性修改 -->
	<update id="update${table_name}Selective" parameterType="${package}.domain.${table_name}DO">
		UPDATE 
			${raw_table} 
		<set>
		<#list table_columns as column>
			<#if column.isPrimary != "1">
			<if test="${column.dataName} != null">
				${column.columnName} = #${r"{"}${column.dataName}}<#if column_has_next>,</#if>
			</if>
			</#if>
		</#list>
		</set>
		WHERE 
			${primary_key}=#${r"{"}${j_primary_key}}
	</update>

	<!-- 删除 -->
	<delete id="delete${table_name}" parameterType="java.lang.Long">
		DELETE FROM ${raw_table} WHERE ${primary_key}=#${r"{"}${j_primary_key}}
	</delete>

</mapper>