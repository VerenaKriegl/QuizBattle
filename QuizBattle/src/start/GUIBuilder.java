package start;

import beans.Category;
import beans.Question;
import client.Client;
import dlg.LoginDlg;
import dlg.SignUpDlg;
import gui.BattleView;
import gui.ChooseCategory;
import gui.EqualView;
import gui.LoadingView;
import gui.LoserView;
import gui.QuestionView;
import gui.StartGame;
import gui.StartPage;
import gui.WinnerView;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tobias Wechtitsch
 */
public class GUIBuilder {

    private LoadingView loadingView = null;
    private StartGame startGame;
    private StartPage startPage;
    private LoginDlg loginDlg;
    private QuestionView questionView;
    private Client client;
    private BattleView battleView;
    private SignUpDlg signUp;
    private EqualView equalView;
    private boolean battleViewOpen = false;

    public GUIBuilder() {
        client = new Client();
        client.setGUIBuilder(this);
        openStartPage(false);
    }

    public ChooseCategory openChooseCategoryGUI(ArrayList<Category> categories) {
        ChooseCategory chooseCategory = new ChooseCategory(categories, client);
        chooseCategory.setVisible(true);
        return chooseCategory;
    }

    public void openEqualView() {
        equalView = new EqualView();
        equalView.setVisible(true);
    }

    public void openStartPage(boolean loginFailed) {
        try {
            if (!loginFailed) {
                startPage = new StartPage("StartPage", client, this);
                startPage.setVisible(true);
            } else {
                startPage.setVisible(true);
                startPage.setJOptionPane();
            }
        } catch (Exception ex) {
            Logger.getLogger(GUIBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openLoginDlg() {
        loginDlg = new LoginDlg(startPage, client, this);
        loginDlg.setVisible(true);
    }

    public void openLoadingViewGUI() {
        loadingView = new LoadingView(client);
        loadingView.setVisible(true);
    }

    public void openStartGame(Map<String, Integer> map) {
        startGame = new StartGame(client, map);
        startGame.setVisible(true);
    }

    public void closeLoadingView() {
        if (loadingView.isVisible()) {
            loadingView.setVisible(false);
        }
    }

    public void closeCategoryView(ChooseCategory chooseCategory) {
        chooseCategory.setVisible(false);
    }

    public void closeQuestionView() {
        questionView.setVisible(false);
    }

    public void openQuestionView(Question question) {
        questionView = new QuestionView(question, client);
        questionView.setVisible(true);
    }

    public boolean isBattleViewOpen() {
        return battleViewOpen;
    }

    public void openBattleView(String username, String usernameFromOpponent, int scorePlayerOne, int scorePlayerTwo) {
        battleView = new BattleView(client, username, usernameFromOpponent, scorePlayerOne, scorePlayerTwo);
        battleView.setVisible(true);
        battleViewOpen = true;
    }

    public void closeBattleView() {
        if (battleView != null) {
            battleView.setVisible(false);
        }
    }

    public void openWinnerView() {
        WinnerView winnerView = new WinnerView();
        winnerView.setVisible(true);
    }

    public void openLoserView() {
        LoserView loserView = new LoserView();
        loserView.setVisible(true);
    }

    public void setButton() {
        battleView.setPlayButton();
    }

    public static void main(String[] args) {
        new GUIBuilder();
    }

    public void openSignUpDlg() 
    {
        signUp = new SignUpDlg(startPage, client);
        signUp.setVisible(true);
    }
}
