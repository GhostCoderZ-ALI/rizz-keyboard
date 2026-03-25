package com.example.rizzkeyboard;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;

public class AiResponseGenerator {

    private static final String AI_API_URL = "https://api.example.com/generate-response"; // Replace with actual API URL
    private static final String[] FALLBACK_TEMPLATES = {
        "Are you a magician? Because whenever I look at you, everyone else disappears.",
        "Do you believe in love at first sight, or should I walk by again?",
        "If you were a vegetable, you'd be a cute-cumber!",
        "Is your name Google? Because you have everything I’ve been searching for."
    };

    private OkHttpClient client;

    public AiResponseGenerator() {
        client = new OkHttpClient();
    }

    public String getAiResponse(String input) {
        try {
            // Prepare the request
            RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), "{\"prompt\":\"" + input + "\"}");
            Request request = new Request.Builder()
                .url(AI_API_URL)
                .post(body)
                .build();

            // Execute the request
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return getFallbackTemplate();
            }
        } catch (Exception e) {
            return getFallbackTemplate();
        }
    }

    private String getFallbackTemplate() {
        int randomIndex = (int) (Math.random() * FALLBACK_TEMPLATES.length);
        return FALLBACK_TEMPLATES[randomIndex];
    }
}