package com.timr.dishes;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsEqual.*;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DishesTest {
  private static final Logger log = LogManager
      .getLogger(DishesTest.class.getName());

  @DataProvider
  public Object[][] dishes() {
    return new Object[][] {
        {
            "Only one Ingredient Test",
            new String[][] { // input
                { "Salad", "Tomato" },
            },
            new String[][] { // expected response
                { "Tomato", "Salad" },
            },
        },
        {
            "No Overlap Test",
            new String[][] { // input
                { "Salad", "Tomato", "Cucumber", "Salad", "Sauce" },
                { "Sandwich", "Bread", "Cheese" },
            },
            new String[][] { // expected response
                { "Bread", "Sandwich" },
                { "Cheese", "Sandwich" },
                { "Cucumber", "Salad" },
                { "Salad", "Salad" },
                { "Sauce", "Salad" },
                { "Tomato", "Salad" },
            },
        },
        {
            "Multiple Rows, same one ingredient Test",
            new String[][] { // input
                { "Salad", "Tomato" },
                { "Sandwich", "Tomato" },
                { "Soup", "Tomato" },
            },
            new String[][] { // expected response
                { "Tomato", "Salad", "Sandwich", "Soup" },
            },
        },
        {
            "Repeat Same Dish Test",
            new String[][] { // input
                { "Soup", "Tomato", "Beans" },
                { "Soup", "Tomato", "Beans" },
                { "Soup", "Tomato", "Beans" },
            },
            new String[][] { // expected response
                { "Beans", "Soup" },
                { "Tomato", "Soup" },
            },
        },
        {
            "NULL Dish Test",
            new String[][] { // input
                { null, "Cheeze", "Beans" },
            },
            new String[][] { // expected response
                { "Beans", "ERROR_NULL_DISH" },
                { "Cheeze", "ERROR_NULL_DISH" },
            },
        },
        {
            "NULL Ingredient Test",
            new String[][] { // input
                { "Stew", null, "Beans" },
            },
            new String[][] { // expected response
                { "Beans", "Stew" },
                { "ERROR_NULL_INGREDIENT", "Stew" },
            },
        },
        {
            "Empty String Ingredient Test",
            new String[][] { // input
                { "dish1", "", "Beans" },
            },
            new String[][] { // expected response
                { "Beans", "dish1" },
                { "ERROR_EMPTY_INGREDIENT", "dish1" },
            },
        },
        {
            "Empty String Dish Test",
            new String[][] { // input
                { "", "Tomato", "Beans" },
            },
            new String[][] { // expected response
                { "Beans", "ERROR_EMPTY_DISH" },
                { "Tomato", "ERROR_EMPTY_DISH" },
            },
        },
        {
            "Happy Path: Multiple Dishes with Multiple Ingredients Test",
            new String[][] { // input
                { "Salad", "Tomato", "Cucumber", "Salad", "Sauce" },
                { "Pizza", "Tomato", "Sausage", "Sauce", "Dough" },
                { "Quesadilla", "Chicken", "Cheese", "Sauce" },
                { "Sandwich", "Salad", "Bread", "Tomato", "Cheese" },
            },

            new String[][] { // expected response
                { "Bread", "Sandwich" },
                { "Cheese", "Quesadilla", "Sandwich" },
                { "Chicken", "Quesadilla" },
                { "Cucumber", "Salad" },
                { "Dough", "Pizza" },
                { "Salad", "Salad", "Sandwich" },
                { "Sauce", "Pizza", "Quesadilla", "Salad" },
                { "Sausage", "Pizza" },
                { "Tomato", "Pizza", "Salad", "Sandwich" },
            },
        },
    };
  }

  @Test(dataProvider = "dishes")
  public void dishesHashTest(String testDesc, String[][] input,
      String[][] expected) {
    Dishes dishes = new Dishes();
    String[][] actual = dishes.dishesToIngredientsHash(input);
    if (log.isDebugEnabled()) {
      log.debug("Description: " + testDesc);
      for (int i = 0; i < actual.length; i++) {
        log.debug(Arrays.toString(actual[i]));
      }
    }
    assertThat(actual, equalTo(expected));
  }

  @Test(dataProvider = "dishes")
  public void dishesTreeTest(String testDesc, String[][] input,
      String[][] expected) {
    Dishes dishes = new Dishes();
    String[][] actual = dishes.dishesToIngredientsTree(input);

    if (log.isDebugEnabled()) {
      log.debug("Description: " + testDesc);
      for (int i = 0; i < actual.length; i++) {
        log.debug(Arrays.toString(actual[i]));
      }
    }
    assertThat(actual, equalTo(expected));
  }

  @DataProvider
  public Object[][] throwException() {
    return new Object[][] {
        {
            "No Ingredients Test",
            new String[][] { // input
                { "Salad" },
            },
        },
        {
            "Null Input Test",
            null,
        },
    };
  }

  @Test(dataProvider = "throwException", expectedExceptions = IllegalArgumentException.class)
  public void dishesInvalidInputTreeTest(String testDesc, String[][] input) {
    Dishes dishes = new Dishes();
    dishes.dishesToIngredientsTree(input);
  }
}
