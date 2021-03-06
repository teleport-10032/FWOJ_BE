package com.teleport.fwoj_backend.mapper;
import com.teleport.fwoj_backend.pojo.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface userMapper {

    //提供用户名密码查找是否有这个人
    int loginCheck(@Param("username") String username, @Param("passwd") String passwd);
    //根据用户名查找available是否为1
    Integer getAvailableByUsername(@Param("username") String username);
    //根据用户名设置available
    int setAvailableByUsername(@Param("username") String username,@Param("available") boolean available);

    //根据id设置available
    int setAvailableById(@Param("id") int id,@Param("available") boolean available);

    //token存到数据库并返回对象:
    int createToken(@Param("username") String username,@Param("token") String token);

    //通过token查找用户名
    String getUserNameByToken(@Param("token") String token);
    //通过token查找密码
    String getUserPasswdByUsername(@Param("username") String username);
    //通过token查找用户Id
    Integer getUserIdByToken(@Param("token") String token);
    //通过token查找type
    String getUserTypeByToken(@Param("token") String token);
    //根据token查找用户邮箱
    String getUserEmailByToken(@Param("token") String token);
    //通过token查找用户soledList
    String getUserSolvedListByToken(@Param("token") String token);
    //通过token查找用户attemptList
    String getUserAttemptListByToken(@Param("token") String token);

    //根据id查看姓名
    String getUserNameById(@Param("id") Integer id);
    //根据id查看签名档
    String getUserSignById(@Param("id") int id);
    //根据id查看个人站点
    String getUserSiteById(@Param("id") int id);
    //根据id查看github
    String getUserGithubById(@Param("id") int id);
    //根据id查看type
    String getUserTypeById(@Param("id") int id);
    //根据id查看available
    Integer getUserAvailableById(@Param("id") int id);
    //根据id查看solvedNumber
    Integer getUserSolvedNumberById(@Param("id") int id);

    //根据id查看solvedList
    String getUserSolvedListById(@Param("id") int id);
    //根据id查看attemptList
    String getUserAttemptListById(@Param("id") int id);
    //根据id设置solvedList
    int setUserSolvedListById(@Param("id") int id,@Param("solvedList") String solvedList);
    //根据id设置attemptList
    int setUserAttemptListById(@Param("id") int id,@Param("attemptList") String attemptList);
    //使solvedNum++
    int setSolvedNumPlus(@Param("id") int id);



    //通过username查找个人签名
    String getUserSignByUsername(@Param("username") String username);
    //通过username查找site
    String getUserSiteByUsername(@Param("username") String username);
    //通过username查找github
    String getUserGithubByUsername(@Param("username") String username);
    //通过username查找type
    String getUserTypeByUsername(@Param("username") String username);
    //通过username查找solvedList
    String getUserSolvedListByUsername(@Param("username") String username);

    //根据token更新用户名
    int updateUserNameByToken(@Param("token") String token,@Param("username") String username);
    //    根据token更新个人签名
    int updateUserSignByToken(@Param("token") String token,@Param("sign") String sign);
    //    根据token更新site
    int updateUserSiteByToken(@Param("token") String token,@Param("site") String site);
    //    根据token更新github
    int updateUserGithubByToken(@Param("token") String token,@Param("github") String github);

    //通过email查询用户数量
    int emailExist(@Param("email") String email);
    //通过username查询用户数量
    int usernameExist(@Param("username") String username);
//  根据email查询除自己之外的用户数量
    int getEmailNumExpect(@Param("email") String email,@Param("id") int id);
//  根据username查询除自己之外的用户数量
    int getUsernameNumExpect(@Param("username") String username,@Param("id") int id);
    //传入email username passwd写入
    int register(@Param("email") String email,@Param("username") String username,@Param("passwd") String passwd);
    //获取用户列表
    List<user> getUserList(@Param("start") int start, @Param("num") int num,@Param("key") String key);
    //获取用户总数
    int getUserNum();
    //查询token对应的type
    String getTypeByToken(String token);
    //根据id查询用户详细信息
    user getUserDetailById(int id);
    //编辑用户(admin界面)
    int editUserDetail(@Param("email") String email,@Param("username") String username,@Param("type") String type,@Param("passwd") String passwd,@Param("id") int id);
    int editUserDetailWithoutPasswd(@Param("email") String email,@Param("username") String username,@Param("type") String type,@Param("id") int id);
    //根据id删除用户
    int deleteUser(int id);
    int updatePassword(String username,String passwd);
}
