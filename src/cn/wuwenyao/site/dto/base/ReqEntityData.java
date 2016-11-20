package cn.wuwenyao.site.dto.base;

/***
 * 标志这个请求DTO是实体类数据的载体
 * @author 文尧
 *
 * @param <T>
 */
public interface ReqEntityData<T> {

	public void setDataToEntity(T entity);
}
