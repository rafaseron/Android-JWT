package br.com.dio.picpayclone.data

import android.os.Parcel
import android.os.Parcelable

//@Parcelize
//data class Usuario(
//    val login: String = "",
//    val senha: String = "",
//    val email: String = "",
//    val nomeCompleto: String = "",
//    val cpf: String = "",
//    val dataNascimento: String = "",
//    val numeroTelefone: String = "",
//    var saldo: Double = 0.0
//) : Parcelable

data class Usuario(
    val login: String = "",
    val senha: String = "",
    val email: String = "",
    val nomeCompleto: String = "",
    val cpf: String = "",
    val dataNascimento: String = "",
    val numeroTelefone: String = "",
    var saldo: Double = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeString(senha)
        parcel.writeString(email)
        parcel.writeString(nomeCompleto)
        parcel.writeString(cpf)
        parcel.writeString(dataNascimento)
        parcel.writeString(numeroTelefone)
        parcel.writeDouble(saldo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }
}