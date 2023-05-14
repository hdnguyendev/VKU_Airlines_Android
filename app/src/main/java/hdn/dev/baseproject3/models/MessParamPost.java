package hdn.dev.baseproject3.models;

public class MessParamPost {
    String model;
    String prompt;
    int max_tokens;
    int temperature;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
