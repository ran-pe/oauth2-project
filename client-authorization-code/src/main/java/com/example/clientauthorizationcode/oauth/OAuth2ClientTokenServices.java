package com.example.clientauthorizationcode.oauth;

import com.example.clientauthorizationcode.security.ClientUserDetails;
import com.example.clientauthorizationcode.user.ClientUser;
import com.example.clientauthorizationcode.user.UserRepository;
import com.mysql.cj.xdevapi.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Calendar;

public class OAuth2ClientTokenServices implements ClientTokenServices {

    @Autowired
    private UserRepository users;

    private ClientUser getClientUser(Authentication authentication) {
        ClientUserDetails loggedUser = (ClientUserDetails) authentication.getPrincipal();
        Long userId = loggedUser.getClientUser().getId();
        return users.findOne(userId);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        ClientUser clientUser = getClientUser(authentication);
        String accessToken = clientUser.getAccessToken();
        Calendar expirationDate = clientUser.getAccessTokenValidity();
        if(accessToken == null) return null;
        DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        oAuth2AccessToken.setExpiration(expirationDate.getTime());

        return oAuth2AccessToken;
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {

        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime(accessToken.getExpiration());
        ClientUser clientUser = getClientUser(authentication);
        clientUser.setAccessToken(accessToken.getValue());
        clientUser.setAccessTokenValidity(expirationDate);
        users.save(clientUser);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        ClientUser clientUser = getClientUser(authentication);
        clientUser.setAccessToken(null);
        clientUser.setRefreshToken(null);
        clientUser.setAccessTokenValidity(null);
        users.save(clientUser);
    }
}
