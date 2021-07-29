package com.example.sehat

import android.app.Activity
import android.app.Service.START_STICKY
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import kotlin.math.roundToInt

class StepDetectorService : Service(), SensorEventListener {

        companion object {
            lateinit var callback: stepsCallback
        }

        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

            val sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val countSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

            if(countSensor != null){

                sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL)

                GeneralHelper.updateNotification(this, this, PrefsHelper.getInt("FSteps"))
                callback.subscribeSteps(PrefsHelper.getInt("FSteps"))

            }else{
                return 0
            }

            return START_STICKY
        }

    private fun getSystemService(sensorService: String) {

    }


     fun onBind(p0: Intent?): IBinder? {
            TODO("Not yet implemented")
        }

        override fun onSensorChanged(p0: SensorEvent?) {
            if (PrefsHelper.getString("TodayDate") != GeneralHelper.getToadyDate()) {
                PrefsHelper.putInt("Steps", p0!!.values[0].roundToInt())
                PrefsHelper.putString("TodayDate", GeneralHelper.getToadyDate())
            } else {
                val storeSteps = PrefsHelper.getInt("Steps")
                val sensorSteps = p0!!.values[0].roundToInt()
                val finalSteps = sensorSteps - storeSteps
                if (finalSteps > 0) {
                    PrefsHelper.putInt("FSteps", finalSteps)
                    GeneralHelper.updateNotification(this, this, finalSteps)
                    callback?.subscribeSteps(finalSteps)
                }
            }

        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            Log.d("SERVICE", p0.toString())
        }

        object subscribe {
            fun register(activity: Activity) {
                callback = activity as stepsCallback
            }
        }

    }
