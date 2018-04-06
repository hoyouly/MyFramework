package top.hoyouly.framework.config;

/**
 * Created by hoyouly on 18/3/28.
 */

public class ApiConfig {
    public static final String DOUBAN_BASE_URL = "https://api.douban.com/v2/";
    public static final String DOUBAN_MOVIE_URL = DOUBAN_BASE_URL+"movie/";



    public static final String GANK_BASE_URL = "http://gank.io/api/";
    public static final String GANK_DATA_BASE_URL = GANK_BASE_URL+"data/";


    //https://blog.csdn.net/c__chao/article/details/78573737
    public static final String APIOPEN_BASE_URL="https://www.apiopen.top/";
    //
    public static final String apiopen_meitu_URL=APIOPEN_BASE_URL+"meituApi?page=1";

    //https://www.apiopen.top/meituApi?page=1   美图获取接口  当page=0时，会随机返回一页数据，page>=1时会返回相应页码的数据。

    // 接口传参说明：
    //type=1 : 全部
    //type=2 : 文字
    //type=3 : 图片
    //type=4 : gif
    //type=5 : 视频
    //
    //page=1:页码

    //返回type备注：
    //type=text : 文字
    //type=image : 图片
    //type=gif : Gif
    //type=video: 视频

    //https://www.apiopen.top/satinGodApi?type=1&page=1  随机推荐热门段子【神评版本】

    //https://www.apiopen.top/satinApi?type=1&page=1  随机推荐段子（包含文字、图片、GIF、视频）：
    /**
     *
     * 接口传参说明
     * type=1 : 全部
     type=2 : 文字
     type=3 : 图片
     type=4 : 视频
     page=1:页码

     返回type

     返回type备注：
     type=1 : 全部
     type=41 : 视频
     type=10 : 图片
     type=29 : 段子
     type=31 : 声音

     */

}
