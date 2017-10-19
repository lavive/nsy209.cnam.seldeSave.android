package nsy209.cnam.seldesave.shared.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 23/09/17.
 */

public class SuppliesDemandsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Json(name = "suppliesDemands")
    private List<SupplyDemandDto> suppliesDemands = new ArrayList<SupplyDemandDto>();

    /* getter and setter */

    public List<SupplyDemandDto> getSuppliesDemands() {
        return suppliesDemands;
    }

    public void setSuppliesDemands(List<SupplyDemandDto> suppliesDemands) {
        this.suppliesDemands = suppliesDemands;
    }

    @Override
    public String toString(){
        String result ="{ ";
        for(SupplyDemandDto supplyDemandDto:suppliesDemands){
            result += supplyDemandDto.toString()+" , ";
        }
        result = result.substring(0,result.length()-1);
        result +="}";
        return result;
    }
}
