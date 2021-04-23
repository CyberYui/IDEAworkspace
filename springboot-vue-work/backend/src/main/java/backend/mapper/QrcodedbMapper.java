package backend.mapper;

import backend.entity.Qrcodedb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * 本类继承于 BaseMapper 类,里面有相应的数据库操作方法
 *
 * @author CyberKaka
 * @since 2021-04-19
 */
public interface QrcodedbMapper extends BaseMapper<Qrcodedb> {
    /**
     *  BaseMapper 里面写好了相关的增删改查内容,也就是 CRUD
     *  int insert(T entity) 方法用于添加内容
     *  int deleteById(Serializable id) 方法用于通过 id 删除内容
     *  int updateById 方法用于通过 id 更新内容
     *  T selectById(Serializable id) 方法用于通过 id 查询内容
     * */
}
