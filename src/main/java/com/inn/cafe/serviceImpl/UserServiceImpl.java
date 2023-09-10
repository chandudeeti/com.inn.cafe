package com.inn.cafe.serviceImpl;

import com.google.common.base.Strings;
import com.inn.cafe.JWT.CustomerUsersDetailsService;
import com.inn.cafe.JWT.JWTUtil;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.User;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.utils.EmailUtils;
import com.inn.cafe.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFormMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Email Already Exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login {}");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            User user = customerUsersDetailsService.getUserDetails(); // assuming that getUserDetails() returns a User object
            if (auth.isAuthenticated()) {
                if (user.getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(user.getEmail(),
                                    user.getRole()) + "\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval." + "\"}", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (BadCredentialsException ex) {
            log.error("Bad credentials", ex);
            return new ResponseEntity<String>("{\"message\":\"" + "Bad credentials." + "\"}", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("Unexpected error during login", ex);
            return new ResponseEntity<String>("{\"message\":\"" + "Unexpected error." + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad credentials." + "\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUsers(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*@Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
             Optional<User> optionalUser = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (optionalUser.isPresent()) {
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CafeUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("User Id Doesn't exist", HttpStatus.OK);
                }
            }else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
       return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }*/

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> user = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!user.isEmpty()) {
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"), user.get().getEmail(), userDao.getAllAdmin());
                    log.info("user updated successfully");
                    return CafeUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
                } else {
                    log.info("user id not found");
                    return CafeUtils.getResponseEntity("User Id doesn't exists", HttpStatus.OK);
                }

            } else {
                CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //sending mail to all admins
    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "User:- " + user + "\n is approved by \nADMIN :-" + jwtFilter.getCurrentUser(), allAdmin);
        } else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Rejected", "User:- " + user + "\n is rejected by \nADMIN :-" + jwtFilter.getCurrentUser(), allAdmin);
        }
    }


    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        } else {
            return false;
        }
    }

    private User getUserFormMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (!userObj.equals(null)) {
                if (userObj.getPassword().equals(requestMap.get("oldPassword"))) {
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return CafeUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                }
                System.out.println("Incorrect password");
                return CafeUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(requestMap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
                emailUtils.forgotMail(user.getEmail(), "credentials by chandu", user.getPassword());
            return CafeUtils.getResponseEntity("Check your mail for credentials", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
