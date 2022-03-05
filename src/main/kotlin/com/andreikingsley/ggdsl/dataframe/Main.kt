package com.andreikingsley.ggdsl.dataframe

import com.andreikingsley.ggdsl.dsl.*
import com.andreikingsley.ggdsl.echarts.toJSON
import com.andreikingsley.ggdsl.echarts.toOption
import com.andreikingsley.ggdsl.ir.NamedData
import com.andreikingsley.ggdsl.ir.Plot
import com.andreikingsley.ggdsl.ir.aes.MappableNonPositionalAes
import com.andreikingsley.ggdsl.ir.aes.PositionalAes
import com.andreikingsley.ggdsl.ir.aes.X
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.columns.ColumnAccessor
import org.jetbrains.kotlinx.dataframe.columns.ColumnReference
import org.jetbrains.kotlinx.dataframe.name

fun <T> ColumnReference<T>.toDataSource(): DataSource<T> {
    return DataSource(name())
}

context(BaseContext) inline infix fun <reified DomainType : Any>
        PositionalAes.mapTo(column: ColumnReference<DomainType>):
        PositionalMapping<DomainType> {
    return mapTo(column.toDataSource())
}

context(BaseContext) inline infix fun <reified DomainType : Any, reified RangeType : Any>
        MappableNonPositionalAes<RangeType>.mapTo(column: ColumnReference<DomainType>):
        NonPositionalMapping<DomainType, RangeType> {
    return mapTo(column.toDataSource())
}

fun DataFrame<*>.toNamedData(): NamedData {
    return toMap().map { it.key to it.value.map { it!! /*TODO*/ }.toTypedArray() }.toMap()
}

fun <T> DataFrame<T>.plot(block: context(DataFrame<T>, PlotContext).() -> Unit): Plot {
    val plotContext = PlotContext().apply {
        dataset = toNamedData()
    }
    block(this, plotContext)
    return plotContext.toPlot()
}

class A



fun main() {


    val movieName by columnOf("Intouchables", "Trainspotting", "In Bruges", "Leon", "Snatch")
    val imdbRating by columnOf(8.5, 8.1, 7.9, 8.7, 8.2)
    val country by columnOf("France", "UK", "UK", "France", "UK")

    val df = dataFrameOf(movieName, imdbRating, country)

    val movieNameRef by column<String>("movieName")


    df.plot {
        x mapTo movieNameRef
        y mapTo imdbRating

        bars {
            color mapTo country

            width setTo 1.5
        }

        layout {
            title = "European movies"
        }
    }


}




