package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameBoardView;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.HumanActionPromptView;

public class SandboxModeView extends BorderPane {

	@FXML
	private Button backButton;

	@FXML
	private Button playButton;

	private final GameBoardView boardView;
	private final ToolboxView toolboxView;
	private final HumanActionPromptView actionPromptView;

	public SandboxModeView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SandboxModeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		backButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU));

		playButton.setOnAction(this::startPlayMode);

		boardView = new GameBoardView();
		boardView.setScaleX(0.9);
		boardView.setScaleY(0.9);
		boardView.setScaleZ(0.9);
		setCenter(getBoardView());

		toolboxView = new ToolboxView();
		setRight(toolboxView);

		actionPromptView = new HumanActionPromptView();
	}

	public HumanActionPromptView getActionPromptView() {
		return actionPromptView;
	}

	public GameBoardView getBoardView() {
		return boardView;
	}

	private void startPlayMode(ActionEvent actionEvent) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.PLAY_SANDBOX);
		setRight(getActionPromptView());
		backButton.setVisible(false);
		playButton.setText("Stop");
		playButton.setOnAction(this::stopPlayMode);
	}

	private void stopPlayMode(ActionEvent actionEvent) {
		setRight(toolboxView);
		backButton.setVisible(false);
		playButton.setText("Play");
		playButton.setOnAction(this::startPlayMode);
	}

	public void updateSandbox(GameContext context) {
		getBoardView().updateGameState(context);
		toolboxView.setContext(context);
	}

}
