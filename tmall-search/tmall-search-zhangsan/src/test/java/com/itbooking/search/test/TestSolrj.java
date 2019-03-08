package com.itbooking.search.test;

import org.junit.Test;

public class TestSolrj {

	@Test
	public void testSolrJ()  {
		// 创建一个solrserver对象，创建一个httpsolrserver对象
		// 需要制定solr服务url
		/*String urlString = "http://localhost:8080/solr/core_test";
		SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		//solr4.x SolrServer solr = new HttpSolrServer(urlString);
		// 创建一个文档对象solrinputdocument对象
		SolrInputDocument document = new SolrInputDocument();
		// 向文档中添加域，必须有id域，域的名字必须在manage-scheme中定义
		//第一种方式添加数据，(使用字段)
	    //添加字段
	    document.addField("id", "9527");
	    document.addField("name", "啦啦");
	    document.addField("content", "solr是个好东西");
		// 把文档对象写入索引库
	    solr.add(document);
		// 提交
	    solr.commit();*/
	}
	
	/*@Test
	public void testMapper() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext-dao.xml");
		ISearchItemMapper searchItemMapper = context.getBean(ISearchItemMapper.class);
		List<SearchResultVo> searchResultVos = searchItemMapper.findItemList();
		for (SearchResultVo searchResultVo : searchResultVos) {
			System.out.println(searchResultVo.getTitle());
		}
	}*/
}
