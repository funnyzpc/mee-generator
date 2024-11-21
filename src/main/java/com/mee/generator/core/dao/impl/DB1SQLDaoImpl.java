package com.mee.generator.core.dao.impl;


import com.mee.generator.core.dao.DB1SQLDao;
import com.mee.generator.service.impl.SeqGenServiceImpl;
import com.mee.generator.util.JacksonUtil;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
* DAO操作
* @className    DB1SQLDaoImpl
* @author       shadow
* @date         2023/7/5 11:05
* @version      1.0
*/
@Repository
public class DB1SQLDaoImpl implements DB1SQLDao {
	private static final Logger log = LoggerFactory.getLogger(DB1SQLDaoImpl.class);

	@Resource
	private SeqGenServiceImpl seqGenService;

	@Autowired
//	@Qualifier("db1SqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	@Override
	public List query(String id) {
		return sqlSession.selectList(id);
	}
	@Override
	public List query(String id, int count) {
		return sqlSession.selectList(id, null, new RowBounds(0, count));
	}

	@Override
	public List query(String id, Map params) {
		return sqlSession.selectList(id, params);
	}
	@Override
	public List query(String id, Map params, int count) {
		return sqlSession.selectList(id, params, new RowBounds(0, count));
	}

	@Override
	public <T> T queryOne(String id,Map params){
		return sqlSession.selectOne(id,params);
	}

	@Override
	public <T extends Object,R> List<R> query(String id, T params){
		return sqlSession.selectList(id,params);
	}

	@Override
	public int create(String id, Object params) {
		return sqlSession.insert(id, params);
	}

	@Override
	public int create(String id, Map params) {
		return sqlSession.insert(id, params);
	}

	@Override
	public int create(String id, List params) {
		return sqlSession.insert(id, params);
	}

//	@Override
//	public <P extends BaseEntity> String create(String id, P params) {
//		// 设置ID、创建人、创建日期
//		params.setId(seqGenService.genPrimaryKey());
//		params.setCreate_time(DateUtil.now());
//		if(null == params.getCreate_by()){
//			params.setCreate_by(JwtUtil.getUserId());
//		}
//		if(sqlSession.insert(id, params)>0){
//			return  params.getId();
//		}
//		return null;
//	}

	@Override
	public int update(String id, Map params) {
		return sqlSession.update(id, params);
	}

//	public <P extends BaseEntity> int update(String id, P params) {
//		// 空ID不更新
//		if(null == params.getId() || "".equals(params.getId().trim())){
//			log.error("更新记录ID为空：{}", JacksonUtil.toJsonString(params));
//			return 0;
//		}
//		return sqlSession.update(id, params);
//	}

	public  int update(String id, Object param) {
		if(null == param){
			log.error("更新记录参数不可为空：{}", JacksonUtil.toJsonString(param));
			return 0;
		}
		return sqlSession.update(id, param);
	}

	@Override
	public int delete(String id, Object params) {
		return sqlSession.delete(id, params);
	}


	@Override
	public int delete(String id, Map params) {
		return sqlSession.delete(id, params);
	}
	
    @Override
	public List query(String id, List list) {
		return sqlSession.selectList(id, list);
	}
	
//	@Override
//	public Page list(String id, int pageIdx, int pageSize) {
//		return list(id, null, pageIdx, pageSize);
//	}
//
//	@Override
//	public Page list(String id, Map params, int pageIdx, int pageSize) {
//  		Page p = new Page<>(params, pageIdx, pageSize);
//		return p.setData(sqlSession.selectList(id, p));
//	}

}
