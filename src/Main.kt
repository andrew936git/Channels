import kotlinx.coroutines.channels.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.system.measureTimeMillis

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
suspend fun main() = coroutineScope {
    val time = measureTimeMillis {
        val stringResult = getList()
        //stringResult.consumeEach { print("$it ") }
        val stringResult2 = modifiedList(stringResult)
        val resultStringList: List<String> = stringResult2.toList()
        resultStringList.forEach{print("\"$it\" ")}
    }
    println()
    println("Затрачено времени $time")



}
fun getStringList(text: String): List<String>{
    val list = text
        .trim()
        .splitToSequence(' ')
        .filter { it.isNotEmpty() }
        .toList()
    return list
}

suspend fun CoroutineScope.getList(): ReceiveChannel<String> = produce{
    val list = getStringList(Storage().text)
    for (i in list){
        delay(10L)
        send(i)
    }
}

suspend fun CoroutineScope.modifiedList(channel: ReceiveChannel<String>): ReceiveChannel<String> = produce{
    channel.consumeEach { send(it.first().uppercase()) }
}