package co.edu.cicloDeVidaTiempo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

import co.edu.cronometro.R;

public class MainActivity extends AppCompatActivity {
    private boolean ejecución;
    private int segundos = 0;
    private int ultimoTiempoGuardado = 0;
    private String[] guardados = new String[5];
    private int guardarNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runTimer();
        for (int i = 0; i < 5; i++) {
            guardados[i] = "";
        }
    }

    public void iniciar(View view) {
        ejecución = true;
    }

    public void pausar(View view) {
        ejecución = false;
    }

    public void reinicio(View view) {
        ejecución = false;
        segundos = 0;
        ultimoTiempoGuardado = 0;
        guardarNo = 0;
        for (int i = 0; i < 5; i++) {
            guardados[i] = "";
        }
        TextView vistaTiempo2 = (TextView) findViewById(R.id.time_guardados);
        vistaTiempo2.setText("");
    }

    public void guardar(View view) {
        TextView vistaTiempo2 = (TextView) findViewById(R.id.time_guardados);

        int tiempoVuelta = segundos - ultimoTiempoGuardado;
        ultimoTiempoGuardado = segundos;

        int hora = tiempoVuelta / 3600;
        int minutes = (tiempoVuelta % 3600) / 60;
        int secs = tiempoVuelta % 60;

        String tiempoVueltaString = String.format(Locale.getDefault(), "%d:%02d:%02d", hora, minutes, secs);
        guardados[guardarNo] = tiempoVueltaString;

        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            resultado.append(guardados[i]).append("\n");
        }

        guardarNo++;
        if (guardarNo == 5)
            guardarNo = 0;

        vistaTiempo2.setText(resultado.toString());
    }

    private void runTimer() {
        TextView vistaTiempo = (TextView) findViewById(R.id.time_text);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hora = segundos / 3600;
                int minutes = (segundos % 3600) / 60;
                int secs = segundos % 60;
                String tiempo = String.format(Locale.getDefault(), "%d:%02d:%02d", hora, minutes, secs);
                vistaTiempo.setText(tiempo);
                if (ejecución) {
                    segundos++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}