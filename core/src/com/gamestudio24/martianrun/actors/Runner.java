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

package com.gamestudio24.martianrun.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gamestudio24.martianrun.box2d.RunnerUserData;
import com.gamestudio24.martianrun.enums.Difficulty;
import com.gamestudio24.martianrun.enums.GameState;
import com.gamestudio24.martianrun.utils.AssetsManager;
import com.gamestudio24.martianrun.utils.AudioUtils;
import com.gamestudio24.martianrun.utils.Constants;
import com.gamestudio24.martianrun.utils.GameManager;

public class Runner extends GameActor {

    private boolean dodging;
    private boolean jumping;
    private boolean hit;
    private Animation runningAnimation;
    private Animation jumpingAnimation;
    private Animation dodgingAnimation;
    private Animation hitAnimation;
    private float stateTime;

    private Sound jumpSound;
    private Sound hitSound;
    private Sound coinSound;

    private int jumpCount;

    public Runner(Body body) {
        super(body);
        jumpCount = 0;
        runningAnimation = AssetsManager.getAnimation(Constants.RUNNER_RUNNING_ASSETS_ID);
        stateTime = 0f;
        jumpingAnimation = AssetsManager.getAnimation(Constants.RUNNER_JUMPING_ASSETS_ID);

        dodgingAnimation = AssetsManager.getAnimation(Constants.RUNNER_DODGING_ASSETS_ID);

        hitAnimation = AssetsManager.getAnimation(Constants.RUNNER_HIT_ASSETS_ID);
        jumpSound = AudioUtils.getInstance().getJumpSound();
        hitSound = AudioUtils.getInstance().getHitSound();
        coinSound = AudioUtils.getInstance().getCoinSound();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float x = screenRectangle.x - (screenRectangle.width * 0.1f);
        float y = screenRectangle.y;
        float width = screenRectangle.width * 1.2f;
        if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (dodging) {
            batch.draw(dodgingAnimation.getKeyFrame(stateTime, true), x, y + screenRectangle.height / 4, width, screenRectangle.height * 3 / 4);
        } else if (hit) {
            // When he's hit we also want to apply rotation if the body has been rotated
            batch.draw(hitAnimation.getKeyFrame(stateTime, false), x, y, width, screenRectangle.height, width, screenRectangle.height, 1f,
                    1f, (float) Math.toDegrees(body.getAngle()));
        } else if (jumping) {
            batch.draw(jumpingAnimation.getKeyFrame(stateTime, false), x, y, width, screenRectangle.height);
        } else {
            batch.draw(runningAnimation.getKeyFrame(stateTime, true), x, y, width, screenRectangle.height);
        }
    }

    @Override
    public RunnerUserData getUserData() {
        return (RunnerUserData) userData;
    }

    public void jump() {

        if (!(jumping || dodging || hit)) {
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
            jumping = true;
            stateTime = 0;
            AudioUtils.getInstance().playSound(jumpSound);
            jumpCount++;
        }

    }

    public void landed() {
        jumping = false;
    }

    public void dodge() {
        if (!(jumping || hit)) {
            stateTime = 0;
            body.setTransform(getUserData().getDodgePosition(), getUserData().getDodgeAngle());
            dodging = true;
        }
    }

    public void stopDodge() {
        dodging = false;
        // If the runner is hit don't force him back to the running position
        if (!hit) {
            body.setTransform(getUserData().getRunningPosition(), 0f);
        }
    }

    public boolean isDodging() {
        return dodging;
    }

    public void hit() {
        stateTime = 0;
        body.applyAngularImpulse(getUserData().getHitAngularImpulse(), true);
        hit = true;
        AudioUtils.getInstance().playSound(hitSound);
    }

    public void grabCoin() {
        AudioUtils.getInstance().playSound(coinSound);
    }

    public boolean isHit() {
        return hit;
    }

    public void onDifficultyChange(Difficulty newDifficulty) {
        setGravityScale(newDifficulty.getRunnerGravityScale());
        getUserData().setJumpingLinearImpulse(newDifficulty.getRunnerJumpingLinearImpulse());
    }

    public void setGravityScale(float gravityScale) {
        body.setGravityScale(gravityScale);
        body.resetMassData();
    }

    public int getJumpCount() {
        return jumpCount;
    }
}
