package com.example.network.model

import com.example.network.R

enum class TypeInfo (val originalName: String, val koreanName: String, val imageRes: Int, val color: Long){
    Normal("normal", "노말", R.drawable.img_normal, 0xFF919AA2),
    Fire("fire", "불꽃", R.drawable.img_fire, 0xFFFF9741),
    Water("water", "물", R.drawable.img_water, 0xFF3692DC),
    Electric("electric", "전기", R.drawable.img_electric, 0xFFFBD100),
    Grass("grass", "풀", R.drawable.img_grass, 0xFF38BF4B),
    Ice("ice", "얼음", R.drawable.img_ice, 0xFF4CD1C0),
    Fighting("fighting", "격투", R.drawable.img_fighting, 0xFFE0306A),
    Poison("poison", "독", R.drawable.img_poison, 0xFFB567CE),
    Ground("ground", "땅", R.drawable.img_ground, 0xFFE87236),
    Flying("flying", "비행", R.drawable.img_flying, 0xFF89AAE3),
    Psychic("psychic", "에스퍼", R.drawable.img_psychic, 0xFFFF6675),
    Bug("bug", "벌레", R.drawable.img_bug, 0xFF83C300),
    Rock("rock", "바위", R.drawable.img_rock, 0xFFC8B686),
    Ghost("ghost", "고스트", R.drawable.img_ghost, 0xFF4C6AB2),
    Dragon("dragon", "드래곤", R.drawable.img_dragon, 0xFF006FC9),
    Dark("dark", "악", R.drawable.img_dark, 0xFF5B5466),
    Steel("steel", "강철", R.drawable.img_steel, 0xFF5A8EA2),
    Fairy("fairy", "페어리", R.drawable.img_fairy, 0xFFFB89EB),
    Unknown("unknown", "???", R.drawable.img_monsterbal, 0xFF000000)
}

fun getTypeKoreaName(originalName: String) =
    TypeInfo.values().find { it.originalName == originalName }?.koreanName ?: TypeInfo.Unknown.name

fun getTypeInfo(koreanName: String) =
    TypeInfo.values().find { it.koreanName == koreanName } ?: TypeInfo.Unknown

fun getTypeImage(koreanName: String) =
    TypeInfo.values().find { it.koreanName == koreanName }?.imageRes ?: TypeInfo.Unknown.imageRes

fun getTypeColor(koreanName: String) =
    TypeInfo.values().find { it.koreanName == koreanName }?.color ?: TypeInfo.Unknown.color

fun getTypeColorList(typeList: List<String>): List<Long> {
    return if (typeList.isEmpty()) {
        listOf(TypeInfo.Unknown.color, TypeInfo.Unknown.color)
    } else if (typeList.size == 1) {
        listOf(getTypeColor(typeList[0]), getTypeColor(typeList[0]))
    } else {
        listOf(getTypeColor(typeList[0]), getTypeColor(typeList[1]))
    }
}

