package com.houseant.backend.controller;

import com.houseant.backend.annotations.NoLogin;
import com.houseant.backend.entity.House;
import com.houseant.backend.entity.Reservation;
import com.houseant.backend.entity.User;
import com.houseant.backend.service.HouseService;
import com.houseant.backend.service.ReservationService;
import com.houseant.backend.service.TokenService;
import com.houseant.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理客户端发来的有关用户信息的请求
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;
    private final TokenService tokenService;
    private final HouseService houseService;
    private final ReservationService reservationService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService, HouseService houseService,ReservationService reservationService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.houseService = houseService;
        this.reservationService=reservationService;
    }

    /**
     * 响应登录请求的函数
     *
     * @param params   登录请求请求体，内含登录账号与密码。
     * @param response 登录响应
     * @param request  登录请求
     * @return 返回登录结果的Map，自动转换为JSON格式的字符串
     */
    @ResponseBody
    @NoLogin
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @NonNull @RequestBody Map<String, Object> params,
            @NonNull HttpServletResponse response,
            @NonNull HttpServletRequest request) {
        logger.info("Login from " + request.getRemoteAddr());
        boolean login = false;
        String msg;
        Map<String, Object> userData = new HashMap<>();

        String accountGet = (String) params.get("account");
        String passwordGet = (String) params.get("password");

        User user = userService.findByAccount(accountGet);
        if (user == null) {
            msg = "User not found";
            logger.warn(msg);

        } else if (user.getPassword().equals(passwordGet)) {
            msg = "Login succeed";
            logger.info(msg);

            login = true;
            userData = user.getUserMsgExceptPasswd();

            // Add a cookie
            response.addCookie(tokenService.createTokenCookie(user));

            // Create a session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

        } else {
            msg = "Wrong password";
            logger.warn(msg + ": " + user.getAccount());
        }

        return ResponseEntity.ok().body(Map.of(
                "login", login,
                "message", msg,
                "userMsg", userData
        ));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            @NonNull HttpServletResponse response,
            @NonNull HttpServletRequest request) {
        logger.info("Logout from " + request.getRemoteAddr());

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Add a cookie
        response.addCookie(tokenService.createInvalidCookie());

        logger.info("Logout succeed");
        return ResponseEntity.ok().body(Map.of(
                "logout", true,
                "message", "Logout succeed"
        ));
    }

    @NoLogin
    @PostMapping("/register")
    public ResponseEntity<?> register(@NonNull @RequestBody User user) {
        logger.info("Registering for " + user.getAccount());
        boolean success;
        String msg;

        if (userService.isAccountAvailable(user.getAccount())) {
            // Default settings
            user.setUsername(user.getAccount());
            user.setTel("");
            user.setAvatar("");
            userService.create(user);

            msg = "User registered successfully";
            success = true;
        } else {
            msg = "Account already exists";
            success = false;
        }

        return ResponseEntity.ok().body(Map.of(
                "success", success,
                "message", msg
        ));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@NonNull @RequestBody User user, @NonNull HttpServletRequest request) {

        String newTel = user.getTel();
        String newName = user.getUsername();
        String newPassword = user.getPassword();
        User newUser = (User) request.getSession().getAttribute("user");
        if (user.getTel() == null) {
            newTel = newUser.getTel();
        }
        if (user.getUsername() == null) {
            newName = newUser.getUsername();
        }
        if (user.getPassword() == null) {
            newPassword = newUser.getPassword();
        }
        newUser.setTel(newTel);
        newUser.setPassword(newPassword);
        newUser.setUsername(newName);
        userService.update(newUser);
        if(user.getPassword() != null){
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }

        if (user.getPassword() == null) {
            return ResponseEntity.ok().body(Map.of("keep", true, "message", "Successfully update userIfo"));
        } else {
            return ResponseEntity.ok().body(Map.of("keep", false, "message", "Successfully update userIfo"));
        }
    }

    @PostMapping("/userInfo")
    public ResponseEntity<?> userInfo(@NonNull HttpServletRequest request) {
        String msg;
        User res = userService.findByAccount(((User) (request.getSession().getAttribute("user"))).getAccount());
        msg = "searchUser successfully";
        return ResponseEntity.ok().body(Map.of(
                "result", res,
                "message", msg));
    }

    @NoLogin
    @GetMapping("/findAllHouseInfo")
    public ResponseEntity<?> findAllHouseInfo() {
        List<House> houseInfos = houseService.findAll();
        return ResponseEntity.ok().body(Map.of(
                "result", houseInfos));
    }

    @GetMapping("/myHouseInfo")
    public ResponseEntity<?> myHouseInfo(@NonNull HttpServletRequest request) {
        var param = new HashMap<String, Object>();
        User currentUser =(User) request.getSession().getAttribute("user");
        param.put("account", currentUser.getAccount());
        List<House> houseInfos = houseService.findByKey(param);
        return ResponseEntity.ok().body(Map.of(
                "result", houseInfos));
    }


    @GetMapping("/myReservationInfo")
    public ResponseEntity<?> myReservationInfo(@NonNull HttpServletRequest request) {
        User currentUser =(User) request.getSession().getAttribute("user");
        List<Reservation> reservationInfos = reservationService.findByAccount(currentUser.getAccount());
        return ResponseEntity.ok().body(Map.of(
                "result", reservationInfos));
    }
}
