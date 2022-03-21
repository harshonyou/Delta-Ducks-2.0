import com.badlogic.gdx.math.Vector2;
import java.util.concurrent.ThreadLocalRandom;

class Test {
    public static void main(String[] args) {
        System.out.println("Class Run");
        Test instance = new Test();
        instance.test();
    }

    public void test () {
        System.out.println("Test Run");
        float x = randomNum(0, 100);
        float y = randomNum(0, 100);
    }

    private int randomNum(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}