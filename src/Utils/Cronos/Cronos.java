package Utils.Cronos;


/**
 *
 * @author Nito.Crespi
 */
public class Cronos {

    private stopwatch st = new stopwatch();

    public void Start() {
        st = new stopwatch();
        st.start();
    }

    public void End() {
        st.stop();
        System.out.println(st.getElapsedMilliseconds());
    }

}
