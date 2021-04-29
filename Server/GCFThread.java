
public class GCFThread extends DockerThread {

    DockerInteger input;
    DockerList<DockerInteger> listToSum;

    public GCFThread(DockerInteger a) {
        input = a;
    }

    
    private static final long serialVersionUID = 1L;

    //Used this as a reference for this function
    //https://www.geeksforgeeks.org/java-program-for-find-largest-prime-factor-of-a-number/
    @Override
    public void execute() {
        int n = (Integer)input.getValue();
        long maxPrime = -1;

        while(n % 2 == 0) {
            maxPrime = 2;
            n >>= 1;
        }

        for (int i = 3; i < Math.sqrt(n); i += 2) {
            while (n % i == 0) {
                maxPrime = i; 
                n = n / i;
            }
        }

        
        
        result = new DockerInteger(n);
    }

    public DockerVar getReturn() {
        return this.result;
    }

}
