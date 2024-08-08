<<<<<<< HEAD:src/main/java/org/g9project4/global/Utils.java
package org.g9project4.global;
=======
package org.g9project4.admin.global;
>>>>>>> 0694ea017b72880143ea0b0b2a75758e79b2496d:src/main/java/org/g9project4/admin/global/Utils.java

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
<<<<<<< HEAD:src/main/java/org/g9project4/global/Utils.java
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("utils")
@RequiredArgsConstructor
public class Utils { // 빈의 이름 - utils

    private final MessageSource messageSource;
    private final HttpServletRequest request;
    private final DiscoveryClient discoveryClient;

    public String url(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("admin-service");

        try {
            return String.format("%s%s", instances.get(0).getUri().toString(), url);
        } catch (Exception e) {
            return String.format("%s://%s:%d%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath(), url);
        }
    }

    public String frontUrl(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("front-service");

        try {
            return String.format("%s%s", instances.get(0).getUri().toString(), url);
        } catch (Exception e) {
            return String.format("%s://%s:%d%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath(), url);
        }
    }

    public Map<String, List<String>> getErrorMessages(Errors errors) {//JSON 받을 때는 에러를 직접 가공
        // FieldErrors


        Map<String, List<String>> messages = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, e -> getCodeMessages(e.getCodes())));

        // GlobalErrors
        List<String> gMessages = errors.getGlobalErrors()
                .stream()
                .flatMap(e -> getCodeMessages(e.getCodes()).stream()).toList();

        if (!gMessages.isEmpty()) {
            messages.put("global", gMessages);
        }
        return messages;
    }


    public List<String> getCodeMessages(String[] codes) {
        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false);

        List<String> messages = Arrays.stream(codes)
                .map(c -> {
                    try {
                        return ms.getMessage(c, null, request.getLocale());
                    } catch (Exception e) {
                        return "";
                    }
                })
                .filter(s -> !s.isBlank())
                .toList();

        ms.setUseCodeAsDefaultMessage(true);
        return messages;
    }
    public String getMessage(String code){
        List<String> messages = getCodeMessages(new String[]{code});

        return messages.isEmpty() ? code : messages.get(0);
    }
=======
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Utils {

    private final DiscoveryClient discoveryClient;

    private final HttpServletRequest request;

    public String url(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("admin-service");

        return String.format("%s%s", instances.get(0).getUri().toString(), url);
    }
    /*
    public String redirectUrl(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("admin-service");
        String fromGateway = request.getHeader("from-gateway");
        if (StringUtils.hasText("fromGateway") && fromGateway.equals("true")) {
            String host = request.getHeader("gateway-port");
            String protocol = request.isSecure() ? "https://" : "http://";
             url = protocol + host + "/admin" + url;
        }

        return "redirect:" + url;

    }

     */

>>>>>>> 0694ea017b72880143ea0b0b2a75758e79b2496d:src/main/java/org/g9project4/admin/global/Utils.java
}