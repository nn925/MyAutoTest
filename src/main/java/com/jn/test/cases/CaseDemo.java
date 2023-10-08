package com.jn.test.cases;

import com.jn.test.bean.CaseInfo;
import com.jn.test.mapper.CaseInfoMapper;
import com.jn.test.utils.JDBCUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class CaseDemo {
    private String url;
    private ResourceBundle bundle;
    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }
    @Test(dataProvider = "getCase")
    public void getResult(CaseInfo caseInfo) throws IOException {
        String result;
        String testUrl = url + caseInfo.getApiUrl();
        HttpPost post = new HttpPost(testUrl);
        post.setHeader("content-type","application/json");
        StringEntity stringEntity = new StringEntity(caseInfo.getInputInfo(),"utf-8");
        post.setEntity(stringEntity);
        CloseableHttpResponse response = HttpClients.custom().build().execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
    }

    @DataProvider
    public Iterator<Object[]> getCase() throws IOException {
        SqlSession sqlSession = JDBCUtils.getSqlSession();
        CaseInfoMapper mapper = sqlSession.getMapper(CaseInfoMapper.class);
        CaseInfo caseInfo = mapper.getCaseInfoById("0001");

        List<Object[]> caseInfoListArr = new ArrayList<>();
        caseInfoListArr.add(new Object[]{caseInfo});

        return caseInfoListArr.iterator();
    }
}
