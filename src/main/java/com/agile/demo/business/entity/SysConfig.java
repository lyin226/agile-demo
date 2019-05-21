package com.agile.demo.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统配置信息表
 * </p>
 *
 * @author liuyi
 * @since 2019-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysConfig extends Model<SysConfig> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * key
     */
    private String configKey;

    /**
     * value
     */
    private String configValue;

    /**
     * 状态   0：隐藏   1：显示
     */
    private Integer configStatus;

    /**
     * 备注
     */
    private String remark;


}
