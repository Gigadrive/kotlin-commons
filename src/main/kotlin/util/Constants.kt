package com.gigadrivegroup.kotlincommons.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

public const val ALPHANUMERIC_CHARACTERS_UPPERCASE: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

public const val ALPHANUMERIC_CHARACTERS_LOWERCASE: String = "abcdefghijklmnopqrstuvwxyz"

public const val ALPHANUMERIC_CHARACTERS_NUMBERS: String = "0123456789"

public val GSON: Gson = Gson()

public val GSON_PRETTY: Gson = GsonBuilder().setPrettyPrinting().create()

public val ALPHANUMERIC_CHARACTERS: String =
    ALPHANUMERIC_CHARACTERS_UPPERCASE +
        ALPHANUMERIC_CHARACTERS_LOWERCASE +
        ALPHANUMERIC_CHARACTERS_NUMBERS
