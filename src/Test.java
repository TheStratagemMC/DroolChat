/**
 * Created by 18AxMoreen on 5/21/2016.
 */
public class Test {
    public static void main(String[] args){
        System.out.println(p("mul add 5 10 5"));
        System.out.println(p("add 3 2.5 8.8"));
        System.out.println(p("sub mul 2 1 3"));
    }

    public static float p(String i){
        String[] d = i.split(" ");
        int l = 0;
        for (int n = 0; n < d.length; n++) if (!d[n].matches("-?\\d+(.\\d+)?")) l = n;
        int o = d.length - 3 - l;
        float c = m(d[0], Float.valueOf(d[l+1]), Float.valueOf(d[l+2]));
        for (int n = 0; n < o; n++){
            c = m((l > 0 && l-n >= 0) ? d[l+n] : d[0], c, Float.valueOf(d[n+3+l]));
        }
        return c;
    }

    public static float m(String t, float x, float y){
        return (t.equals("mul") ? x * y : t.equals("add") ? x + y : t.equals("sub") ? x - y: t.equals("div") ? x/y:0);
    }
}
