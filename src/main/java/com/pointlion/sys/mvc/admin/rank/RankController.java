package com.pointlion.sys.mvc.admin.rank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.admin.login.SessionUtil;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysPost;
import com.pointlion.sys.mvc.common.model.SysRank;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;

/**
 * @ClassName:  OrgController   
 * @Description:TODO(岗位管理控制器)   
 * @author LiYonghui
 * @date 2018年4月8日 上午11:14:12
 */
@Before(MainPageTitleInterceptor.class)
public class RankController extends BaseController {
	/***
	 * 获取列表页面
	 */
	public void getListPage(){
    	render("/WEB-INF/admin/rank/list.html");
    }
   
    /***
     * 获取分页数据
     **/
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<Record> page = SysRank.dao.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    
    /***
     * 获取键值对数据
     **/
    public void mapData(){
    	Map<String,String> data = SysRank.dao.getAll();
    	renderJson(data);
    }
    
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){
    		SysRank rank = SysRank.dao.getById(id);
    		setAttr("rank", rank);
    	}
    	render("/WEB-INF/admin/rank/edit.html");
    }
    /***
     * 保存
     */
    public void save(){
    	SysRank rank = getModel(SysRank.class);
    	if(StrKit.notBlank(rank.getId())){
    		rank.setUpdateUser(SessionUtil.getUserIdFromSession(this.getRequest()));
    		rank.setUpdateTime(DateUtil.getTime());
    		rank.update();
    	}else{
    		rank.setId(UuidUtil.getUUID());
    		rank.setCreateuser(SessionUtil.getUserIdFromSession(this.getRequest()));
    		rank.setCreateTime(DateUtil.getTime());
    		rank.setUpdateUser(SessionUtil.getUserIdFromSession(this.getRequest()));
    		rank.setUpdateTime(DateUtil.getTime());
    		rank.save();
    	}
    	renderSuccess();
    }
    /***
     * 删除
     * @throws Exception
     */
    public void delete() throws Exception{
		String ids = getPara("ids");
		String idarr[] = ids.split(",");
    	for(String id : idarr){
    		List<SysPost> list = SysPost.dao.getPostByRankId(id);
    		if(list.size()>0){
    			renderError("该职级下有岗位，不能删除!");
    			return;
    		}
    	}
    	//执行删除
    	SysRank.dao.deleteByIds(ids);
    	renderSuccess("删除成功!");
    }
    
    /**************************************************************************/
	private String pageTitle = "职级管理";//模块页面标题
	private String breadHomeMethod = "getListPage";//面包屑首页方法
	
	public Map<String,String> getPageTitleBread() {
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", pageTitle);
		pageTitleBread.put("breadHomeMethod", breadHomeMethod);
		return pageTitleBread;
	}
}
