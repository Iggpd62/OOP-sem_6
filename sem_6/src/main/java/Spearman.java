import java.util.List;

class Spearman extends BaseHero implements CharacterInterface {
    private int arrows;
    private List<CharacterInterface> heroes1;
    private List<CharacterInterface> heroes2;

    public Spearman(String name, int health, int speed, int x, int y, int arrows, List<CharacterInterface> heroes1, List<CharacterInterface> heroes2) {
        super(name, health, speed, x, y);
        this.arrows = arrows;
        this.heroes1 = heroes1;
        this.heroes2 = heroes2;
    }

    public void step() {
        if (health <= 0) {
            System.out.println(name + " погиб, не может совершать ход.");
            return;
        }

        if (arrows <= 0) {
            System.out.println(name + " не осталось стрел, не может совершать ход.");
            return;
        }

        CharacterInterface nearestEnemy = findNearestEnemy();
        if (nearestEnemy == null) {
            System.out.println("Нет врагов вокруг " + name + ", не может совершать ход.");
            return;
        }

        moveTowardsEnemy(nearestEnemy);

        if (isCharacterOnTile(nearestEnemy.getCoordinates().getX(), nearestEnemy.getCoordinates().getY())) {
            System.out.println(name + " не может двигаться на клетку с живым персонажем.");
            return;
        }

        if (calculateDistance(nearestEnemy.getCoordinates().getX(), nearestEnemy.getCoordinates().getY()) == 1) {
            attackEnemy(nearestEnemy);
        }

        arrows--;
    }

    private CharacterInterface findNearestEnemy() {
        CharacterInterface nearestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (CharacterInterface enemy : heroes2) {
            double distance = calculateDistance(enemy.getCoordinates().getX(), nearestEnemy.getCoordinates().getY());
            if (distance < closestDistance) {
                closestDistance = distance;
                nearestEnemy = enemy;
            }
        }

        return nearestEnemy;
    }

    private void moveTowardsEnemy(CharacterInterface enemy) {
        int enemyX = enemy.getCoordinates().getX();
        int enemyY = enemy.getCoordinates().getY();

        int deltaX = enemyX - getCoordinates().getX();
        int deltaY = enemyY - getCoordinates().getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            setCoordinates(getCoordinates().getX() + Integer.compare(deltaX, 0), getCoordinates().getY());
        } else {
            setCoordinates(getCoordinates().getX(), getCoordinates().getY() + Integer.compare(deltaY, 0));
        }

        System.out.println(name + " двигается в сторону врага.");
    }


    private boolean isCharacterOnTile(int posX, int posY) {
        for (CharacterInterface hero : heroes1) {
            if (hero.getCoordinates().getX() == posX && hero.getCoordinates().getY() == posY) {
                return true;
            }
        }

        return false;
    }

    private double calculateDistance(int posX, int posY) {
        int deltaX = Math.abs(posX - getCoordinates().getX());
        int deltaY = Math.abs(posY - getCoordinates().getY());

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private void attackEnemy(CharacterInterface enemy) {
        int damage = calculateAverageDamage();
        enemy.takeDamage(damage);
        System.out.println(name + " атакует врага " + enemy.getInfo() + " и наносит " + damage + " урона.");
    }

    private int calculateAverageDamage() {
        // Расчет среднего повреждения
        return 15;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            System.out.println(name + " погиб!");
        }
    }

    public void farm() {
        System.out.println(name + " удар копьем");
        System.out.println(name + " делает шаг!");
    }
}