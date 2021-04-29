


public class MatMultiplyThread extends DockerThread {

    //DockerList<DockerMatrix> listToMultiply;

    DockerList<DockerMatrix> aCol;
    DockerList<DockerMatrix> bRow;;
    int n;
    Integer[][] summation;
    int listSize;
    public MatMultiplyThread(DockerList<DockerMatrix> a, DockerList<DockerMatrix> b, int listSize) {
        aCol = a;
        bRow = b;
        n = a.get(0).size();
        this.listSize = listSize;
        summation = new Integer[n][n];
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < n; j ++) {
                summation[i][j] = 0;
            }
        }
    }


    public void multiplyMatrix(Integer a[][], Integer b[][], Integer n) {
        Integer[][] ret = new Integer [n][n];
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < n; j ++) {
                //ret[i][j] = 0;
                for (int k = 0; k < n; k ++) {
                    summation[i][j] += a[i][k] * b[j][k];
                }
            }
        }
    }

    private static final long serialVersionUID = 1L;
    @Override
    public void execute() {
        for (int i = 0; i < listSize; i ++) {
            
            multiplyMatrix(aCol.get(i).getValue(), bRow.get(i).getValue(), n);
        }
        this.result = new DockerMatrix( aCol.get(0).size(), summation);
    }

    @Override
    public DockerVar getReturn() {
        return  this.result;
    }

}
