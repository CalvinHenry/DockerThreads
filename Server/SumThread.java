
public class SumThread extends DockerThread {

    DockerList<DockerInteger> listToSum;

    public SumThread(DockerList<DockerInteger> a) {
        listToSum = a;
    }

    int sum = 0;
    private static final long serialVersionUID = 1L;
    @Override
    public void execute() {

        for (DockerInteger i : listToSum) {
            sum += i.getValue();
        }
        this.result = new DockerInteger(sum);
    }


}
