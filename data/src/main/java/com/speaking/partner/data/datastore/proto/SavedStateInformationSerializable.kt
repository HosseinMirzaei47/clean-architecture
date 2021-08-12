package com.speaking.partner.data.datastore.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.speaking.partner.model.models.SavedStateInformation
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object SavedStateInformationSerializable : Serializer<SavedStateInformation> {
    override val defaultValue: SavedStateInformation
        get() = SavedStateInformation.newBuilder().build()

    override suspend fun readFrom(input: InputStream): SavedStateInformation {
        try {
            return SavedStateInformation.parseFrom(input)
        } catch (ex: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", ex)
        } catch (ex: IOException) {
            throw CorruptionException("Cannot read proto.", ex)
        }
    }

    override suspend fun writeTo(t: SavedStateInformation, output: OutputStream) {
        try {
            t.writeTo(output)
        } catch (ex: IOException) {
            throw CorruptionException("Cannot read proto.", ex)
        } catch (ex: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", ex)
        }
    }

}