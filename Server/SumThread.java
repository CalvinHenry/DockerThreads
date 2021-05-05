
import java.util.ArrayList;

public class SumThread extends DockerThread {

    
    ArrayList<Integer> listToSum;
    public SumThread(ArrayList<Integer> a) {
        listToSum = a;
    }

    int sum = 0;
    private static final long serialVersionUID = 1L;
    @Override
    public void execute() {

        for (int i : listToSum) {
            sum += i;
        }
        this.result = new Integer(sum);
    }


}
