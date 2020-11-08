package com.teleport.fwoj_backend.mapper;

import com.teleport.fwoj_backend.pojo.problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface problemMapper {
    //查询问题列表(页数,每页几条)
    List<problem> getProblemList(@Param("start") int start,@Param("num") int num);
    //查询问题总记录数
    Integer getProblemSum();
    //查询总数(admin)
    int getProblemSumAdmin();
    //查询问题详情
    problem getProblemDetail(int id);
    //获取问题列表(Admin) id title createTime visible authorName
    List<problem> getProblemListAdmin(@Param("start") int start,@Param("num") int num,@Param("token") String token,@Param("key") String key);
    //根据id获取问题的visible
    int getProblemVisibleById(int id);
    //根据id设置问题的visible
    int setProblemVisibleById(int id,boolean visible);
    //根据id删除问题
    int deleteProblemById(int id);
    //创建问题
    int createProblem(String title,String des,String input,String output,String inputExample,String outputExample,
                      String hint,int acSubmit,int totalSubmit,String createTime, String authorName,boolean visible);
    //根据id编辑问题id
    int editProblem(String title,String des,String input,String output,String inputExample,String outputExample,
                    String hint,int id);
}
