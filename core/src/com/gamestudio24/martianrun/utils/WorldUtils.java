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

package com.gamestudio24.martianrun.utils;

import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gamestudio24.martianrun.actors.Coin;
import com.gamestudio24.martianrun.actors.Enemy;
import com.gamestudio24.martianrun.actors.Interaction;
import com.gamestudio24.martianrun.box2d.CoinUserData;
import com.gamestudio24.martianrun.box2d.EnemyUserData;
import com.gamestudio24.martianrun.box2d.GroundUserData;
import com.gamestudio24.martianrun.box2d.RunnerUserData;
import com.gamestudio24.martianrun.config.ConfigEnemy;


import java.util.Random;

import static com.gamestudio24.martianrun.utils.Constants.ENEMY_X;

public class WorldUtils {

    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    private static Random random = new Random();

    public static Body createGround(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(Constants.GROUND_X, Constants.GROUND_Y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.GROUND_WIDTH / 2, Constants.GROUND_HEIGHT / 2);
        body.createFixture(shape, Constants.GROUND_DENSITY);
        body.setUserData(new GroundUserData(Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createRunner(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RUNNER_WIDTH / 2, Constants.RUNNER_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.createFixture(shape, Constants.RUNNER_DENSITY);
        body.resetMassData();
        body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Interaction createEnemy(World world) {
        ConfigEnemy enemyType = RandomUtils.getRandomEnemyType();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(ENEMY_X, enemyType.getYindex() + enemyType.getHeight() / 2f - 0.5f));


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(enemyType.getWidth() / 2, enemyType.getHeight() / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.ENEMY_DENSITY);
        body.resetMassData();


        EnemyUserData userData = new EnemyUserData(enemyType.getWidth(), enemyType.getHeight(),
                enemyType.getId());
        body.setUserData(userData);
        shape.dispose();
        return new Enemy(body);
    }

    public static Interaction createCoin(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(ENEMY_X  + Math.abs(random.nextInt() % 25),  Constants.COIN_Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f / 2, 1f / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.ENEMY_DENSITY);
        body.resetMassData();

        CoinUserData userData = new CoinUserData(1f,1f,
                Constants.COIN_ASSETS_ID);
        body.setUserData(userData);
        shape.dispose();
        return new Coin(body);
    }
}
