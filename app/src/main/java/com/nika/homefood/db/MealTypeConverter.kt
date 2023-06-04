package com.nika.homefood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        return attribute?.toString() ?: ""
    }


        @TypeConverter
    fun fromStringToAny(atribute: String?):Any{

        return atribute ?: ""
    }
}