import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task1 {

    public List<Integer> checkArray (List<Integer> inArr) throws RuntimeException {

        List<Integer> outArr=new ArrayList<>();
        if (!inArr.isEmpty()) {
            for (int i = inArr.size()-1; i >= 0; i--) {
                if (inArr.get(i) == 4) {
                    break;
                } else {
                    outArr.add(inArr.get(i));
                }
            }
            Collections.reverse(outArr);
            try {
                if (outArr.equals(inArr)) {
                    outArr = null;
                    throw new RuntimeException("Array havnot 4!");
                }
            }catch (RuntimeException e){
                System.out.println(e);
            }
            }else{
            throw new RuntimeException("Array is empty!");
        }


        return outArr;
    }

    public boolean checkArr (List<Integer> inArr){
        boolean result1=false;
        boolean result4=false;
        boolean result=true;
        for (int i = 0; i< inArr.size(); i++){
            if (inArr.get(i)==1){result1=true;}
            if (inArr.get(i)==4){result4=true;}
            if (inArr.get(i)!=1 && inArr.get(i)!=4){
                result=false;
                break;}
            }

        return result1 && result4 && result;
    }

}
