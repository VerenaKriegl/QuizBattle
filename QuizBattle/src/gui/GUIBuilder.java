package gui;

import beans.Category;
import beans.Question;
import client.Client;
import java.util.ArrayList;

/**
 *
 * @author kriegl
 */
public class GUIBuilder {
    private LoadingView loadingView = null;
    private Client client;
    
    public ChooseCategory openChooseCategoryGUI(ArrayList<Category> categories){
        ChooseCategory chooseCategory = new ChooseCategory("Category", categories, client);
        chooseCategory.setVisible(true);
        return chooseCategory;
    }
    public GUIBuilder()
    {
        client = new Client();
        StartPage startPage = new StartPage("StartPage", client, this);
        startPage.setVisible(true);
    }
    
    public void openLoadingViewGUI(){
        loadingView = new LoadingView("Loading", client);
        loadingView.setVisible(true);
    }
    
    public void closeLoadingView(){
        if(loadingView.isVisible()){
            loadingView.setVisible(false);
        }
    }
    
    public void closeCategoryView(ChooseCategory chooseCategory){
        chooseCategory.setVisible(false);
    }
    
    public QuestionView openQuestionView(Question question){
        QuestionView questionView = new QuestionView("Question", question, client);
        questionView.setVisible(true);
        return questionView;
    }
    
    public static void main(String[] args) {
        new GUIBuilder();
    }
}
