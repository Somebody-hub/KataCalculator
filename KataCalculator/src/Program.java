import java.util.Scanner;

public class Program{

    enum NumberSys{
        NOONE,
        DECIMAL,
        ROMAN
    }

    enum RomanNumb{
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        int value;

        RomanNumb(int value){
            this.value = value;
        }

        int getVal (){return value;
        }
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String inp = in.nextLine();
        in.close();
        System.out.print(calc(inp));
    }

    public static String calc(String input)
    {
        String[] argums = input.split(" ");
        try {
            if (argums.length != 3)
                throw new Exception("throws Exception");
        }
        catch (Exception ex) {
            System.out.print(ex.getMessage());
            System.exit(0);
        }

        int a = 0, b = 0, ans = 0;
        String sign = argums[1];
        NumberSys numSys = NumberSys.NOONE;

        try{
            if (isNecDecimal(argums[0])){
                a = Integer.parseInt(argums[0]);
                numSys = NumberSys.DECIMAL;
            }
            else if (isNecRoman(argums[0])){
                a = romanToDecimal(argums[0]);
                numSys = NumberSys.ROMAN;
            }
            else throw new Exception("throws Exception");
        }
        catch (Exception ex) {
            System.out.print(ex.getMessage());
            System.exit(0);
        }

        switch (numSys){
            case DECIMAL:
                try {
                    b = Integer.parseInt(argums[2]);
                } catch (Exception ex){
                    System.out.print("throws Exception");
                    System.exit(0);
                }
                break;
            case ROMAN:
                try {
                    b = romanToDecimal(argums[2]);
                } catch (Exception ex){
                    System.out.print("throws Exception");
                    System.exit(0);
                }
                break;
        }
        try {
            ans = switch (sign) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                case "/" -> a / b;
                default -> throw new Exception("throws Exception");
            };
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.exit(0);
        }

        switch (numSys){
            case DECIMAL:
                return Integer.toString(ans);
            case ROMAN:
                try {

                    if (ans > 0)
                        return decimalToRoman(ans);
                    else throw new Exception("throws Exception");
                } catch (Exception ex){
                    System.out.print(ex.getMessage());
                    System.exit(0);
                }
        }
        return "";
    }

    static boolean isNecDecimal(String strNum){
        int i;
        if (strNum == null)
            return false;
        try {
            i = Integer.parseInt(strNum);
        } catch (Exception ex){
            return false;
        }
        if ((i > 10) || (i < 1)){
            return false;
        }
        return true;
    }
    static boolean isNecRoman(String strNum){
        int i;
        if (strNum == null)
            return false;
        try {
            i = romanToDecimal(strNum);
        } catch (Exception ex){
            return false;
        }
        if ((i > 10) || (i < 1)) {
            return false;
        }
        return true;
    }

    static int romanToDecimal(String str){
        int res = 0;
        for(int i = 0; i < str.length(); i++){
            char c1 = str.charAt(i);
            int s1 = RomanNumb.valueOf(String.valueOf(c1)).value;
            if(i + 1 < str.length()){
                char c2 = str.charAt(i+1);
                int s2 = RomanNumb.valueOf(String.valueOf(c2)).value;
                if (s1 >= s2){
                    res += s1;
                }
                else{
                    res = res + s2 - s1;
                    i++;
                }
            }
            else{
                res += s1;
            }
        }
        return res;
    }

    static String decimalToRoman(int d){
        StringBuilder res = new StringBuilder();
        int i = RomanNumb.values().length - 1;

        while (d > 0){
            int rn = RomanNumb.values()[i].getVal();
            int div = d/rn;
            d %= rn;
            while (div-- > 0){
                res.append(RomanNumb.values()[i].name());
            }
            i--;
        }
        return res.toString();
    }
}