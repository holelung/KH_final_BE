package com.kh.saintra.file.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@Getter
@ToString
public class ProfileVO {

	Long userId;
	String filename;
	String origin;
	String url;
}
