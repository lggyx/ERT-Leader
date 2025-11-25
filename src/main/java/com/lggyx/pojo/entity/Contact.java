package com.lggyx.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Getter
@Setter
@Accessors(chain = true)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，固定为 1
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 办公地址
     */
    private String address;


}
