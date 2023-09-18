import cz.cvut.fel.pjv.engine.controller.EnemyController;
import cz.cvut.fel.pjv.engine.controller.InputHandler;
import cz.cvut.fel.pjv.engine.controller.PlayerController;
import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.object.entity.Player;
import cz.cvut.fel.pjv.engine.model.object.entity.ShadowEnemy;
import cz.cvut.fel.pjv.engine.model.object.TargetZone;
import cz.cvut.fel.pjv.engine.model.utils.Position;

import java.util.stream.Stream;

import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.model.utils.Vector;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PlayerTests {


    @ParameterizedTest(name = "coordX: {0}, coordY: {1}")
    @MethodSource("ProvideArgumentsForPlayerIsFacingEnemyTest")
    public void PlayerIsFacingEnemyTest(int coordX, int coordY) {
        //SETTING UP
        boolean expectedResult = true;

        ShadowEnemy shadowEnemy = new ShadowEnemy(new EnemyController(), new SpriteStorage(), new TargetZone());
        shadowEnemy.setPosition(new Position(50,50));
        shadowEnemy.setDirectionVector(new Vector(0,1)); //set direction vector to S
//        System.out.println(shadowEnemy.getDirectionVector().getX() + " " + shadowEnemy.getDirectionVector().getY() );

        Player player = new Player(new GameState(new InputHandler(), new Size(500,500), false),new PlayerController(new InputHandler()),new SpriteStorage(),new TargetZone());
        player.setPosition(new Position(coordX,coordY));
        player.setDirectionVector(new Vector(0,-1)); //set direction vector to N
//        System.out.println(player.getDirectionVector().getX() + " " + player.getDirectionVector().getY() );

        //TESTING
        boolean result = shadowEnemy.isFacing(player.getPosition());



        //FINAL CHECK
        Assertions.assertEquals(expectedResult, result);
    }

    public static Stream<Arguments> ProvideArgumentsForPlayerIsFacingEnemyTest(){
        return Stream.of
                (
                        Arguments.of(10,60),
                        Arguments.of(20,70),
                        Arguments.of(30,80),
                        Arguments.of(40,90),
                        Arguments.of(50,100),
                        Arguments.of(60,110),
                        Arguments.of(70,120),
                        Arguments.of(80,130)
                );
    }

    @ParameterizedTest(name = "coordX: {0}, coordY: {1}, resultDistance: {3}")
    @MethodSource("ProvideArgumentsForPlayerDistanceToEnemyTest")
    public void PlayerDistanceToEnemyTest(int coordX, int coordY, int resultDistance) {
        //SETTING UP
        boolean expectedResult = true;

        ShadowEnemy shadowEnemy = new ShadowEnemy(new EnemyController(), new SpriteStorage(), new TargetZone());
        shadowEnemy.setPosition(new Position(100,100));

        Player player = new Player(new GameState(new InputHandler(), new Size(500,500), false),new PlayerController(new InputHandler()),new SpriteStorage(),new TargetZone());
        player.setPosition(new Position(coordX,coordY));

        //TESTING
        int distance = (int)player.getPosition().distanceTo(shadowEnemy.getPosition());
        boolean result = (distance == resultDistance);


        //FINAL CHECK
        Assertions.assertEquals(expectedResult, result);
    }

    public static Stream<Arguments> ProvideArgumentsForPlayerDistanceToEnemyTest(){
        return Stream.of
                (
                        Arguments.of(100, 100, 0),
                        Arguments.of(150, 100, 50),
                        Arguments.of(150, 150, 70),
                        Arguments.of(50, 150, 70)
                );
    }

    @ParameterizedTest(name = "numberOfCoins: {0}, cost: {1}, resultPrediction: {2}")
    @MethodSource("ProvideArgumentsForPlayerHaveEnoughMoneyTest")
    public void PlayerHaveEnoughMoneyTest(int numberOfCoins, int cost, boolean resultPrediction) {
        //SETTING UP
        boolean expectedResult = resultPrediction;

        Player player = new Player(new GameState(new InputHandler(), new Size(500,500), false),new PlayerController(new InputHandler()),new SpriteStorage(),new TargetZone());
        player.getCoins().earnCoins(numberOfCoins);

        //TESTING
        boolean result = player.getCoins().isEnough(cost);


        //FINAL CHECK
        Assertions.assertEquals(expectedResult, result);
    }

    public static Stream<Arguments> ProvideArgumentsForPlayerHaveEnoughMoneyTest(){
        return Stream.of
                (
                        Arguments.of(100, 100, true),
                        Arguments.of(100, 99, true),
                        Arguments.of(100, 101, false),
                        Arguments.of(50, 0, true),
                        Arguments.of(1, 2, false),
                        Arguments.of(-1, 0, false)
                );
    }

    @Test
    public void PlayerReceivedMoney(){
        //SETTING UP
        boolean expectedResult = true;

        Player player = new Player(new GameState(new InputHandler(), new Size(500,500), false),new PlayerController(new InputHandler()),new SpriteStorage(),new TargetZone());

        //TESTING
        player.getCoins().earnCoins(100);
        boolean result = (player.getCoins().getAvailableCoins() == 100);


        //FINAL CHECK
        Assertions.assertEquals(expectedResult, result);
    }

}
