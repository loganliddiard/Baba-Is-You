import edu.usu.graphics.Graphics2D;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class GamePlayView extends GameStateView {

    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.GamePlay;
    private GameModel gameModel;
    private List<String>  level;

    @Override
    public void setLevelData(List<String> level) {
        this.level = level;
    }

    @Override
    public void initialize(Graphics2D graphics) {
        super.initialize(graphics);

        inputKeyboard = new KeyboardInput(graphics.getWindow());
        // When ESC is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ESCAPE, true, (double elapsedTime) -> {
            nextGameState = GameStateEnum.MainMenu;
        });
    }

    @Override
    public void initializeSession() {
        gameModel = new GameModel();

        if(level != null){

            String[] dim = level.get(1).split(" x ");

            int size = Math.max(Integer.parseInt(dim[0]), Integer.parseInt(dim[1]));

            gameModel.initialize(graphics,size,level.get(2)); //parameters should change to when we actually read the first two lines of each level file
            nextGameState = GameStateEnum.GamePlay;

        }

    }
    public void giveLevelInfo(String level) {
        gameModel = new GameModel();
        gameModel.initialize(graphics,20,"lvl-1"); //parameters should change to when we actually read the first two lines of each level file
        nextGameState = GameStateEnum.GamePlay;
    }

    @Override
    public GameStateEnum processInput(double elapsedTime) {
        // Updating the keyboard can change the nextGameState
        inputKeyboard.update(elapsedTime);
        return nextGameState;
    }

    @Override
    public void update(double elapsedTime) {
        gameModel.update(elapsedTime);
    }

    @Override
    public void render(double elapsedTime) {
        // Nothing to do because the render now occurs in the update of the game model
    }
}
