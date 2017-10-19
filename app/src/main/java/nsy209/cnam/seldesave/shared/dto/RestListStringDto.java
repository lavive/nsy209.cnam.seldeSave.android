package nsy209.cnam.seldesave.shared.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 22/09/17.
 */

public class RestListStringDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Json(name = "listString")
    private List<String> listString = new ArrayList<String>();

    /* getter and setter */
    public List<String> getListString() {
        return listString;
    }

    public void setListString(List<String> listString) {
        this.listString = listString;
    }

    @Override
    public String toString(){
        String result ="{ ";
        for(String s:listString){
            result += s+" , ";
        }
        result = result.substring(0,result.length()-1);
        result +="}";
        return result;
    }

}
