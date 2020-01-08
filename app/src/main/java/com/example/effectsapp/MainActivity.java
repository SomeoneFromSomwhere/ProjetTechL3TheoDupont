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

import static android.graphics.Color.HSVToColor;


public class MainActivity extends AppCompatActivity {

    Bitmap bit, bit_copy;
    ImageView iv;
    TextView width_tv, height_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.image);
        width_tv = (TextView) findViewById(R.id.width);
        height_tv = (TextView) findViewById(R.id.height);

        bit = BitmapFactory.decodeResource(getResources(), R.drawable.img_cours);
        bit_copy = bit.copy(bit.getConfig(), true);


        width_tv.setText("Width: " + bit.getWidth());
        height_tv.setText("Height: " + bit.getHeight());
        iv.setImageBitmap(bit_copy);

        Button button_reset = (Button) findViewById(R.id.button_reset);
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit_copy = bit.copy(bit.getConfig(), true);
                iv.setImageBitmap(bit_copy);
            }
        });

        Button button_toEye = (Button) findViewById(R.id.button_toEye);
        button_toEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit = BitmapFactory.decodeResource(getResources(), R.drawable.eye);
                width_tv.setText("Width: " + bit.getWidth());
                height_tv.setText("Height: " + bit.getHeight());
                bit_copy = bit.copy(bit.getConfig(), true);
                iv.setImageBitmap(bit_copy);
            }
        });

        Button button_toMario = (Button) findViewById(R.id.button_toMario);
        button_toMario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
                width_tv.setText("Width: " + bit.getWidth());
                height_tv.setText("Height: " + bit.getHeight());
                bit_copy = bit.copy(bit.getConfig(), true);
                iv.setImageBitmap(bit_copy);

            }
        });

        Button button_toRedGradient = (Button) findViewById(R.id.button_toRedGradient);
        button_toRedGradient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit = BitmapFactory.decodeResource(getResources(), R.drawable.red_gradient);
                width_tv.setText("Width: " + bit.getWidth());
                height_tv.setText("Height: " + bit.getHeight());
                bit_copy = bit.copy(bit.getConfig(), true);
                iv.setImageBitmap(bit_copy);
            }
        });

        Button button_to_gray = (Button) findViewById(R.id.button_toGray);
        button_to_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrayRS(bit_copy);
                iv.setImageBitmap(bit_copy);
            }
        });

        Button button_test = (Button) findViewById(R.id.button_test);
        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tests(bit_copy);
                iv.setImageBitmap(bit_copy);
            }
        });


        Button button_to_grays = (Button) findViewById(R.id.button_to_grays);
        button_to_grays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrays(bit_copy);
                iv.setImageBitmap(bit_copy);
            }
        });


        Button button_colorize = (Button) findViewById(R.id.button_colorize);
        button_colorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorize(bit_copy);
                iv.setImageBitmap(bit_copy);
            }
        });


        Button button_keepColor = (Button) findViewById(R.id.button_keepColor);
        button_keepColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keepColorRandom(bit_copy, 30);
                iv.setImageBitmap(bit_copy);
            }
        });


        Button button_extDyn = (Button) findViewById(R.id.button_extDynG);
        button_extDyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrays(bit_copy);                                          //Transforms in grey in case of colored picture
                extendDyn(bit_copy);
                iv.setImageBitmap(bit_copy);
            }
        });


        Button button_resDyn = (Button) findViewById(R.id.button_resDynG);
        button_resDyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrays(bit_copy);                                          //Transforms in grey in case of colored picture
                contractDyn(bit_copy);
                iv.setImageBitmap(bit_copy);
            }
        });

        Button button_extDynC = (Button) findViewById(R.id.button_extDynC);
        button_extDynC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extendDynColor(bit_copy);
                iv.setImageBitmap(bit_copy);
            }
        });

        Button button_equalizerg = (Button) findViewById(R.id.button_equalizerg);
        button_equalizerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrays(bit_copy);                                          //Transforms in grey in case of colored picture
                equalizerGray(bit_copy);
                iv.setImageBitmap(bit_copy);
            }
        });

        Button button_equalizerc = (Button) findViewById(R.id.button_equalizerc);
        button_equalizerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equalizerColor(bit_copy);
                iv.setImageBitmap(bit_copy);
            }
        });

    }

    private void tests(Bitmap bmp){
        int color;
        float hsvc[] = new float[3];
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            RGBtoHSV(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]), hsvc);
            color = HSVtoColor(hsvc);
            pixels[i] = color;
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }

    private void black_line(Bitmap bmp ){
        double j = bmp.getHeight()*0.5;
        for(double i =0; i<bmp.getWidth(); i++){
            bmp.setPixel((int) i, (int) j, Color.BLACK);
        }
        return;
    }          // NOT Displayed in application

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
    }               // NOT Displayed in application

    private void toGrayRS(Bitmap bmp){

        RenderScript rs = RenderScript.create(this);

        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());


        ScriptC_gray2 grayScript = new ScriptC_gray2(rs);


        grayScript.forEach_toGray2(input, output);

        output.copyTo(bmp);

        input.destroy(); output.destroy();

        grayScript.destroy(); rs.destroy();

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

    private void RGBtoHSV(int red,int green, int blue, float hsv[] ){
        double r = (double)red/255.0;
        double g = (double)green/255.0;
        double b = (double)blue/255.0;

        double max = Math.max(b,Math.max(r,g));
        double min = Math.min(b,Math.min(r,g));
        double delta = max - min;

        if(delta == 0){
            hsv[0] = 0;
        }else if(max == r){
            hsv[0] = (float) ((60* ((g-b)/delta) + 360)%360 );
        }else if(max == g){
            hsv[0] = (float) (60* ((b-r)/delta) + 120);
        }else if(max == b){
            hsv[0] = (float) (60* ((r-g)/delta) + 240);
        }

        if(max == 0){
            hsv[1] = 0;
        }else{
            hsv[1] = (float)(delta/max);
        }

        hsv[2] = (float)max;
    }      //WORKS

    private int HSVtoColor(float hsv[]){

        int ti = (int)(Math.abs(hsv[0]/60))%6;

        float f = hsv[0]/60 - ti;
        float l = hsv[2]*(1-hsv[1]);
        float m = hsv[2]*(1- f*hsv[1]);
        float n = hsv[2]*(1- (1-f)*hsv[1]);

        switch(ti){
            case 0:
                return Color.rgb((int)(255*hsv[2]), (int)(255*n), (int)(255*l));
            case 1:
                return Color.rgb((int)(255*m), (int)(255*hsv[2]), (int)(255*l));
            case 2:
                return Color.rgb((int)(255*l), (int)(255*hsv[2]), (int)(255*n));
            case 3:
                return Color.rgb((int)(255*l), (int)(255*m), (int)(255*hsv[2]));
            case 4:
                return Color.rgb((int)(255*n), (int)(255*l), (int)(255*hsv[2]));
            default:
                return Color.rgb((int)(255*hsv[2]), (int)(255*l), (int)(255*m));
        }

    }                                   //WORKS

    private void keepColorRandom(Bitmap bmp, int size_keeping){
        int color;
        float hue;
        double t = Math.random()*360;
        float[] hsvc = new float[3];
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            RGBtoHSV(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]), hsvc);
            hue = hsvc[0];
            boolean to_verify = hue <= t+size_keeping && hue >= t-size_keeping;
            if(t<size_keeping)
                to_verify = ((hue <= t+size_keeping && hue>= 0 ) || hue >= 360-(size_keeping-t));
            if(t>360-size_keeping)
                to_verify = ((hue >= t-size_keeping && hue <=360) || hue <= t-(360-t));
            if(to_verify){
                color = HSVtoColor(hsvc);
                pixels[i] = color;
            }else{
                int grey = (int) (Color.red(pixels[i]) * 0.3) + (int) (Color.green(pixels[i]) * 0.59) + (int) (Color.blue(pixels[i]) * 0.11);
                pixels[i] = Color.rgb(grey, grey, grey);
            }

        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }            //WORKS

    private void keepColor(Bitmap bmp, int size_to_keep_color, int color_to_keep){
        int color;
        float hue;
        float[] hsv_old = new float[3];
        RGBtoHSV(Color.red(color_to_keep),Color.green(color_to_keep),Color.blue(color_to_keep), hsv_old);
        double t = Math.random()*360;
        float[] hsv_new = new float[3];
        int pixels[] = new int[bmp.getHeight()*bmp.getWidth()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0; i<bmp.getHeight()*bmp.getWidth(); i++){
            RGBtoHSV(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]), hsv_new);
            hue = hsv_new[0];
            if(hue <= t+size_to_keep_color && hue >= t-size_to_keep_color){         //Case around 0 not managed
                color = HSVtoColor(hsv_new);
                pixels[i] = color;
            }else{
                int grey = (int) (Color.red(pixels[i]) * 0.3) + (int) (Color.green(pixels[i]) * 0.59) + (int) (Color.blue(pixels[i]) * 0.11);
                pixels[i] = Color.rgb(grey, grey, grey);
            }

        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return;
    }

    private void extendDyn(Bitmap bmp){
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
    }                                   //Seems to work

    private void extendDynColor(Bitmap bmp){                                   //Passer en hsv puis histo sur h puis traitement puis reconversion en rgb pour affichage
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
    }                              //Seems to work

    private void contractDyn(Bitmap bmp){
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
    }                                 //NOT WORKING

    private void equalizerGray(Bitmap bmp){
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
    }                               //Works maybe

    private void equalizerColor(Bitmap bmp){
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
    }                              //Works maybe

    private void convolution(Bitmap bmp){

    }                                    //TO DO

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
