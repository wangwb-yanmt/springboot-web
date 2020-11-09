package com.wangwb.web.common.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RedisUtil {

	@Autowired
	private RedisTemplate<String, Object> myRedisTemplate;
	
	    
	/**
	 * description:指定缓存失效时间
	 * @param key
	 * @param time
	 * @return
	 */
	public boolean expire(String key, long time) {
        try {
            if (time > 0) {
            	myRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * description:根据key获取过期时间
	 * @param key
	 * @return
	 */
	public long getExpire(String key) {
        return myRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
	
	/**
	 * description:判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
        try {
            return myRedisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * description:删除缓存
	 * @param key 可以传一个值 或多个
	 */
	 @SuppressWarnings("unchecked")
     public void del(String... key) {
         if (key != null && key.length > 0) {
             if (key.length == 1) {
            	 myRedisTemplate.delete(key[0]);
             } else {
            	 myRedisTemplate.delete(CollectionUtils.arrayToList(key));
             }
         }
     }
	 
	// ============================String=============================
	 
	 /**
	  * description:普通缓存获取
	  * @param key
	  * @return
	  */
	 public Object get(String key) {
		 return myRedisTemplate.opsForValue().get(key);

     }
	 
	 /**
	  * description:普通缓存放入
	  * @param key
	  * @param value
	  * @return
	  */
	 public boolean set(String key, Object value) {
         try {
        	 myRedisTemplate.opsForValue().set(key, value);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
	 
	 /**
	  * description:普通缓存放入并设置时间
	  * @param key
	  * @param value
	  * @param time
	  * @return
	  */
	 public boolean set(String key, Object value, long time) {
         try {
             if (time > 0) {
            	 myRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
             } else {
                 set(key, value);
             }
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }

	 /**
	  * description:递增
	  * @param key
	  * @param delta 要增加几
	  * @return
	  */
	 public long incr(String key, long delta) {
         if (delta < 0) {
             throw new RuntimeException("递增因子必须大于0");
         }
         return myRedisTemplate.opsForValue().increment(key, delta);
     }
	 
	 /**
	  * description:递减
	  * @param key
	  * @param delta 要减少几
	  * @return
	  */
	 public long decr(String key, long delta) {
         if (delta < 0) {
             throw new RuntimeException("递减因子必须大于0");
         }
         return myRedisTemplate.opsForValue().decrement(key, delta);
     }
	 
	// ================================Map=================================
	 
	 /**
	  * description:HashGet
	  * @param key	键 不能为null
	  * @param item	项 不能为null
	  * @return
	  */
	 public Object hget(String key, String item) {
         return myRedisTemplate.opsForHash().get(key, item);
     }
	 
	 /**
	  * description:获取hashKey对应的所有键值
	  * @param key	键
	  * @return	对应的多个键值
	  */
	 public Map<Object, Object> hmget(String key) {
         return myRedisTemplate.opsForHash().entries(key);
     }
	 
	 /**
	  * description:HashSet
	  * @param key	键
	  * @param map	对应多个键值
	  * @return
	  */
	 public boolean hmset(String key, Map<String, Object> map) {
         try {
        	 myRedisTemplate.opsForHash().putAll(key, map);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
	 
	 /**
	  * description:HashSet 并设置时间
	  * @param key	键
	  * @param map	对应多个键值
	  * @param time	时间(秒)
	  * @return
	  */
	 public boolean hmset(String key, Map<String, Object> map, long time) {
         try {
        	 myRedisTemplate.opsForHash().putAll(key, map);
             if (time > 0) {
                 expire(key, time);
             }
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
	 
	 /**
	  * description:向一张hash表中放入数据,如果不存在将创建
	  * @param key	键
	  * @param item	项
	  * @param value	值
	  * @return
	  */
	 public boolean hset(String key, String item, Object value) {
         try {
        	 myRedisTemplate.opsForHash().put(key, item, value);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
	 
	 /**
	  * description:向一张hash表中放入数据,如果不存在将创建
	  * @param key	键
	  * @param item	项
	  * @param value	值
	  * @param time	时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	  * @return
	  */
	 public boolean hset(String key, String item, Object value, long time) {
         try {
        	 myRedisTemplate.opsForHash().put(key, item, value);
             if (time > 0) {
                 expire(key, time);
             }
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
	 
	 /**
	  * description:删除hash表中的值
	  * @param key	键 不能为null
	  * @param item	项 可以是多个 不能为null
	  */
     public void hdel(String key, Object... item) {
    	 myRedisTemplate.opsForHash().delete(key, item);
     }
	 
     /**
      * description:判断hash表中是否有该项的值
      * @param key	键 不能为null
      * @param item	项 不能为null
      * @return
      */
     public boolean hHasKey(String key, String item) {
         return myRedisTemplate.opsForHash().hasKey(key, item);
     }
	 
     /**
      * description:hash递增 如果不存在,就会创建一个 并把新增后的值返回
      * @param key	键
      * @param item	项
      * @param delta	要增加几(大于0)
      * @return
      */
     public double hincr(String key, String item, double delta) {
         return myRedisTemplate.opsForHash().increment(key, item, delta);
     }
	 
     /**
      * description:hash递减
      * @param key	键
      * @param item	项
      * @param delta	要减少几
      * @return
      */
     public double hdecr(String key, String item, double delta) {
         return myRedisTemplate.opsForHash().increment(key, item, -delta);
     }
	 
     // ============================set=============================
	 
	 /**
	  * description:根据key获取Set中的所有值
	  * @param key
	  * @return
	  */
     public Set<Object> sGet(String key) {
         try {
             return myRedisTemplate.opsForSet().members(key);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
	 
     /**
      * description:根据value从一个set中查询 是否存在
      * @param key
      * @param value
      * @return	true 存在 false不存在
      */
     public boolean sHasKey(String key, Object value) {
         try {
             return myRedisTemplate.opsForSet().isMember(key, value);
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
     
     /**
      * description:将set数据放入缓存
      * @param key
      * @param values
      * @return	成功个数
      */
     public long sSet(String key, Object... values) {
         try {
             return myRedisTemplate.opsForSet().add(key, values);
         } catch (Exception e) {
             e.printStackTrace();
             return 0;
         }
     }
     
     /**
      * description:将set数据放入缓存
      * @param key
      * @param time	时间(秒)
      * @param values	值 可以是多个
      * @return	成功个数
      */
     public long sSetAndTime(String key, long time, Object... values) {
         try {
             Long count = myRedisTemplate.opsForSet().add(key, values);
             if (time > 0)
                 expire(key, time);
             return count;
         } catch (Exception e) {
             e.printStackTrace();
             return 0;
         }
     }
     
     /**
      * description:获取set缓存的长度
      * @param key
      * @return
      */
     public long sGetSetSize(String key) {
         try {
             return myRedisTemplate.opsForSet().size(key);
         } catch (Exception e) {
             e.printStackTrace();
             return 0;
         }
     }
     
     /**
      * description:移除值为value的
      * @param key
      * @param values
      * @return
      */
     public long setRemove(String key, Object... values) {
         try {
             Long count = myRedisTemplate.opsForSet().remove(key, values);
             return count;
         } catch (Exception e) {
             e.printStackTrace();
             return 0;
         }
     }
     
     // ===============================list=================================
     
     
     /**
      * description:获取list缓存的内容
      * @param key	键
      * @param start	开始
      * @param end	结束 0 到 -1代表所有值
      * @return
      */
     public List<Object> lGet(String key, long start, long end) {
         try {
             return myRedisTemplate.opsForList().range(key, start, end);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
     
     /**
      * description:获取list缓存的长度
      * @param key	键
      * @return
      */
     public long lGetListSize(String key) {
         try {
             return myRedisTemplate.opsForList().size(key);
         } catch (Exception e) {
             e.printStackTrace();
             return 0;
         }
     }
     
     /**
      * description:通过索引 获取list中的值
      * @param key	键
      * @param index	索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
      * @return
      */
     public Object lGetIndex(String key, long index) {
         try {
             return myRedisTemplate.opsForList().index(key, index);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
     
     /**
      * description:将list放入缓存
      * @param key	键
      * @param value	值
      * @return
      */
     public boolean lSet(String key, Object value) {
         try {
        	 myRedisTemplate.opsForList().rightPush(key, value);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
     
     /**
      * description:将list放入缓存
      * @param key
      * @param value
      * @param time	时间(秒)
      * @return
      */
     public boolean lSet(String key, Object value, long time) {
         try {
        	 myRedisTemplate.opsForList().rightPush(key, value);
             if (time > 0)
                 expire(key, time);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
     
     /**
      * description:将list放入缓存
      * @param key
      * @param value
      * @return
      */
     public boolean lSet(String key, List<Object> value) {
         try {
        	 myRedisTemplate.opsForList().rightPushAll(key, value);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
 
     /**
      * description:将list放入缓存
      * @param key
      * @param value
      * @param time	时间(秒)
      * @return
      */
     public boolean lSet(String key, List<Object> value, long time) {
         try {
        	 myRedisTemplate.opsForList().rightPushAll(key, value);
             if (time > 0)
                 expire(key, time);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
     
     /**
      * description:根据索引修改list中的某条数据
      * @param key
      * @param index	索引
      * @param value
      * @return
      */
     public boolean lUpdateIndex(String key, long index, Object value) {
         try {
        	 myRedisTemplate.opsForList().set(key, index, value);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
     
     /**
      * description:移除N个值为value
      * @param key
      * @param count	移除多少个
      * @param value
      * @return	移除的个数
      */
     public long lRemove(String key, long count, Object value) {
         try {
             Long remove = myRedisTemplate.opsForList().remove(key, count, value);
             return remove;
         } catch (Exception e) {
             e.printStackTrace();
             return 0;
         }
     }
     
     
	 
}
