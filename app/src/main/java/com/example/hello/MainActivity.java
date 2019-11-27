package com.example.hello;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.renderscript.Allocation;

import android.renderscript.ScriptC_gray;


public class MainActivity extends AppCompatActivity {

    Bitmap bit, bitprint;
    ImageView iv;
    TextView tvw, tvh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.image);
        tvw = (TextView) findViewById(R.id.width);
        tvh = (TextView) findViewById(R.id.height);

        bit = BitmapFactory.decodeResource(getResources(), R.drawable.eye);
        bitprint = bit.copy(bit.getConfig(), true);


        tvw.setText("Width: " + bit.getWidth());
        tvh.setText("Height: " + bit.getHeight());
        iv.setImageBitmap(bitprint);

        Button button_reset = (Button) findViewById(R.id.button_reset);
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitprint = bit.copy(bit.getConfig(), true);
                iv.setImageBitmap(bitprint);
            }
        });

        Button button_toEye = (Button) findViewById(R.id.button_toEye);
        button_toEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit = BitmapFactory.decodeResource(getResources(), R.drawable.eye);
                tvw.setText("Width: " + bit.getWidth());
                tvh.setText("Height: " + bit.getHeight());
                bitprint = bit.copy(bit.getConfig(), true);
                iv.setImageBitmap(bitprint);
            }
        });

        Button button_toMario = (Button) findViewById(R.id.button_toMario);
        button_toMario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
                tvw.setText("Width: " + bit.getWidth());
                tvh.setText("Height: " + bit.getHeight());
                bitprint = bit.copy(bit.getConfig(), true);
                iv.setImageBitmap(bitprint);

            }
        });

        Button button_toRedGradient = (Button) findViewById(R.id.button_toRedGradient);
        button_toRedGradient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit = BitmapFactory.decodeResource(getResources(), R.drawable.red_gradient);
                tvw.setText("Width: " + bit.getWidth());
                tvh.setText("Height: " + bit.getHeight());
                bitprint = bit.copy(bit.getConfig(), true);
                iv.setImageBitmap(bitprint);
            }
        });

        Button button_to_gray = (Button) findViewById(R.id.button_toGray);
        button_to_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrayRS(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });

        Button button_black_line = (Button) findViewById(R.id.button_blackline);
        button_black_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                black_line(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });


        Button button_to_grays = (Button) findViewById(R.id.button_to_grays);
        button_to_grays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrays(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });


        Button button_colorize = (Button) findViewById(R.id.button_colorize);
        button_colorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorize(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });


        Button button_keepColor = (Button) findViewById(R.id.button_keepColor);
        button_keepColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keepColor(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });


        Button button_extDyn = (Button) findViewById(R.id.button_extDynG);
        button_extDyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrays(bitprint);                                          //Transforms in grey in case of colored picture
                extDyn(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });


        Button button_resDyn = (Button) findViewById(R.id.button_resDynG);
        button_resDyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrays(bitprint);                                          //Transforms in grey in case of colored picture
                resDyn(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });

        Button button_extDynC = (Button) findViewById(R.id.button_extDynC);
        button_extDynC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extDynColor(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });

        Button button_equalizerg = (Button) findViewById(R.id.button_equalizerg);
        button_equalizerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrays(bitprint);                                          //Transforms in grey in case of colored picture
                equalizerg(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });

        Button button_equalizerc = (Button) findViewById(R.id.button_equalizerc);
        button_equalizerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equalizerc(bitprint);
                iv.setImageBitmap(bitprint);
            }
        });

    }

    private void black_line(Bitmap bmp ){
        double j = bmp.getHeight()*0.5;
        for(double i =0; i<bmp.getWidth(); i++){
            bmp.setPixel((int) i, (int) j, Color.BLACK);
        }
        return;
    }




    private void toGray(Bitmap bmp){
        int p, grey;
        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j< bmp.getHeight(); j++){
                p = bmp.getPixel(i,j);
                grey = (int)(Color.red(p)*0.3) + (int)(Color.green(p)*0.59)+ (int)(Color.blue(p)*0.11);
                bmp.setPixel(i, j, Color.rgb(grey, grey, grey));
            }
        }
        return;
    }

    private void toGrayRS(Bitmap bmp){

        RenderScript rs = RenderScript.create(this);

        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());


        ScriptC_gray grayScript = new ScriptC_gray(rs);


        grayScript . forEach_toGray ( input , output ) ;

        output . copyTo ( bmp ) ;

        input . destroy () ; output . destroy () ;

        grayScript . destroy () ; rs . destroy () ;

    }

    private void toGrays(Bitmap bmp){
        int p, grey;
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            p = pixels[i];
            grey = (int) (Color.red(p) * 0.3) + (int) (Color.green(p) * 0.59) + (int) (Color.blue(p) * 0.11);
            pixels[i] = Color.rgb(grey, grey, grey);

        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }

    private void colorize(Bitmap bmp){
        int color;
        double t = Math.random()*360;
        float hsvc[] = new float[3];
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            RGBtoHSV(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]), hsvc);
            hsvc[0] = (float)t;
            color = HSVtoColor(hsvc);
            pixels[i] = color;
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }




    private void RGBtoHSV(int r,int g, int b, float hsv[] ){
        double max = Math.max(b,Math.max(r,g));
        double min = Math.min(b,Math.min(r,g));
        double delta = max - min;
        if(max == min){
            hsv[0] = 0;
        }else if(max == r){
            hsv[0] = (float)(60*((g-b)/delta)+360)%360;
        }else if(max == g){
            hsv[0] = (float)(60*((b-r)/delta)+120);
        }else if(max == b){
            hsv[0] = (float)(60*((r-g)/delta)+240);
        }
        if(max ==0){
            hsv[1] = 0;
        }else{
            hsv[1] = 1-(float)(min/max);
        }
        hsv[2] = (float)max;
    }



    private int HSVtoColor(float hsv[]){

        int ti = (int)((hsv[0]/60) % 6);
        int f = (int)((hsv[0]/60) - ti);
        int l = (int)(hsv[2]*(1-hsv[1]));
        int m = (int)(hsv[2]*(1-(f*hsv[1])));
        int n = (int)(hsv[2]*(1-(1-f)*hsv[1]));
        switch (ti){
            case 0:
                return Color.rgb((int)hsv[2], n, l);
            case 1:
                return Color.rgb(m, (int)hsv[2], l);
            case 2:
                return Color.rgb(l, (int)hsv[2], n);
            case 3:
                return Color.rgb(l, m, (int)hsv[2]);
            case 4:
                return Color.rgb(n, l, (int)hsv[2]);
            case 5:
                return Color.rgb((int)hsv[2], l, m);
            default:
                return Color.rgb(255,255,255);
        }
    }


    private void keepColor(Bitmap bmp){
        int color;
        float hue;
        double t = Math.random()*360;
        float[] hsvc = new float[3];
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            RGBtoHSV(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]), hsvc);
            hue = hsvc[0];
            if(hue <= t+30 && hue >= t-30){
                color = HSVtoColor(hsvc);
                pixels[i] = color;
            }else{
                int grey = (int) (Color.red(pixels[i]) * 0.3) + (int) (Color.green(pixels[i]) * 0.59) + (int) (Color.blue(pixels[i]) * 0.11);
                pixels[i] = Color.rgb(grey, grey, grey);
            }

        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }

    private void extDyn(Bitmap bmp){
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        int min = 255;
        int max = 0;
        int grey;
        int ngrey;
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            grey = Color.red(pixels[i]);
            if(grey>max){
                max = grey;
            }
            if(grey<min){
                min = grey;
            }
        }
        int dynamic = max - min;
        if(dynamic == 255){
            return;
        }
        int[] LUT = new int[256];
        for(int ng =0; ng < 256; ng++){
            LUT[ng] = (255*(ng-min))/dynamic;
        }
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            grey = Color.red(pixels[i]);
            ngrey = LUT[grey];
            pixels[i] = Color.rgb(ngrey, ngrey, ngrey);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }



    private void resDyn(Bitmap bmp){
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        int min = 255;
        int max = 0;
        int grey;
        int ngrey;
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            grey = Color.red(pixels[i]);
            if(grey>max){
                max = grey;
            }
            if(grey<min){
                min = grey;
            }
        }
        int dynamic = max - min ;
        if(dynamic <= 30){
            return;
        }
        int[] LUT = new int[256];
        dynamic = max - min -10;                    //Modify dynamic or max/min to get a better result (replace that -10)

        for(int ng =0; ng < 256; ng++){
            LUT[ng] = (255*(ng-min))/dynamic;
        }
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            grey = Color.red(pixels[i]);
            ngrey = LUT[grey];
            pixels[i] = Color.rgb(ngrey, ngrey, ngrey);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }                               //NOT WORKING

    private void extDynColor(Bitmap bmp){
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        int[] min = new int[]{255, 255, 255};
        int[] max = new int[3];
        int[] rgb = new int[3];
        int[] nrgb = new int[3];

        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++) {
            rgb[0] = Color.red(pixels[i]);
            rgb[1] = Color.green(pixels[i]);
            rgb[2] = Color.blue(pixels[i]);
            for (int j = 0; j < 3; j++) {
                if (rgb[j] > max[j]) {
                    max[j] = rgb[j];
                }
                if (rgb[j] < min[j]) {
                    min[j] = rgb[j];
                }
            }
        }
        int[] dynamic = new int[]{max[0] - min[0], max[1] - min[1], max[2] - min[2]};
        if(dynamic[0] == 255 && dynamic[1] == 255 && dynamic[2] == 255){
            return;
        }
        int[][] LUT = new int[256][3];
        for(int j=0; j<3; j++) {
            for (int ng = 0; ng < 256; ng++) {
                LUT[ng][j] = (255 * (ng - min[j]))/dynamic[j];
            }
        }
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            rgb[0] = Color.red(pixels[i]);
            rgb[1] = Color.green(pixels[i]);
            rgb[2] = Color.blue(pixels[i]);
            for(int j=0; j<3; j++) {
                nrgb[j] = LUT[rgb[j]][j];
            }
            pixels[i] = Color.rgb(nrgb[0], nrgb[1], nrgb[2]);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }

    private void equalizerg(Bitmap bmp){
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        int grey;
        long ngrey;
        int[] hist = new int[256];
        long[] histc = new long[256];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            grey = Color.red(pixels[i]);
            hist[grey]++;
        }
        long sum = 0;
        for (int i=0; i<256; i++) {
            sum += hist[i];
            histc[i] = sum;
        }
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            grey = Color.red(pixels[i]);
            ngrey = (histc[grey]*255)/(long)(bmp.getWidth()*bmp.getHeight());
            pixels[i] = Color.rgb((int)ngrey, (int)ngrey, (int)ngrey);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }

    private void equalizerc(Bitmap bmp){
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        int[] rgb = new int[3];
        long[] nrgb = new long[3];
        int[][] hist = new int[256][3];
        long[][] histc = new long[256][3];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            rgb[0] = Color.red(pixels[i]);
            rgb[1] = Color.green(pixels[i]);
            rgb[2] = Color.blue(pixels[i]);
            for(int j=0; j<3; j++){
                hist[rgb[j]][j]++;
            }
        }

        long[] sum = new long[3];
        for (int i=0; i<256; i++) {
            for(int j=0; j<3; j++){
                sum[j] += hist[i][j];
                histc[i][j] = sum[j];
            }
        }

        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            rgb[0] = Color.red(pixels[i]);
            rgb[1] = Color.green(pixels[i]);
            rgb[2] = Color.blue(pixels[i]);
            for(int j=0; j<3; j++){
                nrgb[j] = (histc[rgb[j]][j]*255)/(long)(bmp.getWidth()*bmp.getHeight());
            }
            pixels[i] = Color.rgb((int)nrgb[0], (int)nrgb[1], (int)nrgb[2]);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("Deb","Start(Hello world!)");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("Deb","Resume(Here we go again)");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("Deb","Pause(Hold on a sec)");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("Deb","Stop");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("Deb","Restart(It's me, Mario!)");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("Deb","Destroy(Byebye!)");
    }

}
