package com.potato.config;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@Slf4j
public class SmsConfig {

  @Value("${sms.secretId}")
  private String secretId;

  @Value("${sms.secretKey}")
  private String secretKey;

  @Value("${sms.sdkAppId}")
  private String sdkAppId;

  @Value("${sms.signName}")
  private String signName;

  @Value("${sms.templateId}")
  private String templateId;

  public void sendMessage(String phone, String code) throws Exception {

      Credential cred = new Credential(secretId,secretKey);

      HttpProfile httpProfile = new HttpProfile();
      httpProfile.setEndpoint("sms.tencentcloudapi.com");

      ClientProfile clientProfile = new ClientProfile();
      clientProfile.setHttpProfile(httpProfile);

      SmsClient client = new SmsClient(cred,"ap-guangzhou",clientProfile);

      SendSmsRequest request = new SendSmsRequest();
      request.setSmsSdkAppId(sdkAppId);
      request.setSignName(signName);
      request.setTemplateId(templateId);

      String[] templateParamSet = {"5",code};
      request.setTemplateParamSet(templateParamSet);

      String[] phoneNumSet = {phone};
      request.setPhoneNumberSet(phoneNumSet);

      client.SendSms(request);

  }
}
