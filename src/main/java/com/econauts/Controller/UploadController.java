package com.econauts.Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private Cloudinary cloudinary;

    @SuppressWarnings("unchecked")
	@GetMapping("/signature")
    public Map<String, Object> generateSignature() {

        long timestamp = System.currentTimeMillis() / 1000;

        // params for signing (NOT upload)
        Map<String, Object> params = ObjectUtils.asMap(
                "timestamp", timestamp,
                "folder", "products"
        );

        // ✅ generate signature
        String signature = cloudinary.apiSignRequest(params, cloudinary.config.apiSecret);

        // ✅ response
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", timestamp);
        response.put("signature", signature);
        response.put("apiKey", cloudinary.config.apiKey);
        response.put("cloudName", cloudinary.config.cloudName);
        response.put("folder", "products");

        return response;
    }
}