package com.itbooking.service.search;

import com.itbooking.vo.DataResult;
import com.itbooking.vo.SearchResult;

public interface ISearchItemService {

	public DataResult createItemIndex();
	public SearchResult searchItemData(String keyword,int pageNo,int pageSize);
}
