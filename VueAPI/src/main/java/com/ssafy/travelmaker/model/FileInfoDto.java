package com.ssafy.travelmaker.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "FileInfoDto : 게시글정보", description = "게시글의 상세 정보를 나타낸다.")
public class FileInfoDto {
	
	private int no       	;
	private String rfilename;
	private String sfilename;
	
	public FileInfoDto(String rfilename, String sfilename) {
		this.rfilename = rfilename;
		this.sfilename = sfilename;
	}
}