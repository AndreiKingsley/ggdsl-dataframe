package com.andreikingsley.ggdsl.dataframe

import com.andreikingsley.ggdsl.dsl.*
import com.andreikingsley.ggdsl.ir.NamedData
import com.andreikingsley.ggdsl.ir.aes.MappableNonPositionalAes
import com.andreikingsley.ggdsl.ir.aes.PositionalAes
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.toMap
import org.jetbrains.kotlinx.dataframe.name

class A()
fun A.a(){}
class B()
fun B.b(){}

context(A, B) fun myFunc(){}

fun <T> DataColumn<T>.toDataSource(): DataSource<T> {
    return DataSource<T>(name)
}

context(BaseContext) inline infix fun <reified DomainType : Any>
        PositionalAes.mapTo(column: DataColumn<DomainType>):
        PositionalMapping<DomainType> {
    return mapTo(column.toDataSource())
}

context(BaseContext) inline infix fun <reified DomainType : Any, reified RangeType : Any>
        MappableNonPositionalAes<RangeType>.mapTo(column: DataColumn<DomainType>):
        NonPositionalMapping<DomainType, RangeType> {
    return mapTo(column.toDataSource())
}

fun DataFrame<*>.toNamedData(): NamedData {
    return toMap().map { it.key to it.value.map { it!! /*TODO*/ }.toTypedArray() }.toMap()
}

fun <T> DataFrame<T>.plot(block: context(DataFrame<T>, PlotContext).() -> Unit) {
    val plotContext = PlotContext().apply {
        dataset = toNamedData()
    }
    block(this, plotContext)
}
/*
fun main(args: Array<String>) {
    // create columns
    val movieName by columnOf("Intouchables", "Trainspotting", "In Bruges", "Leon", "Snatch")
    val imdbRating by columnOf(8.5, 8.1, 7.9, 8.7, 8.2)
    val country by columnOf("France", "UK", "UK", "France", "UK")

// create dataframe
    val df = dataFrameOf(movieName, imdbRating, country)

    println(df.com.andreikingsley.ggdsl.dataframe.toNamedData()["movieName"]!!.toList())
}

 */
