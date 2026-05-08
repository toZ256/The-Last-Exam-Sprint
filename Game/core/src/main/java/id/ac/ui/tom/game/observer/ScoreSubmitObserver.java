package id.ac.ui.tom.game.observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

public class ScoreSubmitObserver implements GameEventListener {

    private static final String SUBMIT_URL = "http://localhost:8080/api/sessions/submit";
    private static final long   PLAYER_ID  = 1L;

    @Override
    public void onGameOver(int score, float distanceMeters, int durationSeconds) {
        String jsonPayload = String.format(
            "{\"playerId\": %d, \"score\": %d, \"distanceMeters\": %.2f, \"durationSeconds\": %d}",
            PLAYER_ID, score, distanceMeters, durationSeconds);

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl(SUBMIT_URL);
        request.setHeader("Content-Type", "application/json");
        request.setContent(jsonPayload);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode == 200 || statusCode == 201) {
                    Gdx.app.log("ScoreSubmitObserver", "Skor berhasil disimpan. Status: " + statusCode);
                } else {
                    Gdx.app.error("ScoreSubmitObserver", "Server menolak. Status: " + statusCode);
                }
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("ScoreSubmitObserver", "Gagal kirim skor: " + t.getMessage());
            }

            @Override
            public void cancelled() { }
        });
    }

    @Override
    public void onPlayerSlip(float slipDuration) {
    }
}
