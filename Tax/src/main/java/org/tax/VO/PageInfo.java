package org.tax.VO;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PageInfo<T> {
	// 当前页
	private long currentPage;
	// 当前页显示的条数
	private long currentCount;
	// 总条数
	private long totalCount;
	// 总页数
	private long totalPage;
	// 每页显示的数据
	private List<T> list = new ArrayList<T>();
	
}
