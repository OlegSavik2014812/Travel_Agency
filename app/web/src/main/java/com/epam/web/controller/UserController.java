package com.epam.web.controller;

import com.epam.core.entity.User;
import com.epam.core.entity.UserRole;
import com.epam.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type User controller.
 */
@SessionAttributes("user")
@Controller
public class UserController {
    private static final String SIGN_UP_PAGE = "auth/sign_up";
    private static final String SIGN_IN_PAGE = "auth/sign_in";
    private static final String USER_LIST_PAGE = "user_list";
    private static final String HOME_PAGE = "redirect:/";
    private static final String USER_LOGIN_ATTRIBUTE = "user";
    private static final String USER_LIST_ATTRIBUTE = "userList";
    private final UserService service;

    /**
     * Instantiates a new User controller.
     *
     * @param service the service
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Show sign up page string.
     *
     * @return the string
     */
    @GetMapping(value = "/go_to_sign_up")
    public String showSignUpPage() {
        return SIGN_UP_PAGE;
    }

    /**
     * Sign up string.
     *
     * @param login           the login
     * @param password        the password
     * @param confirmPassword the confirm password
     * @param request         the request
     * @return the string
     */
    @PostMapping(value = "/sign_up")
    public String signUp(@RequestParam("login") String login,
                         @RequestParam("password") String password,
                         @RequestParam("confirmPassword") String confirmPassword,
                         HttpServletRequest request) {
        if (!checkUserInputData(login, password, confirmPassword) || service.getUserByLogin(login) != null) {
            return SIGN_UP_PAGE;
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(UserRole.MEMBER);

        if (!service.add(user)) {
            return SIGN_UP_PAGE;
        }
        HttpSession session = request.getSession();
        session.setAttribute(USER_LOGIN_ATTRIBUTE, user);
        return HOME_PAGE;
    }

    /**
     * Show sign in string.
     *
     * @return the string
     */
    @GetMapping(value = "/go_to_sign_in")
    public String showSignIn() {
        return SIGN_IN_PAGE;
    }

    /**
     * Sign in string.
     *
     * @param login    the login
     * @param password the password
     * @param request  the request
     * @return the string
     */
    @PostMapping(value = "/sign_in")
    public String signIn(@RequestParam("login") String login, @RequestParam("password") String password, HttpServletRequest request) {
        User user = service.getUserByLoginAndPassword(login, password);
        request.getSession().setAttribute(USER_LOGIN_ATTRIBUTE, user);
        return HOME_PAGE;
    }

    /**
     * Logout page string.
     *
     * @param request  the request
     * @param response the response
     * @return the string
     */
    @GetMapping(value = "/sign_out")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    /**
     * Gets user list.
     *
     * @param model the model
     * @return the user list
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/get_users")
    public String getUserList(Model model) {
        List<User> list = service.getList();
        model.addAttribute(USER_LIST_ATTRIBUTE, list);
        return USER_LIST_PAGE;
    }

    /**
     * Remove user string.
     *
     * @param id the id
     * @return the string
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/remove_user{id}")
    public String removeUser(@PathVariable("id") String id) {
        service.delete(Long.parseLong(id));
        return "redirect:/get_users";
    }

    /**
     * Forward string.
     *
     * @return the string
     */
    @PostMapping(value = "/forward")
    public String forward() {
        return "tour_list";
    }


    private boolean checkUserInputData(String login, String password, String confirmPassword) {
        if (login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false;
        }
        return password.equals(confirmPassword);
    }

}
