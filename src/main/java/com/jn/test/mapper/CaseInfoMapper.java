package com.jn.test.mapper;

import com.jn.test.bean.CaseInfo;
import org.apache.ibatis.annotations.Param;

public interface CaseInfoMapper {
    CaseInfo getCaseInfoById(@Param("caseNo") String caseNo);
    CaseInfo getCaseInfo();
}
