package com.it.rword.controller;

import com.it.rword.pojo.User;
import com.it.rword.service.UserService;
import com.it.rword.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final int OK = 200;
    @Resource
    private UserService userService;

    @RequestMapping("/register")
    public JsonResult<Void> register(User user) {
        userService.register(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User result = userService.login(username, password);
        if (result == null) {
            return new JsonResult<>(2);
        }

        session.setAttribute("uid", result.getUid());
        session.setAttribute("username", result.getUsername());

        return new JsonResult<>(OK, result);
    }

    @RequestMapping("/changePassword")
    public JsonResult<User> changePassword(String oldPassword, String newPassword, HttpSession session){
        Integer uid = (Integer)session.getAttribute("uid");
        String username = (String)session.getAttribute("username");
        Integer result = userService.changePassword(oldPassword, newPassword, username, uid);
        if(result == 0) {
            System.out.println("该用户不存在");
            return new JsonResult<>(0);
        }
        if(result == -1){
            System.out.println("用户名和用户id不匹配");
            return new JsonResult<>(-1);
        }
        if(result == -2){
            System.out.println("用户输入的原密码错误");
            return new JsonResult<>(-2);
        }
        if(result == -3){
            System.out.println("存入新密码发生错误！");
            return new JsonResult<>(-3);
        }
        return new JsonResult<>(OK);
    }

    /**
     * 设置上传文件的最大值
     */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * 限制上传文件的类型
     */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile接口是SpringMVC提供的一个接口，这个接口为我们包装了获取文件类型的数据（任何类型的file都可以接收）
     * SpringBoot整合了SpringMVC，只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，SpringBoot会自动将文件中的数据赋值给这个参数
     * @param session 当前会话
     * @param file 上传的文件
     * @return 返回值为void
     */
    @RequestMapping("/change_avatar")
    public JsonResult<String> changeHeadPhoto(HttpSession session, @RequestParam("headFile") MultipartFile file) {
        // 判断文件是否为null
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            System.out.println("文件超出限制");
        }
        // 判断文件的类型是否是规定的前缀和后缀`
        String contentType = file.getContentType();
        // 如果集合包含某个元素则返回值为true
        if (!AVATAR_TYPE.contains(contentType)) {
            System.out.println("文件类型不支持");
        }

        // 上传的文件.../upload/文件.png
        String parent = "G:/JavaTemp/rwordFile";
        System.out.println(parent);

        // File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if (!dir.exists()) {     // 检测目录是否存在
            dir.mkdir();         // 创建当前的目录
        }

        // 获取到这个文件名称，UUID工具来生成一个新的字符串作为文件名
        // 例如：avatar01.png
        String originalFilename = file.getOriginalFilename();
        System.out.println("OriginalFilename:=====" + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;

        File dest = new File(dir, filename);    // 是一个空文件
        // 参数file中数据写入到这个空文件中
        try {
            file.transferTo(dest);    // 将file文件中的数据写入到dest文件中
        } catch (Exception e) {
            System.out.println("头像上传失败");
        }

        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username = session.getAttribute("username").toString();

        // 返回头像的路径/rwordFile/test.png
        String headPhoto = "/rwordFile/" + filename;
        userService.changeHeadPhoto(uid, headPhoto, username);

        return new JsonResult<>(OK, headPhoto);
    }
}
