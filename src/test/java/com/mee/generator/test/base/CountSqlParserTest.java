//package com.mee.generator.test.base;
//
//import com.github.pagehelper.Dialect;
//import com.github.pagehelper.parser.CountSqlParser;
//import com.github.pagehelper.util.ExecutorUtil;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.junit.jupiter.api.Test;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//
//public class CountSqlParserTest {
//
//
//    @Test
//    public void test01(){
//        // final String sql = "select count(0) from sys_user";
//        final String sql = "        SELECT distinct m.menu_id as id,m.* FROM sys_menu m inner join sys_roles_menus r on m.menu_id = r.menu_id\n" +
//                "        where 1=1"+
//
//                "        order by pid nulls first,m.menu_sort asc";
//        CountSqlParser parser = new CountSqlParser();
//        String smartCountSql = parser.getSmartCountSql(sql,"1");
//        System.out.println(smartCountSql);
//    }
//
////    private Long count(Executor executor, MappedStatement ms, Object parameter,
////                       RowBounds rowBounds, ResultHandler resultHandler,
////                       BoundSql boundSql) throws SQLException {
////        return ExecutorUtil.executeAutoCount(this.dialect, executor, countMs, parameter, boundSql, rowBounds, resultHandler);
////    }
//
//    public static Long executeAutoCount(Dialect dialect, Executor executor, MappedStatement countMs,
//                                        Object parameter, BoundSql boundSql,
//                                        RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
//         // Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
//        //创建 count 查询的缓存 key
//        CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
//        //调用方言获取 count sql
//        String countSql = "";
//        //countKey.update(countSql);
//        BoundSql countBoundSql = new BoundSql(countMs.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
//        //当使用动态 SQL 时，可能会产生临时的参数，这些参数需要手动设置到新的 BoundSql 中
//        // for (String key : additionalParameters.keySet()) {
//        //     countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
//        // }
//        //对 boundSql 的拦截处理
//        // if (dialect instanceof BoundSqlInterceptor.Chain) {
//        //     countBoundSql = ((BoundSqlInterceptor.Chain) dialect).doBoundSql(BoundSqlInterceptor.Type.COUNT_SQL, countBoundSql, countKey);
//        // }
//        //执行 count 查询
//        Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
//        //某些数据（如 TDEngine）查询 count 无结果时返回 null
//        if (countResultList == null || ((List) countResultList).isEmpty()) {
//            return 0L;
//        }
//        return ((Number) ((List) countResultList).get(0)).longValue();
//    }
//
//}
