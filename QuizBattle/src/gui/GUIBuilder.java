package gui;

import beans.Category;
import beans.Question;
import client.Client;
import dlg.LoginDlg;
import java.util.ArrayList;

/**
 *
 * @author kriegl
 */
public class GUIBuilder {
    private LoadingView loadingView = null;
    private StartGame startGame;
    private StartPage startPage;
    private LoginDlg loginDlg;
    private QuestionView questionView;
    private PlayerWait playerWait;
    private Client client;
    private BattleView battleView;
    
    public ChooseCategory openChooseCategoryGUI(ArrayList<Category> categories){
        ChooseCategory chooseCategory = new ChooseCategory("Category", categories, client);
        chooseCategory.setVisible(true);
        return chooseCategory;
    }
    public GUIBuilder()
    {
        client = new Client();
        client.setGUIBuilder(this);
        openStartPage(false);
    }
    
    
    public void openStartPage(boolean loginFailed)
    {
      if(!loginFailed)
      {
          startPage = new StartPage("StartPage", client, this);
          startPage.setVisible(true);
      }
      else
      {
          startPage.setVisible(true);
          startPage.setJOptionPane();
      }
    }
    
    public void openLoginDlg()
    {
        loginDlg = new LoginDlg(startPage, true, client, this);
        loginDlg.setVisible(true);
    }
    
    public void openPlayerWait(int scorePlayerOne, int scorePlayerTwo)
    {
        playerWait = new PlayerWait(scorePlayerOne, scorePlayerTwo);
        playerWait.setVisible(true);
    }
    
    public void openLoadingViewGUI(){
        loadingView = new LoadingView("Loading", client);
        loadingView.setVisible(true);
    }
    public void openStartGame()
    {
        startGame = new StartGame(client);
        startGame.setVisible(true);
    }
    public void closeLoadingView(){
        if(loadingView.isVisible()){
            loadingView.setVisible(false);
        }
    }
    
    public void closeCategoryView(ChooseCategory chooseCategory){
        chooseCategory.setVisible(false);
    }
    
    public void closeQuestionView()
    {
        questionView.setVisible(false);
    }
    
    public void openQuestionView(Question question){
        questionView = new QuestionView("Question", question, client);
        questionView.setVisible(true);
    }
    
    public static void main(String[] args) {
        new GUIBuilder();
    }

    public void closePlayerWait() 
    {
        if(playerWait != null)
        {
            playerWait.setVisible(false);
        }
    }

    public void openBattleView(String username, String usernameFromOpponent, int scorePlayerOne, int scorePlayerTwo) {
        battleView = new BattleView(username, usernameFromOpponent, scorePlayerOne, scorePlayerTwo);
        battleView.setVisible(true);
    }

    public void closeBattleView() {
        if(battleView != null){
            battleView.setVisible(false);
        }
    }
}
