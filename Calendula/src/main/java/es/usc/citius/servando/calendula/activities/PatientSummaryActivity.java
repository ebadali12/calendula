/*
 *    Calendula - An assistant for personal medication management.
 *    Copyright (C) 2016 CITIUS - USC
 *
 *    Calendula is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this software.  If not, see <http://www.gnu.org/licenses>.
 */

package es.usc.citius.servando.calendula.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.usc.citius.servando.calendula.CalendulaActivity;
import es.usc.citius.servando.calendula.R;
import es.usc.citius.servando.calendula.database.DB;
import es.usc.citius.servando.calendula.persistence.Patient;
import es.usc.citius.servando.calendula.persistence.Schedule;
import es.usc.citius.servando.calendula.util.AvatarMgr;
import es.usc.citius.servando.calendula.util.Strings;

public class PatientSummaryActivity extends CalendulaActivity {


    @BindView(R.id.top)
    View top;
    @BindView(R.id.patient_avatar)
    ImageView patientAvatar;
    @BindView(R.id.patient_avatar_bg)
    View patientAvatarBackground;
    @BindView(R.id.patient_name)
    TextView patientName;
    @BindView(R.id.current_medicines_list)
    TextView currentMedicinesList;

    private Patient patient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_summary);
        ButterKnife.bind(this);
        patient = DB.patients().getActive(this);
        loadPatient();
    }

    private void loadPatient() {
        patientName.setText(patient.name());
        top.setBackgroundColor(patient.color());
        patientAvatar.setImageResource(AvatarMgr.res(patient.avatar()));

        //get current medicines
        final List<Schedule> schedules = DB.schedules().findAll(patient);
        final Set<String> meds = new HashSet<>();
        for (Schedule schedule : schedules) {
            meds.add(schedule.medicine().name());
        }
        if (meds.isEmpty()) {
            currentMedicinesList.setText(getString(R.string.current_medicines_no_medicines));
        } else {
            currentMedicinesList.setText(Strings.genBulletList(meds));
        }

        setupToolbar(patient.name(), Color.TRANSPARENT);
        setupStatusBar(Color.TRANSPARENT);

    }

}
