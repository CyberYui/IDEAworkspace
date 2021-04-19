package backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author CyberKaka
 * @since 2021-04-19
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Qrcodedb implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 变量 id 类型为 Integer 对接的是数据库的 id 属性
     * id 作为唯一标识符,用来提取数据库的行数据
    * */
    private Integer id;

  /**
   * 变量 title 类型为 String 对接的是数据库的 title 属性
   * title 表示标题,也就是每篇文章的标题
   * */
    private String title;

  /**
   * 变量 major 类型为 String 对接的是数据库的 major 属性
   * major 表示主题,每篇文章会对应一个主题
   * */
    private String major;

  /**
   * 变量 content 类型为 String 对接的是数据库的 content 属性
   * content 表示内容,每篇文章的内容基本都保存在这里
   * */
    private String content;

  /**
   * 变量 image 类型为 String 对接的是数据库的 image 属性
   * image 表示图片的地址
   * */
    private String image;

  /**
   * 变量 videos 类型为 String 对接的是数据库的 videos 属性
   * videos 表示视频的地址
   * */
    private String videos;

  /**
   * 变量 address 类型为 String 对接的是数据库的 address 属性
   * address 表示生成的每篇文章的链接地址
   * */
    private String address;
}
