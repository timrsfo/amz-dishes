Dishes

Problem Statement:

Given list of dishes and associated ingredients you have to group dishes by ingredients in lexicographical order.

Example: 

Input: First item in each row is the name of a dish, remaining items are
       ingredients used in the dish.

    dishes 
      = [ 
          [ "Salad", "Tomato", "Cucumber", "Salad", "Sauce"],
          ["Pizza", "Tomato", "Sausage", "Sauce", "Dough" ],
          ["Quesadilla", "Chicken", "Cheese", "Sauce"],
          ["Sandwich", "Salad", "Bread", "Tomato", "Cheese"],
        ]

Output: For each ingredient list the dishes it is in, in alpha order.
        Where ingredient is first item in list and dish(s) follow

    ingredients 
        = [
             [ "Bread", "Sandwich" ],
             [ "Cheese", "Quesadilla", "Sandwich" ],
             [ "Chicken", "Quesadilla" ],
             [ "Cucumber", "Salad" ],
             [ "Dough", "Pizza" ],
             [ "Salad", "Salad", "Sandwich" ],
             [ "Sauce", "Pizza", "Quesadilla", "Salad" ],
             [ "Sausage", "Pizza" ],
             [ "Tomato", "Pizza", "Salad", "Sandwich" ],
          ]

 Observations:
 
  * Input values are not alpha-sorted, output is alpha-sorted
  * Output sorting is row alpha sorted by ingredient, within each row dishes are alpha-sorted 
  * Input is a multi-dimensional array of Strings
  * Output is multi-dimensional array of Strings
  
 Test Cases:
 
   * Only one Ingredient Test
   * No Ingredients Test - exception
   * Null Input Test - exception
   * No Overlap Test
   * Multiple Rows, same one ingredient Test
   * Repeat Same Dish Test
   * NULL Dish Test
   * NULL Ingredient Test
   * Empty String Ingredient Test
   * Empty String Dish Test
   * Happy Path: Multiple Dishes with Multiple Ingredients Test
   
 Solution:
 
   * Nested for loop, row, column
   * column, first column is dish
   * data transfered to Map<Ingredient, Set of Dishes >  
   * TreeMap for sorted keys, TreeSet for sorted Dishes
   * Get keySet, alpha-sort
   * foreach sorted keyset get ingredient list
   * alpha-sort ingredient list
   * output to multi-dimensional array
   
  Technical Issue:
   
  * Map<String, Set<String> whether to use HashMap/TreeMap  and/or HashSet/TreeSet.
  * Issue is post insert, need to access keys in sorted order and need the sets to be in sorted order.
  * HashMap and HashSet, keys need to be sorted O(nLogn), and sets need to be sorted O(nLogn), but insert/get/contains are O(1).
  * TreeMap and TreeSet, insert/contain/get is O(logn), but don't need sort Keys / Sets, saving  a O(nLogn) and O(mLogm)
  * Ultimately have to sort both keys and sets, so using TreeMap and TreeSet. 
  * Difference for the insert/get/contain , O(1) vs O(logn) is minimal for small (n) need for (2) O(nLogn) sorts.
