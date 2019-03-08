package com.itbooking.service.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbooking.search.mapper.ISearchItemMapper;
import com.itbooking.vo.DataResult;
import com.itbooking.vo.SearchResult;
import com.itbooking.vo.SearchResultVo;

@Service
public class SearchItemServiceImpl implements ISearchItemService {
	
	@Autowired
	private ISearchItemMapper searchItemMapper;
	
	@Autowired
	private HttpSolrClient solrClient;

	@Override
	public DataResult createItemIndex(){
		try {
			//1、先查询所有商品数据
			List<SearchResultVo> itemList = searchItemMapper.findItemList();
			//2、遍历商品数据添加到索引库
			for (SearchResultVo searchItem : itemList) {
				//创建文档对象
				SolrInputDocument document = new SolrInputDocument();
				//向文档中添加域
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSellPoint());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategoryName());
				document.addField("item_desc", searchItem.getItemDesc());
				//把文档写入索引库
				solrClient.add(document);
			}
			//3、提交
			solrClient.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return DataResult.build(500, "数据导入失败");
		}
		//4、返回添加成功
		return DataResult.ok();
	}

	
	//查看搜索
	@Override
	public SearchResult searchItemData(String keyword,int pageNo,int pageSize) {
		try {
			//创建solrQuery服务
			SolrQuery query = new SolrQuery();
			//设置查询条件，过滤条件，分页条件，排序条件，高亮
			query.setQuery(keyword);
			//分页条件
			query.setStart(pageNo);
			query.setRows(pageSize);
			//设置默认搜索域
			query.set("df", "item_keywords");
			//设置高亮
			query.setHighlight(true);
			query.addHighlightField("item_title");
			query.setHighlightSimplePre("<em class='red'>");
			query.setHighlightSimplePost("</em>");
			//执行查询
			QueryResponse response = solrClient.query(query);
			//获取结果
			SolrDocumentList solrDocumentList = response.getResults();
			//查询总记录数是
			SearchResult searchResult = new SearchResult();
			searchResult.setTotalPages(solrDocumentList.getNumFound());
			List<SearchResultVo> searchResultVos = new ArrayList<>();
			for (SolrDocument solrDocument : solrDocumentList) {
				SearchResultVo searchResultVo = new SearchResultVo();
				searchResultVo.setId(String.valueOf(solrDocument.get("id")));
				Map<String,Map<String, List<String>>> hightling = response.getHighlighting();
				List<String> list = hightling.get(solrDocument.get("id")).get("item_title");
				if(list!=null && list.size()>0) {
					searchResultVo.setTitle(list.get(0));
				}else {
					searchResultVo.setTitle(String.valueOf(solrDocument.get("item_title")));
				}
				searchResultVo.setImage(String.valueOf(solrDocument.get("item_image")));
				searchResultVo.setCategoryName(String.valueOf(solrDocument.get("item_category_name")));
				searchResultVo.setPrice(Long.parseLong(String.valueOf(solrDocument.get("item_price"))));
				searchResultVo.setSellPoint(String.valueOf(solrDocument.get("item_sell_point")));
				searchResultVo.setItemDesc(String.valueOf(solrDocument.get("item_desc")));
				searchResultVos.add(searchResultVo);
			}
			searchResult.setItemLists(searchResultVos);
			return searchResult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
