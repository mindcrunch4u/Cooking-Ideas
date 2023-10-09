package com.bob.info.ui.cook;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;


public class ReceiptGenerator {
    private static class Types {
        public static int none =0;
        public static int vegetable =1;
        public static int misc =2;
        public static int seasoning =3;
        public static int method =4;
        public static int shape =5;
        public static int meat =6;
        public static int carb =7;

        public static String intToName(int type){
            String name = "";
            switch (type) {
                case 0:
                    name = "none";
                    break;
                case 1:
                    name = "vegetable";
                    break;
                case 2:
                    name = "misc";
                    break;
                case 3:
                    name = "seasoning";
                    break;
                case 4:
                    name = "method";
                    break;
                case 5:
                    name = "shape";
                    break;
                case 6:
                    name = "meat";
                    break;
                case 7:
                    name = "carb";
                    break;
                default:
                    name = "unknown";
                    break;
            }
            return name;
        }

        public static String intToNameCn(int type){
            String name = "";
            switch (type) {
                case 0:
                    name = "none";
                    break;
                case 1:
                    name = "蔬菜";
                    break;
                case 2:
                    name = "其他";
                    break;
                case 3:
                    name = "调味";
                    break;
                case 4:
                    name = "做法";
                    break;
                case 5:
                    name = "形状";
                    break;
                case 6:
                    name = "肉类";
                    break;
                case 7:
                    name = "碳水";
                    break;
                default:
                    name = "unknown";
                    break;
            }
            return name;
        }
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

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        int ret =random.nextInt(max - min + 1) + min;
        return ret;
    }
    private int[] randomIntList(int minLength, int maxLength, int minValue, int maxValue){
        Random rand = new Random();
        int arraySize = getRandomNumberUsingNextInt(minLength, maxLength);
        int[] ret = new int [arraySize];
        for(int index=0; index<arraySize; index++){
            ret[index] = getRandomNumberUsingNextInt(minValue, maxValue);
        }
        return ret;
    }

    public ReceiptGenerator(){
        this.ingredients= new Hashtable<>();
        this.required_amount = new Hashtable<>();

        ingredients.put(Types.none, "");
        ingredients.put(Types.vegetable, "茄子，豆角，生菜，菜心，甜椒，土豆，白菜，娃娃菜，菠菜，菌菇，黄瓜，丝瓜，南瓜，苦瓜，包菜，番茄，苋菜，韭菜，豆芽，荷兰豆，木耳，红萝卜，上海青，蒜苔，芹菜，菜心");
        ingredients.put(Types.misc, "豆皮，豆干，虾皮，鸡蛋，皮蛋，豆腐" );
        ingredients.put(Types.seasoning     , "洋葱，玉米，大葱，小葱，葱叶" );
        ingredients.put(Types.method        , "黑椒，大料，糖盐炒，蒜香，蚝酱油，凉拌，水煮，蒸，豆瓣酱，咖喱" );
        ingredients.put(Types.shape         , "条，丝，粒，片，块，沫，球/饼" );
        ingredients.put(Types.meat          , "鸡蛋，五花肉，猪肉，排骨，猪肝，鸡翅，鸡腿，鸡腿肉，鸡胸，牛排，牛腱，牛腩，肥牛片，虾，扇贝" );
        ingredients.put(Types.carb          , "米饭，面饼，意面，小米粥，粥" );

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
            String name = Types.intToNameCn(key);
            ArrayList<String> current = new ArrayList<>();
            current.add(name);
            String[] items = new String(ingredients.get(key)).split("，", 0);
            int required_min = required_amount.get(key).min;
            int required_max = required_amount.get(key).max;
            int[] pickedItems = randomIntList(required_min, required_max, 0, items.length-1);
            ArrayList<String> currentSelections = new ArrayList<>();
            for(int index : pickedItems){
                currentSelections.add(items[index]);
            }
            current.add(String.join(", ", currentSelections));
            ret.add(current);
        }
        return ret;
    }
}
