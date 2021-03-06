package com.teleport.fwoj_backend.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.teleport.fwoj_backend.service.annService;
import com.teleport.fwoj_backend.service.contestService;
import com.teleport.fwoj_backend.service.problemService;
import com.teleport.fwoj_backend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class adminContorller {

    @Autowired
    private problemService problemServiceObject;
    @Autowired
    private userService userServiceObject;
    @Autowired
    private contestService contestServiceObject;
    @Autowired
    private annService annServiceObject;

    //获取系统信息面板
    @RequestMapping(value = "/getSystemInfo",method = {RequestMethod.GET})
    @CrossOrigin
    public String getSystemInfo(@RequestParam("token") String token)throws JsonProcessingException {
        return userServiceObject.getSystemInfo(token);
    }

    //    查询公告列表(Admin) id title date visible authorName
    @RequestMapping(value = "/getAnnListAdmin",method = {RequestMethod.GET})
    @CrossOrigin
    public String getAnnListAdmin(@RequestParam("page") int page, @RequestParam("pre") int pre,
                                  @RequestParam("key") String key,@RequestParam("token") String token)
            throws JsonProcessingException {
            return annServiceObject.getAnnListAndNumAdmin(page,pre,key,token);
    }


    //按id查询公告详情(admin) id title content
    @RequestMapping(value = "/getAnnDetailByIdAdmin",method = {RequestMethod.GET})
    @CrossOrigin
    public String getAnnDetailByIdAdmin(@RequestParam("token") String token,@RequestParam("id") int id) throws JsonProcessingException {
        return annServiceObject.getAnnDetailByIdAdmin(token,id);
    }

    //根据id删除公告
    @RequestMapping(value = "/deleteAnnById",method = {RequestMethod.DELETE})
    @CrossOrigin
    public String deleteAnnById(@RequestParam("token") String token,@RequestParam("id") int id) throws JsonProcessingException {
        return annServiceObject.deleteAnnById(token,id);
    }

    //根据id改变公告的visible
    @RequestMapping(value = "/changeAnnVisibleById",method = {RequestMethod.PUT})
    @CrossOrigin
    public String changeAnnVisibleById(@RequestParam("token") String token,@RequestParam("id") int id) throws JsonProcessingException {
        return annServiceObject.changeAnnVisible(token,id);
    }

    //创建公告 title content token
    @RequestMapping(value = "/createAnn",method = {RequestMethod.POST})
    @CrossOrigin
    public String createAnn(@RequestParam("token") String token,
                            @RequestParam("title") String title,@RequestParam("content") String content) throws JsonProcessingException {
        return annServiceObject.createAnn(title,content,token);
    }

    //修改公告 id title content token
    @RequestMapping(value = "/updateAnn",method = {RequestMethod.PUT})
    @CrossOrigin
    public String updateAnn(@RequestParam("token") String token,@RequestParam("id") int id,
                            @RequestParam("title") String title,@RequestParam("content") String content) throws JsonProcessingException {
        return annServiceObject.updateAnn(token,id,title,content);
    }

    //获取问题列表(Admin)
    //id title createTime visible authorName
    @RequestMapping(value = "/getProblemListAdmin",method = {RequestMethod.GET})
    @CrossOrigin
    public String getProblemList(@RequestParam("page") int page, @RequestParam("pre") int pre,@RequestParam("token") String token,@RequestParam("key") String key)throws JsonProcessingException {
        return problemServiceObject.getProblemListAdmin(page,pre,key,token);
    }

    //更改题目的visible
    @RequestMapping(value = "/changeProblemVisible",method = {RequestMethod.PUT})
    @CrossOrigin
    public String changeProblemVisible(@RequestParam("token") String token,@RequestParam("id") int id) throws JsonProcessingException {
        return problemServiceObject.changeProblemVisible(token,id);
    }

    //根据id删除题目
    @RequestMapping(value = "/deleteProblemById",method = {RequestMethod.DELETE})
    @CrossOrigin
    public String deleteProblemById(@RequestParam("token") String token,@RequestParam("id") int id) throws JsonProcessingException {
        return problemServiceObject.deleteProblemById(token,id);
    }

    //创建题目
    @RequestMapping(value = "/createProblem",method = {RequestMethod.POST})
    @CrossOrigin
    public String createProblem(@RequestParam("token") String token,@RequestParam("title") String title,@RequestParam("des") String des,
                                @RequestParam("input") String input,@RequestParam("output") String output,
                                @RequestParam("inputExample") String inputExample,@RequestParam("outputExample") String outputExample,
                                @RequestParam("hint") String hint) throws JsonProcessingException {
        return problemServiceObject.createProblem(token,title,des,input,output,inputExample,outputExample,hint);
    }

    //按id查找题目详情(Admin)
    @RequestMapping(value = "/getProblemDetailAdmin",method = {RequestMethod.GET})
    @CrossOrigin
    public String getProblemDetailAdmin(@RequestParam("token") String token,@RequestParam("id") int id)throws JsonProcessingException {
        return problemServiceObject.getProblemDetailAdmin(token,id);
    }

    //按id更新题目信息
    @RequestMapping(value = "/editProblem",method = {RequestMethod.PUT})
    @CrossOrigin
    public String editProblem(@RequestParam("token") String token,@RequestParam("title") String title,@RequestParam("des") String des,
                                @RequestParam("input") String input,@RequestParam("output") String output,
                                @RequestParam("inputExample") String inputExample,@RequestParam("outputExample") String outputExample,
                                @RequestParam("hint") String hint,@RequestParam("id") int id) throws JsonProcessingException {
        return problemServiceObject.editProblem(token,title,des,input,output,inputExample,outputExample,hint,id);
    }

    //按id上传题目测试样例
    @RequestMapping(value = "/uploadTestCaseById",method = {RequestMethod.PUT})
    @CrossOrigin
    public String uploadTestCaseById(@RequestParam("file") MultipartFile file,@RequestParam("token") String token, @RequestParam("id") int id) throws JsonProcessingException {
        return problemServiceObject.uploadTestCaseById(file,token,id);
    }


    //按id下载题目测试样例
    @RequestMapping(value = "/downloadTestCaseById",method = {RequestMethod.GET},produces="application/zip")
    @CrossOrigin
    public void uploadTestCaseById(@RequestParam("token") String token, @RequestParam("id") int id, HttpServletResponse res) throws IOException {
        problemServiceObject.downloadTestCaseById(token, id, res);
    }

    //按id查询题目是否有测试样例
    @RequestMapping(value = "/isTestCaseExistById",method = {RequestMethod.GET})
    @CrossOrigin
    public String isTestCaseExistById(@RequestParam("token") String token, @RequestParam("id") int id) throws IOException {
        return problemServiceObject.isTestCaseExistById(token,id);
    }

    //获取比赛列表(Admin)
    //id title des createTime visible authorName
    @RequestMapping(value = "/getContestListAdmin",method = {RequestMethod.GET})
    @CrossOrigin
    public String getContestListAdmin(@RequestParam("page") int page, @RequestParam("pre") int pre,@RequestParam("token") String token,@RequestParam("key") String key)throws JsonProcessingException {
        return contestServiceObject.getContestListAdmin(token,page,pre,key);
    }

    //更改比赛可见性
    @RequestMapping(value = "/changeContestVisible",method = {RequestMethod.PUT})
    @CrossOrigin
    public String changeContestVisible(@RequestParam("token") String token,@RequestParam("id") int id)throws JsonProcessingException {
        return contestServiceObject.contestVisibleChanged(token,id);
    }

    //根据id删除比赛
    @RequestMapping(value = "/deleteContestById",method = {RequestMethod.DELETE})
    @CrossOrigin
    public String deleteContestById(@RequestParam("token") String token,@RequestParam("id") int id) throws JsonProcessingException {
        return contestServiceObject.deleteContestById(token,id);
    }

    // 按id查询比赛详情(Admin) id,title,des,problemList,startTime,endTime
    @RequestMapping(value = "/getContestDetailByIdAdmin",method = {RequestMethod.GET})
    @CrossOrigin
    public String getContestDetailByIdAdmin(@RequestParam("token") String token,@RequestParam("id") int id) throws JsonProcessingException {
        return contestServiceObject.getContestDetailByIdAdmin(token, id);
    }

    //创建比赛 title des problemList startTime endTime  authorName
    @RequestMapping(value = "/createContest",method = {RequestMethod.POST})
    @CrossOrigin
    public String createContest(@RequestParam("token") String token,
                                @RequestParam("title") String title,@RequestParam("des") String des, @RequestParam("problemList") String problemList,
                                @RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime) throws JsonProcessingException {
        return contestServiceObject.createContest(token,title,des,problemList,startTime,endTime,false,userServiceObject.getUserNameByToken(token));
    }

    //根据id更新比赛信息 title title startTime endTime
    @RequestMapping(value = "/editContestById",method = {RequestMethod.PUT})
    @CrossOrigin
    public String editContestById(@RequestParam("token") String token,@RequestParam("title") String title,@RequestParam("des") String des,
                              @RequestParam("problemList") String problemList,@RequestParam("startTime") String startTime,
                                  @RequestParam("endTime") String endTime, @RequestParam("id") int id) throws JsonProcessingException {
        return contestServiceObject.editContestById(token,title,des,problemList,startTime,endTime,id);
    }

}
