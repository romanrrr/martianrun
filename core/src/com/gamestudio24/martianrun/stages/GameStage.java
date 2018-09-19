/*
 * Copyright (c) 2014. William Mora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gamestudio24.martianrun.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.gamestudio24.martianrun.actors.*;
import com.gamestudio24.martianrun.actors.menu.*;
import com.gamestudio24.martianrun.box2d.InterractionUserData;
import com.gamestudio24.martianrun.box2d.UserData;
import com.gamestudio24.martianrun.config.Config;
import com.gamestudio24.martianrun.config.ConfigBackground;
import com.gamestudio24.martianrun.config.ConfigButton;
import com.gamestudio24.martianrun.config.ConfigLoader;
import com.gamestudio24.martianrun.enums.Difficulty;
import com.gamestudio24.martianrun.enums.GameState;
import com.gamestudio24.martianrun.enums.UserDataType;
import com.gamestudio24.martianrun.utils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class GameStage extends Stage implements ContactListener {

    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private World world;
    private Ground ground;
    private Runner runner;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;

    private Rectangle screenLeftSide;
    private Rectangle screenRightSide;

    private SoundButton soundButton;
    private MusicButton musicButton;
    private PauseButton pauseButton;
    private StartButton startButton;

    private AboutButton aboutButton;
    private ShareButton shareButton;
    private ExitButton exitButton;

    private ResultLabel resultLabel;


    private Timer.Task timerTask;
    private Timer timer;
    private List<Background> backgroundList;

    private Score score;
    private float totalTimePassed;
    private boolean tutorialShown;

    private Vector3 touchPoint;
    private Random random;

    private int gameOverCounter = 0;

    Preferences prefs = Gdx.app.getPreferences("default");

    public GameStage() {
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        random = new Random();
        setUpCamera();
        setUpStageBase();
        setUpGameLabel();
        setUpMainMenu();
        setUpTouchControlAreas();
        Gdx.input.setInputProcessor(this);
        AudioUtils.getInstance().init();
        onGameOver();
    }

    private void setUpStageBase() {
        setUpWorld();
        setUpFixedMenu();
    }

    private void setUpGameLabel() {
        Rectangle gameLabelBounds = new Rectangle(0, getCamera().viewportHeight * 7 / 9,
                getCamera().viewportWidth, getCamera().viewportHeight / 4);
        addActor(new GameLabel(gameLabelBounds));
    }

    private void setUpAboutText() {
        Rectangle gameLabelBounds = new Rectangle(0, getCamera().viewportHeight * 5 / 8,
                getCamera().viewportWidth, getCamera().viewportHeight / 4);
        addActor(new AboutLabel(gameLabelBounds));
    }

    /**
     * These menu buttons are always displayed
     */
    private void setUpFixedMenu() {
        setUpSound();
        setUpMusic();
        setUpScore();
    }

    private void setUpSound() {
        Rectangle soundButtonBounds = createButtonBounds(ConfigLoader.getConfig().getSoundButton());
        soundButton = new SoundButton(soundButtonBounds);
        addActor(soundButton);
    }

    private void setUpMusic() {
        Rectangle musicButtonBounds = createButtonBounds(ConfigLoader.getConfig().getMusicButton());
        musicButton = new MusicButton(musicButtonBounds);
        addActor(musicButton);
    }

    private void setUpScore() {

        Rectangle maxScoreBounds = new Rectangle(0,
                getCamera().viewportHeight * 57 / 64 - 20, getCamera().viewportWidth -10,
                getCamera().viewportHeight / 8);

        Rectangle scoreBounds = new Rectangle(0,
                getCamera().viewportHeight * 57 / 64  - getCamera().viewportHeight / 16 - 20, getCamera().viewportWidth - 10,
                getCamera().viewportHeight / 8);

        Rectangle multipleBounds = new Rectangle(0,
                getCamera().viewportHeight * 57 / 64  - getCamera().viewportHeight / 16 - 50, getCamera().viewportWidth - 10,
                getCamera().viewportHeight / 8);

        score = new Score(scoreBounds, maxScoreBounds, multipleBounds);
        score.setMaxScore(prefs.getFloat("maxScore"));
        addActor(score);
    }

    private void setUpPause() {
        Rectangle pauseButtonBounds = createButtonBounds(ConfigLoader.getConfig().getPauseButton());
        pauseButton = new PauseButton(pauseButtonBounds, new GamePauseButtonListener());
        addActor(pauseButton);
    }

    /**
     * These menu buttons are only displayed when the game is over
     */
    private void setUpMainMenu() {
        setUpStart();
        setUpAbout();
        setUpShare();
        setUpExit();
    }

    private Rectangle createButtonBounds(ConfigButton configButton){

        return new Rectangle(configButton.getX() * Constants.WORLD_TO_SCREEN - configButton.getWidth() * Constants.WORLD_TO_SCREEN / 2,
                configButton.getY() * Constants.WORLD_TO_SCREEN - configButton.getHeight() * Constants.WORLD_TO_SCREEN / 2,
                configButton.getWidth() * Constants.WORLD_TO_SCREEN,
                configButton.getWidth() * Constants.WORLD_TO_SCREEN);
    }

    private void setUpStart() {
        Rectangle startButtonBounds = createButtonBounds(ConfigLoader.getConfig().getStartButton());
        startButton = new StartButton(startButtonBounds, new GameStartButtonListener());
        addActor(startButton);
    }

    private Boolean aboutEnabled;
    private boolean aboutRequest = false;

    private void setUpAbout() {

        Rectangle aboutButtonBounds = createButtonBounds(ConfigLoader.getConfig().getAboutButton());
        aboutButton = new AboutButton(aboutButtonBounds, new GameAboutButtonListener());
        addActor(aboutButton);
        aboutButton.setVisible(false);
        Gdx.app.log("aboutEnabled", String.valueOf(aboutEnabled)+" "+GameManager.getInstance().getGameState().name());

        if(aboutEnabled == null) {
            if(!aboutRequest) {
                aboutRequest = true;
                GameManager.getInstance().isDialogEnabled(new GameManager.AboutDialogEnabledListener() {
                    @Override
                    public void onAboutEnabled(boolean enabled) {
                        Gdx.app.log("about", String.valueOf(enabled));
                        aboutEnabled = enabled;
                        aboutRequest = false;
                        aboutButton.setVisible(enabled && GameManager.getInstance().getGameState() == GameState.OVER);
                    }
                });
            }
        }else {
            aboutButton.setVisible(aboutEnabled && GameManager.getInstance().getGameState() == GameState.OVER);
        }

    }

    private void setUpShare() {
        Rectangle shareButtonBounds =  createButtonBounds(ConfigLoader.getConfig().getShareButton());
        shareButton = new ShareButton(shareButtonBounds, new GameShareButtonListener());
        addActor(shareButton);
    }

    private void setUpExit() {
        Rectangle exitButtonBounds =  createButtonBounds(ConfigLoader.getConfig().getExitButton());
        exitButton = new ExitButton(exitButtonBounds, new GameExitButtonListener());
        addActor(exitButton);
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setUpBackground();
        setUpGround();
    }


    private void setUpBackground() {
        backgroundList = new ArrayList<Background>();
        for (ConfigBackground configBackground : ConfigLoader.getConfig().getConfigBackgroundList()) {
            Background background = new Background(configBackground.getId(), configBackground.getSpeedPercentage());
            backgroundList.add(background);
            addActor(background);
        }
    }

    private void setUpGround() {
        ground = new Ground(WorldUtils.createGround(world));
        addActor(ground);
    }

    private void setUpCharacters() {
        setUpRunner();
        setUpPauseLabel();
        setUpResultLabel();
        timerTask = new Timer.Task() {
            @Override
            public void run() {
                if (!runner.isHit()) {
                    createEnemy();
                    if (Math.abs(random.nextInt() % 100) < 10) {
                        createCoin();
                    }
                    timer.scheduleTask(timerTask
                            , GameManager.getInstance().getDifficulty().getGenerateTime() / 1000f);
                }
            }
        };
        timer = new Timer();
        timer.scheduleTask(timerTask
                , GameManager.getInstance().getDifficulty().getGenerateTime() / 1000f);

    }

    private void setUpRunner() {
        if (runner != null) {
            runner.remove();
        }
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
    }

    private void setUpCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    private void setUpTouchControlAreas() {
        touchPoint = new Vector3();
        screenLeftSide = new Rectangle(0, 0, getCamera().viewportWidth / 2,
                getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0,
                getCamera().viewportWidth / 2, getCamera().viewportHeight);
    }

    private void setUpPauseLabel() {
        Rectangle pauseLabelBounds = new Rectangle(0, getCamera().viewportHeight /2,
                getCamera().viewportWidth, getCamera().viewportHeight / 4);
        addActor(new PausedLabel(pauseLabelBounds));
    }

    private void setUpResultLabel() {
        Rectangle resultLabelBounds = new Rectangle(0, getCamera().viewportHeight * 7 / 9,
                getCamera().viewportWidth, getCamera().viewportHeight / 4);
        resultLabel = new ResultLabel(resultLabelBounds);
        addActor(resultLabel);
    }


    private void setUpTutorial() {
        if (tutorialShown) {
            return;
        }
        setUpLeftTutorial();
        setUpRightTutorial();
        setUpTopTutorial();
        tutorialShown = true;
    }

    private void setUpLeftTutorial() {
        float width = getCamera().viewportHeight / 4;
        float x = getCamera().viewportWidth / 4 - width / 2;
        Rectangle leftTutorialBounds = new Rectangle(x, getCamera().viewportHeight * 9 / 20, width,
                width);
        addActor(new Tutorial(leftTutorialBounds, Constants.TUTORIAL_BOX_NAME, Constants.RUNNER_DODGING_HINT,
                Constants.TUTORIAL_LEFT_TEXT, false));
    }

    private void setUpRightTutorial() {
        float width = getCamera().viewportHeight / 4;
        float x = getCamera().viewportWidth * 3 / 4 - width / 2;
        Rectangle rightTutorialBounds = new Rectangle(x, getCamera().viewportHeight * 9 / 20, width,
                width);
        addActor(new Tutorial(rightTutorialBounds, Constants.TUTORIAL_BOX_NAME, Constants.RUNNER_JUMPING_HINT,
                Constants.TUTORIAL_RIGHT_TEXT, true));
    }

    private void setUpTopTutorial() {
        Rectangle gameLabelBounds = new Rectangle(200, getCamera().viewportHeight - 100,
                getCamera().viewportWidth - 400, getCamera().viewportHeight / 4);
        addActor(new BonusLabel(gameLabelBounds));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (GameManager.getInstance().getGameState() == GameState.PAUSED) return;

        if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
            totalTimePassed += delta;
            updateDifficulty();
        }

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }
        for (Background background : backgroundList) {
            background.setSpeed(GameManager.getInstance().getDifficulty().getEnemyLinearVelocity().x);
        }

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 8, 3);
            accumulator -= TIME_STEP;

        }

        //TODO: Implement interpolation

    }


    private void update(Body body) {
        if (!BodyUtils.bodyInBounds(body) || (body.getUserData() instanceof InterractionUserData && ((InterractionUserData) body.getUserData()).isToDelete())) {
            /*if ((BodyUtils.bodyIsCoin(body)) && !runner.isHit()) {
                createCoin();
            }
            if ((BodyUtils.bodyIsEnemy(body)) && !runner.isHit()) {
                createEnemy();
            }*/
            world.destroyBody(body);
        } else {

            if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
                ((UserData) body.getUserData()).setLinearVelocity(
                        GameManager.getInstance().getDifficulty().getEnemyLinearVelocity());
            }else {
                ((UserData) body.getUserData()).setLinearVelocity(new Vector2(0, 0));
            }

        }
    }

    private void createEnemy() {
        Interaction enemy = WorldUtils.createEnemy(world);
        enemy.getUserData().setLinearVelocity(
                GameManager.getInstance().getDifficulty().getEnemyLinearVelocity());
        addActor(enemy);
    }

    private void createCoin() {
        Interaction enemy = WorldUtils.createCoin(world);
        enemy.getUserData().setLinearVelocity(
                GameManager.getInstance().getDifficulty().getEnemyLinearVelocity());
        addActor(enemy);
    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        // Need to get the actual coordinates
        translateScreenToWorldCoordinates(x, y);

        // If a menu control was touched ignore the rest
        if (menuControlTouched(touchPoint.x, touchPoint.y)) {
            return super.touchDown(x, y, pointer, button);
        }

        if (GameManager.getInstance().getGameState() != GameState.RUNNING) {
            return super.touchDown(x, y, pointer, button);
        }

        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            runner.jump();
        } else if (leftSideTouched(touchPoint.x, touchPoint.y)) {
            runner.dodge();
        }

        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (GameManager.getInstance().getGameState() != GameState.RUNNING) {
            return super.touchUp(screenX, screenY, pointer, button);
        }

        if (runner.isDodging()) {
            runner.stopDodge();
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }

    private boolean menuControlTouched(float x, float y) {
        boolean touched = false;

        switch (GameManager.getInstance().getGameState()) {
            case OVER:
                touched = startButton.getBounds().contains(x, y)
                        || aboutButton.getBounds().contains(x, y);
                break;
            case RUNNING:
            case PAUSED:
                touched = pauseButton.getBounds().contains(x, y);
                break;
        }

        return touched || soundButton.getBounds().contains(x, y)
                || musicButton.getBounds().contains(x, y);
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    private boolean leftSideTouched(float x, float y) {
        return screenLeftSide.contains(x, y);
    }

    /**
     * Helper function to get the actual coordinates in my world
     *
     * @param x
     * @param y
     */
    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b)) ||
                (BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsRunner(b))) {
            if (runner.isHit()) {
                return;
            }
            runner.hit();


            resultLabel.setScore(score, prefs.getFloat("maxScore") < score.getScore());

            GameManager.getInstance().submitScore(score.getScore());
            prefs.putFloat("maxScore", score.getMaxScore());
            prefs.flush();

            onGameOver();
            GameManager.getInstance().addGamePlayed();
            GameManager.getInstance().addJumpCount(runner.getJumpCount());
        } else if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
                (BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
            runner.landed();
        }

    }

    private void updateDifficulty() {

        if (GameManager.getInstance().isMaxDifficulty()) {
            return;
        }

        Difficulty currentDifficulty = GameManager.getInstance().getDifficulty();

        if (totalTimePassed > GameManager.getInstance().getDifficulty().getLevel() * 5) {

            int nextDifficulty = currentDifficulty.getLevel() + 1;
            String difficultyName = "DIFFICULTY_" + nextDifficulty;
            GameManager.getInstance().setDifficulty(Difficulty.valueOf(difficultyName));

            runner.onDifficultyChange(GameManager.getInstance().getDifficulty());
            score.setMultiplier(score.getMultiplier() + 1);

            displayAd();
        }

    }

    private void displayAd() {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if (contact.getFixtureA() == null || contact.getFixtureB() == null || contact.getFixtureA().getBody() == null ||
                contact.getFixtureA().getBody() == null) {
            contact.setEnabled(false);
            return;
        }
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsCoin(b) ||
                BodyUtils.bodyIsCoin(a) && BodyUtils.bodyIsRunner(b)) {
            contact.setEnabled(false);
            runner.grabCoin();
            if (BodyUtils.bodyIsCoin(a)) {
                if (!((InterractionUserData) a.getUserData()).isToDelete()) {
                    score.setMultiplier(score.getMultiplier() + 1);
                }
                ((InterractionUserData) a.getUserData()).setToDelete(true);
            }
            if (BodyUtils.bodyIsCoin(b)) {
                if (!((InterractionUserData) b.getUserData()).isToDelete()) {
                    score.setMultiplier(score.getMultiplier() + 1);
                }
                ((InterractionUserData) b.getUserData()).setToDelete(true);
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private class GamePauseButtonListener implements PauseButton.PauseButtonListener {

        @Override
        public void onPause() {
            if (GameManager.getInstance().getGameState() != GameState.OVER) {
                onGamePaused();
            }
        }

        @Override
        public void onResume() {
            if (GameManager.getInstance().getGameState() != GameState.OVER) {
                onGameResumed();
            }
        }

    }

    private class GameStartButtonListener implements StartButton.StartButtonListener {

        @Override
        public void onStart() {
            clear();
            setUpStageBase();
            setUpCharacters();
            setUpPause();
            setUpTutorial();
            onGameResumed();
        }

    }


    private class GameAboutButtonListener implements AboutButton.AboutButtonListener {

        @Override
        public void onAbout() {

            GameManager.getInstance().showDialog();

            /*if (GameManager.getInstance().getGameState() == GameState.OVER) {
                onGameAbout();
            } else {
                clear();
                setUpStageBase();
                setUpGameLabel();
                onGameOver();
            }*/
        }

    }

    private class GameShareButtonListener implements ShareButton.ShareButtonListener {

        @Override
        public void onShare() {
            if(score == null || score.getScore() == 0){
                GameManager.getInstance().share(null);
            }else {
                GameManager.getInstance().share(score.getScore());
            }
        }

    }

    private class GameExitButtonListener implements ExitButton.ExitButtonListener {

        @Override
        public void onExit() {
            Gdx.app.exit();
        }

    }


    Long timerDelay = null;

    private void onGamePaused() {
        GameManager.getInstance().setGameState(GameState.PAUSED);
        if (timer != null) {
            timerDelay = System.currentTimeMillis();
            timer.stop();
        }
    }

    private void onGameResumed() {
        GameManager.getInstance().setGameState(GameState.RUNNING);
        if (timer != null) {
            if (timerDelay != null) {
                timer.delay(System.currentTimeMillis() - timerDelay);
            }
            timer.start();
        }
    }

    private void onGameOver() {
        gameOverCounter++;
        GameManager.getInstance().setGameState(GameState.OVER);
        setUpMainMenu();
        GameManager.getInstance().resetDifficulty();


        timerDelay = null;
        if (timer != null) {
            timer.stop();
        }
        if(totalTimePassed > 0 && gameOverCounter > 3) {
            gameOverCounter = 0;

            GameManager.getInstance().showFullscreenBanner(new GameManager.FullscreenBannerClosedListener() {
                @Override
                public void onBannerClosed() {

                }
            });
        }
        totalTimePassed = 0;
    }

    private void onGameAbout() {
        GameManager.getInstance().setGameState(GameState.ABOUT);
        clear();
        setUpStageBase();
        setUpGameLabel();
        setUpAboutText();
        setUpAbout();
    }

}