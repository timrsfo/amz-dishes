package com.timr.dishes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class Dishes {

  public static final String ERR_EMPTY_INGREDIENT = "ERROR_EMPTY_INGREDIENT";
  public static final String ERR_EMPTY_DISH = "ERROR_EMPTY_DISH";
  public static final String ERR_NULL_DISH = "ERROR_NULL_DISH";
  public static final String ERR_NULL_INGREDIENT = "ERROR_NULL_INGREDIENT";


  /**
   * <pre>
   * Given list of dishes and associated ingredients you have to group dishes by ingredients in lexicographical order.
   * 
   * This solution uses HashMap, and HashSet for O(n) add/contain/get, but requires
   * additional array sort O(nlogn) on both the map keySet: set-of-ingredients
   * and the map value: set-of-dishes
   * 
   * @param dishes String[][] - Each row first element is a dish, subsequent elements are ingredients
   *                            
   * @return ingredients String[][] - Each row first element ingredient, subsequent elements are dishes that use the ingredient
   * </pre>
   */
  public String[][] dishesToIngredientsHash(String[][] dishes) {
    Map<String, Set<String>> map = new HashMap<>();

    if (dishes == null) {
      throw new IllegalArgumentException("There are no dishes!");
    }

    // O(n * m), where n = dishes, m = ingredients
    for (int row = 0; row < dishes.length; row++) {
      if (dishes[row].length <= 1) {
        throw new IllegalArgumentException(
            "Each dish needs at least on ingredient");
      }
      String aDish = null;
      for (int col = 0; col < dishes[row].length; col++) {
        if (col == 0) { // its a dish
          aDish = dishes[row][0];
          aDish = (aDish != null ? aDish : ERR_NULL_DISH);
          aDish = (!aDish.isEmpty() ? aDish : ERR_EMPTY_DISH);
        } else { // ingredient(S)
          String ingredient = dishes[row][col];
          ingredient = (ingredient != null ? ingredient : ERR_NULL_INGREDIENT);
          ingredient = (!ingredient.isEmpty() ? ingredient
              : ERR_EMPTY_INGREDIENT);
          Set<String> tmpSet = null;
          if (map.containsKey(ingredient)) { // O(1)
            // already have a set
            tmpSet = map.get(ingredient); // O(1)
            tmpSet.add(aDish); // O(1)
          } else {
            // need to create a set
            tmpSet = new HashSet<String>(); // alpha-sort order
            tmpSet.add(aDish); // O(1)
            map.put(ingredient, tmpSet); // O(1)
          }
        }
      }
    }
    // Convert Map to Multi-D array alpha-sorted
    Set<String> ingredients = map.keySet();
    String[] keys = ingredients.toArray(new String[map.size()]);
    Arrays.sort(keys); // Hash vs Tree requires this O(nlogn)

    // O(m) where m = ingredients
    String[][] output = new String[keys.length][];
    int row = 0;
    for (String ingredient : keys) {
      output[row++] = createRowHash(ingredient, map);
    }
    return output;
  }

  /**
   * Create a single row, first element is ingredient, subsequent elements
   * are alpha-sorted Dishes which use the ingredient
   * @param ingredientKey
   * @param map - value Set of String, is a HashSet, require sorting O(nLogn)
   * @return
   */
  String[] createRowHash(String ingredientKey, Map<String, Set<String>> map) {
    Set<String> dishSet = map.get(ingredientKey); // O(1)
    String[] dishArr = dishSet.toArray(new String[dishSet.size()]);
    Arrays.sort(dishArr); // Hash vs tree requires this O(nlogn)

    String[] row = new String[1 + dishArr.length];
    row[0] = ingredientKey;
    System.arraycopy(dishArr, 0, row, 1, row.length - 1);
    return row;
  }

  /**
   * <pre>
   * Given list of dishes and associated ingredients you have to group dishes by ingredients in lexicographical order.
   * 
   * This solution uses TreeMap, and TreeSet for O(logn) add/contain/get, 
   * but does not require additional array sorts saving O(nlogn) 
   * on both the map keySet: set-of-ingredients and the map value: set-of-dishes
   * 
   * @param dishes String[][] - Each row first element is a dish, subsequent elements are ingredients
   *                            
   * @return ingredients String[][] - Each row first element ingredient, subsequent elements are dishes that use the ingredient
   * </pre>
   */
  public String[][] dishesToIngredientsTree(String[][] dishes) {
    SortedMap<String, Set<String>> smap = new TreeMap<>(); // keys sorted order

    if (dishes == null) {
      throw new IllegalArgumentException("There are no dishes!");
    }

    // O(n * m), where n = dishes, m = ingredients
    for (int row = 0; row < dishes.length; row++) {
      String aDish = null;
      if (dishes[row].length <= 1) {
        throw new IllegalArgumentException(
            "Each dish needs at least on ingredient");
      }
      for (int col = 0; col < dishes[row].length; col++) {
        if (col == 0) { // its a dish
          aDish = dishes[row][0];
          aDish = (aDish != null ? aDish : ERR_NULL_DISH);
          aDish = (!aDish.isEmpty() ? aDish : ERR_EMPTY_DISH);
        } else { // ingredient(S)
          String ingredient = dishes[row][col];
          ingredient = (ingredient != null ? ingredient : ERR_NULL_INGREDIENT);
          ingredient = (!ingredient.isEmpty() ? ingredient
              : ERR_EMPTY_INGREDIENT);
          Set<String> tmpSet = null;
          if (smap.containsKey(ingredient)) { // O(1)
            // already have a set
            tmpSet = smap.get(ingredient); // O(1)
            tmpSet.add(aDish); // O(1)
          } else {
            // need to create a set
            tmpSet = new TreeSet<String>(); // alpha-sort order
            tmpSet.add(aDish); // O(1)
            smap.put(ingredient, tmpSet); // O(1)
          }
        }
      }
    }

    // Convert Map to Multi-D array alpha-sorted
    Set<String> ingredients = smap.keySet();
    String[] keys = ingredients.toArray(new String[smap.size()]);

    String[][] output = new String[keys.length][];
    int row = 0;
    for (String ingredient : keys) { // O(m) where m = ingredients
      output[row++] = createRowTree(ingredient, smap);
    }
    return output;
  }

  /**
   * Create a single row, first element is ingredient, subsequent elements
   * are alpha-sorted Dishes which use the ingredient
   * @param ingredientKey
   * @param map - value Set of String, is a TreeSet, no sort required
   * @return
   */
  String[] createRowTree(String ingredientKey, Map<String, Set<String>> map) {
    Set<String> dishSet = map.get(ingredientKey); // O(1)
    String[] dishArr = dishSet.toArray(new String[dishSet.size()]);
    String[] row = new String[1 + dishArr.length];
    row[0] = ingredientKey;
    System.arraycopy(dishArr, 0, row, 1, row.length - 1);
    return row;
  }

}
