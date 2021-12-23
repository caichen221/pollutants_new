package com.iscas.biz.controller.common.openauth;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.iscas.base.biz.config.openauth.ConditionalOnOpenAuth;
import com.iscas.base.biz.config.openauth.OpenAuthConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/22 13:50
 * @since jdk1.8
 */
@Controller
@RequestMapping("/openauth")
@Slf4j
@ConditionalOnOpenAuth
public class OpenAuthController {
    @Value("${security.oauth2.open.auth.resource.userInfoUri:}")
    private String userInfoUri;

    @Value("${security.oauth2.open.auth.client.revokeTokenUri:}")
    private String revokeTokenUri;

    private static final String SESSION_KEY_ACCESS_TOKEN = "MY_ACCESS_TOKEN";

    @Autowired
    private OAuth20Service oAuth20Service;

    @GetMapping("/signin")
    public void signIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("signin");
        log.info("session id:{}", request.getSession().getId());
        String authorizationUrl = oAuth20Service.getAuthorizationUrl();
        log.info("redirectURL:{}", authorizationUrl);
        response.sendRedirect(authorizationUrl);
    }

    @GetMapping("/signout")
    public String signOut(HttpServletRequest request) throws InterruptedException, ExecutionException, IOException {
        //注销access_token
        OAuth2AccessToken accessToken = (OAuth2AccessToken) request.getSession().getAttribute(SESSION_KEY_ACCESS_TOKEN);
        oAuth20Service.revokeToken(accessToken.getAccessToken());
        //清除session中保存的状态
        request.getSession().removeAttribute(SESSION_KEY_ACCESS_TOKEN);
        request.getSession().setAttribute("isAuth", false);
        return "/index";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code", required = false) String code, @RequestParam(value = "state", required = false) String state, HttpServletRequest request, Model model) throws Exception {

        log.debug("callback [code:{}],[state:{}],[sessionId:{}]", code, state, request.getSession().getId());
        if (OpenAuthConfig.STATE.equals(state)) {
            log.info("State OK!");
        } else {
            log.error("State NG!");
        }

        OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
        request.getSession().setAttribute(SESSION_KEY_ACCESS_TOKEN, accessToken);
        request.getSession().setAttribute("isAuth", true);

//        getProfile(model, accessToken);
//        getMyOrder(model);
//        getMyGoods(model);

        return "signsuccess";
    }

//    /**
//     * 应该从auth中取，但是目前auth的接口没有调整好
//     */
//    private void getProfile(Model model, OAuth2AccessToken accessToken) throws Exception {
//        OAuthRequest apiRequest = new OAuthRequest(Verb.GET, userInfoUri);
//        hiAuthApi.signRequest(accessToken, apiRequest);
//
//        Response resourceResponse = hiAuthApi.execute(apiRequest);
//
//        logger.info("code:{}", resourceResponse.getCode());
//        logger.info("message:{}", resourceResponse.getMessage());
//        logger.info("body:{}", resourceResponse.getBody());
//
//        JSONObject obj = new JSONObject(resourceResponse.getBody());
//        logger.info("json:{}", obj.toString());
//        JSONObject data = obj.getJSONObject("data");
//        Long userId = data.getLong("userId");
//        String name = data.getString("name");
//        String username = data.getString("username");
//        String tel = data.getString("tel");
//        Long lastLoginTime = data.getLong("lastLoginTime");
//
//        model.addAttribute("userId", userId);
//        model.addAttribute("name", name);
//        model.addAttribute("username", username);
//        model.addAttribute("tel", tel);
//        model.addAttribute("lastLoginTime", lastLoginTime);
//    }
//
//    /**
//     * 连接order服务获取订单信息
//     */
//    private void getMyOrder(Model model) throws Exception {
//        ApiResponse<Order> res =  orderApi.getInfo(1L);
//        Order order = res.getData();
//        model.addAttribute("orderId", order.getId());
//        model.addAttribute("orderNo", order.getNo());
//        model.addAttribute("orderTitle", order.getTitle());
//        model.addAttribute("orderCreateTime", order.getCreateTime());
//        model.addAttribute("orderTotalAmount", order.getTotalAmount());
//    }
//
//    /**
//     * 连接goods服务获取商品信息
//     */
//    private void getMyGoods(Model model) {
//        ApiResponse<PageVo<Goods>> res = goodsApi.query(1,10);
//        logger.info("res:{}", res);
//        PageVo<Goods> page = res.getData();
//        model.addAttribute("goodsList", page.getList());
//    }

}
