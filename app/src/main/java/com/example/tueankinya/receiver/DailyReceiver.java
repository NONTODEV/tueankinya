package com.example.tueankinya.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tueankinya.dao.DrugDatabaseHelper;
import com.example.tueankinya.dao.TakingMedicineDatabaseHelper;
import com.example.tueankinya.model.DrugTime;
import com.example.tueankinya.model.TakingMedicine;

import java.util.List;

public class DailyReceiver extends BroadcastReceiver {
    private DrugDatabaseHelper dbHelper;
    private TakingMedicineDatabaseHelper dbHelperTakingMedicine;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            dbHelper = new DrugDatabaseHelper(context);
            dbHelperTakingMedicine = new TakingMedicineDatabaseHelper(context);

            saveDrugDataToTakingMedicine();
            dbHelper.setAllToDefault();
        }
    }

    private void saveDrugDataToTakingMedicine() {
        List<DrugTime> drugTimeList = dbHelper.getTakingMedicineByTime();

        for (DrugTime drugTime : drugTimeList) {
            TakingMedicine takingMedicine = new TakingMedicine(
                    drugTime.getDrugName(),
                    drugTime.getTimeEat(),
                    drugTime.getEatActive(),
                    ""
            );

            dbHelperTakingMedicine.insertTakingMedicine(takingMedicine);
        }
    }
}