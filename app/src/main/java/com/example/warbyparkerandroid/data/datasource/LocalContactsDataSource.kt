package com.example.warbyparkerandroid.data.datasource

import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.Contacts

val AllContacts = listOf<Contacts>(
    Contacts(
        image = R.drawable.scount,
        name = "Scout",
        pack = "90 pack"
    ),
    Contacts(
        image = R.drawable.acuve_dailies,
        name = "Acuve Oasys 1-Day",
        pack = "90 pack"
    ),
    Contacts(
        image = R.drawable.aosys,
        name = "Acuvue Oasys",
        pack = "12 pack"
    ),
    Contacts(
        image = R.drawable.acuvepack,
        name = "1-Day Acuvue Moist",
        "90 pack"
    ),
    Contacts(
        R.drawable.oasisastig,
        name ="Acuvue Oasys for Astigmatism",
        "6 pack"
    ),
    Contacts(
        R.drawable.bioinfinitypack,
        "Biofinity Toric",
        "6 pack"
    ),
    Contacts(
        R.drawable.dailiespack,
        "Dailies Total",
        "90 pack"
    )
)