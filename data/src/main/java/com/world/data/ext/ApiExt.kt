package com.world.data.ext



import android.content.Context
import android.util.Log
import com.world.domain.common.ApiError
import com.world.domain.common.ApiErrorException
import com.world.domain.common.UnauthorizedException
import com.world.domain.common.UnknownException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.*
import java.net.UnknownHostException

const val MULTIPART = "multipart/form-data"
const val API_ERROR = "api_error"

private val json = Json {
    ignoreUnknownKeys = true
}

fun Throwable.mapToErrors(): Throwable {
    when (this) {
        is HttpException -> {
            when(this.code()) {
                401, 403 -> return UnauthorizedException()
                else -> {
                    return try {
                        val response = json.decodeFromString(
                            ApiErrorResponse.serializer(),
                            response()?.errorBody()?.string() ?: ""
                        )

                        ApiErrorException(
                            apiError = ApiError(
                                fieldsError = response.errorDescription,
                                detail = response.detail ?: response.error
                            )
                        )
                    } catch (e: Exception) {
                        Log.e("parse error", "mapToErrors: ", e)
                        UnknownException()
                    }
                }
            }
        }
        is NullPointerException -> {
            return NullPointerException()
        }
        is UnknownHostException -> {
            return UnknownHostException()
        }
        else -> return this
    }
}

suspend fun <T : Any> handleRequest(requestFunc: suspend () -> T): T {
    return try {
        requestFunc.invoke()
    } catch (e: Exception) {
        throw e.mapToErrors()
    }
}

fun ResponseBody.writeResponseBodyToDisk(
    context: Context,
    fileName: String
): String? {
    return try {
        val futureStudioIconFile =
            File(context.getExternalFilesDir(null)?.absolutePath + File.separator.toString() + fileName)
//            File(Environment.getExternalStorageDirectory().toString() + "/Download/" + "Contract.pdf")
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            val fileReader = ByteArray(4096)
            val fileSize: Long = this.contentLength()
            var fileSizeDownloaded: Long = 0
            inputStream = this.byteStream()
            outputStream = FileOutputStream(futureStudioIconFile)
            while (true) {
                val read: Int = inputStream.read(fileReader)
                if (read == -1) {
                    break
                }
                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded += read.toLong()
                Log.d("TAG", "file download: $fileSizeDownloaded of $fileSize")
            }
            outputStream.flush()
            futureStudioIconFile.absolutePath
        } catch (e: IOException) {
            null
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    } catch (e: IOException) {
        null
    }
}

@Serializable
open class ApiErrorResponse(
    @SerialName("code")
    val code: Int? = null,

    @SerialName("message")
    val errorDescription: HashMap<String, List<String>>? = null,

    val detail: String? = null,
    val error: String? = null,
)