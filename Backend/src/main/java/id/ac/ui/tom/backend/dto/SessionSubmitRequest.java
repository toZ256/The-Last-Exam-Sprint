package id.ac.ui.tom.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body untuk {@code POST /api/sessions/submit}.
 *
 * Dikirim langsung dari LibGDX client melalui {@code Gdx.net.sendHttpRequest} saat game-over
 *
 * Contoh JSON:
 * <pre>
 * {
 *   "playerId":      1,
 *   "score":         3420,
 *   "distanceMeters": 3420.0,
 *   "durationSeconds": 87
 * }
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionSubmitRequest {

    // ID pemain
    private Long    playerId;

    // Score yang dibulatkan
    private Integer score;

    // Jarak tempuh (meter)
    private Double  distanceMeters;

    // Total waktu main (detik)
    private Integer durationSeconds;
}