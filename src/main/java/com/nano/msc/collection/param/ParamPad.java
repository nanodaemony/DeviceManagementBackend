package com.nano.msc.collection.param;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 采集器Post的实体对象参数
 * @author nano
 * 用于采集数据的通信流程
 */
@Data
@Valid
public class ParamPad implements Serializable {

    private static final long serialVersionUID = 233410313723289238L;

    /**
     * 采集器的MAC地址
     */
    @ApiModelProperty(example = "08:00:20:0A:8C:6D")
    @Pattern(regexp = "([A-F0-9]{2}:){5}[A-F0-9]{2}", message = "MAC地址各式错误")
    @NotNull(message = "MAC地址不能为空")
    private String mac;

    /**
     * 手术顺序号
     */
    @Min(value = -1, message = "手术场次号不能低于-1")
    @NotNull(message = "采集场次号不能为空")
    private Integer collectionNumber;

    /**
     * 包含信息的Data
     */
    @NotNull(message = "数据不能为空")
    private String data;

}
