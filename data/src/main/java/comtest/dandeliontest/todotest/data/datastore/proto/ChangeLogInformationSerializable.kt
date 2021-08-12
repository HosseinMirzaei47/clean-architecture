package comtest.dandeliontest.todotest.data.datastore.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object ChangeLogInformationSerializable : Serializer<ChangeLogInformation> {

    override val defaultValue: ChangeLogInformation
        get() = ChangeLogInformation.newBuilder()
            .build()

    override suspend fun readFrom(input: InputStream): ChangeLogInformation {
        try {
            return ChangeLogInformation.parseFrom(input)
        } catch (ex: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", ex)
        } catch (ex: IOException) {
            throw CorruptionException("Cannot read proto.", ex)
        }
    }

    override suspend fun writeTo(t: ChangeLogInformation, output: OutputStream) {
        try {
            t.writeTo(output)
        } catch (ex: IOException) {
            throw CorruptionException("Cannot read proto.", ex)
        } catch (ex: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", ex)
        }
    }
}

