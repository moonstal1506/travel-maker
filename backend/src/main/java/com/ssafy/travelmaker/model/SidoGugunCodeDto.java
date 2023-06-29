package com.ssafy.travelmaker.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SidoGugunCodeDto : 시도, 구군정보", description = "시도, 구군의 이름을 나타낸다.")
public class SidoGugunCodeDto {

    @ApiModelProperty(value = "시도코드")
    private String sidoCode;
    @ApiModelProperty(value = "시도이름")
    private String sidoName;
    @ApiModelProperty(value = "구군코드")
    private String gugunCode;
    @ApiModelProperty(value = "구군이름")
    private String gugunName;
}
