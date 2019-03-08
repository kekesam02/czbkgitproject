package com.itbooking.search.mapper;

import java.util.List;

import com.itbooking.vo.SearchResultVo;

public interface ISearchItemMapper {

	public List<SearchResultVo> findItemList();
}
