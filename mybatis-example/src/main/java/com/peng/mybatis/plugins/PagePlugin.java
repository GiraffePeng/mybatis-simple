package com.peng.mybatis.plugins;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

//声明要拦截的四大对象之一的Executor,并声明具体拦截的是哪个方法
@Intercepts({@Signature(type = Executor.class, method = "query", 
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PagePlugin implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//获取query方法的参数列表的参数
		MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
		
		Map<String, Object> param = (Map<String, Object>) invocation.getArgs()[1];
		//判断传入的参数中是否page对象，要求page传入到第一位的参数位置。如果存在page，则进行分页操作
		if(!(param.get("arg0") instanceof Page)) {
			return invocation.proceed();
		}
		//获取到分页对象
		Page page = (Page) param.get("arg0");
		//将分页对象在参数中删除
		param.remove("arg0");
		param.remove("param1");
		//重新组装入参参数
		Set<String> keySet = param.keySet();
		if(keySet.size() > 2) {
			for (int i = 0; i < keySet.size(); i++) {
				String key = (String) keySet.toArray()[i];
				Object object = param.get(key);
				int index = Integer.parseInt(key.substring(key.length() - 1, key.length()));
				param.put(key.substring(0, key.length() - 1 ) + (index - 1), object);
				param.remove(key);
			}
		}else {
			Set<Entry<String, Object>> entrySet = param.entrySet();
			Object value = null;
			for (Entry<String, Object> entry : entrySet) {
				value = entry.getValue();
				break;
			}
			invocation.getArgs()[1] = (Map<String, Object>) value;
		}
		//获取到查询sql
		BoundSql boundSql = ms.getBoundSql(invocation.getArgs()[1]);
		//将原 RowBounds 参数设为 RowBounds.DEFAULT，关闭 MyBatis 内置的分页机制
		invocation.getArgs()[2] = RowBounds.DEFAULT;
		
        String sql = boundSql.getSql();
        //更换原有的sql逻辑,拼接limit
        String limit = String.format("LIMIT %d,%d", page.getCurrent(), page.getSize());
        sql = sql + " " + limit;
        
        SqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), sql, boundSql.getParameterMappings());

        // 修改原来的sqlSource
        Field field = MappedStatement.class.getDeclaredField("sqlSource");
        field.setAccessible(true);
        field.set(ms, sqlSource);
        
		return invocation.proceed();
	}

}
