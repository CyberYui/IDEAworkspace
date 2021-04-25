package backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author CyberKaka
 * @since 2021-04-25
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Qrcodedb implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String title;

    private String major;

    private String content;

    private String image;

    private String videos;

    private String address;


}
