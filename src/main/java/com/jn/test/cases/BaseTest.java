package com.jn.test.cases;

import com.alibaba.fastjson.JSONObject;
import com.jn.test.bean.CaseInfo;
import com.jn.test.config.Environment;
import com.jn.test.mapper.VariableMapper;
import com.jn.test.utils.JDBCUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseTest {
    /**
     * 封装 post 请求
     * @param caseInfo  测试用例对象
     * @return response响应对象
     * @throws IOException
     */
    public HttpResponse getResultForPost(CaseInfo caseInfo) throws IOException {
        String result;
        String testUrl = "http://localhost:8090";
        HttpPost post = new HttpPost(testUrl + caseInfo.getApiUrl());
        //设置请求头信息
        Map<String, Object> requestHeaderMap = JSONObject.parseObject(caseInfo.getApiHeader());
        for(String key : requestHeaderMap.keySet()){
            post.setHeader(key,requestHeaderMap.get(key).toString());
        }
        //设置入参
        StringEntity stringEntity = new StringEntity(caseInfo.getInputInfo(),"utf-8");
        post.setEntity(stringEntity);
        //执行post请求，获取响应信息
        CloseableHttpResponse response = HttpClients.createDefault().execute(post);
        //result = EntityUtils.toString(response.getEntity(),"utf-8");
        return response;
    }

    /**
     * 响应断言
     * @param res
     * @param caseInfo
     * @throws IOException
     */
    public void assertResponse(HttpResponse res,CaseInfo caseInfo) throws IOException {
        String result = EntityUtils.toString(res.getEntity(),"utf-8");
        Map<String,Object> actualMap = JSONObject.parseObject(result);
        String expected = caseInfo.getExpected();
        if(expected != null){
            Map<String,Object> expectedMap = JSONObject.parseObject(expected);
            for(String key : expectedMap.keySet()){
                Assert.assertEquals(expectedMap.get(key),actualMap.get(key));
            }
        }

    }

    /**
     * 数据库断言
     * @param caseInfo
     * @throws IOException
     */
    public void assetDB(CaseInfo caseInfo) throws IOException {
        SqlSession sqlSession = JDBCUtils.getSqlSession();
        String checked = caseInfo.getChecked();
        if(checked != null){
            Map<String, Object> checkedMap = JSONObject.parseObject(checked);
            for(String key : checkedMap.keySet()){
                Object dbActual = sqlSession.selectOne(key);
                Assert.assertEquals(dbActual, checkedMap.get(key));
            }

        }
    }

    /**
     * 提取响应结果至环境变量中
     * @param res
     * @throws IOException
     */
    public void extractToEnvironment(HttpResponse res) throws IOException {
        String result = EntityUtils.toString(res.getEntity(),"utf-8");
        Map<String,Object> actualMap = JSONObject.parseObject(result);
        for(String key : actualMap.keySet()){
            Environment.envMap.put(key,actualMap.get(key));
        }
    }

    /**
     * 正则表达式替换
     * @param orgStr
     * @return
     * @throws IOException
     */
    public String regexReplace(String orgStr) throws IOException {
        SqlSession sqlSession = JDBCUtils.getSqlSession();
        VariableMapper mapper = sqlSession.getMapper(VariableMapper.class);
        if(orgStr != null){
            Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
            Matcher matcher = pattern.matcher(orgStr);
            String result = orgStr;
            //循环遍历匹配对象
            while (matcher.find()) {
                Object replaceValue;
                //获取整个匹配正则的字符串 ${key}
                String allFindStr = matcher.group(0);
                //找到${XXX}内部的匹配的字符串 key
                String innerStr = matcher.group(1);
                //找到key：xxxx
                //具体的要替换的值（先从自定义的变量中找，再去环境变量中找）
                if(mapper.getVariableByName(innerStr) != null){
                    replaceValue = mapper.getVariableByName(innerStr);
                }else{
                    replaceValue = Environment.envMap.get(innerStr);
                }
                //要替换${key} --> xxxx
                result = result.replace(allFindStr, replaceValue + "");
            }
            return result;
        }
        return null;
    }
}
