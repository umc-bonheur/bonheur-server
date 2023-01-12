package com.bonheur.config.provider;

import com.bonheur.config.client.kakao.KakaoAuthApiClient;
import com.bonheur.config.client.kakao.dto.KakaoProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KakaoAuthProvider implements AuthProvider {
    private final KakaoAuthApiClient kakaoAuthApiClient;
    @Override
    public String getSocialId(String token) {
        KakaoProfileResponse response = kakaoAuthApiClient.getProfileInfo(token);
        return response.getId();
    }
}
