package com.example.effectsapp.convolutions;

import android.graphics.Bitmap;

import com.example.effectsapp.kernel.styles.Gauss;

public class ConvoluttionGauss extends Convolution {
    public ConvolutionGauss(Bitmap bmp, int size){
        super(bmp, new Gauss());
    }
}
