import com.jn.test.cases.BaseTest;
import com.jn.test.config.Environment;
import com.jn.test.mapper.VariableMapper;
import com.jn.test.utils.JDBCUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class VariableTest {
    @Test
    public void test() throws IOException {
        SqlSession sqlSession = JDBCUtils.getSqlSession();
        VariableMapper mapper = sqlSession.getMapper(VariableMapper.class);
        String result = mapper.getVariableByName("operator_no1");
        System.out.println(result);
    }

    @Test
    public void test01() throws IOException {
        Environment.envMap.put("name","haha");
        BaseTest baseTest = new BaseTest();
        String test = "haha111${name}222${operator_no}";
        String s = baseTest.regexReplace(test);
        System.out.println(s);
    }
}
