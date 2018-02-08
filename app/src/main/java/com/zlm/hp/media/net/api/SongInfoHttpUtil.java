package com.zlm.hp.media.net.api;

import android.content.Context;

import com.zlm.hp.media.net.HttpClientUtils;
import com.zlm.hp.media.net.entity.SongInfoResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangliangming on 2017/7/30.
 */
public class SongInfoHttpUtil {

    static final String url = "http://m.kugou.com/app/i/getSongInfo.php";
    /**
     * 获取歌曲的具体信息
     *
     * @param context
     * @param hash
     * @return
     */
    public static SongInfoResult songInfo(Context context, String hash) {

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cmd", "playInfo");
            params.put("hash", hash);
            // 获取数据
            String result = HttpClientUtils.httpGetRequest(url, params).string();
            if (result != null) {

                JSONObject jsonNode = new JSONObject(result);
                int status = jsonNode.getInt("status");
                if (status == 1) {

                    SongInfoResult songInfoResult = new SongInfoResult();
                    songInfoResult.setDuration(jsonNode.getInt("timeLength")
                            * 1000 + "");
                    songInfoResult.setExtName(jsonNode.getString("extName"));
                    songInfoResult.setFileSize(jsonNode.getString("fileSize"));
                    songInfoResult.setHash(jsonNode.getString("hash").toLowerCase());
                    songInfoResult.setImgUrl(jsonNode.getString("imgUrl")
                            .replace("{size}", "400"));
                    songInfoResult.setSingerName(jsonNode.getString("singerName"));
                    songInfoResult.setSongName(jsonNode.getString("songName"));
                    songInfoResult.setUrl(jsonNode.getString("url"));


                    return songInfoResult;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void cancel() {
        HttpClientUtils.cancelTag(url);
    }
}
