package com.core;

public class PageElement {
	    private int pageNum;	//当前页
	    private int pageSize;	//页面显示数据的条数
	    private long maxPage;	//总页数
	    private int prePage;	//上一页
	    private int nextPage;	//下一页

	    public int getNextPage() {
	        return nextPage;
	    }

	    public int getPrePage() {
	        return prePage;
	    }

	    public long getMaxPage() {
	        return maxPage;
	    }

	    public void setMaxPage(long maxPage) {
	        this.maxPage = maxPage;
	    }

	    public int getPageSize() {
	        return pageSize;
	    }

	    public void setPageSize(int pageSize) {
	        this.pageSize = pageSize;
	    }

	    public int getPageNum() {
	        return pageNum;
	    }

	    public void setPageCount(int pageNum) {
	        this.pageNum = pageNum;
	        //设置上一页的页码
	        prePage=pageNum-1<=1?1:pageNum-1;
	        //设置下一页的页码 
	        nextPage=(int)(pageNum + 1 >= maxPage ? maxPage : pageNum + 1);
	    }

}