fun getWeaknessInfo(type: String): List<Float> {
    return when(type) {
        TypeInfo.Normal.koreanName -> {
            listOf(
                1f, 1f, 1f, 1f, 1f, 1f,
                2f, 1f, 1f, 1f, 1f, 1f,
                1f, 0f, 1f, 1f, 1f, 1f,
            )
        }
        TypeInfo.Fire.koreanName -> {
            listOf(
                1f, 0.5f, 2f, 1f, 0.5f, 0.5f,
                1f, 1f, 2f, 1f, 1f, 0.5f,
                2f, 1f, 1f, 1f, 0.5f, 0.5f,
            )
        }
        TypeInfo.Water.koreanName -> {
            listOf(
                1f, 0.5f, 0.5f, 2f, 2f, 0.5f,
                1f, 1f, 1f, 1f, 1f, 1f,
                1f, 1f, 1f, 1f, 0.5f, 1f,
            )
        }
        TypeInfo.Electric.koreanName -> {
            listOf(
                1f, 1f, 1f, 0.5f, 1f, 1f,
                1f, 1f, 2f, 0.5f, 1f, 1f,
                1f, 1f, 1f, 1f, 0.5f, 1f,
            )
        }
        TypeInfo.Grass.koreanName -> {
            listOf(
                1f, 2f, 0.5f, 0.5f, 0.5f, 2f,
                1f, 2f, 0.5f, 2f, 1f, 2f,
                1f, 1f, 1f, 1f, 1f, 1f,
            )
        }
        TypeInfo.Ice.koreanName -> {
            listOf(
                1f, 2f, 1f, 1f, 1f, 0.5f,
                2f, 1f, 1f, 1f, 1f, 1f,
                2f, 1f, 1f, 1f, 2f, 1f,
            )
        }
        TypeInfo.Fighting.koreanName -> {
            listOf(
                1f, 1f, 1f, 1f, 1f, 1f,
                1f, 1f, 1f, 2f, 2f, 0.5f,
                0.5f, 1f, 1f, 0.5f, 1f, 2f,
            )
        }
        TypeInfo.Poison.koreanName -> {
            listOf(
                1f, 1f, 1f, 1f, 0.5f, 1f,
                0.5f, 0.5f, 2f, 1f, 2f, 0.5f,
                1f, 1f, 1f, 1f, 1f, 0.5f,
            )
        }
        TypeInfo.Ground.koreanName -> {
            listOf(
                1f, 1f, 2f, 0.5f, 2f, 2f,
                1f, 0.5f, 1f, 1f, 1f, 1f,
                0.5f, 1f, 1f, 1f, 1f, 1f,
            )
        }
        TypeInfo.Flying.koreanName -> {
            listOf(
                1f, 1f, 1f, 2f, 0.5f, 2f,
                0.5f, 1f, 0f, 1f, 1f, 0.5f,
                2f, 1f, 1f, 1f, 1f, 1f,
            )
        }
        TypeInfo.Psychic.koreanName -> {
            listOf(
                1f, 1f, 1f, 1f, 1f, 1f,
                0.5f, 1f, 1f, 1f, 0.5f, 2f,
                1f, 2f, 1f, 2f, 1f, 1f,
            )
        }
        TypeInfo.Bug.koreanName -> {
            listOf(
                1f, 2f, 1f, 1f, 0.5f, 1f,
                0.5f, 1f, 0.5f, 2f, 1f, 1f,
                2f, 1f, 1f, 1f, 1f, 1f,
            )
        }
        TypeInfo.Rock.koreanName -> {
            listOf(
                0.5f, 0.5f, 2f, 1f, 2f, 1f,
                2f, 0.5f, 2f, 0.5f, 1f, 1f,
                1f, 1f, 1f, 1f, 2f, 1f,
            )
        }
        TypeInfo.Ghost.koreanName -> {
            listOf(
                0f, 1f, 1f, 1f, 1f, 1f,
                0f, 0.5f, 1f, 1f, 1f, 0.5f,
                1f, 2f, 1f, 2f, 1f, 1f,
            )
        }
        TypeInfo.Dragon.koreanName -> {
            listOf(
                1f, 0.5f, 0.5f, 0.5f, 0.5f, 2f,
                1f, 1f, 1f, 1f, 1f, 1f,
                1f, 1f, 2f, 1f, 1f, 2f,
            )
        }
        TypeInfo.Dark.koreanName -> {
            listOf(
                1f, 1f, 1f, 1f, 1f, 1f,
                2f, 1f, 1f, 1f, 0.5f, 2f,
                1f, 0.5f, 1f, 0.5f, 1f, 2f,
            )
        }
        TypeInfo.Steel.koreanName -> {
            listOf(
                0.5f, 2f, 1f, 1f, 0.5f, 0.5f,
                2f, 0.5f, 2f, 0.5f, 0.5f, 0.5f,
                0.5f, 1f, 0.5f, 1f, 0.5f, 0.5f,
            )
        }
        TypeInfo.Fairy.koreanName -> {
            listOf(
                1f, 1f, 1f, 1f, 1f, 1f,
                0.5f, 2f, 1f, 1f, 1f, 0.5f,
                1f, 1f, 0f, 0.5f, 2f, 1f
            )
        }
        else -> {
            listOf(
                0f, 0f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f
            )
        }
    }
}