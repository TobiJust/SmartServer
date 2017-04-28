package just.de.smartserver.model;


import com.amazon.speech.speechlet.SpeechletResponse;

public class AlexaResponse {

    private SpeechletResponse response;

    public AlexaResponse(SpeechletResponse response) {
        this.response = response;
    }

    public SpeechletResponse getResponse() {
        return response;
    }

    public void setResponse(SpeechletResponse response) {
        this.response = response;
    }
}
