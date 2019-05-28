/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    LoadingView loadingView = null;
    
    public ChooseCategory openChooseCategoryGUI(ArrayList<Category> categories, Client client){
        ChooseCategory chooseCategory = new ChooseCategory("Category", categories, client);
        chooseCategory.setVisible(true);
        return chooseCategory;
    }
    
    public void openLoadingViewGUI(Client client){
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
    
    public void openQuestionView(Question question){
        QuestionView questionView = new QuestionView("Question", question);
        questionView.setVisible(true);
    }
}
