package com.example.renthubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

public class SliderEffects extends AppCompatActivity implements
                            BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    SliderLayout sliderLayout;
    HashMap<String,String> Hash_file_maps ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_effects);
        Hash_file_maps = new HashMap<String, String>();
        sliderLayout = (SliderLayout)findViewById(R.id.slider);
        Hash_file_maps.put("Slider1", "http://colorsfx.com/android/files/splash1.png");
        Hash_file_maps.put("Slider2", "http://colorsfx.com/android/files/splash2.png");
        Hash_file_maps.put("Slider3", "http://colorsfx.com/android/files/splash3.png");
        Hash_file_maps.put("Slider4", "http://colorsfx.com/android/files/splash4.png");
        for(String name : Hash_file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(SliderEffects.this);
            textSliderView .description(name) .image(Hash_file_maps.get(name)) .setScaleType(BaseSliderView.ScaleType.Fit) .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle() .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000); sliderLayout.addOnPageChangeListener(this);
    }
    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }
    @Override public void onPageScrollStateChanged(int state) {

    }

}
