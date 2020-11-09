package com.wangwb.web.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.wangwb.web.common.bean.MongoBean;



/**
 * 	mongodb业务类 collection:MongoBean
 * @author wangwb
 *
 */
public class MongoDbService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbService.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 保存对象
	 * @param book
	 * @return
	 */
	public String saveObj(MongoBean mongoBean) {
		mongoBean.setId("001");
		mongoBean.setName("wangwb");
		mongoTemplate.save(mongoBean);
		return "添加成功";
	}
	
	/**
	 * 查询collection:MongoBean所有数据
	 * @return
	 */
	public List<MongoBean> findAll() {
		return mongoTemplate.findAll(MongoBean.class);
	}

	/***
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public MongoBean getBeanById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));//Criteria 标准、准则
		return mongoTemplate.findOne(query, MongoBean.class);
	}

	/**
	 * 根据名称查询
	 *
	 * @param name
	 * @return
	 */
	public MongoBean getBeanByName(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		return mongoTemplate.findOne(query, MongoBean.class);
	}

	/**
	 * 更新对象
	 *
	 * @param book
	 * @return
	 */
	public String updateBean(MongoBean mongoBean) {
		//查询出需要更新的数据
		Query query = new Query(Criteria.where("_id").is(mongoBean.getId()));
		//更新内容
		Update update = new Update().set("name", mongoBean.getName()).set("createTime",new Date());
		// updateFirst 更新查询返回结果集的第一条
		mongoTemplate.updateFirst(query, update, MongoBean.class);
		// updateMulti 更新查询返回结果集的全部
		// mongoTemplate.updateMulti(query,update,Book.class);
		// upsert 更新对象不存在则去添加
		// mongoTemplate.upsert(query,update,Book.class);
		return "success";
	}

	/***
	 * 删除对象
	 * @param book
	 * @return
	 */
	public String deleteBean(MongoBean mongoBean) {
		mongoTemplate.remove(mongoBean);
		return "success";
	}

	/**
	 * 根据id删除
	 *
	 * @param id
	 * @return
	 */
	public String deleteBeanById(String id) {
		// findOne
		MongoBean mongoBean = getBeanById(id);
		// delete
		deleteBean(mongoBean);
		return "success";
	}
	
	/**
	 * 模糊查询
	 * @param search
	 * @return
	 */
	public List<MongoBean> findByLikes(String search){
		Query query = new Query();
		Criteria criteria = new Criteria();
		Pattern pattern = Pattern.compile("^.*" + search + ".*$" , Pattern.CASE_INSENSITIVE);
		criteria.where("name").regex(pattern);
		List<MongoBean> lists = mongoTemplate.findAllAndRemove(query, MongoBean.class);
		return lists;
	}
	
}
