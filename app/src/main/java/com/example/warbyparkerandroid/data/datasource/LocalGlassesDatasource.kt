package com.example.warbyparkerandroid.data.datasource

import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses

val AllGlasses = listOf(
    Glasses(
        2L,
        "Simon",
        description = "With round lenses and a keyhole bridge, Simon is a bookish modern classic.",
        arrayListOf(
            GlassStyle(
                1L,
                "Brushed Ink with Polished Gold",
                R.drawable.simon_rye_skin,
                R.drawable.simon,
                95.00f,
                false
            ),
            GlassStyle(
                2L,
                "Brushed Ink",
                R.drawable.simon_black_skin,
                R.drawable.simon_black,
                95.00f,
                false
            ),
            GlassStyle(
                3L,
                "Polished Gold ",
                R.drawable.simon_gold_skin,
                R.drawable.simon_gold,
                95.00f,
                false
            ),
            GlassStyle(
                4L,
                "Antique Silver",
                R.drawable.simon_silver_skin,
                R.drawable.simon_silver,
                95.00f,
                false
            ),
        ),
        isStackPick = true,
        imageIds = listOf(R.drawable.simon_1, R.drawable.simon_2, R.drawable.simon_3),
        descriptionImg = R.drawable.simon_person,
        virtualTryOnImg = R.drawable.virtual_try,
        multiWidthImg = R.drawable.simon_width,
        perscriptionImg = R.drawable.simon_pers,
        whatsIncludedImg = R.drawable.whats_included
    ),
    Glasses(
        1L,
        "Percey",
        description = "With round lenses and a keyhole bridge, Percey is a bookish modern classic.",
        arrayListOf(
            GlassStyle(
                1L,
                "Striped Sassafras",
                R.drawable.percy_striped_sassafras,
                R.drawable.percy,
                95.00f,
                false
            ),
            GlassStyle(
                2L,
                "Seaweed Crystal with Amber Tortoise\n",
                R.drawable.seaweed_crystal_amber_skin,
                R.drawable.seaweed_crystal_amber,
                95.00f,
                false
            ),
            GlassStyle(
                3L,
                "Rye Tortoise",
                R.drawable.rye_tortoise_skin,
                R.drawable.rye_tortoise,
                95.00f,
                false
            ),
            GlassStyle(
                4L,
                "Chestnut Crystal",
                R.drawable.chestnut_crystal_skin,
                R.drawable.chestnut_crystal,
                95.00f,
                false
            ),
            GlassStyle(
                5L,
                "Crystal with Oak Barrel and Blue Bay\n",
                R.drawable.crystal_oak_barrel_blue_bay_skin,
                R.drawable.crystal_oak_barrel_blue_bay,
                95.00f,
                false
            ),
            GlassStyle(
                56L,
                "Tidal Blue",
                R.drawable.tidal_blue_skin,
                R.drawable.tidal_blue,
                95.00f,
                false
            ),
            GlassStyle(
                7L,
                "Jet Black Matte",
                R.drawable.jet_black_matte_skin,
                R.drawable.jetblack_matte,
                95.00f,
                false
            ),
            GlassStyle(
                8L,
                "Crystal",
                R.drawable.crystal_skin,
                R.drawable.crystal,
                95.00f,
                false
            )
        ),
        isStackPick = true,
        imageIds = listOf(R.drawable.percy_1, R.drawable.percy_2, R.drawable.percy_3),
        descriptionImg = R.drawable.percey_person,
        virtualTryOnImg = R.drawable.virtual_try,
        multiWidthImg = R.drawable.multi_width,
        perscriptionImg = R.drawable.perscription,
        whatsIncludedImg = R.drawable.whats_included
    ),
    Glasses(
        3L,
        "Raider",
        description = "With round lenses and a keyhole bridge, Raider is a bookish modern classic.",
        arrayListOf(
            GlassStyle(
                1L,
                "Polished Gold",
                R.drawable.raider_gold_skin,
                R.drawable.raider_gold,
                95.00f,
                false
            ),
            GlassStyle(
                2L,
                "Polished Silver",
                R.drawable.raider_silver_skin,
                R.drawable.raider_silver,
                95.00f,
                false
            ),
        ),
        isStackPick = true,
        imageIds = listOf(R.drawable.raider_1, R.drawable.raider_2, R.drawable.raider_3),
        descriptionImg = R.drawable.simon_person,
        virtualTryOnImg = R.drawable.virtual_try,
        multiWidthImg = R.drawable.raider_width,
        perscriptionImg = R.drawable.raider_pers,
        whatsIncludedImg = R.drawable.whats_included),
    Glasses(
        4L,
        "Amari",
        description = "With round lenses and a keyhole bridge, Percey is a bookish modern classic.",
        arrayListOf(
            GlassStyle(
                1L,
                "Daybreak Matte Fade",
                R.drawable.amari_white_skin,
                R.drawable.amari_silver,
                95.00f,
                false
            ),
            GlassStyle(
                2L,
                "Arabica Matte",
                R.drawable.amari_brown_skin,
                R.drawable.amari_brown,
                95.00f,
                false
            ),
        ),
        isStackPick = true,
        imageIds = listOf(R.drawable.amari_1, R.drawable.amari_2, R.drawable.amari_3),
        descriptionImg = R.drawable.amari_person,
        virtualTryOnImg = R.drawable.virtual_try,
        multiWidthImg = R.drawable.amari_silver,
        perscriptionImg = R.drawable.amari_pers,
        whatsIncludedImg = R.drawable.whats_included),
)