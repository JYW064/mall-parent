package com.jyw.portal.controller;


import com.jyw.common.annotation.Token;
import com.jyw.common.util.RSAUtil;
import com.jyw.common.util.ResultUtil;
import com.jyw.common.util.TokenUtil;
import com.jyw.common.vo.ResultVO;
import com.jyw.portal.domain.User;
import com.jyw.portal.service.RegisterService;
import com.jyw.portal.service.UserService;
import com.jyw.portal.service.impl.LoginServiceImpl;
import com.jyw.portal.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    LoginServiceImpl login;

    @Autowired
    RegisterService registerService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResultVO login(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        User loginUser = login.getUserByUsername(user.getUsername());
        ResultVO resultVO;
        if (loginUser == null) {
            resultVO = new ResultUtil<>().setErrorMsg("002","用户名不存在，可以注册");
        } else {
            if (RSAUtil.privateDecrypt(loginUser.getPassword(), RSAUtil.getPrivateKey(loginUser.getPrivateKey().trim())).equals(user.getPassword())) {
                String token = TokenUtil.token(loginUser.getUserId());
                loginUser.setPassword(null);
                loginUser.setPrivateKey(null);
                resultVO = new ResultUtil<>().setData(DTOUtil.userToLoginDTO(loginUser,token));
            } else {
                resultVO = new ResultUtil<>().setErrorMsg("004","用户名或密码错误");
            }
        }
        return resultVO;
    }

    @PostMapping("/register")
    public ResultVO register(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        User registerUser = registerService.getUserByName(user.getUsername());
        ResultVO resultVO;
        if (registerUser != null) {
            resultVO = new ResultUtil<>().setErrorMsg("002","用户名已存在！");
        }else{
            registerUser = user;
            Map<String, String> keyMap = RSAUtil.createKeys(512);
            String publicKey = keyMap.get("publicKey");
            String privateKey = keyMap.get("privateKey");
            Date date = new Date();
            //公钥加密
            String encodedData = RSAUtil.publicEncrypt(user.getPassword(), RSAUtil.getPublicKey(publicKey));

            registerUser.setPassword(encodedData);
            registerUser.setGmtCreate(date);
            registerUser.setProfilePicture("http://cdn.will-store.xyz/profile1.jpg");
            registerUser.setStatus(1);
            registerUser.setPrivateKey(privateKey);
            registerService.insertUser(registerUser);
            resultVO = new ResultUtil<>().setData(null);
        }
        return resultVO;
    }

    @Token
    @PostMapping("userInfo")
    public ResultVO getUserInfo(HttpServletRequest request){
        long userId = (long) request.getAttribute("userId");
        return new ResultUtil<User>().setData(userService.getUserInfo(userId));
    }

    @Token
    @PostMapping("updateUser")
    public ResultVO updateUSer(@RequestBody User user,
                               HttpServletRequest request){
        long userId = (long) request.getAttribute("userId");
        user.setUserId(userId);
        userService.updateUser(user);
        return new ResultUtil<>().setData(null);
    }
}
