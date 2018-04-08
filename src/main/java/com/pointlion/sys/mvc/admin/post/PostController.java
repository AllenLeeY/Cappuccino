package com.pointlion.sys.mvc.admin.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.model.SysPost;
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
    		SysPost p = SysPost.dao.getById(id);
    		SysOrg org = SysOrg.dao.getById(p.getOrgid());
    		setAttr("post", p);
    		setAttr("org", org);
    	}
    	//添加子模块
    	String parentid = getPara("parentid");
    	if(StrKit.notBlank(parentid)){
    		SysOrg parent = SysOrg.dao.getById(parentid);
    		setAttr("p", parent);
    	}
    	render("/WEB-INF/admin/post/edit.html");
    }
    /***
     * 保存
     */
    public void save(){
    	SysOrg o = getModel(SysOrg.class);
    	if(StrKit.notBlank(o.getId())){
    		o.update();
    	}else{
    		o.setId(UuidUtil.getUUID());
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
    		List<SysOrg> list = SysOrg.dao.getChildrenByPid(id);
    		if(list.size()>0){
    			renderError("有子菜单,不允许删除!");
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
