package com.zzj.zhijian.action;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.bean.ProductBigType;
import com.zzj.zhijian.service.ProductBigTypeService;
import com.zzj.zhijian.service.ProductSmallTypeService;
import com.zzj.zhijian.util.ResponseUtil;

/**
 * 商品大类Action类
 * 
 * @author zhaozengjie
 *
 */
@Controller
public class ProductBigTypeAction extends ActionSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String page;
	private String rows;
	private ProductBigType s_productBigType;

	private String ids;

	private ProductBigType productBigType;

	private JSONObject responseJson ; 
	
	public JSONObject getResponseJson()
	{
		return responseJson;
	}

	public void setResponseJson(JSONObject responseJson)
	{
		this.responseJson = responseJson;
	}

	public ProductBigTypeAction()
	{
		responseJson = new JSONObject();
	}

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public ProductBigType getProductBigType()
	{
		return productBigType;
	}

	public void setProductBigType(ProductBigType productBigType)
	{
		this.productBigType = productBigType;
	}

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public String getRows()
	{
		return rows;
	}

	public void setRows(String rows)
	{
		this.rows = rows;
	}

	public ProductBigType getS_productBigType()
	{
		return s_productBigType;
	}

	public void setS_productBigType(ProductBigType s_productBigType)
	{
		this.s_productBigType = s_productBigType;
	}

	@Resource
	private ProductBigTypeService productBigTypeService;

	@Resource
	private ProductSmallTypeService productSmallTypeService;

	/**
	 * 分页查询商品大类信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception
	{
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<ProductBigType> productBigTypeList = productBigTypeService
				.findProductBigTypeList(s_productBigType, pageBean);
		long total = productBigTypeService
				.getProductBigTypeCount(s_productBigType);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "productList", "smallTypeList" });
		JSONArray rows = JSONArray.fromObject(productBigTypeList, jsonConfig);
		responseJson.clear();
		responseJson.put("rows", rows);
		responseJson.put("total", total);
		return "json";
	}

	/**
	 * 后台-保存商品大类信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception
	{
		productBigTypeService.saveProductBigType(productBigType);
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

	/**
	 * 删除商品大类
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception
	{
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			if (productSmallTypeService.existSmallTypeWithBigTypeId(Integer
					.parseInt(idsStr[i])))
			{
				responseJson.clear();
				responseJson.put("exist", "商品大类包含商品小类");
			} else
			{
				ProductBigType productBigType = productBigTypeService
						.getProductBigTypeById(Integer.parseInt(idsStr[i]));
				productBigTypeService.delete(productBigType);
			}
		}
		responseJson.put("success", true);
		return "json";
	}

	/**
	 * 商品大类下拉框集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public String comboList() throws Exception
	{
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", "");
		jsonObject.put("name", "请选择...");
		jsonArray.add(jsonObject);
		List<ProductBigType> productBigTypeList = productBigTypeService
				.findAllBigTypeList();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "productList", "smallTypeList" });
		JSONArray rows = JSONArray.fromObject(productBigTypeList, jsonConfig);
		jsonArray.addAll(rows);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jsonArray.toString());
		out.flush();
		out.close();
		return null;
	}

}
