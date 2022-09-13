import com.hb.common.enums.BaseEnum;
import com.hb.common.enums.BusinessTypeEnum;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/9/13
 */
public class BeasEnumTest {
    @Test
    public void baseEnumToList(){
        List<Map<String, Object>> maps = BaseEnum.toEnumList(BusinessTypeEnum.class);
        System.out.println(maps);
    }


    @Test
    public void baseEnumByCode(){
        BaseEnum codeEnum = BaseEnum.valueOf(BusinessTypeEnum.class, 1);
        System.out.println(codeEnum);
    }

}
