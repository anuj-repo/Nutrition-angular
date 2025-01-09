package com.fertilizer.request1;

public class CommonSearchDto {
	
	protected String smartSearch;
	protected Long page; 
	protected Long size;
	protected String sortBy; 
	protected String sortDir;
	
	/**
	 * @return the smartSearch
	 */
	public String getSmartSearch() {
		return smartSearch;
	}
	/**
	 * @param smartSearch the smartSearch to set
	 */
	public void setSmartSearch(String smartSearch) {
		this.smartSearch = smartSearch;
	}
	/**
	 * @return the page
	 */
	public Long getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(Long page) {
		this.page = page;
	}
	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}
	/**
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}
	/**
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	/**
	 * @return the sortDir
	 */
	public String getSortDir() {
		return sortDir;
	}
	/**
	 * @param sortDir the sortDir to set
	 */
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}
	public CommonSearchDto(
			String smartSearch, Long page, Long size, String sortBy, String sortDir) {
		super();
		this.smartSearch = smartSearch;
		this.page = page;
		this.size = size;
		this.sortBy = sortBy;
		this.sortDir = sortDir;
	}
	public CommonSearchDto() {

	}
	
	

}
