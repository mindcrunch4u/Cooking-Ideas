package com.bob.info.ui.cook;
import android.content.Context;
import android.content.res.Resources;
import com.bob.info.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;


public class ReceiptGenerator {
    private Context context = null;
    private static class Types {
        public static int none =0;
        public static int vegetable =1;
        public static int misc =2;
        public static int seasoning =3;
        public static int method =4;
        public static int shape =5;
        public static int meat =6;
        public static int carb =7;
    }
    private class MinMax{
        public int min = 0;
        public int max = 0;
        public MinMax(int min, int max){
            this.min = min;
            this.max = max;
        }
    }

    private Dictionary<Integer, String> ingredients = null;
    private Dictionary<Integer, MinMax> required_amount  = null;
    public String intToTranslatedName(int type){
        String name = "";
        switch (type) {
            case 0:
                name = context.getResources().getString(R.string.fragment_cook_type_none);
                break;
            case 1:
                name = context.getResources().getString(R.string.fragment_cook_type_vegetable);
                break;
            case 2:
                name = context.getResources().getString(R.string.fragment_cook_type_misc);
                break;
            case 3:
                name = context.getResources().getString(R.string.fragment_cook_type_seasoning);
                break;
            case 4:
                name = context.getResources().getString(R.string.fragment_cook_type_method);
                break;
            case 5:
                name = context.getResources().getString(R.string.fragment_cook_type_shape);
                break;
            case 6:
                name = context.getResources().getString(R.string.fragment_cook_type_meat);
                break;
            case 7:
                name = context.getResources().getString(R.string.fragment_cook_type_carb);
                break;
            default:
                name = context.getResources().getString(R.string.fragment_cook_type_unknown);
                break;
        }
        return name;
    }
    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        int ret =random.nextInt(max - min + 1) + min;
        return ret;
    }
    private int[] randomIntList(int minLength, int maxLength, int minValue, int maxValue){
        int arraySize = getRandomNumberUsingNextInt(minLength, maxLength);
        int[] ret = new int [arraySize];
        for(int index=0; index<arraySize; index++){
            ret[index] = getRandomNumberUsingNextInt(minValue, maxValue);
        }
        return ret;
    }

    public ReceiptGenerator(Context context){
        this.context = context;
        this.ingredients= new Hashtable<>();
        this.required_amount = new Hashtable<>();

        ingredients.put(Types.none, "");
        ingredients.put(Types.vegetable, this.context.getResources().getString(R.string.fragment_cook_array_vegetable));
        ingredients.put(Types.misc, this.context.getResources().getString(R.string.fragment_cook_array_misc));
        ingredients.put(Types.seasoning     , this.context.getResources().getString(R.string.fragment_cook_array_seasoning));
        ingredients.put(Types.method        , this.context.getResources().getString(R.string.fragment_cook_array_method));
        ingredients.put(Types.shape         , this.context.getResources().getString(R.string.fragment_cook_array_shape));
        ingredients.put(Types.meat          , this.context.getResources().getString(R.string.fragment_cook_array_meat));
        ingredients.put(Types.carb          , this.context.getResources().getString(R.string.fragment_cook_array_carb));

        required_amount.put(Types.none          , new MinMax(0,0));
        required_amount.put(Types.vegetable     , new MinMax(1,3));
        required_amount.put(Types.misc          , new MinMax(0,1));
        required_amount.put(Types.seasoning     , new MinMax(0,2));
        required_amount.put(Types.method        , new MinMax(1,1));
        required_amount.put(Types.shape         , new MinMax(1,1));
        required_amount.put(Types.meat          , new MinMax(1,2));
        required_amount.put(Types.carb          , new MinMax(1,1));
    }

    public ArrayList<ArrayList<String>> generate(){
        // item[0] = name, item[1] = ingredients
        ArrayList<ArrayList<String>> ret = new ArrayList<>();
        Enumeration<Integer> all_ingredients = ingredients.keys();
        while(all_ingredients.hasMoreElements()){
            int key = all_ingredients.nextElement();
            String name = intToTranslatedName(key);
            ArrayList<String> current = new ArrayList<>();
            current.add(name);
            String[] items = new String(ingredients.get(key)).split(",", 0);
            int required_min = required_amount.get(key).min;
            int required_max = required_amount.get(key).max;
            int[] pickedItems = randomIntList(required_min, required_max, 0, items.length-1);
            ArrayList<String> currentSelections = new ArrayList<>();
            for(int index : pickedItems){
                currentSelections.add(items[index].trim());
            }
            String comma = this.context.getResources().getString(R.string.text_comma);
            current.add(String.join(comma, currentSelections));
            ret.add(current);
        }
        return ret;
    }
}
