package Automat;

import com.google.gson.Gson;

/**
 * @author 846995
 */
public class JsonService {
    public static Symbol jsonToSymbol(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Symbol.class);
    }

    public static String symbolToJsonString(Symbol symbol){
        Gson gson = new Gson();
        return gson.toJson(symbol);
    }
}
