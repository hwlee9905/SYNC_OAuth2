package sync.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import sync.oauth2.dto.OAuth2UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final OAuth2UserDto oAuth2UserDto;

    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return oAuth2UserDto.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {

        return oAuth2UserDto.getName();
    }

    public String getUsername() {

        return oAuth2UserDto.getUsername();
    }
    public String getRole() {

        return oAuth2UserDto.getRole();
    }
    public String getInfoSet(){
        return oAuth2UserDto.getInfoSet();
    }
}