package com.pointlion.sys.mvc.admin.post;

import java.util.ArrayList;
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
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.model.SysPost;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;

/**
 * @ClassName:  PostController   
 * @Description:TODO(岗位管理控制器)   
 * @author LiYonghui
 * @date 2018年4月8日 上午11:14:12
 */
@Before(MainPageTitleInterceptor.class)
public class PostController extends BaseController {
	/***
	 * 获取列表页面
	 */
	public void getListPage(){
    	render("/WEB-INF/admin/post/list.html");
    }
    /***
     * 获取分页数据
     **/
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	String orgid = getPara("orgid");
    	Page<Record> page = SysPost.dao.getPageByOrgid(Integer.valueOf(curr),Integer.valueOf(pageSize),orgid);
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){
    		SysPost post = SysPost.dao.getPostById(id);
    		SysOrg org = SysOrg.dao.getById(post.getOrgid());
    		setAttr("post", post);
    		setAttr("org", org);
    	}
    	//获取组织架构的根节点
    	List<SysOrg> orgs = SysOrg.dao.getChildrenByPid("#root");
		setAttr("rootOrg", orgs.get(0));
    	render("/WEB-INF/admin/post/edit.html");
    }
    
    public void getPostJsonById() {
    	//添加和修改
    	String id = getPara("id");
    	SysPost post = null;
    	if(StrKit.notBlank(id)){
    		post = SysPost.dao.getPostById(id);
    	}
    	renderJson(post);
    }
    
    /***
     * 保存
     */
    public void save(){
    	SysPost o = getModel(SysPost.class);
    	if(StrKit.notBlank(o.getId())){
    		o.update();
    	}else{
    		o.setId(UuidUtil.getUUID());
    		o.setCreateuser(SessionUtil.getUserIdFromSession(this.getRequest()));
    		o.setCreateTime(DateUtil.getTime());
    		o.save();
    	}
    	renderSuccess();
    }
    /***
     * 获取选择树页面
     * */
    public void getSelectOneOrgPage(){
    	render("/WEB-INF/admin/common/pointLion/selectOneOrg/selectOneOrg.html");
    }
    public void getSelectManyOrgPage(){
    	render("/WEB-INF/admin/common/pointLion/selectManyOrg/selectManyOrg.html");
    }
    
    /***
     * 删除
     * @throws Exception
     */
    public void delete() throws Exception{
		String ids = getPara("ids");
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		List<SysUser> list = SysUser.dao.getUserListByPostId(id);
    		if(list.size()>0){
    			renderError("该岗位下有用户，不能删除!");
    			return;
    		}
    	}
    	//执行删除
    	SysOrg.dao.deleteByIds(ids);
    	renderSuccess("删除成功!");
    }
    
    /**************************************************************************/
	private String pageTitle = "岗位管理";//模块页面标题
	private String breadHomeMethod = "getListPage";//面包屑首页方法
	
	public Map<String,String> getPageTitleBread() {
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", pageTitle);
		pageTitleBread.put("breadHomeMethod", breadHomeMethod);
		return pageTitleBread;
	}
	
}
