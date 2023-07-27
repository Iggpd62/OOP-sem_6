import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
interface CharacterInterface {
    void takeDamage(int damage);

    void step();
    String getInfo();
    Coordinates getCoordinates();
}

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        List<CharacterInterface> heroes1 = new ArrayList<>();
        List<CharacterInterface> heroes2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            heroes1.add(new Peasant("Крестьянин" + (i + 1), 100, 5, i, i, 10, heroes1, heroes2));
            heroes2.add(new Rogue("Разбойник" + (i + 1), 150, 10, i, i, 10, heroes1, heroes2));
            heroes2.add(new Sniper("Снайпер" + (i + 1), 130, 8, i, i,10, heroes1, heroes2 ));
            heroes2.add(new Warlock("Колдун" + (i + 1), 80, 6, i, i, 10, heroes1, heroes2));
            heroes1.add(new Spearman("Клпейщик" + (i + 1), 150, 7, i, i, 10, heroes1, heroes2));
            heroes1.add(new Crossbowman("Арбалетчик" + (i + 1), 120, 8, i, i, 10, heroes1, heroes2));
            heroes1.add(new Monk("Монах" + (i + 1), 90, 5, i, i, 10, heroes1, heroes2));
        }
        for (CharacterInterface hero : heroes1) {
            Circle circle = new Circle(hero.getCoordinates().getX() * 50 + 50, hero.getCoordinates().getY() * 50 + 50, 20, Color.BLUE);
            root.getChildren().add(circle);
        }

        for (CharacterInterface hero : heroes2) {
            Circle circle = new Circle(hero.getCoordinates().getX() * 50 + 750, hero.getCoordinates().getY() * 50 + 50, 20, Color.RED);
            root.getChildren().add(circle);
        }
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                for (CharacterInterface hero : heroes1) {
                    hero.step();
                    Circle circle = (Circle) root.getChildren().get(heroes1.indexOf(hero));
                    circle.setCenterX(hero.getCoordinates().getX() * 50 + 50);
                    circle.setCenterY(hero.getCoordinates().getY() * 50 + 50);
                }

                for (CharacterInterface hero : heroes2) {
                    hero.step();
                    Circle circle = (Circle) root.getChildren().get(heroes1.size() + heroes2.indexOf(hero));
                    circle.setCenterX(hero.getCoordinates().getX() * 50 + 750);
                    circle.setCenterY(hero.getCoordinates().getY() * 50 + 50);
                }
            }
        };
        timer.start();
        primaryStage.setScene(scene);
        primaryStage.show();

        CharacterInterface closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (CharacterInterface hero : heroes1) {
            for (CharacterInterface enemy : heroes2) {
                double distance = calculateDistance(hero, enemy);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEnemy = enemy;
                }
            }
        }

        if (closestEnemy != null) {
            System.out.println("Наиболее приближенный противник:");
            System.out.println("Имя: " + closestEnemy.getInfo());
            System.out.println("Расстояние: " + closestDistance);
        }
    }

    public static double calculateDistance(CharacterInterface c1, CharacterInterface c2) {
        int dx = c2.getCoordinates().getX() - c1.getCoordinates().getX();
        int dy = c2.getCoordinates().getY() - c1.getCoordinates().getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}