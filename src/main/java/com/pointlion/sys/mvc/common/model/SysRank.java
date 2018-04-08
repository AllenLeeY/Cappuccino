package com.pointlion.sys.mvc.common.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.common.dto.ZtreeNode;
import com.pointlion.sys.mvc.common.model.base.BaseSysOrg;
import com.pointlion.sys.mvc.common.model.base.BaseSysRank;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class SysRank extends BaseSysRank<SysRank> {
	public static final SysRank dao = new SysRank();
	
	/***
	 * 根据主键查询
	 */
	public SysRank getById(String id){
		return SysRank.dao.findById(id);
	}
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SysRank o = SysRank.dao.getById(id);
    		o.delete();
    	}
	}
	
}