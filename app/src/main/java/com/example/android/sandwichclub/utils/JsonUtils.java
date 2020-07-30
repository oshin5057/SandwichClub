package com.example.android.sandwichclub.utils;

import com.example.android.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        ArrayList<String> knownAsList = new ArrayList<>();
        ArrayList<String> ingredientsList = new ArrayList<>();

        JSONObject jsonSandwich = new JSONObject(json);

        JSONObject name = jsonSandwich.getJSONObject("name");
        String mainNameString = name.getString("mainName");

        JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
        if (alsoKnownAsArray.length() > 0){
            for (int i = 0; i <alsoKnownAsArray.length(); i++){
                knownAsList.add(alsoKnownAsArray.getString(i));
            }
        }

        String placeOfOriginString = jsonSandwich.getString("placeOfOrigin");

        String descriptionString = jsonSandwich.getString("description");

        String imageString = jsonSandwich.getString("image");

        JSONArray ingredientsArray = jsonSandwich.getJSONArray("ingredients");
        if (ingredientsArray.length()>0){
            for (int i = 0; i<ingredientsArray.length(); i++){
                ingredientsList.add(ingredientsArray.getString(i));
            }
        }

        return new Sandwich(mainNameString, knownAsList, placeOfOriginString, descriptionString, imageString, ingredientsList);
    }
}
