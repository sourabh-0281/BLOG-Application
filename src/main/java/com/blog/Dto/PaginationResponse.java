package com.blog.Dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaginationResponse {

	private List<PostDto> content;
	private int pagenumber;
	private int pageSize;
	private int TotalPages;
	private long TotalElements;
	private boolean Lastpage;
}
