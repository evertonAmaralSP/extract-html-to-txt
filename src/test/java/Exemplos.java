import org.junit.Test;

public class Exemplos {

    @Test
    public void eventoDeRepeticao() {
        for (int i = 0; i <= 5; i++) {
            if(i%2 ==0)
                System.out.println(i+" - par");
            else
                System.out.println(i+" - impar");
        }
        int i = 0;
        while (i <= 5) {
            if(i%2 ==0)
                System.out.println(i+" - par");
            else
                System.out.println(i+" - impar");

            i++;
        }

    }
}
