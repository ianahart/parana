package com.hart.backend.parana.story.request;

public class SendStoryRequest {

    private Long userId;

    private String text;

    private String fontSize;

    private String duration;

    private String color;

    private String background;

    private String alignment;

    private String type;

    public SendStoryRequest() {

    }

    public SendStoryRequest(
            Long userId,
            String text,
            String fontSize,
            String duration,
            String color,
            String background,
            String alignment,
            String type) {

        this.userId = userId;
        this.text = text;
        this.fontSize = fontSize;
        this.duration = duration;
        this.color = color;
        this.background = background;
        this.alignment = alignment;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public Long getUserId() {
        return userId;
    }

    public String getDuration() {
        return duration;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getAlignment() {
        return alignment;
    }

    public String getBackground() {
        return background;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setType(String type) {
        this.type = type;
    }
}
