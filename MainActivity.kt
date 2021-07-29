package com.example.sehat
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sehat.GeneralHelper
import com.example.sehat.R
import com.example.sehat.StepDetectorService
import com.example.sehat.databinding.ActivityMainBinding
import com.example.sehat.stepsCallback

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, StepDetectorService::class.java)
        startService(intent)

        StepDetectorService.subscribe.register(this)
    }

       fun subscribeSteps(steps: Int) {
        binding.Steps.setText(steps.toString())
        binding.caloriesBurnt.setText(GeneralHelper.getCalories(steps))
        binding.distanceTravelled.setText(GeneralHelper.getDistanceCovered(steps))
    }
}