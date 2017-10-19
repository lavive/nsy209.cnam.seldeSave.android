package nsy209.cnam.seldesave.dao;

import java.util.List;

import nsy209.cnam.seldesave.validator.helper.EnumCheck;

/**
 * Created by lavive on 08/06/17.
 */

public interface ErrorMessageDao {

    public void createErrorMessage(List<String> errorMessages);

    public String getErrorMessage(EnumCheck check);
}
