package com.lggyx.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PortraitVO {
    @Schema(description= "画像主键")
    private Integer id;

    @Schema(description= "画像编码")
    private String code;

    @Schema(description= "E 维度等级：H 高，L 低")
    private String eLevel;

    @Schema(description= "R 维度等级：H 高，L 低")
    private String rLevel;

    @Schema(description= "T 维度等级：H 高，L 低")
    private String tLevel;

    @Schema(description= "画像描述（富文本）")
    private String description;
}
