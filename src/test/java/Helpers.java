import java.util.Random;

public class Helpers {
    int number;
    public int randomGenerator(int bound){
        Random random = new Random();
        number = random.nextInt(bound);
        System.out.println(number);
        return number;
    }

}
