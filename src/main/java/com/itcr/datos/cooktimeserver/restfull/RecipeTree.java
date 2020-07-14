package com.itcr.datos.cooktimeserver.restfull;

import com.itcr.datos.cooktimeserver.data_structures.AlphAvlTree;
import com.itcr.datos.cooktimeserver.data_structures.AlphNodeAVL;
import com.itcr.datos.cooktimeserver.object.Recipe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RecipeTree {
    private static AlphAvlTree<Recipe> avlRecipeTree;

    public static void initRecipeList(){
        avlRecipeTree = new AlphAvlTree<>();
        updateRecipeList();

    }
    public static void updateRecipeList(){
        avlRecipeTree.clear();

        JSONParser recipeParser = new JSONParser();
        try{
            JSONObject recipeJSON =  (JSONObject) recipeParser.parse(new FileReader("res/data/Recipes.json"));
            getBranch(recipeJSON);
        }
        catch (Exception e){
            e.printStackTrace();
            getBranch(new JSONObject());
        }
        System.out.println(avlRecipeTree.toString());
    }
    public static void getBranch(JSONObject jsonObject){
        Recipe newRecipe = new Recipe();

        newRecipe = TypeConversion.makeRecipe(jsonObject);

        avlRecipeTree.add(newRecipe, newRecipe.getTitle());

        if(jsonObject.get("right") != null){
            getBranch((JSONObject) jsonObject.get("right"));
        }
        if (jsonObject.get("left") != null){
            getBranch((JSONObject) jsonObject.get("left"));
        }
    }
    public static AlphAvlTree<Recipe> getAvlRecipeTree(){
        return avlRecipeTree;
    }
    public static void addRecipe(Recipe newRecipe){
        if(newRecipe != null){
            avlRecipeTree.add(newRecipe, newRecipe.getTitle());
            saveUser();
            updateRecipeList();
        }
        else{
            System.out.println("Something went wrong while adding the recipe, the recipe was empty");

        }
    }

    public static void saveUser(){
        try (FileWriter file = new FileWriter("res/data/Recipes.json")){
            file.write(avlTravel(avlRecipeTree.getRoot(), new JSONObject()).toString());
            file.flush();
        }catch (IOException e ){ e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    public static JSONObject avlTravel(AlphNodeAVL<Recipe> recipe, JSONObject jsonObject){
        try{jsonObject.put("title", recipe.getData().getTitle());}
        catch (NullPointerException e){jsonObject.put("title",null);}

        try{jsonObject.put("description", recipe.getData().getDescription());}
        catch (NullPointerException e){jsonObject.put("description",null);}

        try{jsonObject.put("author", recipe.getData().getAuthor());}
        catch (NullPointerException e){jsonObject.put("author",null);}

        try{jsonObject.put("type", recipe.getData().getType());}
        catch (NullPointerException e){jsonObject.put("type",null);}

        try{jsonObject.put("duration", recipe.getData().getDuration());}
        catch (NullPointerException e){jsonObject.put("duration",null);}

        try{jsonObject.put("time", recipe.getData().getTime());}
        catch (NullPointerException e){jsonObject.put("time",null);}

        try{jsonObject.put("diet", recipe.getData().getDiet());}
        catch (NullPointerException e){jsonObject.put("diet",null);}

        try{jsonObject.put("steps", recipe.getData().getSteps());}
        catch (NullPointerException e){jsonObject.put("steps",null);}

        try{jsonObject.put("image", recipe.getData().getImage());}
        catch (NullPointerException e){jsonObject.put("image",null);}

        try{jsonObject.put("date", recipe.getData().getDate());}
        catch (NullPointerException e){jsonObject.put("date",null);}

        try{jsonObject.put("price", recipe.getData().getPrice());}
        catch (NullPointerException e){jsonObject.put("price",null);}

        try{jsonObject.put("servings", recipe.getData().getServings());}
        catch (NullPointerException e){jsonObject.put("servings",null);}

        try{jsonObject.put("rating", recipe.getData().getRating());}
        catch (NullPointerException e){jsonObject.put("rating",null);}

        try{jsonObject.put("difficulty", recipe.getData().getDifficulty());}
        catch (NullPointerException e){jsonObject.put("difficulty",null);}

        try{jsonObject.put("comments", TypeConversion.makeCommentArray(recipe.getData().getComments(), new JSONArray()));}
        catch (NullPointerException e){jsonObject.put("difficulty",null);}

        jsonObject.put("left", null);
        jsonObject.put("right", null);

        if(recipe.getLeft()!=null){
            jsonObject.replace("left", avlTravel(recipe.getLeft(), new JSONObject()));
        }
        if(recipe.getRight()!=null){
            jsonObject.replace("right", avlTravel(recipe.getRight(), new JSONObject()));
        }
        return jsonObject;
    }
}
