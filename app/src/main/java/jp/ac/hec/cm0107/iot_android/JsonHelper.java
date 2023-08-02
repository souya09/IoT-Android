package jp.ac.hec.cm0107.iot_android;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Stack;

//TODO : URL -> 処理を実装　動作チェック
public class JsonHelper {

    static String pubDate;

    public static Stack<IoTItem> parseJson (String strJson) {

        Stack<IoTItem> charaItemArrayList = new Stack<>();
        try{
            JSONArray entries = new JSONArray(strJson);
            for(int i = 0; i < entries.length();i++) {
                JSONObject entry = entries.getJSONObject(i);
                charaItemArrayList.add(parseToItem(entry));
            }
        }catch (Exception e) {
            Log.e("JsonHelper", e.getMessage());
        }
        return charaItemArrayList;

    }
    public static IoTItem parseToItem (JSONObject json) throws JSONException {
        IoTItem item = new IoTItem();

/**
 * [
 * {
 * id: "295",
 * team_id: "CM08",
 * data1: "34.50",
 * data2: "0.00",
 * data3: "0.00",
 * create_time: "2023-07-20 12:06:04"
 * },
 */
/**
 * {
 * id: "297",
 * team_id: "CM08",
 * data1: "74.00",
 * data2: "0.00",
 * data3: "0.00",
 * create_time: "2023-07-20 12:06:10"
 * }
 * ]
 */
        item.setId(json.getInt("id"));
        item.setTeam_id(json.getString("team_id"));
        item.setData1(json.getDouble("data1"));
        item.setData2(json.getDouble("data2"));
        item.setData3(json.getDouble("data3"));
        item.setCreate_time(json.getString("create_time"));
        return item;
    }
}
