package app.touchlessChef.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import app.touchlessChef.dao.recipe.RecipeDAO;
import app.touchlessChef.dao.SQLiteDbCRUD;
import app.touchlessChef.model.Recipe;

public class DatabaseAdapter {
    /**
     * The singleton instance.
     */
    private static DatabaseAdapter instance;
    private static final String DATABASE_NAME = "recipesDatabase";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDbCRUD dbCRUD;
    private SQLiteDatabase db;
    private Context myContext;

//    private UserDAO userDAO;
    private RecipeDAO recipeDAO;

    /**
     * A static factory method.
     *
     * @param context
     * @return the singleton instance of the DatabaseAdapter.
     */
    public static DatabaseAdapter getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseAdapter.class) {
                if (instance == null)
                    instance = new DatabaseAdapter(context).open();
            }
        }
        return instance;
    }

    private DatabaseAdapter(Context context) {
        myContext = context;
        dbCRUD = new SQLiteDbCRUD(context, DATABASE_NAME, DATABASE_VERSION);
    }

    private DatabaseAdapter open() {
        db = dbCRUD.getWritableDatabase();
//        userDAO = new UserDAO(db);
        recipeDAO = new RecipeDAO(db);
        return this;
    }

//    public boolean signIn(String email, String password) {
//        User currentUser = userDAO.getUserByEmailAndPassword(email, password);
//        UserPreferences.saveCurrentUser(myContext, currentUser);
//        return currentUser != null;
//    }

//    public void addNewUser(User user) {
//        userDAO.insert(user);
//    }

    public long addNewRecipe(Recipe recipe) {
        return recipeDAO.insert(recipe);
    }

    public void updateRecipe(Recipe recipe) {
        recipeDAO.update(recipe);
    }

    public void deleteRecipe(long recipeId) {
        recipeDAO.deleteById(recipeId);
    }

    public List<Recipe> getAllRecipesByCategory(String category) {
        return recipeDAO.selectAllByCategory(category);
    }
}
