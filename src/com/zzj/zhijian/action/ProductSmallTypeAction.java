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
import com.zzj.zhijian.bean.ProductSmallType;
import com.zzj.zhijian.service.ProductService;
import com.zzj.zhijian.service.ProductSmallTypeService;
import com.zzj.zhijian.util.ResponseUtil;

/**
 * 商品小类Action类
 * 
 * @author zhaozengjie
 *
 */
@Controller
public class ProductSmallTypeAction extends ActionSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProductSmallType s_productSmallType;

	private String page;
	private String rows;
	private String ids;
	private ProductSmallType productSmallType;

	
	private JSONObject responseJson ; 
	
	
	public JSONObject getResponseJson()
	{
		return responseJson;
	}

	public void setResponseJson(JSONObject responseJson)
	{
		this.responseJson = responseJson;
	}

	public ProductSmallTypeAction()
	{
		responseJson = new JSONObject();
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

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public ProductSmallType getS_productSmallType()
	{
		return s_productSmallType;
	}

	public void setS_productSmallType(ProductSmallType s_productSmallType)
	{
		this.s_productSmallType = s_productSmallType;
	}

	public ProductSmallType getProductSmallType()
	{
		return productSmallType;
	}

	public void setProductSmallType(ProductSmallType productSmallType)
	{
		this.productSmallType = productSmallType;
	}

	@Resource
	private ProductSmallTypeService productSmallTypeService;

	@Resource
	private ProductService productService;

	/**
	 * 分页查询商品小类信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception
	{
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<ProductSmallType> productSmallTypeList = productSmallTypeService
				.findProductSmallTypeList(s_productSmallType, pageBean);
		long total = productSmallTypeService
				.getProductSmallTypeCount(s_productSmallType);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "productList" });
		jsonConfig.registerJsonValueProcessor(ProductBigType.class,
				new ObjectJsonValueProcessor(new String[] { "id", "name" },
						ProductBigType.class));
		JSONArray rows = JSONArray.fromObject(productSmallTypeList, jsonConfig);
		responseJson.clear();
		responseJson.put("rows", rows);
		responseJson.put("total", total);
		return "json";
	}

	/**
	 * 后台-保存商品小类信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception
	{
		productSmallTypeService.saveProductSmallType(productSmallType);
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

	/**
	 * 删除商品小类
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception
	{
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			if (productService.existProductWithSmallTypeId(Integer
					.parseInt(idsStr[i])))
			{
				responseJson.clear();
				responseJson.put("exist", "商品小类包含商品");
			} else
			{
				ProductSmallType productSmallType = productSmallTypeService
						.getProductSmallTypeById(Integer.parseInt(idsStr[i]));
				productSmallTypeService.delete(productSmallType);
			}
		}
		responseJson.put("success", true);
		return "json";
	}

	/**
	 * 商品小类下拉框集合
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
		List<ProductSmallType> productSmallTypeList = productSmallTypeService
				.findProductSmallTypeList(s_productSmallType, null);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "bigType", "productList" });
		JSONArray rows = JSONArray.fromObject(productSmallTypeList, jsonConfig);
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
