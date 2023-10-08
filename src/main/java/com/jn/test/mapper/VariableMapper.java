package com.jn.test.mapper;

import com.jn.test.bean.Variable;
import org.apache.ibatis.annotations.Param;

public interface VariableMapper {
    String getVariableByName(@Param("name") String name);
}
