package com.pcwk.ehr.cmn;

public class DTO {

	private String searchDiv;//검색구분
	private String searchWord;//검색어
	public DTO() {
		super();
	}
	public DTO(String searchDiv, String searchWord) {
		super();
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
	}
	public String getSearchDiv() {
		return searchDiv;
	}
	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	@Override
	public String toString() {
		return "DTO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + "]";
	}
	
	
	
	
	
}
