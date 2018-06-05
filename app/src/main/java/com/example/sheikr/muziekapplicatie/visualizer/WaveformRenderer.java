package com.example.sheikr.muziekapplicatie.visualizer;

import android.graphics.Canvas;

public interface WaveformRenderer {

    void render(Canvas canvas, byte[] waveform);
}
