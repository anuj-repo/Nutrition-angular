package com.fertilizer.request1;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonRequestDTO {

	private String smartSearch;

	private String sortBy;

	private String sortDir;

	private Long page;

	private Long size;

	public CommonRequestDTO(String smartSearch, String sortBy, String sortDir, Long page, Long size) {
		this.smartSearch = smartSearch;
		this.sortBy = sortBy;
		this.sortDir = sortDir;
		this.page = page;
		this.size = size;
	}
}
