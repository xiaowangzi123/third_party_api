package com.unisound.demo.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Result {
    private int duration;
    @JSONField(name = "start_time")
    private long startTime;
    @JSONField(name = "use_hot_data")
    private boolean useHotData;
    @JSONField(name = "cost_time")
    private int costTime;
    private int progress;
    @JSONField(name = "error_code")
    private int errorCode;
    private String message;
    private List<SpeechResult> results;
    private String status;

    public static Result fromJson(String jsonString) {
        return JSONObject.parseObject(jsonString, Result.class);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isUseHotData() {
        return useHotData;
    }

    public void setUseHotData(boolean useHotData) {
        this.useHotData = useHotData;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SpeechResult> getResults() {
        return results;
    }

    public void setResults(List<SpeechResult> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class SpeechResult {
        private int speaker;
        private int start;
        private int index;
        private int end;
        private String text;
        @JSONField(name = "text_length")
        private int textLength;

        public int getSpeaker() {
            return speaker;
        }

        public void setSpeaker(int speaker) {
            this.speaker = speaker;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getTextLength() {
            return textLength;
        }

        public void setTextLength(int textLength) {
            this.textLength = textLength;
        }
    }
}
